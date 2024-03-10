package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.entities.projectile.EyeOfDisturbedEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class DisturbedHollow extends Monster {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    public DisturbedHollow(EntityType<? extends DisturbedHollow> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ShootDisturbedEyeGoal(this));
        this.goalSelector.addGoal(2, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MAX_HEALTH, 20.0F);
        builder.add(Attributes.MOVEMENT_SPEED, 0.5F);
        builder.add(Attributes.ATTACK_DAMAGE, 3.0F);
        builder.add(Attributes.FOLLOW_RANGE, 35.0D);
        builder.add(Attributes.ARMOR, 2.0F);
        return builder;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide()) {
            this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.walkAnimation.isMoving(), this.tickCount);
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 4) {
            this.attackAnimationState.startIfStopped(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        if (super.doHurtTarget(entity)) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 3;
    }

    private static class ShootDisturbedEyeGoal extends Goal {

        private final DisturbedHollow disturbedHollow;
        private int chargeTime;

        public ShootDisturbedEyeGoal(DisturbedHollow disturbedHollow) {
            setFlags(EnumSet.of(Flag.MOVE));
            this.disturbedHollow = disturbedHollow;
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.disturbedHollow.getTarget();
            return target != null && this.disturbedHollow.distanceToSqr(target) > this.getAttackReachSqr(target);
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse() || this.disturbedHollow.getNavigation().isDone();
        }

        @Override
        public void start() {
            this.chargeTime = 0;
            this.disturbedHollow.setAggressive(true);
            this.disturbedHollow.level().broadcastEntityEvent(this.disturbedHollow, (byte) 4);
        }

        @Override
        public void stop() {
            this.disturbedHollow.setAggressive(false);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = this.disturbedHollow.getTarget();
            if (target != null) {
                if (target.distanceToSqr(this.disturbedHollow) < 4096.0F && this.disturbedHollow.hasLineOfSight(target)) {
                    Level level = this.disturbedHollow.level();
                    ++this.chargeTime;
                    if (this.chargeTime == 20) {
                        this.disturbedHollow.setDeltaMovement(Vec3.ZERO);
                        Vec3 vec3 = this.disturbedHollow.getViewVector(1.0F);
                        double d2 = target.getX() - (this.disturbedHollow.getX() + vec3.x * 4.0D);
                        double d3 = target.getY(0.5D) - (0.5D + this.disturbedHollow.getY(0.5D));
                        double d4 = target.getZ() - (this.disturbedHollow.getZ() + vec3.z * 4.0D);
                        EyeOfDisturbedEntity eyeOfDisturbed = new EyeOfDisturbedEntity(level, this.disturbedHollow, d2, d3, d4);
                        level.levelEvent(1002, this.disturbedHollow.blockPosition(), 0);
                        double x = this.disturbedHollow.getX() + vec3.x * 5.0D;
                        double y = this.disturbedHollow.getY(0.5D) + 0.5D;
                        double z = eyeOfDisturbed.getZ() + vec3.z * 5.0D;
                        eyeOfDisturbed.setOwner(this.disturbedHollow);
                        eyeOfDisturbed.setPos(x, y, z);
                        level.addFreshEntity(eyeOfDisturbed);
                        this.chargeTime = -60;
                    }
                } else if (this.chargeTime > 0) {
                    --this.chargeTime;
                }
            }
        }

        private double getAttackReachSqr(LivingEntity attackTarget) {
            return this.disturbedHollow.getBbWidth() * 2.0F * this.disturbedHollow.getBbWidth() * 2.0F + attackTarget.getBbWidth();
        }

    }

}