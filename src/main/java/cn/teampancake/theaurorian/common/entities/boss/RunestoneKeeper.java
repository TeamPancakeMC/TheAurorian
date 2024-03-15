package cn.teampancake.theaurorian.common.entities.boss;

import cn.teampancake.theaurorian.common.registry.TAAttributes;
import cn.teampancake.theaurorian.common.registry.TAEnchantments;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class RunestoneKeeper extends AbstractAurorianBoss implements RangedAttackMob, GeoEntity {

    private static final RawAnimation DEATH = RawAnimation.begin().thenPlay("misc.death");
    private static final EntityDataAccessor<Integer> HURT_BY_MELEE_ATTACK_COUNT =
            SynchedEntityData.defineId(RunestoneKeeper.class, EntityDataSerializers.INT);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public RunestoneKeeper(EntityType<? extends RunestoneKeeper> type, Level level) {
        super(type, level);
        this.xpReward = 350;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RangedAttackGoal((this), (0.85F), (20), (40.0F)));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, (1.6D), Boolean.FALSE));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, (1.0D)));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, (1.0D)));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, (8.0F)));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, Boolean.FALSE));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(TAAttributes.MAX_BOSS_HEALTH.get(), 200.0D);
        builder.add(Attributes.ATTACK_DAMAGE, 2.0D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder.add(Attributes.FOLLOW_RANGE, 50.0F);
        builder.add(Attributes.ARMOR, 4.0F);
        return builder;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HURT_BY_MELEE_ATTACK_COUNT, 0);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkRunIdleController(this));
        controllers.add(new AnimationController<>(this, "strike_controller", state -> PlayState.STOP)
                .triggerableAnim("strike_animation", DefaultAnimations.ATTACK_STRIKE).transitionLength(5));
        controllers.add(new AnimationController<>(this, "death_controller", state -> PlayState.STOP)
                .triggerableAnim("death_animation", DEATH).transitionLength(5));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public int getHurtByMeleeAttackCount() {
        return this.entityData.get(HURT_BY_MELEE_ATTACK_COUNT);
    }

    public void setHurtByMeleeAttackCount(int count) {
        this.entityData.set(HURT_BY_MELEE_ATTACK_COUNT, count);
    }

    @Nullable @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WITHER_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.WITHER_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WITHER_SKELETON_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WITHER_SKELETON_STEP, 0.15F, 1.0F);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide()) {
            if (this.tickCount % 2 == 0 && this.getHealth() < this.getMaxHealth() * 0.3F) {
                double x = this.getX() + this.random.nextFloat();
                double z = this.getZ() + this.random.nextFloat();
                double xSpeed = this.random.nextGaussian() * 0.02D;
                double ySpeed = this.random.nextGaussian() * 0.1D;
                double zSpeed = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(ParticleTypes.SPLASH, x, this.getRandomY(), z, xSpeed, ySpeed, zSpeed);
            }
        }
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        Arrow arrow = new Arrow(this.level(), this);
        double d0 = target.getX() - this.getX();
        double d2 = target.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        double d1 = target.getBoundingBox().minY + target.getBbHeight() / 3.0F - arrow.getY();
        float pitch = 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F);
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, pitch);
        arrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, 9F);
        arrow.addEffect(new MobEffectInstance(MobEffects.POISON, 200));
        arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        this.level().addFreshEntity(arrow);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        this.level().broadcastEntityEvent(this, (byte)4);
        if (super.doHurtTarget(entity)) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (!this.level().isClientSide()) {
            if (this.deathTime == 1) {
                this.triggerAnim(("death_controller"), ("death_animation"));
            }
            if (this.deathTime > 60 && !this.isRemoved()) {
                this.level().broadcastEntityEvent(this, (byte) 60);
                this.remove(RemovalReason.KILLED);
            }
        }
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        ItemStack swordStack = new ItemStack(TAItems.MOONSTONE_SWORD.get());
        swordStack.enchant(TAEnchantments.LIGHTNING_DAMAGE.get(), 3);
        swordStack.enchant(Enchantments.KNOCKBACK, 2);
        this.setItemSlot(EquipmentSlot.MAINHAND, swordStack);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypeTags.IS_FALL) || source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypeTags.IS_EXPLOSION)) {
            return false;
        } else {
            if (!this.level().isClientSide && (source.is(DamageTypes.MOB_ATTACK) || source.is(DamageTypes.PLAYER_ATTACK))) {
                int count = this.getHurtByMeleeAttackCount();
                this.setHurtByMeleeAttackCount(count == 10 ? 0 : count + 1);
            }

            return super.hurt(source, amount);
        }
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        return false;
    }

    @NotNull @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

}