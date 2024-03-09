package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.config.AurorianConfig;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class UndeadKnight extends Monster {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTick;

    public UndeadKnight(EntityType<? extends UndeadKnight> type, Level level) {
        super(type, level);
        this.xpReward = 20;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new UndeadKnightAttackGoal(this));
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
    public void aiStep() {
        super.aiStep();
        if (this.attackAnimationTick <= 25) {
            ++this.attackAnimationTick;
        }
        if (this.level().isClientSide()) {
            this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.walkAnimation.isMoving(), this.tickCount);
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 4) {
            this.attackAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
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

    private static class UndeadKnightAttackGoal extends MeleeAttackGoal {

        private final UndeadKnight undeadKnight;

        public UndeadKnightAttackGoal(UndeadKnight undeadKnight) {
            super(undeadKnight, (1.0D), Boolean.FALSE);
            this.undeadKnight = undeadKnight;
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            if (distToEnemySqr < this.getAttackReachSqr(enemy) && this.ticksUntilNextAttack <= 0) {
                this.undeadKnight.level().broadcastEntityEvent(this.undeadKnight, (byte)4);
                this.ticksUntilNextAttack = this.adjustedTickDelay((20));
                if (this.undeadKnight.attackAnimationTick >= 10) {
                    this.undeadKnight.attackAnimationTick = 0;
                    this.undeadKnight.doHurtTarget(enemy);
                }
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return super.getAttackReachSqr(attackTarget) + 9.0D;
        }

    }

}