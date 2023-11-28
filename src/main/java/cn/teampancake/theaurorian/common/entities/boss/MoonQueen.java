package cn.teampancake.theaurorian.common.entities.boss;

import cn.teampancake.theaurorian.common.entities.ai.ModRangedAttackGoal;
import cn.teampancake.theaurorian.registry.TAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumSet;

@ParametersAreNonnullByDefault
public class MoonQueen extends Monster {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState meleeAttackAnimationState = new AnimationState();
    public final AnimationState defendAnimationState = new AnimationState();

    private static final EntityDataAccessor<Boolean> WINDING_UP_CHARGE = SynchedEntityData.defineId(MoonQueen.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(MoonQueen.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CHARGE_HIT = SynchedEntityData.defineId(MoonQueen.class, EntityDataSerializers.BOOLEAN);

    private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(),
            BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);

    public MoonQueen(EntityType<? extends MoonQueen> type, Level level) {
        super(type, level);
        this.xpReward = 500;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
//        this.goalSelector.addGoal(1, new ChargeGoal(this));
//        this.goalSelector.addGoal(2, new SideStrafeGoal(this));
        this.goalSelector.addGoal(3, new MoonQueenAttackGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MAX_HEALTH, 200.0D);
        builder.add(Attributes.ATTACK_DAMAGE, 4.0D);
        builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.85D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.2D);
        builder.add(Attributes.FOLLOW_RANGE, 40.0F);
        builder.add(Attributes.ARMOR, 8.0F);
        return builder;
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

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isCharging() {
        return getEntityData().get(CHARGING);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(WINDING_UP_CHARGE, Boolean.FALSE);
        this.getEntityData().define(CHARGING, Boolean.FALSE);
        this.getEntityData().define(CHARGE_HIT, Boolean.FALSE);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) {
            this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.walkAnimation.isMoving(), this.tickCount);
            if (this.tickCount % 2 == 0 && this.getEntityData().get(WINDING_UP_CHARGE)) {
                double x = this.getX() + this.random.nextFloat();
                double z = this.getZ() + this.random.nextFloat();
                double xSpeed = this.random.nextGaussian() * 0.02D;
                double ySpeed = this.random.nextGaussian() * 0.1D;
                double zSpeed = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(ParticleTypes.ANGRY_VILLAGER, x, this.getRandomY(), z, xSpeed, ySpeed, zSpeed);
            }
        }

        if (this.getEntityData().get(CHARGE_HIT)) {
            if (this.level().isClientSide) {
                this.defendAnimationState.start(this.tickCount);
                this.level().playLocalSound(this.getOnPos(), SoundEvents.ANVIL_PLACE, SoundSource.PLAYERS, 1F, 1.5F, false);
            }

            this.getEntityData().set(CHARGE_HIT, false);
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 4) {
            this.meleeAttackAnimationState.startIfStopped(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (super.doHurtTarget(entity)) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 50));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(TAItems.MOONSTONE_SWORD.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(TAItems.KNIGHT_CHESTPLATE.get()));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(TAItems.KNIGHT_LEGGINGS.get()));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(TAItems.KNIGHT_BOOTS.get()));
    }

    @Override
    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    @Override
    protected boolean canRide(Entity entity) {
        return false;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    private static class MoonQueenAttackGoal extends MeleeAttackGoal {

        private final MoonQueen moonQueen;
        private int attackAnimationTick;

        public MoonQueenAttackGoal(MoonQueen moonQueen) {
            super(moonQueen, 1.35F, Boolean.FALSE);
            this.moonQueen = moonQueen;
        }

        @Override
        public void start() {
            super.start();
            this.attackAnimationTick = 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.attackAnimationTick = 40;
            this.moonQueen.meleeAttackAnimationState.stop();
        }

        @Override
        public void tick() {
            LivingEntity target = this.moonQueen.getTarget();
            if (target != null && this.attackAnimationTick <= 40) {
                this.moonQueen.getLookControl().setLookAt(target, 30.0F, 30.0F);
                this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                boolean flag0 = this.followingTargetEvenIfNotSeen || this.moonQueen.getSensing().hasLineOfSight(target);
                boolean flag1 = this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D;
                boolean flag2 = target.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D;
                boolean flag3 = this.moonQueen.getRandom().nextFloat() < 0.05F;
                double d0 = this.moonQueen.getPerceivedTargetDistanceSquareForMeleeAttack(target);
                if (this.moonQueen.isAlive() && this.attackAnimationTick == 0 && d0 <= this.getAttackReachSqr(target)
                        && this.moonQueen.doHurtTarget(target) && this.ticksUntilNextAttack <= 0) {
                    this.moonQueen.level().broadcastEntityEvent(this.moonQueen, (byte) 4);
                }
                ++this.attackAnimationTick;
                if (flag0 && this.ticksUntilNextPathRecalculation <= 0 && (flag1 || flag2 || flag3)) {
                    this.pathedTargetX = target.getX();
                    this.pathedTargetY = target.getY();
                    this.pathedTargetZ = target.getZ();
                    this.ticksUntilNextPathRecalculation = 4 + this.moonQueen.getRandom().nextInt(7);
                    if (d0 > 1024.0D) {
                        this.ticksUntilNextPathRecalculation += 10;
                    } else if (d0 > 256.0D) {
                        this.ticksUntilNextPathRecalculation += 5;
                    }

                    if (!this.moonQueen.getNavigation().moveTo(target, this.speedModifier)) {
                        this.ticksUntilNextPathRecalculation += 15;
                    }

                    this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
                }

                this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
                if (this.attackAnimationTick > 6 && this.attackAnimationTick <= 8) {
                    if (d0 <= this.getAttackReachSqr(target)) {
                        this.moonQueen.doHurtTarget(target);
                    }
                }

                if (this.attackAnimationTick > 12 && this.attackAnimationTick <= 14) {
                    if (d0 <= this.getAttackReachSqr(target)) {
                        this.moonQueen.doHurtTarget(target);
                    }
                }

                if (this.attackAnimationTick > 20) {
                    this.resetAttackCooldown();
                    if (this.moonQueen.level().isClientSide) {
                        this.moonQueen.meleeAttackAnimationState.stop();
                    }
                }
            }
        }

    }

    private static class ChargeGoal extends ModRangedAttackGoal {

        private int chargeTime = 0;
        private int maxChainCharges = 0;
        private int queuedChainCharges = 0;

        public ChargeGoal(Monster monster) {
            super(monster, 2, 20);
        }

        @Override
        public void stop() {
            super.stop();
            this.monster.stopUsingItem();
        }

        @Override
        public void tick() {
            LivingEntity target = this.monster.getTarget();
            if (this.chargeTime > 0) {
                this.chargeTime--;
            }
            switch (this.attackWindUp) {
                case 1 -> {
                    if (target != null) {
                        this.setAttackLocation(target);
                    }
                }
                case 0 -> {
                    this.monster.getLookControl().setLookAt(this.targetX, this.targetY + this.monster.getEyeY(), this.targetZ);
                    this.monster.getEntityData().set(WINDING_UP_CHARGE, false);
                    this.monster.getNavigation().moveTo(this.targetX, this.targetY, this.targetZ, 3.5D);
                    if (target != null) {
                        if (this.monster.distanceTo(target) <= 2) {
                            this.finishChargeAttack(target);
                        } else if (this.monster.getNavigation().isDone()) {
                            this.finishChargeAttack(null);
                        }
                    } else if (this.monster.getNavigation().isDone()) {
                        this.finishChargeAttack(null);
                    }
                }
            }
        }

        @Override
        public void startAttack() {
            this.chargeTime = 20;
            this.monster.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(TAItems.MOON_SHIELD.get()));
            this.monster.startUsingItem(InteractionHand.OFF_HAND);
            this.attackWindUp = 40 - this.monster.getRandom().nextInt(10);
            double healthScale = this.monster.getHealth() / this.monster.getMaxHealth();
            if (healthScale >= 0.75) {
                this.maxChainCharges = 0;
            } else if (healthScale >= 0.50) {
                this.maxChainCharges = 1;
            } else if (healthScale >= 0.25) {
                this.maxChainCharges = 2;
            } else if (healthScale >= 0) {
                this.maxChainCharges = 3;
            }
        }

        private void finishChargeAttack(@Nullable LivingEntity target) {
            this.attackCooldown = 60 - this.monster.getRandom().nextInt(10);
            this.monster.stopUsingItem();
            if (target != null) {
                Vec3 vec3 = this.monster.getDeltaMovement();
                this.monster.getEntityData().set(CHARGE_HIT, true);
                this.monster.doHurtTarget(target);
                target.setDeltaMovement(vec3.add(0.0D, 0.25D, 0.0D));
            } else {
                this.handleChainCharge();
            }

            this.monster.getEntityData().set(CHARGING, false);
        }

        private void handleChainCharge() {
            if (this.maxChainCharges != 0) {
                if (this.queuedChainCharges != 0) {
                    this.attackCooldown = 5;
                    this.queuedChainCharges--;
                } else {
                    this.queuedChainCharges = this.maxChainCharges;
                }
            }
        }

    }

    private static class SideStrafeGoal extends Goal {

        private enum Direction {
            LEFT, RIGHT
        }

        private final MoonQueen moonQueen;
        private Direction strafeDirection;
        private float strafeTimer;

        public SideStrafeGoal(MoonQueen moonQueen) {
            this.moonQueen = moonQueen;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.moonQueen.getTarget();
            if (target != null && this.moonQueen.distanceTo(target) <= 8.0F && !this.moonQueen.getEntityData().get(CHARGING)) {
                if (this.strafeTimer > 0) {
                    this.strafeTimer--;
                } else {
                    return this.strafeTimer == 0;
                }
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = this.moonQueen.getTarget();
            return target != null && !this.moonQueen.isCharging() && this.moonQueen.distanceTo(target) < 8.0F && this.strafeDirection != null;
        }

        @Override
        public void start() {
            this.moonQueen.getNavigation().stop();
            this.strafeDirection = this.getRandomDirection();
            this.strafeTimer = 10;
            this.moonQueen.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(TAItems.MOON_SHIELD.get()));
            this.moonQueen.startUsingItem(InteractionHand.OFF_HAND);
        }

        @Override
        public void stop() {
            this.moonQueen.stopUsingItem();
            this.moonQueen.getNavigation().stop();
            this.moonQueen.setXxa(0.0F);
        }

        @Override
        public void tick() {
            LivingEntity target = this.moonQueen.getTarget();
            if (target != null) {
                this.moonQueen.lookAt(target, 40.0F, 40.0F);
                this.moonQueen.setXxa(this.strafeDirection == Direction.RIGHT ? 0.3F : -0.3F);
                if (this.moonQueen.distanceTo(target) <= 2.5F) {
                    this.moonQueen.doHurtTarget(target);
                    this.strafeDirection = null;
                }
            }
        }

        private Direction getRandomDirection() {
            return this.moonQueen.getRandom().nextBoolean() ? Direction.LEFT : Direction.RIGHT;
        }

    }

}