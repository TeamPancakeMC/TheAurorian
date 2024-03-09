package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.entities.ai.MeleeNoAttackGoal;
import cn.teampancake.theaurorian.common.entities.phase.AttackManager;
import cn.teampancake.theaurorian.common.entities.phase.UndeadKnightMeleePhase;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UndeadKnight extends Monster implements MultiPhaseAttacker {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    protected static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(UndeadKnight.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(UndeadKnight.class, EntityDataSerializers.INT);
    private final AttackManager<UndeadKnight> attackManager = new AttackManager<>(this, List.of(new UndeadKnightMeleePhase()));

    public UndeadKnight(EntityType<? extends UndeadKnight> type, Level level) {
        super(type, level);
        this.xpReward = 20;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeNoAttackGoal(this, Boolean.FALSE));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, (1.0D)));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, (1.0D)));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, (8.0F)));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, Boolean.TRUE));
    }

    public static boolean checkSpawnRules(EntityType<UndeadKnight> undeadKnight, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON) && checkMonsterSpawnRules(undeadKnight, level, spawnType, pos, random);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MAX_HEALTH, 20.0F);
        builder.add(Attributes.MOVEMENT_SPEED, 0.25F);
        builder.add(Attributes.ATTACK_DAMAGE, 3.0F);
        builder.add(Attributes.FOLLOW_RANGE, 35.0D);
        builder.add(Attributes.ARMOR, 6.0F);
        return builder;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(ATTACK_TICKS, 0);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (accessor.equals(ATTACK_STATE) && this.getAttackState() != 0) {
            if (this.getAttackState() == UndeadKnightMeleePhase.ID) {
                this.attackAnimationState.start(this.tickCount);
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }
    public void setAttackState(int attackState) {
        this.entityData.set(ATTACK_STATE, attackState);
    }

    public int getAttackTicks() {
        return this.entityData.get(ATTACK_TICKS);
    }
    public void setAttackTicks(int attackTicks) {
        this.entityData.set(ATTACK_TICKS, attackTicks);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide()) {
            this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.walkAnimation.isMoving(), this.tickCount);
        } else {
            this.attackManager.tick();
        }
    }

    public boolean canReachTarget(double range) {
        LivingEntity target = this.getTarget();
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
        LivingEntity target = this.getTarget();
        if (target != null) {
            for (LivingEntity livingEntity : level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, getBoundingBox().inflate(range))) {
                if (livingEntity.getUUID().equals(target.getUUID())) {
                    livingEntity.invulnerableTime = 0;
                    this.doHurtTarget(livingEntity);
                }
            }
        }
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        if (super.doHurtTarget(entity)) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 60));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        MobEffect effect = effectInstance.getEffect();
        return effect == MobEffects.REGENERATION || effect == MobEffects.WITHER;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.15F, 1.0F);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(TAItems.AURORIAN_STONE_SWORD.get()));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(TAItems.KNIGHT_HELMET.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(TAItems.KNIGHT_CHESTPLATE.get()));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(TAItems.KNIGHT_LEGGINGS.get()));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(TAItems.KNIGHT_BOOTS.get()));
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 3;
    }

}