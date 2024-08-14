package cn.teampancake.theaurorian.common.entities.animal;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAEntityTags;
import cn.teampancake.theaurorian.common.entities.ai.goal.BlueTailWolfDoNothingGoal;
import cn.teampancake.theaurorian.common.entities.ai.goal.MeleeNoAttackGoal;
import cn.teampancake.theaurorian.common.entities.monster.MultiPhaseAttacker;
import cn.teampancake.theaurorian.common.entities.phase.AttackManager;
import cn.teampancake.theaurorian.common.entities.phase.BlueTailWolfHowlPhase;
import cn.teampancake.theaurorian.common.entities.phase.BlueTailWolfMeleePhase;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class BlueTailWolf extends TamableAnimal implements GeoEntity, NeutralMob, MultiPhaseAttacker {

    protected static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(BlueTailWolf.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(BlueTailWolf.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(BlueTailWolf.class, EntityDataSerializers.INT);
    private static final Predicate<LivingEntity> PREY_SELECTOR = (entity) -> entity.getType().is(TAEntityTags.WOLF_NON_TAME_ATTACK_TARGET);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private final AttackManager<BlueTailWolf> attackManager = new AttackManager<>(this, List.of(
            new BlueTailWolfMeleePhase(),
            new BlueTailWolfHowlPhase()
    ));
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Nullable
    private UUID persistentAngerTarget;

    public BlueTailWolf(EntityType<? extends BlueTailWolf> type, Level level) {
        super(type, level);
        this.setTame(Boolean.FALSE, Boolean.FALSE);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BlueTailWolfDoNothingGoal(this));
        this.goalSelector.addGoal(3, new WolfPanicGoal(1.5D));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeNoAttackGoal(this, 1.5, Boolean.TRUE));
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
        controllers.add(new AnimationController<>(this, "bite_controller", state -> PlayState.STOP)
                .triggerableAnim("bite_animation", DefaultAnimations.ATTACK_BITE).transitionLength(5));
        controllers.add(new AnimationController<>(this, "howl_controller", state -> PlayState.STOP)
                .triggerableAnim("howl_animation", RawAnimation.begin().thenPlay("misc.howl")).transitionLength(5));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
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
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_REMAINING_ANGER_TIME, 0);
        builder.define(ATTACK_STATE, 0);
        builder.define(ATTACK_TICKS, 0);
    }

    @Override
    protected void customServerAiStep() {
        this.attackManager.tick();
        this.setSprinting(getTarget() != null);
    }

    public boolean hasNearbyFriends() {
        for (Mob mob : level().getEntitiesOfClass(Mob.class, getBoundingBox().inflate(50))) {
            if (mob.getType().is(TAEntityTags.ALERTED_BY_BLUE_TAIL_WOLF) && !mob.getUUID().equals(getUUID()) && mob.getTarget() == null) {
                return true;
            }
        }
        return false;
    }

    public void alertNearbyFriends() {
        for (Mob mob : level().getEntitiesOfClass(Mob.class, getBoundingBox().inflate(50))) {
            if (mob.getType().is(TAEntityTags.ALERTED_BY_BLUE_TAIL_WOLF)) {
                if (mob.getAttributes().hasAttribute(Attributes.FOLLOW_RANGE)) {
                    ResourceLocation id = TheAurorian.prefix("follow_range_bonus");
                    AttributeModifier modifier = new AttributeModifier(id, 100, AttributeModifier.Operation.ADD_VALUE);
                    AttributeInstance instance = mob.getAttributes().getInstance(Attributes.FOLLOW_RANGE);
                    if (instance != null && !instance.hasModifier(id)) {
                        instance.addPermanentModifier(modifier);
                    }
                }

                mob.setTarget(getTarget());
            }
        }
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
    public boolean isFood(ItemStack stack) {
        return stack.is(ItemTags.WOLF_FOOD);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        this.level().broadcastEntityEvent(this, (byte) 4);
        if (super.doHurtTarget(entity)) {
            if (entity instanceof LivingEntity livingEntity && this.random.nextInt(10) == 0) {
                livingEntity.addEffect(new MobEffectInstance(TAMobEffects.LACERATION, 60));
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