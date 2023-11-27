package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.entities.ai.ZombieLikeAttackGoal;
import cn.teampancake.theaurorian.config.AurorianConfig;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DisturbedHollow extends Monster {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    public DisturbedHollow(EntityType<? extends DisturbedHollow> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new ZombieLikeAttackGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MAX_HEALTH, 20.0F);
        builder.add(Attributes.MOVEMENT_SPEED, 0.3F);
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
        return 3 * AurorianConfig.CONFIG_RUNESTONE_DUNGEON_MOB_DENSITY.get();
    }

}