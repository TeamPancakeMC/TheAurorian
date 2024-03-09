package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.entities.ai.GiantCrabDoNothingGoal;
import cn.teampancake.theaurorian.common.entities.ai.LookAtTargetGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.UUID;

public class SnowTundraGiantCrab extends Monster implements GeoEntity, NeutralMob, MultiPhaseAttacker {
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int remainingPersistentAngerTime;
    private int safeTime;
    @Nullable
    private UUID persistentAngerTarget;

    private final AttackManager<SnowTundraGiantCrab> attackManager = new AttackManager<>(this, List.of(
            new SnowTundraGiantCrabMeleePhase(),
            new SnowTundraGiantCrabStartHidePhase(),
            new SnowTundraGiantCrabHidePhase(),
            new SnowTundraGiantCrabStopHidePhase()
    ));

    protected static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(SnowTundraGiantCrab.class, EntityDataSerializers.INT);
    public int getAttackState() {
        return entityData.get(ATTACK_STATE);
    }
    public void setAttackState(int attackState) {
        entityData.set(ATTACK_STATE, attackState);
    }

    protected static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(SnowTundraGiantCrab.class, EntityDataSerializers.INT);
    public int getAttackTicks() {
        return entityData.get(ATTACK_TICKS);
    }
    public void setAttackTicks(int attackTicks) {
        entityData.set(ATTACK_TICKS, attackTicks);
    }

    public SnowTundraGiantCrab(EntityType<? extends SnowTundraGiantCrab> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new GiantCrabDoNothingGoal(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, (1.0D), Boolean.FALSE) {
            @Override
            protected void checkAndPerformAttack(LivingEntity target, double dist) {
                // we use our custom attack manager
            }
        });
        this.goalSelector.addGoal(3, new LookAtTargetGoal(this));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, Boolean.TRUE));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, IronGolem.class, Boolean.TRUE));
    }

    @Override
    public void setDeltaMovement(Vec3 pDeltaMovement) {
        super.setDeltaMovement(getAttackState() == SnowTundraGiantCrabHidePhase.ID ? Vec3.ZERO : pDeltaMovement);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ATTACK_STATE, 0);
        entityData.define(ATTACK_TICKS, 0);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.3D);
        builder.add(Attributes.MAX_HEALTH, 125.0D);
        builder.add(Attributes.ATTACK_DAMAGE, 10.0D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.2D);
        builder.add(Attributes.FOLLOW_RANGE, 40.0F);
        builder.add(Attributes.ARMOR, 8.0D);
        return builder;
    }

    public static boolean checkSpawnRules(EntityType<SnowTundraGiantCrab> giantCrab, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return !level.getBlockState(pos.below()).is(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON) && checkAnyLightMonsterSpawnRules(giantCrab, level, spawnType, pos, random);
    }

    public static <T extends SnowTundraGiantCrab> AnimationController<T> hideController(T animatable) {
        return new AnimationController<>(animatable, "hide", 0, (state) -> state.getAnimatable().getAttackState() == SnowTundraGiantCrabHidePhase.ID ? state.setAndContinue(RawAnimation.begin().thenLoop("misc.break")) : PlayState.STOP);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkIdleController(this));
        controllers.add(new AnimationController<>(this, "smash_controller", state -> PlayState.STOP)
                .triggerableAnim("smash_animation", RawAnimation.begin().thenPlay("attack.smash")).transitionLength(5));
        controllers.add(new AnimationController<>(this, "swing_controller", state -> PlayState.STOP)
                .triggerableAnim("swing_animation", RawAnimation.begin().thenPlay("attack.swing")).transitionLength(5));
        controllers.add(new AnimationController<>(this, "start_hide_controller", state -> PlayState.STOP)
                .triggerableAnim("start_hide_animation", RawAnimation.begin().thenPlay("misc.break_begin")).transitionLength(5));
        controllers.add(SnowTundraGiantCrab.hideController(this));
        controllers.add(new AnimationController<>(this, "stop_hide_controller", state -> PlayState.STOP)
                .triggerableAnim("stop_hide_animation", RawAnimation.begin().thenPlay("misc.break_awake")).transitionLength(5));
        // controllers.add(DefaultAnimations.genericAttackAnimation(this, DefaultAnimations.ATTACK_SWING));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return getAttackState() == SnowTundraGiantCrabHidePhase.ID ? super.getDimensions(pPose).scale(1, 0.28f) : super.getDimensions(pPose);
    }

    @Override
    public void tick() {
        super.tick();
        refreshDimensions();
    }

    @Override
    protected void customServerAiStep() {
        if (this.safeTime <= 200) {
            ++this.safeTime;
        }

        if (this.safeTime > 160 && this.tickCount % 20 == 0) {
            this.heal(5.0F);
        }

        attackManager.tick();
    }

    @Override
    protected int decreaseAirSupply(int currentAir) {
        return currentAir;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FREEZE) || super.isInvulnerableTo(source);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.safeTime = 0;
            return super.hurt(source, amount);
        }
    }

    public boolean canReachTarget(double range) {
        LivingEntity target = getTarget();
        if (target == null) {
            return false;
        }
        for (LivingEntity livingEntity : level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, getBoundingBox().inflate(range))) {
            if (livingEntity.getUUID().equals(target.getUUID())) {
                return true;
            }
        }
        return false;
    }

    public void performMeleeAttack(double range) {
        LivingEntity target = getTarget();
        if (target == null) {
            return;
        }
        for (LivingEntity livingEntity : level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, getBoundingBox().inflate(range))) {
            if (livingEntity.getUUID().equals(target.getUUID())) {
                livingEntity.invulnerableTime = 0;
                doHurtTarget(livingEntity);
            }
        }
    }

    @Override
    public void knockback(double strength, double x, double z) {
        super.knockback(this.random.nextBoolean() ? 0.0D : strength, x, z);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("SafeTime", this.safeTime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.safeTime = compound.getInt("SafeTime");
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.remainingPersistentAngerTime = time;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID target) {
        this.persistentAngerTarget = target;
    }

    @Nullable @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }
}