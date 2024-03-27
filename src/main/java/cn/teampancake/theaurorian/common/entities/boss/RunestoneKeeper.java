package cn.teampancake.theaurorian.common.entities.boss;

import cn.teampancake.theaurorian.common.registry.TAAttributes;
import cn.teampancake.theaurorian.common.registry.TAEnchantments;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
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

public class RunestoneKeeper extends AbstractAurorianBoss implements GeoEntity {

    private static final RawAnimation SPELL_COMMON = RawAnimation.begin().thenPlay("attack.spell_common");
    private static final RawAnimation SPELL_BUFF = RawAnimation.begin().thenPlay("attack.spell_buff");
    private static final RawAnimation DEATH = RawAnimation.begin().thenPlay("misc.death");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public RunestoneKeeper(EntityType<? extends RunestoneKeeper> type, Level level) {
        super(type, level);
        this.xpReward = 350;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
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
        builder.add(TAAttributes.MAX_BOSS_HEALTH.get(), 350.0D);
        builder.add(Attributes.ATTACK_DAMAGE, 6.0D);
        builder.add(Attributes.MOVEMENT_SPEED, 0.3D);
        builder.add(Attributes.FOLLOW_RANGE, 50.0F);
        builder.add(Attributes.ARMOR, 6.0F);
        return builder;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkRunIdleController(this));
        controllers.add(new AnimationController<>(this, "spell_common_controller", state -> PlayState.STOP)
                .triggerableAnim("spell_common_animation", SPELL_COMMON).transitionLength(5));
        controllers.add(new AnimationController<>(this, "spell_buff_controller", state -> PlayState.STOP)
                .triggerableAnim("spell_buff_animation", SPELL_BUFF).transitionLength(5));
        controllers.add(new AnimationController<>(this, "block_controller", state -> PlayState.STOP)
                .triggerableAnim("block_animation", DefaultAnimations.ATTACK_BLOCK).transitionLength(5));
        controllers.add(new AnimationController<>(this, "death_controller", state -> PlayState.STOP)
                .triggerableAnim("death_animation", DEATH).transitionLength(5));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
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
    public boolean doHurtTarget(Entity entity) {
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
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        return false;
    }

    @NotNull @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

}