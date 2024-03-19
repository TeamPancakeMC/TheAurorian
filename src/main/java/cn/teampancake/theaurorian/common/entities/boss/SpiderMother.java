package cn.teampancake.theaurorian.common.entities.boss;

import cn.teampancake.theaurorian.common.entities.phase.AttackManager;
import cn.teampancake.theaurorian.common.entities.phase.spidermother.*;
import cn.teampancake.theaurorian.common.registry.TAAttributes;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumSet;
import java.util.List;

@ParametersAreNonnullByDefault
@SuppressWarnings("deprecation")
public class SpiderMother extends AbstractAurorianBoss implements GeoEntity {

    private static final RawAnimation HATCH_BEGIN = RawAnimation.begin().thenPlay("attack.hatch_begin");
    private static final RawAnimation HATCH_HOLD = RawAnimation.begin().thenLoop("attack.hatch_hold");
    private static final RawAnimation HATCH_END = RawAnimation.begin().thenPlay("attack.hatch_end");
    private static final RawAnimation EIDOLON = RawAnimation.begin().thenPlay("attack.eidolon");
    private static final RawAnimation SLASH = RawAnimation.begin().thenPlay("attack.slash");
    private static final RawAnimation SMASH = RawAnimation.begin().thenPlay("attack.smash");
    private static final RawAnimation SPIT = RawAnimation.begin().thenPlay("attack.spit");
    private static final RawAnimation DEATH = RawAnimation.begin().thenPlay("misc.death");
    private static final EntityDataAccessor<Boolean> WINDING_UP_SPIT = SynchedEntityData.defineId(SpiderMother.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SPITTING = SynchedEntityData.defineId(SpiderMother.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HANGING = SynchedEntityData.defineId(SpiderMother.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(SpiderMother.class, EntityDataSerializers.BYTE);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SpiderMother(EntityType<? extends SpiderMother> type, Level level) {
        super(type, level);
        this.xpReward = 500;
        this.attackManager = new AttackManager<>(this, List.of(
                new SpiderMotherSlashPhase(), new SpiderMotherSmashPhase(),
                new SpiderMotherSpitPhase(), new SpiderMotherEidolonPhase(),
                new SpiderMotherHatchBeginPhase(),
                new SpiderMotherHatchHoldPhase(),
                new SpiderMotherHatchEndPhase()));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new HangGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new SpiderTargetGoal<>(this, Player.class));
        this.targetSelector.addGoal(2, new SpiderTargetGoal<>(this, Cow.class));
    }

    @NotNull
    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(TAAttributes.MAX_BOSS_HEALTH.get(), 300.0D);
        builder.add(Attributes.ATTACK_DAMAGE, 10.0D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder.add(Attributes.FOLLOW_RANGE, 50.0F);
        builder.add(Attributes.ARMOR, 2.0F);
        return builder;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(WINDING_UP_SPIT, Boolean.FALSE);
        this.entityData.define(SPITTING, Boolean.FALSE);
        this.entityData.define(HANGING, Boolean.FALSE);
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
    }

    private AnimationController<?> createControllers(String name, RawAnimation animation) {
        return new AnimationController<>(this, name + "_controller", state -> PlayState.STOP)
                .triggerableAnim(name + "_animation", animation).transitionLength(5);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkIdleController(this));
        controllers.add(this.createControllers("hatch_begin", HATCH_BEGIN));
        controllers.add(this.createControllers("hatch_hold", HATCH_HOLD));
        controllers.add(this.createControllers("hatch_end", HATCH_END));
        controllers.add(this.createControllers("eidolon", EIDOLON));
        controllers.add(this.createControllers("slash", SLASH));
        controllers.add(this.createControllers("smash", SMASH));
        controllers.add(this.createControllers("spit", SPIT));
        controllers.add(this.createControllers("death", DEATH));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.getBbHeight() * 0.5F;
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new WallClimberNavigation(this, level);
    }

    public boolean isHanging() {
        return this.getEntityData().get(HANGING);
    }

    public void setClimbing(boolean pClimbing) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (pClimbing) {
            b0 = (byte)(b0 | 1);
        } else {
            b0 = (byte)(b0 & -2);
        }

        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.setClimbing(this.horizontalCollision);
        }
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (!this.level().isClientSide) {
            if (this.deathTime == 1) {
                this.triggerAnim("death_controller", "death_animation");
            }

            if (this.deathTime > 20 && !this.isRemoved()) {
                this.level().broadcastEntityEvent(this, (byte) 60);
                this.remove(RemovalReason.KILLED);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide && this.tickCount % 2 == 0 && this.entityData.get(WINDING_UP_SPIT)) {
            double d0 = Mth.cos(this.xRotO / 180.0F * (float) Math.PI) * 1.6F;
            double d1 = Mth.sin(-this.yHeadRot / 180.0F * (float) Math.PI) * d0;
            double d2 = Mth.cos(this.yHeadRot / 180.0F * (float) Math.PI) * d0;
            double y = this.getY() + this.getEyeY() - 0.2F;
            double xSpeed = this.random.nextGaussian() * 0.02D;
            double ySpeed = this.random.nextGaussian() * 0.02D;
            double zSpeed = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(ParticleTypes.CLOUD, this.getX() * d1, y, this.getZ() + d2, xSpeed, ySpeed, zSpeed);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SPIDER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SPIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SPIDER_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState block) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
    }

    @Override
    public boolean onClimbable() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void makeStuckInBlock(BlockState state, Vec3 motionMultiplier) {
        if (!state.is(Blocks.COBWEB)) {
            super.makeStuckInBlock(state, motionMultiplier);
        }
    }

    @NotNull @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance potionEffect) {
        MobEffect effect = potionEffect.getEffect();
        return effect == MobEffects.POISON || effect == TAMobEffects.CRYSTALLIZATION.get() || super.canBeAffected(potionEffect);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return 1.1F;
    }

    private static class HangGoal extends Goal {

        private final Mob mob;
        private int hangCooldown = 0;
        private int hangTime = 0;
        private int hangingX;
        private int hangingY;
        private int hangingZ;

        public HangGoal(Mob mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.mob.getTarget();
            if (target != null) {
                if (this.hangCooldown == 0) {
                    this.hangingX = this.mob.getBlockX();
                    this.hangingY = this.mob.getBlockY();
                    this.hangingZ = this.mob.getBlockZ();
                    for (int y = 8; y <= 50; y++) {
                        BlockPos pos = new BlockPos(this.hangingX, this.hangingY, this.hangingZ);
                        if (this.mob.level().loadedAndEntityCanStandOn(pos.above(y), this.mob)) {
                            this.hangingY = this.hangingY + y - 3;
                            return true;
                        }
                    }
                } else if (this.hangCooldown > 0) {
                    this.hangCooldown--;
                }
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            if (this.hangTime == 0) {
                this.mob.getEntityData().set(HANGING, false);
                this.mob.setNoGravity(false);
                this.hangCooldown = 200;
                return false;
            } else {
                return true;
            }
        }

        @Override
        public boolean isInterruptable() {
            return false;
        }

        @Override
        public void start() {
            LivingEntity target = this.mob.getTarget();
            this.mob.getNavigation().stop();
            this.mob.getEntityData().set(HANGING, true);
            this.mob.setNoGravity(true);
            this.hangTime = 200;
            if (target != null) {
                this.mob.lookAt(target, this.mob.getHeadRotSpeed() * 2, this.mob.getMaxHeadXRot() * 2);
            }
        }

        @Override
        public void tick() {
            if (this.mob.getY() <= this.hangingY) {
                this.mob.setPos(hangingX, this.mob.position().y + 0.5D, hangingZ);
            }

            this.hangTime--;
        }

    }

    private static class SpiderTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

        public SpiderTargetGoal(Mob mob, Class<T> entityTypeToTarget) {
            super(mob, entityTypeToTarget, true);
        }

        @Override
        public boolean canUse() {
            float f = this.mob.getLightLevelDependentMagicValue();
            return !(f >= 0.5F) && super.canUse();
        }

    }

}