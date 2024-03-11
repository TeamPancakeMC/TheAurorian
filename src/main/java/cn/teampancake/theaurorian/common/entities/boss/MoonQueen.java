package cn.teampancake.theaurorian.common.entities.boss;

import cn.teampancake.theaurorian.common.entities.ai.goal.MeleeNoAttackGoal;
import cn.teampancake.theaurorian.common.entities.phase.*;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class MoonQueen extends AbstractAurorianBoss implements GeoEntity {

    private static final RawAnimation SHRUG = RawAnimation.begin().thenPlay("misc.shrug");
    private static final RawAnimation LUNA_BEFALL = RawAnimation.begin().thenPlay("skill.luna_befall");
    private static final RawAnimation LUNA_BEFALL_END = RawAnimation.begin().thenPlay("skill.luna_befall_end");
    private static final EntityDataAccessor<Float> ATTACK_Y_ROT = SynchedEntityData.defineId(MoonQueen.class, EntityDataSerializers.FLOAT);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private long ticksCanOneHitMustKill = 24000L;

    public MoonQueen(EntityType<? extends MoonQueen> type, Level level) {
        super(type, level);
        this.xpReward = 500;
        this.attackManager = new AttackManager<>(this, List.of(
                new MoonQueenSwingPhase(), new MoonQueenBlockPhase(), new MoonQueenShrugPhase(),
                new MoonQueenLunaBefallPhase(), new MoonQueenLunaBefallEndPhase()));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new MeleeNoAttackGoal(this));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, (1.0D)));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, (1.0D)));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, (8.0F)));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, Boolean.TRUE));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MAX_HEALTH, 500.0D);
        builder.add(Attributes.ATTACK_DAMAGE, 4.0D);
        builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.85D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.25D);
        builder.add(Attributes.FOLLOW_RANGE, 40.0F);
        builder.add(Attributes.ARMOR, 8.0F);
        return builder;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_Y_ROT, 0.0F);
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new MoonQueenBodyRotationControl(this);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkIdleController(this));
        controllers.add(new AnimationController<>(this, "swing_controller", state -> PlayState.STOP)
                .triggerableAnim("swing_animation", DefaultAnimations.ATTACK_SWING).transitionLength(5));
        controllers.add(new AnimationController<>(this, "block_controller", state -> PlayState.STOP)
                .triggerableAnim("block_animation", DefaultAnimations.ATTACK_BLOCK).transitionLength(5));
        controllers.add(new AnimationController<>(this, "shrug_controller", state -> PlayState.STOP)
                .triggerableAnim("shrug_animation", SHRUG).transitionLength(5));
        controllers.add(new AnimationController<>(this, "luna_befall_controller", state -> PlayState.STOP)
                .triggerableAnim("luna_befall_animation", LUNA_BEFALL).transitionLength(5));
        controllers.add(new AnimationController<>(this, "luna_befall_end_controller", state -> PlayState.STOP)
                .triggerableAnim("luna_befall_end_animation", LUNA_BEFALL_END).transitionLength(5));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public float getAttackYRot() {
        return this.entityData.get(ATTACK_Y_ROT);
    }

    public void setAttackYRot(float attackYRot) {
        this.entityData.set(ATTACK_Y_ROT, attackYRot);
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            long l = this.ticksCanOneHitMustKill;
            this.ticksCanOneHitMustKill = Math.min(l + 1L, 24000L);
        }

        super.tick();
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
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putLong("TicksCanOneHitMustKill", this.ticksCanOneHitMustKill);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.ticksCanOneHitMustKill = compound.getLong("TicksCanOneHitMustKill");
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
        if (this.isDeadOrDying() && this.ticksCanOneHitMustKill == 24000L) {
            this.ticksCanOneHitMustKill = this.level().getGameTime();
            this.addEffect(new MobEffectInstance(TAMobEffects.FALL_OF_MOON.get(), 200));
            this.setHealth(1.0F);
            return true;
        }

        boolean flag = this.hasEffect(TAMobEffects.BLESS_OF_MOON.get());
        return super.hurt(source, flag ? amount / 2.0F : amount);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(TAItems.MOONSTONE_SWORD.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(TAItems.KNIGHT_CHESTPLATE.get()));
        this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(TAItems.KNIGHT_LEGGINGS.get()));
        this.setItemSlot(EquipmentSlot.FEET, new ItemStack(TAItems.KNIGHT_BOOTS.get()));
    }

    private class MoonQueenBodyRotationControl extends BodyRotationControl {

        public MoonQueenBodyRotationControl(Mob mob) {
            super(mob);
        }

        @Override
        public void clientTick() {
            if (getAttackState() == 1) {
                yHeadRot = getAttackYRot();
                yBodyRot = getAttackYRot();
            } else {
                super.clientTick();
            }
        }

    }

}