package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.entities.npc.AurorianVillager;
import cn.teampancake.theaurorian.common.entities.npc.Selena;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import com.mojang.math.Constants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public class DisturbedHollow extends Monster {

    private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(DisturbedHollow.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    private int timeUntilAttackTarget;

    public DisturbedHollow(EntityType<? extends DisturbedHollow> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0F, Boolean.FALSE));
        this.goalSelector.addGoal(2, new BreakDoorGoal(this, difficulty -> true));
        this.goalSelector.addGoal(3, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AurorianVillager.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Selena.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MAX_HEALTH, 25.0F);
        builder.add(Attributes.MOVEMENT_SPEED, 0.25F);
        builder.add(Attributes.ATTACK_DAMAGE, 3.0F);
        builder.add(Attributes.FOLLOW_RANGE, 35.0D);
        builder.add(Attributes.ARMOR, 2.0F);
        return builder;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ANGRY, Boolean.FALSE);
    }

    public boolean isAngry() {
        return this.entityData.get(ANGRY);
    }

    public void setAngry(boolean isAngry) {
        this.entityData.set(ANGRY, isAngry);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) {
            this.idleAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        }
    }

    @Override
    protected void customServerAiStep() {
        if (!this.level().isClientSide) {
            if (this.getTarget() != null) {
                if (++this.timeUntilAttackTarget > 100 && !this.isAngry()) {
                    this.setAngry(true);
                }
            } else {
                this.timeUntilAttackTarget = 0;
                this.setAngry(false);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsAngry", this.isAngry());
        compound.putInt("TimeUntilAttackTarget", this.timeUntilAttackTarget);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setAngry(compound.getBoolean("IsAngry"));
        this.timeUntilAttackTarget = compound.getInt("TimeUntilAttackTarget");
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        float f = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        DamageSource damageSource = this.damageSources().mobAttack(this);
        if (this.level() instanceof ServerLevel serverLevel) {
            f = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), entity, damageSource, f);
        }

        if (this.isAngry()) {
            f *= 2.0F;
        }

        boolean flag = entity.hurt(damageSource, f);
        if (flag) {
            this.timeUntilAttackTarget = 0;
            float f1 = this.getKnockback(entity, damageSource);
            if (f1 > 0.0F && entity instanceof LivingEntity livingEntity) {
                double x = Mth.sin(this.getYRot() * Constants.DEG_TO_RAD);
                double z = -Mth.cos(this.getYRot() * Constants.DEG_TO_RAD);
                livingEntity.knockback(f1 * 0.5F, x, z);
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }

            if (this.level() instanceof ServerLevel serverLevel) {
                EnchantmentHelper.doPostAttackEffects(serverLevel, entity, damageSource);
            }

            this.setLastHurtMob(entity);
            this.playAttackSound();
            this.setAngry(false);
        }

        return flag;
    }

    @Override
    public void die(DamageSource damageSource) {
        if (!this.level().isClientSide && this.isDeadOrDying() && this.random.nextFloat() <= 0.02F) {
            Spirit spirit = new Spirit(TAEntityTypes.SPIRIT.get(), this.level());
            spirit.setPos(this.position());
            this.level().addFreshEntity(spirit);
        }

        super.die(damageSource);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 3;
    }

}