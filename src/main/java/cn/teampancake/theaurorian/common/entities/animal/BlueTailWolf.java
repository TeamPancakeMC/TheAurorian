package cn.teampancake.theaurorian.common.entities.animal;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAEntityTags;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;
import java.util.function.Predicate;

public class BlueTailWolf extends TamableAnimal implements GeoEntity, NeutralMob {

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(BlueTailWolf.class, EntityDataSerializers.INT);
    private static final Predicate<LivingEntity> PREY_SELECTOR = (entity) -> entity.getType().is(TAEntityTags.WOLF_NON_TAME_ATTACK_TARGET);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Nullable
    private UUID persistentAngerTarget;

    public BlueTailWolf(EntityType<? extends BlueTailWolf> type, Level level) {
        super(type, level);
        this.setTame(false);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new WolfPanicGoal(1.5D));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, Boolean.TRUE));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, Boolean.TRUE, this::isAngryAt));
        this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class, Boolean.FALSE, PREY_SELECTOR));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, Boolean.FALSE));
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, Boolean.TRUE));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = createMobAttributes();
        builder.add(Attributes.MAX_HEALTH, 35.0D);
        builder.add(Attributes.ATTACK_DAMAGE, 6.0D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.2D);
        return builder;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkRunIdleController(this));
        controllers.add(DefaultAnimations.genericAttackAnimation(this, DefaultAnimations.ATTACK_BITE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    @Nullable @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        this.addPersistentAngerSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.readPersistentAngerSaveData(this.level(), compound);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        this.level().broadcastEntityEvent(this, (byte) 4);
        if (super.doHurtTarget(entity)) {
            if (entity instanceof LivingEntity livingEntity && this.random.nextInt(10) == 0) {
                livingEntity.addEffect(new MobEffectInstance(TAMobEffects.LACERATION.get(), 60));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, time);
    }

    @Nullable @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID target) {
        this.persistentAngerTarget = target;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    private class WolfPanicGoal extends PanicGoal {

        public WolfPanicGoal(double speedModifier) {
            super(BlueTailWolf.this, speedModifier);
        }

        protected boolean shouldPanic() {
            return this.mob.isFreezing() || this.mob.isOnFire();
        }

    }

}