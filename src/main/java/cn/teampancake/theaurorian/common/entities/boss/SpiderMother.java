package cn.teampancake.theaurorian.common.entities.boss;

import cn.teampancake.theaurorian.common.entities.ai.goal.ModRangedAttackGoal;
import cn.teampancake.theaurorian.common.entities.monster.Spiderling;
import cn.teampancake.theaurorian.common.entities.projectile.WebbingEntity;
import cn.teampancake.theaurorian.common.registry.TAAttributes;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
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

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumSet;

@ParametersAreNonnullByDefault
@SuppressWarnings("deprecation")
public class SpiderMother extends AbstractAurorianBoss {

    private static final EntityDataAccessor<Boolean> WINDING_UP_SPIT = SynchedEntityData.defineId(SpiderMother.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SPITTING = SynchedEntityData.defineId(SpiderMother.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HANGING = SynchedEntityData.defineId(SpiderMother.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(SpiderMother.class, EntityDataSerializers.BYTE);

    public SpiderMother(EntityType<? extends SpiderMother> type, Level level) {
        super(type, level);
        this.xpReward = 500;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new HangGoal(this));
        this.goalSelector.addGoal(2, new SpitGoal(this));
        this.goalSelector.addGoal(4, new LeapGoal(this, 0.7F));
        this.goalSelector.addGoal(4, new SpiderAttackGoal(this));
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
        builder.add(TAAttributes.MAX_BOSS_HEALTH.get(), 160.0D);
        builder.add(Attributes.ATTACK_DAMAGE, 3.0D);
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
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        final int size = 35;
        for (BlockPos pos : BlockPos.withinManhattan(this.getOnPos(), size, size, size)) {
            if (this.level().getBlockState(pos).is(TABlocks.MYSTICAL_BARRIER.get())) {
                this.level().destroyBlock(pos, false);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.setClimbing(this.horizontalCollision);
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
        return potionEffect.getEffect() == MobEffects.POISON || super.canBeAffected(potionEffect);
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
            if (this.hangTime % 50 == 0) {
                for (int i = 0; i < this.mob.getRandom().nextInt(5); i++) {
                    Spiderling spiderling = new Spiderling(TAEntityTypes.SPIDERLING.get(), this.mob.level());
                    spiderling.setPos(this.mob.getDeltaMovement().add(0.0F, -0.5F, 0.0F));
                    spiderling.setTarget(mob.getTarget());
                    this.mob.level().addFreshEntity(spiderling);
                }
            }
            this.hangTime--;
        }

    }

    private static class SpitGoal extends ModRangedAttackGoal {

        public SpitGoal(Monster monster) {
            super(monster, 3, 50);
        }

        @Override
        public void start() {
            super.start();
            LivingEntity target = this.monster.getTarget();
            if (target != null) {
                this.monster.lookAt(target, this.monster.getHeadRotSpeed() * 2, this.monster.getMaxHeadXRot() * 2);
            }
        }

        @Override
        public void tick() {
            LivingEntity target = this.monster.getTarget();
            if (target != null) {
                switch (this.attackWindUp) {
                    case 1 -> this.setAttackLocation(target);
                    case 0 -> {
                        this.monster.getLookControl().setLookAt(this.targetX, this.targetY + this.monster.getEyeY(), this.targetZ);
                        this.monster.getEntityData().set(WINDING_UP_SPIT, false);
                        this.finishSpit(target);
                    }
                    default -> {
                        this.monster.getLookControl().setLookAt(target.getX(), target.getY() + target.getEyeY(), target.getZ());
                        this.monster.getEntityData().set(WINDING_UP_SPIT, true);
                        this.attackWindUp--;
                    }
                }
            }
        }

        @Override
        public void setAttack(boolean bool) {
            this.monster.getEntityData().set(SPITTING, bool);
        }

        @Override
        public void startAttack() {
            this.attackWindUp = 20 - this.monster.getRandom().nextInt(10);
        }

        private void finishSpit(LivingEntity target) {
            this.attackCooldown = 30 - this.monster.getRandom().nextInt(10);
            WebbingEntity webbing = new WebbingEntity(this.monster, this.monster.level());
            double d0 = target.getX() - this.monster.getX();
            double d1 = target.getBoundingBox().minY + target.getBbHeight() / 3.0F - webbing.getY();
            double d2 = target.getZ() - this.monster.getZ();
            double d3 = Mth.sqrt((float) (d0 * d0 + d2 * d2));
            float pitch = 0.8F / (this.monster.getRandom().nextFloat() * 0.4F + 0.8F);
            webbing.shoot(d0, d1 + d3 * 0.1D, d2, 1F, 0F);
            this.monster.level().playSound(null, this.monster.getX(), this.monster.getY(), this.monster.getZ(), SoundEvents.CAT_HISS, SoundSource.HOSTILE, 0.8F, pitch);
            this.monster.level().addFreshEntity(webbing);
            this.monster.getEntityData().set(SPITTING, false);
        }

    }

    private static class LeapGoal extends LeapAtTargetGoal {

        public LeapGoal(Mob mob, float yd) {
            super(mob, yd);
        }

        @Override
        public boolean canUse() {
            this.target = this.mob.getTarget();
            if (this.target == null) return false;
            return this.mob.distanceToSqr(this.target) < 20 && this.mob.onGround();
        }

        @Override
        public void start() {
            Vec3 vec3 = this.mob.getDeltaMovement();
            Vec3 vec31 = new Vec3(this.target.getX() - this.mob.getX(), 0.0D, this.target.getZ() - this.mob.getZ());
            if (vec31.lengthSqr() > 1.0E-7D) {
                vec31 = vec31.normalize().scale(0.4D).add(vec3.scale(4.0D));
            }

            this.mob.setDeltaMovement(vec31.x, this.yd, vec31.z);
        }

        @Override
        public void tick() {
            this.mob.lookAt(this.target, this.mob.getHeadRotSpeed() * 2, this.mob.getMaxHeadXRot() * 2);
            this.mob.getLookControl().setLookAt(this.target, this.mob.getHeadRotSpeed(), this.mob.getMaxHeadXRot());
        }

    }

    private static class SpiderAttackGoal extends MeleeAttackGoal {

        public SpiderAttackGoal(PathfinderMob mob) {
            super(mob, 1.0D, true);
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !this.mob.isVehicle();
        }

        @Override
        public boolean canContinueToUse() {
            float f = this.mob.getLightLevelDependentMagicValue();
            if (f >= 0.5F && this.mob.getRandom().nextInt(100) == 0) {
                this.mob.setTarget(null);
                return false;
            } else {
                return super.canContinueToUse();
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return 4.0F + attackTarget.getBbWidth();
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