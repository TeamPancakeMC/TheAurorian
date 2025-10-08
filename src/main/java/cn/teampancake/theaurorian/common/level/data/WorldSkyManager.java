package cn.teampancake.theaurorian.common.level.data;

import cn.teampancake.theaurorian.common.event.subscriber.LevelEventSubscriber;
import cn.teampancake.theaurorian.common.network.NightTypeS2CPacket;
import cn.teampancake.theaurorian.common.network.WorldDayColorS2CPacket;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TAGameRules;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.network.chat.Component;
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

public class WorldSkyManager {

    private static final AttachmentType<Boolean> REMOVE_BLESS = TAAttachmentTypes.REMOVE_BLESS_UNTIL_NEXT_BLOOD_MOON.get();
    private static final AttachmentType<Boolean> IMMUNE_PRESSURE_TEMP = TAAttachmentTypes.IMMUNE_PRESSURE_UNTIL_NEXT_BLOOD_MOON.get();
    private static final AttachmentType<Boolean> IMMUNE_PRESSURE_PERSISTENT = TAAttachmentTypes.IMMUNE_PRESSURE_BY_KILL_MOON_QUEEN.get();

    private static final List<SkyColor> SKY_COLORS = Arrays.asList(
            new SkyColor(0, 0x8d60d7),
            new SkyColor(1, 0xf49cae),
            new SkyColor(2, 0x80e3ec),
            new SkyColor(3, 0xfff089),
            new SkyColor(4, 0x69c941));

    public record SkyColor(int id, int color) { }

    public static class SkyColorForecast {

        public final SkyColor today;
        public final List<SkyColor> futureColors;
        public final int forecastDays;

        public SkyColorForecast(WorldSkyData data) {
            this.today = data.currentDayColor;
            this.futureColors = Arrays.asList(data.futureColors);
            this.forecastDays = data.getForecastDays();
        }

    }

    public static SkyColor getRandomSkyColor() {
        return SKY_COLORS.get(RandomSource.create().nextInt(SKY_COLORS.size()));
    }

    public static Optional<SkyColor> getSkyColorById(int id) {
        return SKY_COLORS.stream().filter(color -> color.id == id).findFirst();
    }

    public static WorldSkyData getWorldSkyData(Level level) {
        if (level.isClientSide()) {
            return getClientWorldSkyData(level);
        } else if (level instanceof ServerLevel serverLevel) {
            WorldSkyDataStorage storage = WorldSkyDataStorage.get(serverLevel);
            return storage.getSkyData();
        }

        return new WorldSkyData(getAvailableSkyColors().getFirst(), 3);
    }

    public static SkyColorForecast getSkyColorForecast(Level level) {
        return new SkyColorForecast(getWorldSkyData(level));
    }

    public static void updateSkyColors(ServerLevel level) {
        long dayTime = level.getDayTime();
        WorldSkyData skyData = getWorldSkyData(level);
        onSkyColorChanged(level, skyData.currentDayColor);
        if (isMidnight(dayTime) && dayTime != skyData.lastMidnightTime) {
            skyData.advanceDay();
            skyData.lastMidnightTime = dayTime;
            WorldSkyDataStorage.get(level).setDirty();
            syncSkyColorToAllPlayers(level, skyData.currentDayColor);
        }
    }

    private static WorldSkyData getClientWorldSkyData(Level level) {
        ClientSkyColorData.ClientSkyData clientData = ClientSkyColorData.getClientData(level);
        WorldSkyData tempData = new WorldSkyData(clientData.currentDayColor, clientData.futureColors.length);
        tempData.futureColors = clientData.futureColors;
        tempData.lastMidnightTime = 0;
        return tempData;
    }

    private static boolean isMidnight(long dayTime) {
        return (dayTime % 24000) >= 18000 && (dayTime % 24000) < 18020;
    }

    public static int getCurrentSkyColor(Level level) {
        long dayTime = level.getDayTime() % 24000 + 12000;
        WorldSkyData skyData = getWorldSkyData(level);
        int nightColor = level.getData(TAAttachmentTypes.NIGHT_SKY_COLOR);
        int currentColor = skyData.currentDayColor.color;
        if (dayTime < 12000) {
            return nightColor;
        } else if (dayTime < 18000) {
            float progress = (dayTime - 12000) / 6000.0F;
            return interpolateColor(nightColor, currentColor, progress);
        } else {
            float progress = (dayTime - 18000) / 6000.0F;
            return interpolateColor(currentColor, nightColor, progress);
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

    private static void onSkyColorChanged(ServerLevel level, SkyColor newColor) {
        long dayTime = level.getDayTime() % 24000;
        if (dayTime % 200 == 0) {
            GameRules.Key<GameRules.BooleanValue> aurorianBless = TAGameRules.RULE_ENABLE_AURORIAN_BLESS;
            boolean enableAurorianBless = level.getGameRules().getBoolean(aurorianBless);
            MobEffectInstance instance = new MobEffectInstance(TAMobEffects.PRESSURE);
            instance.duration = 320;
            instance.visible = false;
            for (ServerPlayer player : level.players()) {
                if (dayTime > 0 && dayTime <= 12000 && enableAurorianBless && !player.getData(REMOVE_BLESS)) {
                    LevelEventSubscriber.NightPhase.fromCode(newColor.id).applyBlessEffect(player);
                } else if (dayTime > 12000 && (!player.getData(IMMUNE_PRESSURE_PERSISTENT) || !player.getData(IMMUNE_PRESSURE_TEMP))) {
                    player.addEffect(instance);
                }
            }
        }
    }

    public static void syncSkyColorToPlayer(ServerPlayer player, WorldSkyData skyData) {
        SkyColor[] futureColors = skyData.futureColors;
        int[] futureColorIds = new int[futureColors.length];
        int[] futureColorValues = new int[futureColors.length];
        for (int i = 0; i < futureColors.length; i++) {
            futureColorIds[i] = futureColors[i].id;
            futureColorValues[i] = futureColors[i].color;
        }

        SkyColor currentDayColor = skyData.currentDayColor;
        WorldDayColorS2CPacket packet = new WorldDayColorS2CPacket(
                currentDayColor.id, currentDayColor.color,
                futureColorIds, futureColorValues);
        PacketDistributor.sendToPlayer(player, packet);
    }

    public static void syncSkyColorToAllPlayers(ServerLevel level, SkyColor color) {
        for (ServerPlayer player : level.players()) {
            PacketDistributor.sendToPlayer(player, new NightTypeS2CPacket(color.id));
            syncSkyColorToPlayer(player, getWorldSkyData(level));
        }
    }

    public static void setSkyColor(ServerLevel level, int colorId) {
        Optional<SkyColor> color = getSkyColorById(colorId);
        if (color.isPresent()) {
            WorldSkyData skyData = getWorldSkyData(level);
            skyData.currentDayColor = color.get();
            syncSkyColorToAllPlayers(level, color.get());
            for (ServerPlayer serverPlayer : level.players()) {
                String key = "commands.theaurorian.night_phase.set";
                String name = LevelEventSubscriber.NightPhase.getDisplayName(colorId);
                serverPlayer.sendSystemMessage(Component.translatable(key, name));
            }
        }
    }

    public static List<SkyColor> getAvailableSkyColors() {
        return new ArrayList<>(SKY_COLORS);
    }

}