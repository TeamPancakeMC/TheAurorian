package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.entities.ai.goal.GiantCrabDoNothingGoal;
import cn.teampancake.theaurorian.common.entities.ai.goal.LookAtTargetGoal;
import cn.teampancake.theaurorian.common.entities.ai.goal.MoveToTargetGoal;
import cn.teampancake.theaurorian.common.entities.phase.*;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
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

public class SnowTundraGiantCrab extends TAMonster implements GeoEntity, NeutralMob {

    protected static final EntityDataAccessor<Float> ATTACK_Y_ROT = SynchedEntityData.defineId(SnowTundraGiantCrab.class, EntityDataSerializers.FLOAT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;

    public SnowTundraGiantCrab(EntityType<? extends SnowTundraGiantCrab> type, Level level) {
        super(type, level);
        this.attackManager = new AttackManager<>(this, List.of(
                new SnowTundraGiantCrabMeleePhase(),
                new SnowTundraGiantCrabStartHidePhase(),
                new SnowTundraGiantCrabHidePhase(),
                new SnowTundraGiantCrabStopHidePhase()
        ));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new GiantCrabDoNothingGoal(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MoveToTargetGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtTargetGoal(this));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, Boolean.TRUE));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, Boolean.TRUE));
    }

    @Override
    public void setDeltaMovement(Vec3 deltaMovement) {
        super.setDeltaMovement(getAttackState() == SnowTundraGiantCrabHidePhase.ID ? Vec3.ZERO : deltaMovement);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_Y_ROT, 0f);
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

    @Override
    protected BodyRotationControl createBodyControl() {
        return new GiantCrabBodyRotationControl(this);
    }

    public static boolean checkSpawnRules(EntityType<SnowTundraGiantCrab> giantCrab, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return !level.getBlockState(pos.below()).is(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON) && checkAnyLightMonsterSpawnRules(giantCrab, level, spawnType, pos, random);
    }

    public static <T extends SnowTundraGiantCrab> AnimationController<T> hideController(T animatable) {
        return new AnimationController<>(animatable, "hide", 0, (state) -> state.getAnimatable().getAttackState() ==
                SnowTundraGiantCrabHidePhase.ID ? state.setAndContinue(RawAnimation.begin().thenLoop("misc.break")) : PlayState.STOP);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkIdleController(this));
        controllers.add(new AnimationController<>(this, "smash_controller", state -> PlayState.STOP)
                .triggerableAnim("smash_animation", RawAnimation.begin().thenPlay("attack.smash")).transitionLength(5));
        controllers.add(new AnimationController<>(this, "swing_controller", state -> PlayState.STOP)
                .triggerableAnim("swing_animation", DefaultAnimations.ATTACK_SWING).transitionLength(5));
        controllers.add(new AnimationController<>(this, "start_hide_controller", state -> PlayState.STOP)
                .triggerableAnim("start_hide_animation", RawAnimation.begin().thenPlay("misc.break_begin")).transitionLength(5));
        controllers.add(SnowTundraGiantCrab.hideController(this));
        controllers.add(new AnimationController<>(this, "stop_hide_controller", state -> PlayState.STOP)
                .triggerableAnim("stop_hide_animation", RawAnimation.begin().thenPlay("misc.break_awake")).transitionLength(5));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public float getAttackYRot() {
        return this.entityData.get(ATTACK_Y_ROT);
    }

    public void setAttackYRot(float attackYRot) {
        this.entityData.set(ATTACK_Y_ROT, attackYRot);
    }

    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return this.getAttackState() == SnowTundraGiantCrabHidePhase.ID ? super.getDimensions(pose).scale(1, 0.28f) : super.getDimensions(pose);
    }

    @Override
    public void tick() {
        super.tick();
        this.refreshDimensions();
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
    public void knockback(double strength, double x, double z) {
        super.knockback(this.random.nextBoolean() ? 0.0D : strength, x, z);
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

    private class GiantCrabBodyRotationControl extends BodyRotationControl {

        public GiantCrabBodyRotationControl(Mob mob) {
            super(mob);
        }

        public void clientTick() {
            if (getAttackState() == SnowTundraGiantCrabHidePhase.ID || getAttackState() == SnowTundraGiantCrabStartHidePhase.ID) {
                SnowTundraGiantCrab.this.yBodyRot = 0;
            } else if (getAttackState() == SnowTundraGiantCrabMeleePhase.ID) {
                SnowTundraGiantCrab.this.yBodyRot = SnowTundraGiantCrab.this.getAttackYRot();
            } else {
                super.clientTick();
            }
        }

    }

}