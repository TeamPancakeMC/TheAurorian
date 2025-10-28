package cn.teampancake.theaurorian.common.level.data.sky_color;

import cn.teampancake.theaurorian.common.network.NightTypeS2CPacket;
import cn.teampancake.theaurorian.common.network.SkyColorS2CPacket;
import cn.teampancake.theaurorian.common.network.UpdateCurrentShieldS2CPacket;
import cn.teampancake.theaurorian.common.registry.*;
import cn.teampancake.theaurorian.common.shields.ShieldStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;

public class SkyColorManager {

    private static final AttachmentType<Boolean> REMOVE_BLESS = TAAttachmentTypes.REMOVE_BLESS_UNTIL_NEXT_BLOOD_MOON.get();
    private static final AttachmentType<Boolean> IMMUNE_PRESSURE_TEMP = TAAttachmentTypes.IMMUNE_PRESSURE_UNTIL_NEXT_BLOOD_MOON.get();
    private static final AttachmentType<Boolean> IMMUNE_PRESSURE_PERSISTENT = TAAttachmentTypes.IMMUNE_PRESSURE_BY_KILL_MOON_QUEEN.get();
    private static final List<ResourceLocation> SKY_COLORS = TASkyColors.REGISTRY.keySet().stream().toList();

    public static class SkyColorForecast {

        public final ResourceLocation today;
        public final List<ResourceLocation> futureColors;
        public final int forecastDays;

        public SkyColorForecast(SkyColorData data) {
            this.today = data.currentDayColor;
            this.futureColors = data.futureColorIds;
            this.forecastDays = data.getForecastDays();
        }

    }

    public static ResourceLocation getRandomSkyColor() {
        return SKY_COLORS.get(RandomSource.create().nextInt(SKY_COLORS.size()));
    }

    public static SkyColorData getWorldSkyData(Level level) {
        if (level.isClientSide() || level instanceof ClientLevel) {
            return getClientWorldSkyData(level);
        } else if (level instanceof ServerLevel serverLevel) {
            return SkyColorDataStorage.get(serverLevel).getSkyData();
        } else {
            return new SkyColorData(getAvailableSkyColors().getFirst(), 3);
        }
    }

    public static SkyColorForecast getSkyColorForecast(Level level) {
        return new SkyColorForecast(getWorldSkyData(level));
    }

    public static void updateSkyColors(ServerLevel level) {
        long dayTime = level.getDayTime();
        SkyColorData skyData = getWorldSkyData(level);
        onSkyColorChanged(level, skyData.currentDayColor);
        if (isMidnight(dayTime) && dayTime != skyData.lastMidnightTime) {
            skyData.advanceDay();
            skyData.lastMidnightTime = dayTime;
            SkyColorDataStorage.get(level).setDirty();
            syncSkyColorToAllPlayers(level, skyData.currentDayColor);
        }
    }

    private static SkyColorData getClientWorldSkyData(Level level) {
        ClientSkyColorData.ClientSkyData clientData = ClientSkyColorData.getClientData(level, 3);
        SkyColorData tempData = new SkyColorData(clientData.currentDayColor, clientData.futureColors.size());
        tempData.futureColorIds = clientData.futureColors;
        tempData.lastMidnightTime = 0;
        return tempData;
    }

    private static boolean isMidnight(long dayTime) {
        return (dayTime % 24000) >= 18000 && (dayTime % 24000) < 18020;
    }

    public static int getCurrentSkyColor(Level level) {
        long dayTime = level.getDayTime() % 24000 + 12000;
        SkyColorData skyData = getWorldSkyData(level);
        var holder = TASkyColors.REGISTRY.getHolder(skyData.currentDayColor);
        int nightColor = level.getData(TAAttachmentTypes.NIGHT_SKY_COLOR);
        if (holder.isPresent()) {
            int currentColor = holder.get().value().color();
            if (dayTime < 12000) {
                return nightColor;
            } else if (dayTime < 18000) {
                float progress = (dayTime - 12000) / 6000.0F;
                return interpolateColor(nightColor, currentColor, progress);
            } else {
                float progress = (dayTime - 18000) / 6000.0F;
                return interpolateColor(currentColor, nightColor, progress);
            }
        } else {
            return nightColor;
        }
    }

    private static int interpolateColor(int color1, int color2, float progress) {
        progress = Mth.clamp(progress, 0.0F, 1.0F);
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;
        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;
        int r = (int) (r1 + (r2 - r1) * progress);
        int g = (int) (g1 + (g2 - g1) * progress);
        int b = (int) (b1 + (b2 - b1) * progress);
        return (r << 16) | (g << 8) | b;
    }

    private static void onSkyColorChanged(ServerLevel level, ResourceLocation newColor) {
        GameRules.Key<GameRules.BooleanValue> aurorianBless = TAGameRules.RULE_ENABLE_AURORIAN_BLESS;
        boolean enableAurorianBless = level.getGameRules().getBoolean(aurorianBless);
        MobEffectInstance instance = new MobEffectInstance(TAMobEffects.PRESSURE);
        instance.duration = 320;
        instance.visible = false;
        long dayTime = level.getDayTime() % 24000;
        for (ServerPlayer player : level.players()) {
            if (dayTime > 0 && dayTime <= 12000) {
                if (player.getData(TAAttachmentTypes.CURRENT_SHIELD) != ShieldStack.EMPTY) {
                    PacketDistributor.sendToPlayer(player, new UpdateCurrentShieldS2CPacket(ShieldStack.EMPTY));
                }

                if (dayTime % 200 == 0 && enableAurorianBless && !player.getData(REMOVE_BLESS)) {
                    Optional<Holder.Reference<BaseSkyColor>> holder = TASkyColors.REGISTRY.getHolder(newColor);
                    holder.ifPresent(reference -> reference.value().effect().accept(player));
                }
            } else if (dayTime > 12000) {
                if (player.getData(TAAttachmentTypes.CURRENT_SHIELD) == ShieldStack.EMPTY && !TAWorldEvents.BLOOD_MOON.get().isActive(level)) {
                    PacketDistributor.sendToPlayer(player, new UpdateCurrentShieldS2CPacket(new ShieldStack(TAShields.COMMON)));
                }

                if (dayTime % 200 == 0 && !player.getData(IMMUNE_PRESSURE_PERSISTENT) && !player.getData(IMMUNE_PRESSURE_TEMP)) {
                    player.addEffect(instance);
                }
            }
        }
    }

    public static void syncSkyColorToPlayer(ServerPlayer player, SkyColorData skyData) {
        List<ResourceLocation> futureColors = skyData.futureColorIds;
        List<ResourceLocation> futureColorIds = new ArrayList<>(futureColors);
        ResourceLocation currentDayColor = skyData.currentDayColor;
        SkyColorS2CPacket packet = new SkyColorS2CPacket(currentDayColor, futureColorIds);
        PacketDistributor.sendToPlayer(player, packet);
    }

    public static void syncSkyColorToAllPlayers(ServerLevel level, ResourceLocation colorId) {
        for (ServerPlayer player : level.players()) {
            PacketDistributor.sendToPlayer(player, new NightTypeS2CPacket(colorId));
            syncSkyColorToPlayer(player, getWorldSkyData(level));
        }
    }

    public static void setSkyColor(ServerLevel level, ResourceLocation colorId) {
        SkyColorData skyData = getWorldSkyData(level);
        skyData.currentDayColor = colorId;
        syncSkyColorToAllPlayers(level, colorId);
        for (ServerPlayer serverPlayer : level.players()) {
            String key = "commands.theaurorian.night_phase.set";
            String name = Component.translatable("night_phase.theaurorian." + colorId).getString();
            serverPlayer.sendSystemMessage(Component.translatable(key, name));
        }
    }

    public static List<ResourceLocation> getAvailableSkyColors() {
        return new ArrayList<>(SKY_COLORS);
    }

}