package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public class SnowTundraGiantCrab extends Monster implements GeoEntity, NeutralMob {

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;

    public SnowTundraGiantCrab(EntityType<? extends SnowTundraGiantCrab> type, Level level) {
        super(type, level);
        this.moveControl = new CrabMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, (1.0D), Boolean.FALSE));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, Boolean.TRUE));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, Boolean.TRUE));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.3D);
        builder.add(Attributes.MAX_HEALTH, 125.0D);
        builder.add(Attributes.ATTACK_DAMAGE, 10.0D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.2D);
        builder.add(Attributes.FOLLOW_RANGE, 40.0F);
        builder.add(Attributes.ARMOR, 8.0D);
        return builder;
    }

    public static boolean checkSpawnRules(EntityType<SnowTundraGiantCrab> giantCrab, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return !level.getBlockState(pos.below()).is(TABlockTags.AUROTIAN_ANIMAL_UNSPAWNABLE_ON) && checkAnyLightMonsterSpawnRules(giantCrab, level, spawnType, pos, random);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkIdleController(this));
        controllers.add(DefaultAnimations.genericAttackAnimation(this, DefaultAnimations.ATTACK_SWING));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    protected int decreaseAirSupply(int currentAir) {
        return currentAir;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FREEZE) || super.isInvulnerableTo(source);
    }

    @Override
    public void knockback(double strength, double x, double z) {
        super.knockback(this.random.nextBoolean() ? 0.0D : strength, x, z);
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.remainingPersistentAngerTime = time;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID target) {
        this.persistentAngerTarget = target;
    }

    @Nullable @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    private static class CrabMoveControl extends MoveControl {

        private final SnowTundraGiantCrab giantCrab;

        public CrabMoveControl(SnowTundraGiantCrab mob) {
            super(mob);
            this.giantCrab = mob;
        }

        @Override
        public void tick() {
            if (this.giantCrab.isInWater()) {
                this.giantCrab.setDeltaMovement(this.giantCrab.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
            }

            super.tick();
        }

    }

}