package cn.teampancake.theaurorian.common.level.data.world_event;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.entities.boss.AbstractAurorianBoss;
import cn.teampancake.theaurorian.common.network.WorldNightColorS2CPacket;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TAEventConfigurations;
import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;

public class BloodMoonEvent extends BaseWorldEvent<BaseEventConfig> {

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
    private final ServerBossEvent bloodMoonEvent = (ServerBossEvent) new ServerBossEvent(BLOOD_MOON_NAME_COMPONENT.copy()
            .withStyle(ChatFormatting.BOLD), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_10).setDarkenScreen(true);

    public BloodMoonEvent() {
        super(BaseEventConfig.CODEC);
    }

    @Override
    protected ResourceKey<ConfiguredEvent<?, ?>> getConfigKey() {
        return TAEventConfigurations.BLOOD_MOON;
    }

    @Override
    public void onPrecursorStart(ServerLevel level) {
        level.setData(TAAttachmentTypes.NIGHT_SKY_COLOR, 0x660000);
        level.players().forEach(player -> PacketDistributor.sendToPlayer(player, new WorldNightColorS2CPacket(0x660000)));
    }

    @Override
    public void onAftermathStart(ServerLevel level) {
        level.setData(TAAttachmentTypes.NIGHT_SKY_COLOR, 0x010e34);
        level.players().forEach(player -> PacketDistributor.sendToPlayer(player, new WorldNightColorS2CPacket(0x010e34)));
    }

    @Override
    public void onEventStart(ServerLevel level) {
        WorldEventData eventData = WorldEventManager.getWorldEventData(level);
        Map<UUID, BloodMoonPlayerData> playerDataMap = eventData.bloodMoonPlayerData;
        for (ServerPlayer player : level.players()) {
            this.bloodMoonEvent.addPlayer(player);
            BloodMoonPlayerData playerData = new BloodMoonPlayerData();
            playerDataMap.put(player.getUUID(), playerData);
            player.setData(KILL_COUNT, 0);
            player.setData(REMOVE_BLESS, false);
            player.sendSystemMessage(BLOOD_MOON_START_COMPONENT, Boolean.FALSE);
            if (!player.getData(IMMUNE_PRESSURE_PERSISTENT)) {
                player.setData(IMMUNE_PRESSURE_TEMP, false);
            }
        }

        for (Entity entity : level.getAllEntities()) {
            if (entity instanceof PathfinderMob mob) {
                enhanceEnemy(level, mob);
            }

            if (entity instanceof Animal animal) {
                animal.goalSelector.removeGoal(new PanicGoal(animal, 2.0D));
                animal.goalSelector.addGoal(1, new MeleeAttackGoal(animal, 1.0F, Boolean.FALSE));
                animal.targetSelector.addGoal(1, new HurtByTargetGoal(animal));
                animal.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(animal, Player.class, true));
            }
        }
    }

    @Override
    public void onEventEnd(ServerLevel level) {
        WorldEventData eventData = WorldEventManager.getWorldEventData(level);
        Map<UUID, BloodMoonPlayerData> playerDataMap = eventData.bloodMoonPlayerData;
        for (ServerPlayer player : level.players()) {
            int killCount = player.getData(KILL_COUNT);
            this.bloodMoonEvent.removePlayer(player);
            BloodMoonPlayerData playerData = new BloodMoonPlayerData();
            playerData.kills = killCount;
            playerDataMap.put(player.getUUID(), playerData);
            player.sendSystemMessage(BLOOD_MOON_END_COMPONENT, Boolean.FALSE);
            if (killCount >= 40) {
                player.setData(IMMUNE_PRESSURE_TEMP, true);
            } else {
                player.setData(REMOVE_BLESS, true);
            }
        }

        for (Entity entity : level.getAllEntities()) {
            if (entity instanceof PathfinderMob mob && mob instanceof Enemy) {
                for (var entry : getEnhanceMultiplier().entrySet()) {
                    AttributeInstance instance = mob.getAttribute(entry.getKey());
                    Pair<ResourceLocation, Double> pair = entry.getValue();
                    if (instance != null && instance.hasModifier(pair.getFirst())) {
                        instance.removeModifier(pair.getFirst());
                    }
                }
            }

            if (entity instanceof Animal animal) {
                NearestAttackableTargetGoal<Player> targetGoal = new NearestAttackableTargetGoal<>(animal, Player.class, true);
                List.of(new HurtByTargetGoal(animal), targetGoal).forEach(animal.targetSelector::removeGoal);
                animal.goalSelector.removeGoal(new MeleeAttackGoal(animal, 1.0F, Boolean.FALSE));
                animal.goalSelector.addGoal(1, new PanicGoal(animal, 2.0D));
            }
        }
    }

    @Override
    public void onEventTick(ServerLevel level, long currentTick) {
        this.bloodMoonEvent.setProgress(this.getProgress(level));
        for (ServerPlayer player : level.players()) {
            MutableComponent component = Component.translatable(BLOOD_MOON_KILL_COUNT, player.getData(KILL_COUNT));
            this.bloodMoonEvent.setName(BLOOD_MOON_NAME_COMPONENT.copy()
                    .append(Component.literal(" - ").withStyle(ChatFormatting.BOLD))
                    .append(component.withStyle(ChatFormatting.BOLD)));
        }
    }

    public static void enhanceEnemy(ServerLevel level, Mob mob) {
        if (mob instanceof Enemy && !(mob instanceof AbstractAurorianBoss) && TACommonUtils.isAurorianDimension(level)) {
            Set<Map.Entry<Holder<Attribute>, Pair<ResourceLocation, Double>>> entrySet = getEnhanceMultiplier().entrySet();
            AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
            for (Map.Entry<Holder<Attribute>, Pair<ResourceLocation, Double>> entry : entrySet) {
                AttributeInstance instance = mob.getAttribute(entry.getKey());
                Pair<ResourceLocation, Double> pair = entry.getValue();
                if (instance != null && !instance.hasModifier(pair.getFirst())) {
                    instance.addPermanentModifier(new AttributeModifier(pair.getFirst(), pair.getSecond(), operation));
                    if (mob.getLastDamageSource() == null) mob.setHealth(mob.getMaxHealth());
                }
            }
        }
    }

    public static void checkIfCanEnhance(PathfinderMob mob) {
        if (mob.level() instanceof ServerLevel serverLevel && TAWorldEvents.BLOOD_MOON.get().isActive(serverLevel)) {
            mob.goalSelector.addGoal(1, new MeleeAttackGoal(mob, 1.0F, Boolean.FALSE));
            mob.targetSelector.addGoal(1, new HurtByTargetGoal(mob));
            mob.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(mob, Player.class, true));
        } else {
            mob.goalSelector.addGoal(1, new PanicGoal(mob, 2.0D));
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