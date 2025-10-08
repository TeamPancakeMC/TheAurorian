package cn.teampancake.theaurorian.common.level.data.event;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.data.*;
import cn.teampancake.theaurorian.common.network.WorldNightColorS2CPacket;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;

public class BloodMoonEvent extends BaseWorldEvent {

    private static final String BLOOD_MOON_KILL_COUNT = "event.theaurorian.blood_moon.kill_count";
    private static final Component BLOOD_MOON_NAME_COMPONENT = Component.translatable("event.theaurorian.blood_moon");
    private static final Component BLOOD_MOON_START_COMPONENT = Component.translatable("event.theaurorian.blood_moon.start");
    private static final Component BLOOD_MOON_END_COMPONENT = Component.translatable("event.theaurorian.blood_moon.end");
    private static final ResourceLocation SPEED_MODIFIER = TheAurorian.prefix("blood_moon_speed");
    private static final ResourceLocation HEALTH_MODIFIER = TheAurorian.prefix("blood_moon_health");
    private static final ResourceLocation ATTACK_MODIFIER = TheAurorian.prefix("blood_moon_attack");
    private static final AttachmentType<Integer> KILL_COUNT = TAAttachmentTypes.KILL_COUNT_IN_BLOOD_MOON.get();
    private static final AttachmentType<Boolean> REMOVE_BLESS = TAAttachmentTypes.REMOVE_BLESS_UNTIL_NEXT_BLOOD_MOON.get();
    private static final AttachmentType<Boolean> IMMUNE_PRESSURE_TEMP = TAAttachmentTypes.IMMUNE_PRESSURE_UNTIL_NEXT_BLOOD_MOON.get();
    private static final AttachmentType<Boolean> IMMUNE_PRESSURE_PERSISTENT = TAAttachmentTypes.IMMUNE_PRESSURE_BY_KILL_MOON_QUEEN.get();
    private final ServerBossEvent bloodMoonEvent = new ServerBossEvent(BLOOD_MOON_NAME_COMPONENT.copy()
            .withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.BOLD),
            BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_10);

    public BloodMoonEvent() {
        super(2, 1.0F, EventTimeRange.byTicks(12000, 12000));
    }

    @Override
    public int getOmenWarningTime() {
        return 6000;
    }

    @Override
    public int getAftermathDelay() {
        return 6000;
    }

    @Override
    public void executeOmen(ServerLevel level) {
        level.getServer().playerList.broadcastSystemMessage(Component.literal("血月即将来临！").withStyle(ChatFormatting.DARK_RED), Boolean.FALSE);
        level.setData(TAAttachmentTypes.NIGHT_SKY_COLOR, 0x8a0303);
        for (ServerPlayer player : level.players()) {
            PacketDistributor.sendToPlayer(player, new WorldNightColorS2CPacket(0x8a0303));
        }
    }

    @Override
    public void executeAftermath(ServerLevel level) {
        level.getServer().playerList.broadcastSystemMessage(Component.literal("血月已经完全消散！").withStyle(ChatFormatting.BLUE), Boolean.FALSE);
        level.setData(TAAttachmentTypes.NIGHT_SKY_COLOR, 0x010e34);
        for (ServerPlayer player : level.players()) {
            PacketDistributor.sendToPlayer(player, new WorldNightColorS2CPacket(0x010e34));
        }
    }

    @Override
    public void onEventStart(ServerLevel level) {
        level.getServer().playerList.broadcastSystemMessage(BLOOD_MOON_START_COMPONENT, Boolean.FALSE);
        for (ServerPlayer player : level.players()) {
            this.bloodMoonEvent.addPlayer(player);
            player.setData(KILL_COUNT, 0);
            player.setData(REMOVE_BLESS, false);
            if (!player.getData(IMMUNE_PRESSURE_PERSISTENT)) {
                player.setData(IMMUNE_PRESSURE_TEMP, false);
            }
        }
    }

    @Override
    public void onEventEnd(ServerLevel level) {
        level.getServer().playerList.broadcastSystemMessage(BLOOD_MOON_END_COMPONENT, Boolean.FALSE);
        for (ServerPlayer player : level.players()) {
            this.bloodMoonEvent.removePlayer(player);
            if (player.getData(KILL_COUNT) >= 40) {
                player.setData(IMMUNE_PRESSURE_TEMP, true);
            } else {
                player.setData(REMOVE_BLESS, true);
            }
        }

        level.getAllEntities().forEach(entity -> {
            if (entity instanceof Mob mob && mob instanceof Enemy) {
                for (var entry : getEnhanceMultiplier().entrySet()) {
                    AttributeInstance instance = mob.getAttribute(entry.getKey());
                    Pair<ResourceLocation, Double> pair = entry.getValue();
                    if (instance != null && instance.hasModifier(pair.getFirst())) {
                        instance.removeModifier(pair.getFirst());
                    }
                }
            }
        });
    }

    @Override
    public void onEventTick(ServerLevel level, long currentTime) {
        if (this.getEventId() != null) {
            long startTime = this.activeTimeRange.getStartTicks();
            long durationTicks = this.activeTimeRange.getDurationTicks();
            WorldEventData eventData = WorldEventManager.getWorldEventData(level);
            float progress = eventData.getEventProgress(
                    this.getEventId(), currentTime, startTime, durationTicks);
            this.bloodMoonEvent.setProgress(progress);
            for (ServerPlayer player : level.players()) {
                MutableComponent component = Component.translatable(
                        BLOOD_MOON_KILL_COUNT, player.getData(KILL_COUNT));
                this.bloodMoonEvent.setName(BLOOD_MOON_NAME_COMPONENT.copy()
                        .append(Component.literal(" - ").withStyle(ChatFormatting.BOLD))
                        .append(component.withStyle(ChatFormatting.BOLD)));
            }

            WorldEventDataStorage.get(level).setDirty();
        }
    }

    public static Map<Holder<Attribute>, Pair<ResourceLocation, Double>> getEnhanceMultiplier() {
        Map<Holder<Attribute>, Pair<ResourceLocation, Double>> map = new HashMap<>();
        map.put(Attributes.MOVEMENT_SPEED, Pair.of(SPEED_MODIFIER, 0.2D));
        map.put(Attributes.MAX_HEALTH, Pair.of(HEALTH_MODIFIER, 0.5D));
        map.put(Attributes.ATTACK_DAMAGE, Pair.of(ATTACK_MODIFIER, 0.5D));
        return map;
    }

}