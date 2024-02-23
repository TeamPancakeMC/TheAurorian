package cn.teampancake.theaurorian.common.entities.boss;

import cn.teampancake.theaurorian.client.animation.MoonQueenAnimation;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MoonQueen extends AbstractAurorianBoss {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState meleeAttackAnimationState = new AnimationState();
    public final AnimationState defendAnimationState = new AnimationState();
    public final AnimationState skillLunaBefallAnimationState = new AnimationState();
    public final AnimationState skillLunaBefallEndAnimationState = new AnimationState();

    private static final EntityDataAccessor<Boolean> GLINTING = SynchedEntityData.defineId(MoonQueen.class, EntityDataSerializers.BOOLEAN);

    public MoonQueen(EntityType<? extends MoonQueen> type, Level level) {
        super(type, level);
        this.xpReward = 500;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new MoonQueenAttackGoal(this));
        this.goalSelector.addGoal(6, new SkillLunaBefall());
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, (1.0D)));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, (1.0D)));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, (8.0F)));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, Boolean.TRUE));
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

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GLINTING, Boolean.FALSE);
    }

    public void setGlinting(boolean glinting) {
        this.entityData.set(GLINTING, glinting);
    }

    public boolean isGlinting() {
        return this.entityData.get(GLINTING);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) {
            this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.walkAnimation.isMoving(), this.tickCount);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Glinting", this.isGlinting());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setGlinting(compound.getBoolean("Glinting"));
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 4) {
            this.meleeAttackAnimationState.start(this.tickCount);
        } else if (id == 5) {
            this.meleeAttackAnimationState.stop();
            this.skillLunaBefallAnimationState.start(this.tickCount);
        } else if (id == 6) {
            this.skillLunaBefallAnimationState.stop();
            this.skillLunaBefallEndAnimationState.startIfStopped(this.tickCount);
        } else if (id == 7) {
            this.skillLunaBefallEndAnimationState.stop();
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (entity instanceof LivingEntity livingEntity) {
            f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), livingEntity.getMobType());
            f1 += (float)EnchantmentHelper.getKnockbackBonus(this);
            if (this.hasEffect(TAMobEffects.FALL_OF_MOON.get())) {
                livingEntity.kill();
                return true;
            }
        }

        if (this.hasEffect(TAMobEffects.MOON_OF_VENGEANCE.get())) {
            f *= 2.0F;
        }

        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            entity.setSecondsOnFire(i * 4);
        }

        boolean flag = entity.hurt(this.damageSources().mobAttack(this), f);
        if (flag) {
            this.level().broadcastEntityEvent(this, (byte) 4);
            if (f1 > 0.0F && entity instanceof LivingEntity livingEntity) {
                float value = this.getYRot() * ((float)Math.PI / 180.0F);
                livingEntity.knockback(f1 * 0.5F, Mth.sin(value), -Mth.cos(value));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }

            if (entity instanceof Player player) {
                this.maybeDisableShield(player, this.getMainHandItem(), player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
            }

            this.doEnchantDamageEffects(this, entity);
            this.setLastHurtMob(entity);
            if (this.hasEffect(TAMobEffects.CRESCENT.get())) {
                this.heal(f / 2.0F);
            }
        }

        return flag;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean flag = this.hasEffect(TAMobEffects.BLESS_OF_MOON.get());
        if ((source.getEntity() != null && this.isGlinting()) || this.isInvulnerableTo(source)) {
            return false;
        }

        return super.hurt(source, flag ? amount / 2.0F : amount);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(TAItems.MOONSTONE_SWORD.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(TAItems.KNIGHT_CHESTPLATE.get()));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(TAItems.KNIGHT_LEGGINGS.get()));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(TAItems.KNIGHT_BOOTS.get()));
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    private class MoonQueenAttackGoal extends MeleeAttackGoal {

        private int attackAnimationTick;

        public MoonQueenAttackGoal(MoonQueen moonQueen) {
            super(moonQueen, 1.35F, Boolean.FALSE);
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
        }

        @Override
        public void tick() {
            LivingEntity target = getTarget();
            if (target != null && this.attackAnimationTick <= 40) {
                getLookControl().setLookAt(target, 30.0F, 30.0F);
                this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                boolean flag0 = this.followingTargetEvenIfNotSeen || getSensing().hasLineOfSight(target);
                boolean flag1 = this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D;
                boolean flag2 = target.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D;
                boolean flag3 = getRandom().nextFloat() < 0.05F;
                double d0 = getPerceivedTargetDistanceSquareForMeleeAttack(target);
                if (isAlive() && this.attackAnimationTick == 0 && d0 <= this.getAttackReachSqr(target) && this.ticksUntilNextAttack <= 0) {
                    level().broadcastEntityEvent(MoonQueen.this, (byte) 4);
                }

                ++this.attackAnimationTick;
                if (flag0 && this.ticksUntilNextPathRecalculation <= 0 && (flag1 || flag2 || flag3)) {
                    this.pathedTargetX = target.getX();
                    this.pathedTargetY = target.getY();
                    this.pathedTargetZ = target.getZ();
                    this.ticksUntilNextPathRecalculation = 4 + getRandom().nextInt(7);
                    if (d0 > 1024.0D) {
                        this.ticksUntilNextPathRecalculation += 10;
                    } else if (d0 > 256.0D) {
                        this.ticksUntilNextPathRecalculation += 5;
                    }

                    if (!getNavigation().moveTo(target, this.speedModifier)) {
                        this.ticksUntilNextPathRecalculation += 15;
                    }

                    this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
                }

                this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
                if (this.attackAnimationTick > 6 && this.attackAnimationTick <= 8) {
                    if (d0 <= this.getAttackReachSqr(target)) {
                        doHurtTarget(target);
                    }
                }

                if (this.attackAnimationTick > 12 && this.attackAnimationTick <= 14) {
                    if (d0 <= this.getAttackReachSqr(target)) {
                        doHurtTarget(target);
                    }
                }

                if (this.attackAnimationTick > 20) {
                    this.resetAttackCooldown();
                    if (level().isClientSide) {
                        meleeAttackAnimationState.stop();
                    }
                }
            }
        }

    }

    private class SkillLunaBefall extends Goal {

        private final float skillAnimationLength = MoonQueenAnimation.SKILL_LUNA_BEFALL.lengthInSeconds() * 20.0F;
        private float skillTime = 0;

        @Override
        public boolean canUse() {
            boolean lessThanHalfHealth = getHealth() < getMaxHealth() / 2.0F;
            return getTarget() != null && lessThanHalfHealth;
        }

        @Override
        public boolean isInterruptable() {
            return false;
        }

        @Override
        public void start() {
            this.skillTime = 0.0F;
            setGlinting(true);
            resetFallDistance();
        }

        @Override
        public void stop() {
            setGlinting(false);
        }

        @Override
        public void tick() {
            LivingEntity target = getTarget();
            if (target != null) {
                Vec3 vec3 = getDeltaMovement();
                boolean flag = getSensing().hasLineOfSight(target);
                if (target.distanceToSqr(MoonQueen.this) < 400.0D && flag) {
                    if (this.skillTime < 1.0F) {
                        level().broadcastEntityEvent(MoonQueen.this, (byte) 5);
                        lookAt(EntityAnchorArgument.Anchor.EYES, target.position());
                    }

                    ++this.skillTime;
                    if (this.skillTime >= 1.0F && this.skillTime <= 80.0F) {
                        setDeltaMovement(vec3.x, 0.1F, vec3.z);
                        setNoGravity(true);
                    } else if (this.skillTime > 160.0F && this.skillTime <= this.skillAnimationLength) {
                        resetFallDistance();
                        setDeltaMovement(vec3.x, -0.2F, vec3.z);
                    } else if (this.skillTime < this.skillAnimationLength + 1.0F) {
                        level().broadcastEntityEvent(MoonQueen.this, (byte) 6);
                        setNoGravity(false);
                    } else {
                        level().broadcastEntityEvent(MoonQueen.this, (byte) 7);
                        resetFallDistance();
                        setGlinting(false);
                        setTarget(null);
                    }
                }
            }
        }

    }

}