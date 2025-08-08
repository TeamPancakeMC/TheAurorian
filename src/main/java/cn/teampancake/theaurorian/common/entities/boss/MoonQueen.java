package cn.teampancake.theaurorian.common.entities.boss;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAMobEffectTags;
import cn.teampancake.theaurorian.common.entities.ai.goal.MeleeNoAttackGoal;
import cn.teampancake.theaurorian.common.entities.ai.goal.MoonQueenAmphibiousStrollGoal;
import cn.teampancake.theaurorian.common.entities.ai.goal.MoonQueenForceStrollGoal;
import cn.teampancake.theaurorian.common.entities.ai.goal.MoonQueenResetAttackStateGoal;
import cn.teampancake.theaurorian.common.entities.phase.AttackManager;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;
import cn.teampancake.theaurorian.common.entities.phase.moonqueen.*;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TAAttributes;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.doubles.DoubleDoubleImmutablePair;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

/** @noinspection deprecation*/
public class MoonQueen extends AbstractAurorianBoss implements GeoEntity {

    private static final RawAnimation DEFEAT = RawAnimation.begin().thenPlay("misc.defeat");
    private static final RawAnimation DEFEAT_LOOP = RawAnimation.begin().thenPlay("misc.defeat_loop");
    private static final RawAnimation ATTACK_SWING_2 = RawAnimation.begin().thenPlay("attack.swing_2");
    private static final RawAnimation ATTACK_BURST = RawAnimation.begin().thenPlay("attack.burst");
    private static final RawAnimation ATTACK_BUFF = RawAnimation.begin().thenPlay("attack.buff");
    private static final RawAnimation ATTACK_DUEL = RawAnimation.begin().thenPlay("attack.duel");
    private static final RawAnimation ATTACK_BLADE_WAVE = RawAnimation.begin().thenPlay("attack.blade_wave");
    private static final RawAnimation ATTACK_MOON_BEFALL = RawAnimation.begin().thenPlay("attack.moon_befall");
    private static final ResourceLocation SPEED_MODIFIER_FOUND_TARGET = TheAurorian.prefix("found_target");
    private static final EntityDataAccessor<Float> ATTACK_Y_ROT = SynchedEntityData.defineId(MoonQueen.class, EntityDataSerializers.FLOAT);
    private static final ImmutableList<MobEffectInstance> BUFF_LIST = ImmutableList.of(
            new MobEffectInstance(TAMobEffects.CRESCENT, 200),
            new MobEffectInstance(TAMobEffects.BLESS_OF_MOON, 200),
            new MobEffectInstance(TAMobEffects.MOON_OF_VENGEANCE, 200));
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final HashSet<String> killedDuelistName = new HashSet<>();
    private final HashSet<String> currentSavedName = new HashSet<>();
    private final HashSet<String> playerAlreadyHealFor = new HashSet<>();
    private final HashSet<String> hostilePlayerNames = new HashSet<>(); // 记录敌对玩家
    private long ticksCanOneHitMustKill = 24000L;
    private int ticksDueling = 2400;
    private int triggerDuelingCount;
    public int preparationTime;
    private int safeTime;
    public int fqmPySwordNum;
    private boolean isNeutral;
    public boolean duelingMoment;
    private String currentDuelistName = "";
    private int idleResetCounter = 0;
    private int outOfCombatTimer = 0; // 脱战计时器
    private static final int OUT_OF_COMBAT_DELAY = 100; // 5秒脱战延迟（20 ticks/秒）

    // 剑雨技能相关的攻击计数器
    private final Map<String, Integer> meleeAttackCounts = new HashMap<>(); // 每个目标的近战攻击次数
    private final Map<String, Long> lastMeleeAttackTime = new HashMap<>(); // 每个目标的最后近战攻击时间

    // burst攻击相关的计数器
    private final Map<String, Integer> swingAttackCounts = new HashMap<>(); // 每个目标的swing攻击次数
    private final Map<String, Integer> burstAttackCounts = new HashMap<>(); // 每个目标的burst攻击次数

    // 格挡系统相关变量
    private boolean isBlocking = false; // 是否正在格挡
    private int blockingTicks = 0; // 格挡持续时间
    private int blockCooldownTicks = 0; // 格挡冷却时间
    private LivingEntity blockingTarget = null; // 格挡时面向的目标
    private static final int BLOCK_DURATION = 20; // 格挡持续时间（1秒）
    private static final int BLOCK_COOLDOWN = 60; // 格挡冷却时间（3秒）
    private static final float BLOCK_CHANCE = 0.3F; // 格挡概率30%

    public MoonQueen(EntityType<? extends MoonQueen> type, Level level) {
        super(type, level);
        this.xpReward = 500;
        List<AttackPhase<MoonQueen>> phaseList = Lists.newArrayList(
                new MoonQueenMeleePhase(),
                new MoonQueenRangedPhase(),
                new MoonQueenAssaultCyclePhase(),
                new MoonQueenMeleePhase(),
                new MoonQueenRangedPhase(),
                new MoonQueenBackAttackPhase(),
                new MoonQueenMoonBefallPhase(),
                new MoonQueenRainOfSwordsPhase());
        this.attackManager = new AttackManager<>(this, phaseList);
    }

    @Override
    protected void registerGoals() {
        // 基础移动目标 - 最高优先级，确保不会溺水
        this.goalSelector.addGoal(0, new FloatGoal(this));

        // 战斗相关目标 - 高优先级
        this.goalSelector.addGoal(1, new MeleeNoAttackGoal(this));
        this.goalSelector.addGoal(1, new MoonQueenResetAttackStateGoal(this));

        // 智能移动目标 - 中等优先级，使用优化的强制漫步（紧急情况）
        this.goalSelector.addGoal(2, new MoonQueenForceStrollGoal(this, 0.7D));

        // 水陆两栖移动 - 专为皎月女王设计的智能移动
        this.goalSelector.addGoal(3, new MoonQueenAmphibiousStrollGoal(this, 0.6D));

        // 备用随机移动 - 最后的移动选择
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.5D));

        // 观察目标 - 低优先级
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        // 目标选择 - 使用统一的目标选择器
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new MoonQueenNearestAttackableTargetGoal<>(this, LivingEntity.class, true, this::isValidTarget));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(TAAttributes.MAX_BOSS_HEALTH, 500.0D);
        builder.add(Attributes.WATER_MOVEMENT_EFFICIENCY, 1.0D);
        builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.85D);
        builder.add(Attributes.ARMOR_TOUGHNESS, 10.0D);
        builder.add(Attributes.ATTACK_KNOCKBACK, 0.5D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.25D); // 基础漫游速度
        builder.add(Attributes.FOLLOW_RANGE, 60.0F);
        builder.add(Attributes.ATTACK_DAMAGE, 8.0D);
        builder.add(Attributes.ARMOR, 8.0F);
        return builder;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACK_Y_ROT, 0.0F);
    }
    // 仅用于限制“战斗奔跑”时的速度加成启用
    private boolean combatRunActive = false;
    public void setCombatRunActive(boolean v){ this.combatRunActive = v; }
    public boolean isCombatRunActive(){ return this.combatRunActive; }


    @Override
    protected BodyRotationControl createBodyControl() {
        return new MoonQueenBodyRotationControl(this);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkRunIdleController(this));
        controllers.add(new AnimationController<>(this, "block_controller", state -> PlayState.STOP)
                .triggerableAnim("block_animation", DefaultAnimations.ATTACK_BLOCK).transitionLength(5));
        controllers.add(new AnimationController<>(this, "swing_controller", state -> PlayState.STOP)
                .triggerableAnim("swing_animation", DefaultAnimations.ATTACK_SWING).transitionLength(1));
        controllers.add(new AnimationController<>(this, "swing_2_controller", state -> PlayState.STOP)
                .triggerableAnim("swing_2_animation", ATTACK_SWING_2).transitionLength(1));
        controllers.add(new AnimationController<>(this, "burst_controller", state -> PlayState.STOP)
                .triggerableAnim("burst_animation", ATTACK_BURST).transitionLength(1));
        controllers.add(new AnimationController<>(this, "buff_controller", state -> PlayState.STOP)
                .triggerableAnim("buff_animation", ATTACK_BUFF).transitionLength(5));
        controllers.add(new AnimationController<>(this, "duel_controller", state -> PlayState.STOP)
                .triggerableAnim("duel_animation", ATTACK_DUEL).transitionLength(5));
        controllers.add(new AnimationController<>(this, "blade_wave_controller", state -> PlayState.STOP)
                .triggerableAnim("blade_wave_animation", ATTACK_BLADE_WAVE).transitionLength(1));
        controllers.add(new AnimationController<>(this, "moon_befall_controller", state -> PlayState.STOP)
                .triggerableAnim("moon_befall_animation", ATTACK_MOON_BEFALL).transitionLength(5));
        controllers.add(new AnimationController<>(this, "defeat_controller", state -> PlayState.STOP)
                .triggerableAnim("defeat_animation", DEFEAT).transitionLength(5));
        controllers.add(new AnimationController<>(this, "defeat_loop_controller", state -> PlayState.STOP)
                .triggerableAnim("defeat_loop_animation", DEFEAT_LOOP).transitionLength(5));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        switch (level.getDifficulty()) {
            case NORMAL -> this.fqmPySwordNum = 24;
            case HARD -> this.fqmPySwordNum = 32;
            default -> this.fqmPySwordNum = 16;
        }

        return spawnGroupData;
    }

    public float getAttackYRot() {
        return this.entityData.get(ATTACK_Y_ROT);
    }

    public void setAttackYRot(float attackYRot) {
        this.entityData.set(ATTACK_Y_ROT, attackYRot);
    }

    public boolean isValidTarget(@Nullable LivingEntity entity) {
        if (entity == null || !entity.attackable()) {
            return false;
        }

        // 不攻击自己和月光骑士
        if (entity instanceof MoonQueen || entity instanceof MoonlightKnight) {
            return false;
        }

        // 获取实体的命名空间
        String entityNamespace = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getNamespace();

        // 不攻击本MOD(TheAurorian)内的任何生物
        if (entityNamespace.equals(TheAurorian.MOD_ID)) {
            return false;
        }

        // 对玩家的特殊处理
        if (entity instanceof Player player) {
            String playerName = player.getName().getString();
            // 只攻击在敌对列表中的玩家
            return this.hostilePlayerNames.contains(playerName);
        }

        // 对其他生物：攻击所有非本MOD的敌对生物
        return this.isHostileMob(entity);
    }

    /**
     * 判断是否为敌对生物
     */
    private boolean isHostileMob(LivingEntity entity) {
        // 检查是否为怪物类型
        if (entity instanceof Monster) {
            return true;
        }

        // 检查是否为其他敌对生物类型
        if (entity instanceof net.minecraft.world.entity.monster.Enemy) {
            return true;
        }

        // 检查实体类别是否为怪物
        if (entity.getType().getCategory() == MobCategory.MONSTER) {
            return true;
        }

        // 额外检查一些特殊的敌对生物
        String entityTypeName = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString();
        return this.isKnownHostileEntity(entityTypeName);
    }

    /**
     * 检查已知的敌对实体类型
     */
    private boolean isKnownHostileEntity(String entityTypeName) {
        // 原版敌对生物
        return entityTypeName.contains("zombie") ||
               entityTypeName.contains("skeleton") ||
               entityTypeName.contains("creeper") ||
               entityTypeName.contains("spider") ||
               entityTypeName.contains("witch") ||
               entityTypeName.contains("enderman") ||
               entityTypeName.contains("blaze") ||
               entityTypeName.contains("ghast") ||
               entityTypeName.contains("wither") ||
               entityTypeName.contains("ender_dragon") ||
               entityTypeName.contains("phantom") ||
               entityTypeName.contains("pillager") ||
               entityTypeName.contains("vindicator") ||
               entityTypeName.contains("evoker") ||
               entityTypeName.contains("ravager") ||
               entityTypeName.contains("vex") ||
               entityTypeName.contains("guardian") ||
               entityTypeName.contains("elder_guardian") ||
               entityTypeName.contains("shulker") ||
               entityTypeName.contains("hoglin") ||
               entityTypeName.contains("piglin_brute") ||
               entityTypeName.contains("zoglin") ||
               entityTypeName.contains("warden");
    }

    /**
     * 将玩家添加到敌对列表
     */
    public void addHostilePlayer(Player player) {
        String playerName = player.getName().getString();
        if (!this.hostilePlayerNames.contains(playerName)) {
            this.hostilePlayerNames.add(playerName);
            // 立即将该玩家设为目标（如果当前没有目标）
            if (this.getTarget() == null) {
                this.setTarget(player);
            }
        }
    }

    /**
     * 从敌对列表中移除玩家
     */
    public void removeHostilePlayer(Player player) {
        String playerName = player.getName().getString();
        this.hostilePlayerNames.remove(playerName);
        // 如果当前目标是被移除的玩家，清除目标
        if (this.getTarget() == player) {
            this.setTarget(null);
        }
    }

    /**
     * 检查玩家是否在敌对列表中
     */
    public boolean isPlayerHostile(Player player) {
        return this.hostilePlayerNames.contains(player.getName().getString());
    }

    /**
     * 清除所有敌对玩家
     */
    public void clearHostilePlayers() {
        this.hostilePlayerNames.clear();
        // 如果当前目标是玩家，清除目标
        if (this.getTarget() instanceof Player) {
            this.setTarget(null);
        }
    }

    /**
     * 获取敌对玩家数量
     */
    public int getHostilePlayerCount() {
        return this.hostilePlayerNames.size();
    }

    /**
     * 获取敌对玩家列表的副本
     */
    public Set<String> getHostilePlayerNames() {
        return new HashSet<>(this.hostilePlayerNames);
    }

    /**
     * 当皎月女王进入中立状态时，可以选择是否清除所有敌对状态
     */
    public void enterNeutralState() {
        this.isNeutral = true;
        // 可以选择清除所有敌对玩家
        // this.clearHostilePlayers();
        this.setTarget(null);
    }

    private ListTag saveListTag(HashSet<String> list) {
        ListTag listTag = new ListTag();
        list.forEach(s -> {
            CompoundTag compound = new CompoundTag();
            compound.putString("Name", s);
            listTag.add(compound);
        });

        return listTag;
    }

    /**
     * 保存近战攻击数据
     */
    private void saveMeleeAttackData(CompoundTag compound) {
        // 保存总攻击计数
        CompoundTag attackCountsTag = new CompoundTag();
        for (Map.Entry<String, Integer> entry : this.meleeAttackCounts.entrySet()) {
            attackCountsTag.putInt(entry.getKey(), entry.getValue());
        }
        compound.put("MeleeAttackCounts", attackCountsTag);

        // 保存swing攻击计数
        CompoundTag swingCountsTag = new CompoundTag();
        for (Map.Entry<String, Integer> entry : this.swingAttackCounts.entrySet()) {
            swingCountsTag.putInt(entry.getKey(), entry.getValue());
        }
        compound.put("SwingAttackCounts", swingCountsTag);

        // 保存burst攻击计数
        CompoundTag burstCountsTag = new CompoundTag();
        for (Map.Entry<String, Integer> entry : this.burstAttackCounts.entrySet()) {
            burstCountsTag.putInt(entry.getKey(), entry.getValue());
        }
        compound.put("BurstAttackCounts", burstCountsTag);

        // 保存攻击时间
        CompoundTag attackTimesTag = new CompoundTag();
        for (Map.Entry<String, Long> entry : this.lastMeleeAttackTime.entrySet()) {
            attackTimesTag.putLong(entry.getKey(), entry.getValue());
        }
        compound.put("LastMeleeAttackTimes", attackTimesTag);
    }

    /**
     * 加载近战攻击数据
     */
    private void loadMeleeAttackData(CompoundTag compound) {
        // 清空现有数据
        this.meleeAttackCounts.clear();
        this.lastMeleeAttackTime.clear();
        this.swingAttackCounts.clear();
        this.burstAttackCounts.clear();

        // 加载总攻击计数
        if (compound.contains("MeleeAttackCounts")) {
            CompoundTag attackCountsTag = compound.getCompound("MeleeAttackCounts");
            for (String key : attackCountsTag.getAllKeys()) {
                this.meleeAttackCounts.put(key, attackCountsTag.getInt(key));
            }
        }

        // 加载swing攻击计数
        if (compound.contains("SwingAttackCounts")) {
            CompoundTag swingCountsTag = compound.getCompound("SwingAttackCounts");
            for (String key : swingCountsTag.getAllKeys()) {
                this.swingAttackCounts.put(key, swingCountsTag.getInt(key));
            }
        }

        // 加载burst攻击计数
        if (compound.contains("BurstAttackCounts")) {
            CompoundTag burstCountsTag = compound.getCompound("BurstAttackCounts");
            for (String key : burstCountsTag.getAllKeys()) {
                this.burstAttackCounts.put(key, burstCountsTag.getInt(key));
            }
        }

        // 加载攻击时间
        if (compound.contains("LastMeleeAttackTimes")) {
            CompoundTag attackTimesTag = compound.getCompound("LastMeleeAttackTimes");
            for (String key : attackTimesTag.getAllKeys()) {
                this.lastMeleeAttackTime.put(key, attackTimesTag.getLong(key));
            }
        }
    }

    /**
     * 安全瞬移到目标背后
     */
    public void teleportToTheBackOfTheTarget(LivingEntity target) {
        Vec3 teleportPos = this.calculateSafeTeleportPosition(target);
        if (teleportPos != null) {
            this.teleportTo(teleportPos.x, teleportPos.y, teleportPos.z);
            if (target.isAlive()) {
                double dx = target.getX();
                double dy = target.getEyeY();
                double dz = target.getZ();
                this.getLookControl().setLookAt(dx, dy, dz);
            }
        }
    }

    /**
     * 计算安全的瞬移位置
     */
    private Vec3 calculateSafeTeleportPosition(LivingEntity target) {
        Vec3 eyePos = target.getEyePosition();
        Vec3 lookAngle = target.getLookAngle();

        // 尝试多个位置，找到最安全的
        double[] distances = {2.0D, 2.5D, 3.0D, 1.5D}; // 优先2格，然后尝试其他距离

        for (double distance : distances) {
            double tx = eyePos.x - lookAngle.x * distance;
            double tz = eyePos.z - lookAngle.z * distance;

            // 寻找合适的Y坐标
            Vec3 safePos = this.findSafeYPosition(tx, target.getY(), tz);
            if (safePos != null) {
                return safePos;
            }
        }

        return null; // 没有找到安全位置
    }

    /**
     * 寻找安全的Y坐标位置
     */
    private Vec3 findSafeYPosition(double x, double baseY, double z) {
        // 检查范围：从目标Y坐标向上下各搜索5格
        for (int yOffset = 0; yOffset <= 5; yOffset++) {
            // 先检查向上
            if (yOffset > 0) {
                Vec3 upPos = new Vec3(x, baseY + yOffset, z);
                if (this.isPositionSafe(upPos)) {
                    return upPos;
                }
            }

            // 再检查向下
            if (yOffset > 0) {
                Vec3 downPos = new Vec3(x, baseY - yOffset, z);
                if (this.isPositionSafe(downPos)) {
                    return downPos;
                }
            }

            // 检查原始高度
            if (yOffset == 0) {
                Vec3 originalPos = new Vec3(x, baseY, z);
                if (this.isPositionSafe(originalPos)) {
                    return originalPos;
                }
            }
        }

        return null;
    }

    /**
     * 检查位置是否安全
     */
    private boolean isPositionSafe(Vec3 pos) {
        BlockPos blockPos = BlockPos.containing(pos);

        // 检查脚下和身体位置是否有足够空间
        if (!this.level().getBlockState(blockPos).isAir() ||
            !this.level().getBlockState(blockPos.above()).isAir()) {
            return false;
        }

        // 使用实体碰撞盒进行无碰撞检测，确保可站立
        if (!this.level().noCollision(this.getDimensions(this.getPose()).makeBoundingBox(pos.x, pos.y, pos.z))) {
            return false;
        }

        // 检查脚下是否有支撑（避免掉入虚空）
        BlockPos belowPos = blockPos.below();
        if (this.level().getBlockState(belowPos).isAir() &&
            this.level().getBlockState(belowPos.below()).isAir()) {
            // 如果脚下2格都是空气，检查是否会掉得太深
            for (int i = 2; i <= 10; i++) {
                BlockPos checkPos = blockPos.below(i);
                if (!this.level().getBlockState(checkPos).isAir()) {
                    break; // 找到了支撑
                }
                if (i >= 10) {
                    return false; // 太深了，不安全
                }
            }
        }

        return true;
    }

    public boolean isTruePlayer(Player player) {
        return !player.isCreative() && !player.isSpectator();
    }

    /**
     * 检查是否可以安全瞬移到目标背后
     */
    public boolean isTeleportPositionSafe(LivingEntity target) {
        if (!(target instanceof Player)) {
            return false;
        }

        Vec3 teleportPos = this.calculateSafeTeleportPosition(target);
        return teleportPos != null;
    }

    /**
     * 记录对目标的近战攻击
     */
    public void recordMeleeAttack(LivingEntity target) {
        this.recordMeleeAttack(target, true); // 默认是swing攻击
    }

    /**
     * 记录对目标的近战攻击
     * @param target 目标
     * @param isSwingAttack 是否是swing攻击（swing或swing_2），false表示burst攻击
     */
    public void recordMeleeAttack(LivingEntity target, boolean isSwingAttack) {
        if (target == null) return;

        String targetKey = this.getTargetKey(target);
        long currentTime = this.level().getGameTime();

        // 如果距离上次攻击超过10秒，重置计数器
        if (this.lastMeleeAttackTime.containsKey(targetKey)) {
            long lastTime = this.lastMeleeAttackTime.get(targetKey);
            if (currentTime - lastTime > 200) { // 10秒 = 200 ticks
                this.meleeAttackCounts.put(targetKey, 0);
                this.swingAttackCounts.put(targetKey, 0);
                this.burstAttackCounts.put(targetKey, 0);
            }
        }

        // 增加总攻击计数
        int currentCount = this.meleeAttackCounts.getOrDefault(targetKey, 0);
        this.meleeAttackCounts.put(targetKey, currentCount + 1);
        this.lastMeleeAttackTime.put(targetKey, currentTime);

        // 只有swing攻击才增加swing计数
        if (isSwingAttack) {
            int currentSwingCount = this.swingAttackCounts.getOrDefault(targetKey, 0);
            this.swingAttackCounts.put(targetKey, currentSwingCount + 1);
        }
    }

    /**
     * 获取对目标的近战攻击次数
     */
    public int getMeleeAttackCount(LivingEntity target) {
        if (target == null) return 0;

        String targetKey = this.getTargetKey(target);
        long currentTime = this.level().getGameTime();

        // 检查是否超时
        if (this.lastMeleeAttackTime.containsKey(targetKey)) {
            long lastTime = this.lastMeleeAttackTime.get(targetKey);
            if (currentTime - lastTime > 200) { // 10秒超时
                this.meleeAttackCounts.remove(targetKey);
                this.lastMeleeAttackTime.remove(targetKey);
                return 0;
            }
        }

        return this.meleeAttackCounts.getOrDefault(targetKey, 0);
    }

    /**
     * 清除目标的攻击记录
     */
    public void clearMeleeAttackRecord(LivingEntity target) {
        if (target == null) return;

        String targetKey = this.getTargetKey(target);
        this.meleeAttackCounts.remove(targetKey);
        this.lastMeleeAttackTime.remove(targetKey);
        this.swingAttackCounts.remove(targetKey);
        this.burstAttackCounts.remove(targetKey);
    }

    /**
     * 检查是否应该使用burst攻击
     */
    public boolean shouldUseBurstAttack(LivingEntity target) {
        if (target == null) return false;

        String targetKey = this.getTargetKey(target);
        int swingCount = this.swingAttackCounts.getOrDefault(targetKey, 0);
        int burstCount = this.burstAttackCounts.getOrDefault(targetKey, 0);

        // 每5次swing攻击后，下一次攻击就是burst攻击
        // 计算应该有多少次burst攻击
        int expectedBurstCount = swingCount / 5;

        return burstCount < expectedBurstCount;
    }

    /**
     * 增加burst攻击计数
     */
    public void incrementBurstAttackCount(LivingEntity target) {
        if (target == null) return;

        String targetKey = this.getTargetKey(target);
        int currentBurstCount = this.burstAttackCounts.getOrDefault(targetKey, 0);
        this.burstAttackCounts.put(targetKey, currentBurstCount + 1);
    }

    /**
     * 获取目标的唯一标识符
     */
    private String getTargetKey(LivingEntity target) {
        if (target instanceof Player player) {
            return "player:" + player.getName().getString();
        } else {
            return "entity:" + target.getUUID().toString();
        }
    }

    /**
     * 检查目标是否满足剑雨攻击条件
     */
    public boolean canUseRainOfSwords(LivingEntity target) {
        if (target == null) return false;

        // 检查距离：必须≥6格
        double distance = this.distanceTo(target);
        if (distance < 6.0D) return false;

        // 检查近战攻击次数：必须至少攻击过2次
        int attackCount = this.getMeleeAttackCount(target);
        if (attackCount < 2) return false;

        // 20%触发概率
        return this.random.nextFloat() < 0.2F;
    }

    /**
     * 更新格挡状态
     */
    private void updateBlockingState() {
        // 更新格挡冷却
        if (this.blockCooldownTicks > 0) {
            this.blockCooldownTicks--;
        }

        // 更新格挡持续时间
        if (this.isBlocking) {
            this.blockingTicks--;

            // 格挡期间面向攻击者
            if (this.blockingTarget != null && this.blockingTarget.isAlive()) {
                this.getLookControl().setLookAt(
                    this.blockingTarget.getX(),
                    this.blockingTarget.getEyeY(),
                    this.blockingTarget.getZ()
                );
            }

            // 格挡时间结束
            if (this.blockingTicks <= 0) {
                this.endBlocking();
            }
        }
    }

    /**
     * 尝试触发格挡
     */
    public boolean tryBlock(LivingEntity attacker, DamageSource damageSource) {
        // 检查是否可以格挡
        if (!this.canBlock(attacker, damageSource)) {
            return false;
        }

        // 30%概率触发格挡
        if (this.random.nextFloat() > BLOCK_CHANCE) {
            return false;
        }

        // 开始格挡
        this.startBlocking(attacker);
        return true;
    }

    /**
     * 检查是否可以格挡
     */
    private boolean canBlock(LivingEntity attacker, DamageSource damageSource) {
        // 基础条件检查
        if (this.isBlocking || this.blockCooldownTicks > 0) {
            return false;
        }

        // 不能在准备阶段格挡
        if (this.preparationTime > 0) {
            return false;
        }

        // 不能在执行攻击时格挡
        if (this.getAttackTicks() > 0) {
            return false;
        }

        // 必须有明确的攻击者
        if (attacker == null) {
            return false;
        }

        // 不格挡某些特殊伤害类型
        if (damageSource.is(DamageTypes.FALL) ||
            damageSource.is(DamageTypes.DROWN) ||
            damageSource.is(DamageTypes.IN_WALL)) {
            return false;
        }

        return true;
    }

    /**
     * 开始格挡
     */
    private void startBlocking(LivingEntity attacker) {
        this.isBlocking = true;
        this.blockingTicks = BLOCK_DURATION;
        this.blockingTarget = attacker;

        // 触发格挡动画
        this.triggerAnim("block_controller", "block_animation");

        // 停止移动
        this.getNavigation().stop();
        this.setSprinting(false);

        // 立即面向攻击者
        if (attacker != null) {
            this.getLookControl().setLookAt(
                attacker.getX(),
                attacker.getEyeY(),
                attacker.getZ()
            );
        }
    }

    /**
     * 结束格挡
     */
    private void endBlocking() {
        this.isBlocking = false;
        this.blockingTicks = 0;
        this.blockingTarget = null;
        this.blockCooldownTicks = BLOCK_COOLDOWN;

        // 可以在这里添加格挡成功后的反击逻辑
        this.onBlockSuccess();
    }

    /**
     * 格挡成功后的处理
     */
    private void onBlockSuccess() {
        // 格挡成功后可以考虑：
        // 1. 短暂的反击窗口
        // 2. 增加攻击速度
        // 3. 特殊效果

        // 这里暂时只是重置一些状态
        if (this.blockingTarget != null && this.blockingTarget.isAlive()) {
            // 可以添加反击逻辑
            // 例如：立即对攻击者进行一次快速攻击
        }
    }

    /**
     * 检查是否正在格挡
     */
    public boolean isBlocking() {
        return this.isBlocking;
    }

    /**
     * 检查是否可以被格挡的伤害
     */
    public boolean isBlockableDamage(DamageSource damageSource) {
        // 格挡期间免疫所有可格挡的伤害
        if (!this.isBlocking) {
            return false;
        }

        // 某些伤害类型无法格挡
        if (damageSource.is(DamageTypes.FALL) ||
            damageSource.is(DamageTypes.DROWN) ||
            damageSource.is(DamageTypes.IN_WALL) ||
            damageSource.is(DamageTypes.GENERIC_KILL)) {
            return false;
        }

        return true;
    }

    public List<Player> getPlayerInBoundingBoxWithInflate(double value) {
        List<Player> list = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(value));
        Stream<Player> stream = list.stream().filter(this::hasLineOfSight).filter(this::isTruePlayer);
        return new ArrayList<>(stream.toList());
    }

    public void selectDuelistFromNearestTarget() {
        List<String> list = new ArrayList<>();
        this.getPlayerInBoundingBoxWithInflate(24.0D).stream()
                .map(Player::getName).forEach(name -> list.add(name.getString()));
        boolean flag = this.currentDuelistName.isEmpty();
        if (!list.isEmpty() && flag && this.ticksDueling > 0) {
            int index = this.random.nextInt(list.size());
            this.triggerAnim(("duel_controller"), ("duel_animation"));
            this.currentDuelistName = list.get(index);
            this.triggerDuelingCount++;
            if (this.triggerDuelingCount == 1) {
                this.addEffect(BUFF_LIST.get(this.random.nextInt(BUFF_LIST.size())));
                this.preparationTime = 20;
                this.duelingMoment = true;
            } else {
                this.heal((this.getMaxHealth() * 0.1F));
            }

            LivingEntity attacker = this.getLastAttacker();
            if (attacker != null && attacker.isAlive()) {
                double dx = attacker.getX();
                double dy = attacker.getEyeY();
                double dz = attacker.getZ();
                this.getLookControl().setLookAt(dx, dy, dz);
            }
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.level() instanceof ServerLevel serverLevel) {
            float maxHealth = this.getMaxHealth();
            LivingEntity target = this.getTarget();

            // 仅每40tick刷新一次玩家相关的累计数据，降低开销
            if ((this.tickCount & 39) == 0) {
                serverLevel.players().forEach(player -> this.currentSavedName.add(player.getName().getString()));
                this.playerAlreadyHealFor.addAll(this.currentSavedName);
            }

            // 决斗触发检查
            boolean isHalfHealth = this.getHealth() < this.getMaxHealth() * 0.5F;
            if (isHalfHealth && !this.duelingMoment && this.ticksDueling == 2400) {
                this.selectDuelistFromNearestTarget();
            }

            // 战斗/非战斗移动
            if (target != null) {
                this.handleCombatMovement(target);
            } else {
                this.handleNonCombatMovement();
            }

            // Boss生命值扩展逻辑
            AttributeInstance health = this.getAttribute(TAAttributes.MAX_BOSS_HEALTH);
            if (health != null) {
                int size = this.currentSavedName.size() - this.playerAlreadyHealFor.size() - 1;
                if (size > 0) {
                    float extraValue = size * 200.0F;
                    health.setBaseValue(maxHealth + extraValue);
                    if (this.lastHurtByPlayer == null) {
                        this.setBossHealth((float) health.getBaseValue());
                    } else {
                        this.heal(extraValue);
                    }
                }
            }

            // 自愈逻辑
            AABB aabb = this.getBoundingBox().inflate(24.0D);
            List<Player> playerList = this.level().getEntitiesOfClass(Player.class, aabb);
            this.safeTime = playerList.isEmpty() ? this.safeTime + 1 : 0;
            if (this.safeTime > 100 && this.tickCount % 20 == 0) {
                this.heal((maxHealth * 0.05F));
            }

            // 重力开关
            if (this.getAttackState() == 0) {
                this.setNoGravity(false);
            }
        }
    }

    private void removeSpeedWhenNoTarget() {
        AttributeInstance instance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (instance != null && instance.getModifier(SPEED_MODIFIER_FOUND_TARGET) != null) {
            instance.removeModifier(SPEED_MODIFIER_FOUND_TARGET);
        }
    }

    /**
     * 获取当前移动速度（用于调试）
     */
    public double getCurrentMovementSpeed() {
        AttributeInstance instance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        return instance != null ? instance.getValue() : 0.0D;
    }

    /**
     * 检查是否处于奔跑状态
     */
    public boolean isInCombatSprint() {
        return this.isSprinting() && this.getTarget() != null;
    }

    /**
     * 调试移动状态
     */
    public void debugMovementStatus() {
        if (!this.level().isClientSide) {
            LivingEntity target = this.getTarget();
            double currentSpeed = this.getCurrentMovementSpeed();
            boolean sprinting = this.isSprinting();

            TheAurorian.LOGGER.info("=== 皎月女王移动状态 ===");
            TheAurorian.LOGGER.info("当前速度: {}", String.format("%.3f", currentSpeed));
            TheAurorian.LOGGER.info("是否冲刺: {}", sprinting);
            TheAurorian.LOGGER.info("是否有目标: {}", target != null);
            if (target != null) {
                double distance = Math.sqrt(this.distanceToSqr(target));
                TheAurorian.LOGGER.info("目标距离: {}", String.format("%.2f", distance));
                TheAurorian.LOGGER.info("目标类型: {}", target.getClass().getSimpleName());
            }
            TheAurorian.LOGGER.info("========================");
        }
    }

    /**
     * 处理战斗状态下的移动
     */
    private void handleCombatMovement(LivingEntity target) {
        double distance = this.distanceToSqr(target);
        boolean shouldSprint = this.shouldSprintToTarget(target, distance);

        // 设置冲刺状态：仅在战斗奔跑阶段允许提速
        this.setSprinting(shouldSprint);
        if (shouldSprint && this.isCombatRunActive()) {
            this.addSpeedWhenFoundTarget();
        } else {
            this.removeSpeedWhenNoTarget();
        }
    }

    /**
     * 处理非战斗状态下的移动
     */
    private void handleNonCombatMovement() {
        // 停止冲刺
        this.setSprinting(false);
        // 移除战斗速度加成
        this.removeSpeedWhenNoTarget();

        // 重置攻击状态（如果不在决斗中且没有准备时间）
        if (!this.duelingMoment && this.preparationTime <= 0 && this.tickCount % 20 == 0) {
            this.setAttackState(0);
            this.setAttackTicks(0);
        }
    }

    /**
     * 判断是否应该向目标冲刺
     */
    private boolean shouldSprintToTarget(LivingEntity target, double distance) {
        // 基本距离判断：距离大于8格时冲刺
        if (distance <= 64.0D) { // 8格的平方
            return false;
        }

        // 如果目标在移动，更容易触发冲刺
        if (target.getDeltaMovement().lengthSqr() > 0.01D) {
            return distance > 36.0D; // 6格的平方
        }

        // 如果目标是玩家且在决斗中，总是冲刺
        if (this.duelingMoment && target instanceof Player) {
            return distance > 16.0D; // 4格的平方
        }

        // 默认距离判断
        return distance > 64.0D; // 8格的平方
    }

    private void addSpeedWhenFoundTarget() {
        AttributeInstance instance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL;
        if (instance != null && instance.getModifier(SPEED_MODIFIER_FOUND_TARGET) == null) {
            // 奔跑速度为漫游速度的120% (增加20%)
            instance.addTransientModifier(new AttributeModifier(SPEED_MODIFIER_FOUND_TARGET, 0.2F, operation));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.isAlive()) {
            long l = this.ticksCanOneHitMustKill + 1L;
            this.ticksCanOneHitMustKill = Math.min(l, 24000L);
            if (this.duelingMoment && --this.ticksDueling == 0) {
                this.triggerDuelingCount = 0;
                this.currentDuelistName = "";
                this.duelingMoment = false;
            }

            if (this.isNeutral && this.safeTime > 300 && this.getTarget() == null) {
                this.isNeutral = false;
            }

            if (++this.idleResetCounter >= 100) {
                this.idleResetCounter = 0;
                if (this.getTarget() == null && !this.duelingMoment && this.preparationTime <= 0) {
                    this.setAttackState(0);
                    this.setAttackTicks(0);
                }
            }

            if (this.preparationTime > 0) {
                --this.preparationTime;
            }

            // 处理脱战计时器
            if (this.outOfCombatTimer > 0) {
                --this.outOfCombatTimer;
                if (this.outOfCombatTimer == 0) {
                    // 脱战完成，确保清理战斗状态
                    this.onExitCombat();
                }
            }

            // 处理格挡状态
            this.updateBlockingState();

            // 主动清除任何可能被强制添加的中毒或凋零效果
            if (this.hasEffect(MobEffects.POISON)) {
                this.removeEffect(MobEffects.POISON);
            }
            if (this.hasEffect(MobEffects.WITHER)) {
                this.removeEffect(MobEffects.WITHER);
            }
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        if (damageSource.getEntity() instanceof ServerPlayer player) {
            player.setData(TAAttachmentTypes.IMMUNE_TO_PRESSURE, true);
        }

        super.die(damageSource);
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (!this.level().isClientSide) {
            if (this.deathTime == 1) {
                this.triggerAnim("defeat_controller", "defeat_animation");
            } else if (this.deathTime == 11) {
                this.triggerAnim("defeat_loop_controller", "defeat_loop_animation");
            }

            if (this.deathTime > 80 && !this.isRemoved()) {
                this.level().broadcastEntityEvent(this, (byte) 60);
                this.remove(RemovalReason.KILLED);
            }
        }
    }

    @Override
    public void onKilledTarget(LivingEntity target) {
        this.safeTime = 0;
        Holder<MobEffect> effect = TAMobEffects.MOON_BEFALL;

        if (target instanceof Player player) {
            if (this.hasEffect(effect)) {
                this.removeEffect(effect);
            }

            if (this.duelingMoment) {
                this.killedDuelistName.add(player.getName().getString());
                this.selectDuelistFromNearestTarget();
                this.heal((this.getMaxHealth() * 0.1F));
            }

            // 杀死玩家后，可以选择是否清除敌对状态
            // 这里保持敌对状态，让玩家复活后仍然是敌对的
            // 如果想要清除敌对状态，可以取消注释下面这行：
            // this.removeHostilePlayer(player);
        }

        // 清除目标的攻击记录
        this.clearMeleeAttackRecord(target);

        // 击杀目标后启动脱战机制
        this.startOutOfCombatTimer();
    }

    /**
     * 启动脱战计时器
     */
    private void startOutOfCombatTimer() {
        this.outOfCombatTimer = OUT_OF_COMBAT_DELAY;
        // 立即清除当前目标，但不立即进入脱战状态
        this.setTarget(null);
        // 停止冲刺状态
        this.setSprinting(false);
        // 移除战斗速度加成
        this.removeSpeedWhenNoTarget();
    }

    /**
     * 检查是否处于脱战状态
     */
    public boolean isOutOfCombat() {
        return this.outOfCombatTimer <= 0 && this.getTarget() == null;
    }

    /**
     * 重置脱战计时器（进入战斗状态）
     */
    private void resetOutOfCombatTimer() {
        this.outOfCombatTimer = 0;
    }

    /**
     * 脱战完成处理
     */
    private void onExitCombat() {
        // 确保停止冲刺
        this.setSprinting(false);
        // 确保移除战斗速度加成
        this.removeSpeedWhenNoTarget();
        // 重置攻击状态
        this.setAttackState(0);
        this.setAttackTicks(0);
        // 可以在这里添加其他脱战后的行为
    }

    @Override
    public void setTarget(@javax.annotation.Nullable LivingEntity target) {
        LivingEntity oldTarget = this.getTarget();
        super.setTarget(target);

        // 如果获得了新目标，重置脱战计时器并准备进入战斗状态
        if (target != null && target != oldTarget) {
            this.resetOutOfCombatTimer();
            // 立即开始战斗移动处理（在下一个tick中生效）
        }
        // 如果失去目标，立即处理非战斗移动
        else if (target == null && oldTarget != null) {
            this.handleNonCombatMovement();
            // 脱战计时器在击杀目标时启动，这里不启动
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.GHAST_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return this.isDamageSourceBlocked(damageSource) ? SoundEvents.ANVIL_PLACE : SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.GHAST_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.15F, 1.0F);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SafeTime", this.safeTime);
        compound.putInt("TicksDueling", this.ticksDueling);
        compound.putInt("PreparationTime", this.preparationTime);
        compound.putInt("TriggerDuelingCount", this.triggerDuelingCount);
        compound.putInt("FqmPySwordNum", this.fqmPySwordNum);
        compound.putBoolean("IsNeutral", this.isNeutral);
        compound.putBoolean("DuelingMoment", this.duelingMoment);
        compound.putString("CurrentDuelistName", this.currentDuelistName);
        compound.putLong("TicksCanOneHitMustKill", this.ticksCanOneHitMustKill);
        compound.put("KilledDuelistName", this.saveListTag(this.killedDuelistName));
        compound.put("CurrentSavedName", this.saveListTag(this.currentSavedName));
        compound.put("PlayerAlreadyHealFor", this.saveListTag(this.playerAlreadyHealFor));
        compound.put("HostilePlayerNames", this.saveListTag(this.hostilePlayerNames));
        compound.putInt("OutOfCombatTimer", this.outOfCombatTimer);

        // 保存格挡状态
        compound.putBoolean("IsBlocking", this.isBlocking);
        compound.putInt("BlockingTicks", this.blockingTicks);
        compound.putInt("BlockCooldownTicks", this.blockCooldownTicks);

        // 保存近战攻击计数器
        this.saveMeleeAttackData(compound);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.safeTime = compound.getInt("SafeTime");
        this.ticksDueling = compound.getInt("TicksDueling");
        this.preparationTime = compound.getInt("PreparationTime");
        this.triggerDuelingCount = compound.getInt("TriggerDuelingCount");
        this.fqmPySwordNum = compound.getInt("FqmPySwordNum");
        this.isNeutral = compound.getBoolean("IsNeutral");
        this.duelingMoment = compound.getBoolean("DuelingMoment");
        this.currentDuelistName = compound.getString("CurrentDuelistName");
        this.ticksCanOneHitMustKill = compound.getLong("TicksCanOneHitMustKill");
        ListTag listTagK = compound.getList("KilledDuelistName", 10);
        for (int i = 0; i < listTagK.size(); i++) {
            this.killedDuelistName.add(listTagK.getCompound(i).getString("Name"));
        }

        ListTag listTagC = compound.getList("CurrentSavedName", 10);
        for (int i = 0; i < listTagC.size(); i++) {
            this.currentSavedName.add(listTagC.getCompound(i).getString("Name"));
        }

        ListTag listTagT = compound.getList("PlayerAlreadyHealFor", 10);
        for (int i = 0; i < listTagT.size(); i++) {
            this.playerAlreadyHealFor.add(listTagT.getCompound(i).getString("Name"));
        }

        ListTag listTagH = compound.getList("HostilePlayerNames", 10);
        for (int i = 0; i < listTagH.size(); i++) {
            this.hostilePlayerNames.add(listTagH.getCompound(i).getString("Name"));
        }

        this.outOfCombatTimer = compound.getInt("OutOfCombatTimer");

        // 加载格挡状态
        this.isBlocking = compound.getBoolean("IsBlocking");
        this.blockingTicks = compound.getInt("BlockingTicks");
        this.blockCooldownTicks = compound.getInt("BlockCooldownTicks");
        // 注意：blockingTarget不保存，因为目标可能已经不存在

        // 加载近战攻击计数器
        this.loadMeleeAttackData(compound);
    }

    @Override
    protected void tickEffects() {
        Iterator<Holder<MobEffect>> iterator = this.activeEffects.keySet().iterator();
        try {
            while (iterator.hasNext()) {
                Holder<MobEffect> holder = iterator.next();
                MobEffectInstance instance = this.activeEffects.get(holder);
                if (!instance.tick(this, () -> this.onEffectUpdated(instance, true, null))) {
                    if (this.duelingMoment && BUFF_LIST.contains(instance)) {
                        this.addEffect(BUFF_LIST.get(this.random.nextInt(BUFF_LIST.size())));
                        this.triggerAnim("buff_controller", "buff_animation");
                    }
                    if (!this.level().isClientSide) {
                        iterator.remove();
                        this.onEffectRemoved(instance);
                    }
                } else if (instance.getDuration() % 600 == 0) {
                    this.onEffectUpdated(instance, false, null);
                }
            }
        } catch (ConcurrentModificationException ignored) {}
        if (this.effectsDirty) {
            if (!this.level().isClientSide) {
                this.updateInvisibilityStatus();
                this.updateGlowingStatus();
            }

            this.effectsDirty = false;
        }

        List<ParticleOptions> list = this.entityData.get(DATA_EFFECT_PARTICLES);
        if (!list.isEmpty()) {
            boolean flag = this.entityData.get(DATA_EFFECT_AMBIENCE_ID);
            int i = this.isInvisible() ? 15 : 4;
            int j = flag ? 5 : 1;
            if (this.random.nextInt(i * j) == 0) {
                this.level().addParticle(Util.getRandom(list, this.random), this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 1.0, 1.0, 1.0);
            }
        }
    }

    @Override
    public boolean isInWall() {
        return false;
    }

    @Override
    protected float getWaterSlowDown() {
        return 1.0F;
    }

    @Override
    public boolean canSwimInFluidType(FluidType type) {
        return true;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource damageSource) {
        // 完全免疫摔落伤害
        return false;
    }

    @Override
    protected void actuallyHurt(DamageSource damageSource, float damageAmount) {
        // 额外的伤害类型检查，防止某些伤害绕过hurt方法
        if (damageSource.is(DamageTypes.MAGIC) ||
            damageSource.is(DamageTypes.INDIRECT_MAGIC) ||
            damageSource.is(DamageTypes.WITHER) ||
            damageSource.is(DamageTypeTags.IS_FIRE) ||
            damageSource.is(DamageTypes.FALL)) {
            return; // 不执行实际伤害
        }

        // 检查药水相关伤害源
        if (damageSource.getDirectEntity() instanceof net.minecraft.world.entity.projectile.ThrownPotion ||
            damageSource.getDirectEntity() instanceof net.minecraft.world.entity.AreaEffectCloud) {
            return; // 不执行实际伤害
        }

        super.actuallyHurt(damageSource, damageAmount);
    }

    @Override
    public boolean ignoreExplosion(Explosion explosion) {
        return explosion.getDirectSourceEntity() instanceof MoonQueen;
    }

    @Override
    public boolean removeEffect(Holder<MobEffect> effect) {
        return !effect.is(TAMobEffectTags.MOON_QUEEN_ONLY) && super.removeEffect(effect);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        MobEffect effect = effectInstance.getEffect().value();

        // 永久免疫中毒效果
        if (effect == MobEffects.POISON) {
            return false;
        }

        // 永久免疫凋零效果
        if (effect == MobEffects.WITHER) {
            return false;
        }

        // 决斗时免疫所有负面效果
        if (this.duelingMoment && !effect.isBeneficial()) {
            return false;
        }

        return super.canBeAffected(effectInstance);
    }



    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        // 对特定伤害类型的额外免疫检查
        if (damageSource.is(DamageTypes.MAGIC) ||
            damageSource.is(DamageTypes.INDIRECT_MAGIC) ||
            damageSource.is(DamageTypes.WITHER)) {
            return true;
        }

        // 对药水相关伤害源的免疫
        if (damageSource.getDirectEntity() instanceof net.minecraft.world.entity.projectile.ThrownPotion ||
            damageSource.getDirectEntity() instanceof net.minecraft.world.entity.AreaEffectCloud) {
            return true;
        }

        return super.isInvulnerableTo(damageSource);
    }

    /**
     * 调试方法：检查皎月女王的免疫状态
     */
    public void debugImmunityStatus() {
        if (!this.level().isClientSide) {
            TheAurorian.LOGGER.info("=== 皎月女王免疫状态检查 ===");
            TheAurorian.LOGGER.info("当前效果: {}", this.getActiveEffects().stream()
                .map(effect -> effect.getEffect().value().getDescriptionId())
                .collect(java.util.stream.Collectors.joining(", ")));
            TheAurorian.LOGGER.info("是否有中毒效果: {}", this.hasEffect(MobEffects.POISON));
            TheAurorian.LOGGER.info("是否有凋零效果: {}", this.hasEffect(MobEffects.WITHER));
            TheAurorian.LOGGER.info("实体类型是否火焰免疫: {}", this.getType().fireImmune());
            TheAurorian.LOGGER.info("===============================");
        }
    }

    /**
     * 测试方法：尝试手动添加中毒效果来测试免疫系统
     */
    public void testPoisonImmunity() {
        if (!this.level().isClientSide) {
            TheAurorian.LOGGER.info("=== 测试皎月女王中毒免疫 ===");

            // 测试canBeAffected方法
            MobEffectInstance poisonEffect = new MobEffectInstance(MobEffects.POISON, 200, 1);
            boolean canBeAffected = this.canBeAffected(poisonEffect);
            TheAurorian.LOGGER.info("canBeAffected(中毒): {}", canBeAffected);

            // 测试实际添加效果
            boolean addResult = this.addEffect(poisonEffect);
            TheAurorian.LOGGER.info("addEffect(中毒)结果: {}", addResult);
            TheAurorian.LOGGER.info("添加后是否有中毒效果: {}", this.hasEffect(MobEffects.POISON));

            // 测试凋零效果
            MobEffectInstance witherEffect = new MobEffectInstance(MobEffects.WITHER, 200, 1);
            boolean canBeAffectedWither = this.canBeAffected(witherEffect);
            TheAurorian.LOGGER.info("canBeAffected(凋零): {}", canBeAffectedWither);

            boolean addResultWither = this.addEffect(witherEffect);
            TheAurorian.LOGGER.info("addEffect(凋零)结果: {}", addResultWither);
            TheAurorian.LOGGER.info("添加后是否有凋零效果: {}", this.hasEffect(MobEffects.WITHER));

            TheAurorian.LOGGER.info("===============================");
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        boolean noLastHurt = this.lastHurtByPlayer == null && this.getLastHurtByMob() == null;

        if (itemInHand.is(Tags.Items.NETHER_STARS) && noLastHurt) {
            // 使用下界之星可以：
            // 1. 将皎月女王设为中立状态
            // 2. 清除该玩家的敌对状态
            this.isNeutral = true;
            this.removeHostilePlayer(player);
            itemInHand.consume(1, player);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        DamageSource source = this.damageSources().mobAttack(this);
        if (entity instanceof LivingEntity livingEntity && !this.isValidTarget(livingEntity)) {
            this.setTarget(null);
            return false;
        }

        if (this.level() instanceof ServerLevel serverLevel) {
            f += EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), entity, source, f);
            if (this.hasEffect(TAMobEffects.MOON_BEFALL) || !(entity instanceof Player)) {
                this.heal(this.getMaxHealth() * 0.2F);
                entity.kill();
                return true;
            }
        }

        if (this.hasEffect(TAMobEffects.MOON_OF_VENGEANCE)) {
            f *= 2.0F;
        }

        boolean flag = entity.hurt(source, f);
        if (flag) {
            float f1 = this.getKnockback(entity, source);
            if (f1 > 0.0F && entity instanceof LivingEntity livingEntity) {
                float value = this.getYRot() * ((float)Math.PI / 180.0F);
                livingEntity.knockback(f1 * 0.5F, Mth.sin(value), -Mth.cos(value));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }

            if (this.level() instanceof ServerLevel serverLevel) {
                EnchantmentHelper.doPostAttackEffects(serverLevel, entity, source);
            }

            if (entity instanceof ServerPlayer player) {
                AttachmentType<Integer> type = TAAttachmentTypes.UNINTERRUPTED_HURT_BY_MOON_QUEEN_COUNT.get();
                player.setData(type, player.getData(type) + 1);
                if (player.getData(type) >= 10) {
                    player.addEffect(new MobEffectInstance(TAMobEffects.LACERATION, 100));
                }
            }

            this.setLastHurtMob(entity);
            this.playAttackSound();
            if (this.hasEffect(TAMobEffects.CRESCENT)) {
                this.heal(f / 2.0F);
            }
        }

        return flag;
    }

    @Override
    public boolean isDamageSourceBlocked(DamageSource source) {
        Entity entity = source.getDirectEntity();
        boolean flag = entity instanceof AbstractArrow arrow && arrow.getPierceLevel() > 0;
        if (!source.is(DamageTypeTags.BYPASSES_SHIELD) && !flag) {
            Vec3 vec32 = source.getSourcePosition();
            if (vec32 != null) {
                Vec3 vec3 = this.getViewVector(1.0F);
                Vec3 vec31 = vec32.vectorTo(this.position()).normalize();
                vec31 = new Vec3(vec31.x, 0.0D, vec31.z);
                return vec31.dot(vec3) < 0.0D && this.random.nextFloat() <= 0.3F;
            }
        }

        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity entity = source.getEntity();
        boolean shouldImmuneRangedAttack = this.duelingMoment && source.getDirectEntity() instanceof Projectile;
        boolean isPreparingAnimation = this.preparationTime > 0 && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY);

        // 格挡系统检查 - 在所有其他检查之前
        if (this.isBlockableDamage(source)) {
            return false; // 格挡成功，完全免疫伤害
        }

        // 尝试触发格挡（在伤害发生前）
        if (entity instanceof LivingEntity attacker && this.tryBlock(attacker, source)) {
            return false; // 格挡触发成功，免疫此次伤害
        }

        // 免疫摔落伤害
        if (source.is(DamageTypes.FALL)) {
            return false;
        }

        // 免疫火焰伤害（实体类型已设置fireImmune，这里作为额外保护）
        if (source.is(DamageTypeTags.IS_FIRE)) {
            return false;
        }

        // 免疫魔法伤害（包括药水伤害）
        if (source.is(DamageTypes.MAGIC) || source.is(DamageTypes.INDIRECT_MAGIC)) {
            return false;
        }

        // 免疫中毒和凋零伤害
        if (source.is(DamageTypes.WITHER)) {
            return false;
        }

        // 检查是否为药水投掷物造成的伤害
        if (source.getDirectEntity() instanceof net.minecraft.world.entity.projectile.ThrownPotion) {
            return false;
        }

        // 检查是否为滞留药水造成的伤害
        if (source.getDirectEntity() instanceof net.minecraft.world.entity.AreaEffectCloud) {
            return false;
        }

        // 检查是否为药水投掷物造成的伤害
        if (source.getDirectEntity() instanceof net.minecraft.world.entity.projectile.ThrownPotion) {
            return false;
        }

        // 检查是否为滞留药水造成的伤害
        if (source.getDirectEntity() instanceof net.minecraft.world.entity.AreaEffectCloud) {
            return false;
        }

        if (this.isInvulnerableTo(source) || this.level().isClientSide || this.isDeadOrDying()) {
            return false;
        } else if (shouldImmuneRangedAttack || isPreparingAnimation) {
            return false;
        } else if (entity instanceof Player player && this.duelingMoment && this.triggerDuelingCount > 0
                && !this.currentDuelistName.equals(player.getName().getString())) {
            return false;
        } else {
            // 若为玩家，先加入敌对列表（允许第一下造成伤害）
            if (entity instanceof Player player) {
                this.addHostilePlayer(player);
            }
            // 非玩家且不在有效目标列表，则拒绝伤害
            if (entity instanceof LivingEntity livingEntity && !(livingEntity instanceof Player) && !this.isValidTarget(livingEntity)) {
                this.setTarget(null);
                return false;
            }

            this.isNeutral = false;
            this.safeTime = 0;
            this.noActionTime = 0;
            float f = amount;
            boolean flag = false;

            if (amount > 0.0F && this.isDamageSourceBlocked(source)) {
                if (!source.is(DamageTypeTags.IS_PROJECTILE) && source.getDirectEntity() instanceof LivingEntity livingEntity) {
                    this.blockUsingShield(livingEntity);
                    this.triggerAnim("block_controller", "block_animation");
                    this.level().broadcastEntityEvent(this, (byte)29);
                }
                amount = 0.0F;
                flag = true;
            }

            if (this.hasEffect(TAMobEffects.BLESS_OF_MOON)) {
                amount *= 0.5F;
            }

            boolean flag1 = true;
            if (this.invulnerableTime > 10.0F && !source.is(DamageTypeTags.BYPASSES_COOLDOWN)) {
                if (amount <= this.lastHurt) {
                    return false;
                }

                this.actuallyHurt(source, amount - this.lastHurt);
                this.lastHurt = amount;
                flag1 = false;
            } else {
                this.lastHurt = amount;
                this.invulnerableTime = 20;
                this.actuallyHurt(source, amount);
                this.hurtDuration = 10;
                this.hurtTime = this.hurtDuration;
            }

            if (entity != null) {
                if (entity instanceof LivingEntity livingEntity
                        && !source.is(DamageTypeTags.NO_ANGER) && (!source.is(DamageTypes.WIND_CHARGE)
                        || !this.getType().is(EntityTypeTags.NO_ANGER_FROM_WIND_CHARGE))) {
                    this.setLastHurtByMob(livingEntity);
                }

                if (entity instanceof Player player1) {
                    this.lastHurtByPlayerTime = 100;
                    this.lastHurtByPlayer = player1;
                }
            }

            if (flag1) {
                if (flag) {
                    this.level().broadcastEntityEvent(this, (byte)29);
                } else {
                    this.level().broadcastDamageEvent(this, source);
                }

                if (!source.is(DamageTypeTags.NO_IMPACT) && !flag) {
                    this.markHurt();
                }

                if (!source.is(DamageTypeTags.NO_KNOCKBACK)) {
                    double d0 = 0.0F;
                    double d1 = 0.0F;
                    if (source.getDirectEntity() instanceof Projectile projectile) {
                        DoubleDoubleImmutablePair pair = projectile.calculateHorizontalHurtKnockbackDirection(this, source);
                        d0 = -pair.leftDouble();
                        d1 = -pair.rightDouble();
                    } else if (source.getSourcePosition() != null) {
                        d0 = source.getSourcePosition().x() - this.getX();
                        d1 = source.getSourcePosition().z() - this.getZ();
                    }

                    this.knockback(0.4F, d0, d1);
                }
            }

            if (this.isDeadOrDying()) {
                if (!this.checkTotemDeathProtection(source)) {
                    if (flag1) {
                        this.makeSound(this.getDeathSound());
                    }

                    this.die(source);
                }
            } else if (flag1) {
                this.playHurtSound(source);
            }

            if (!flag) {
                this.lastDamageSource = source;
                this.lastDamageStamp = this.level().getGameTime();
            }

            if (entity instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.PLAYER_HURT_ENTITY.trigger(serverPlayer, this, source, f, amount, flag);
            }

            return !flag;
        }
    }

    @Override
    public boolean checkTotemDeathProtection(DamageSource damageSource) {
        if (this.ticksCanOneHitMustKill == 24000L) {
            this.triggerAnim(("moon_befall_controller"), ("moon_befall_animation"));
            this.addEffect(new MobEffectInstance(TAMobEffects.MOON_BEFALL, 200));
            this.ticksCanOneHitMustKill = this.level().getDayTime() % 24000L;
            Map<AttributeInstance, Double> map = new HashMap<>();
            map.put(this.getAttribute(Attributes.ARMOR), 30.0D);
            map.put(this.getAttribute(Attributes.ARMOR_TOUGHNESS), 20.0D);
            map.put(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE), 1.0D);
            map.put(this.getAttribute(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE), 1.0D);
            map.forEach(AttributeInstance::setBaseValue);
            this.duelingMoment = false;
            this.preparationTime = 26;
            this.setBossHealth(1.0F);
            this.setAttackState(4);
            return true;
        } else {
            return false;
        }
    }

    private class MoonQueenNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

        public MoonQueenNearestAttackableTargetGoal(Mob mob, Class<T> targetType, boolean mustSee) {
            super(mob, targetType, mustSee);
        }

        public MoonQueenNearestAttackableTargetGoal(Mob mob, Class<T> targetType, boolean mustSee, Predicate<LivingEntity> targetPredicate) {
            super(mob, targetType, mustSee, targetPredicate);
        }

        @Override
        public boolean canUse() {
            // 如果皎月女王处于中立状态，不攻击任何目标
            if (isNeutral) {
                return false;
            }

            // 如果正在脱战过程中，不寻找新目标（除非是决斗）
            if (outOfCombatTimer > 0 && (currentDuelistName.isEmpty() || ticksDueling <= 0)) {
                return false;
            }

            AABB aabb = getBoundingBox().inflate(24.0D);
            List<Player> playerList = level().getEntitiesOfClass(Player.class, aabb);

            // 决斗模式优先级最高
            if (!currentDuelistName.isEmpty() && ticksDueling > 0) {
                for (Player player : playerList) {
                    String name = player.getName().getString();
                    if (name.equals(currentDuelistName)) {
                        if (!isTruePlayer(player)) {
                            currentDuelistName = "";
                            selectDuelistFromNearestTarget();
                            return false;
                        }
                        this.target = player;
                        return true;
                    }
                }
            }

            // 随机间隔检查
            int r = this.mob.getRandom().nextInt(this.randomInterval);
            if (this.randomInterval > 0 && r != 0) {
                return false;
            }

            // 寻找目标的优先级：
            // 1. 敌对玩家（攻击过皎月女王的玩家）
            // 2. 其他敌对生物
            LivingEntity foundTarget = this.findPriorityTarget();

            if (foundTarget != null && isValidTarget(foundTarget)) {
                this.target = foundTarget;
                return true;
            }

            return false;
        }

        /**
         * 按优先级寻找目标
         */
        private LivingEntity findPriorityTarget() {
            AABB aabb = getBoundingBox().inflate(24.0D);

            // 优先寻找敌对玩家
            List<Player> hostilePlayers = level().getEntitiesOfClass(Player.class, aabb)
                .stream()
                .filter(player -> hostilePlayerNames.contains(player.getName().getString()))
                .filter(player -> isTruePlayer(player))
                .toList();

            if (!hostilePlayers.isEmpty()) {
                // 返回最近的敌对玩家
                return hostilePlayers.stream()
                    .min((p1, p2) -> Double.compare(
                        distanceToSqr(p1),
                        distanceToSqr(p2)
                    ))
                    .orElse(null);
            }

            // 如果没有敌对玩家，寻找其他敌对生物
            List<LivingEntity> hostileMobs = level().getEntitiesOfClass(LivingEntity.class, aabb)
                .stream()
                .filter(entity -> !(entity instanceof Player))
                .filter(entity -> isValidTarget(entity))
                .toList();

            if (!hostileMobs.isEmpty()) {
                // 返回最近的敌对生物
                return hostileMobs.stream()
                    .min((m1, m2) -> Double.compare(
                        distanceToSqr(m1),
                        distanceToSqr(m2)
                    ))
                    .orElse(null);
            }

            return null;
        }

    }

    private class MoonQueenBodyRotationControl extends BodyRotationControl {

        public MoonQueenBodyRotationControl(Mob mob) {
            super(mob);
        }

        @Override
        public void clientTick() {
            if (getAttackState() != 0) {
                yHeadRot = getAttackYRot();
                yBodyRot = getAttackYRot();
            } else {
                LivingEntity target = getTarget();
                if (target != null) {
                    double dx = target.getX() - getX();
                    double dz = target.getZ() - getZ();
                    yHeadRot = (float) (Mth.atan2(dz, dx) * (180.0D / Math.PI)) - 90.0F;
                    yBodyRot = yHeadRot;
                } else {
                    super.clientTick();
                }
            }
        }
    }

    /**
     * 调试格挡状态
     */
    public void debugBlockingStatus() {
        if (!this.level().isClientSide) {
            TheAurorian.LOGGER.info("=== 皎月女王格挡状态 ===");
            TheAurorian.LOGGER.info("是否正在格挡: {}", this.isBlocking);
            TheAurorian.LOGGER.info("格挡剩余时间: {} ticks", this.blockingTicks);
            TheAurorian.LOGGER.info("格挡冷却时间: {} ticks", this.blockCooldownTicks);
            TheAurorian.LOGGER.info("格挡目标: {}", this.blockingTarget != null ? this.blockingTarget.getName().getString() : "无");
            TheAurorian.LOGGER.info("========================");
        }
    }

    /**
     * 强制结束格挡（用于调试或特殊情况）
     */
    public void forceEndBlocking() {
        if (this.isBlocking) {
            this.endBlocking();
        }
    }

    /**
     * 检查是否可以开始新的攻击阶段
     * 格挡期间不能开始新的攻击
     */
    public boolean canStartNewAttackPhase() {
        return !this.isBlocking && this.blockingTicks <= 0;
    }

}