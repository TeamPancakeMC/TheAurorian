package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.entities.ai.ZombieLikeAttackGoal;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MoonAcolyte extends Monster implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MoonAcolyte(EntityType<? extends MoonAcolyte> type, Level level) {
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
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public static boolean checkSpawnRules(EntityType<MoonAcolyte> moonAcolyte, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(TABlocks.MOON_TEMPLE_BRICKS.get()) && checkAnyLightMonsterSpawnRules(moonAcolyte, level, spawnType, pos, random);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MAX_HEALTH, 20.0F);
        builder.add(Attributes.MOVEMENT_SPEED, 0.25F);
        builder.add(Attributes.ATTACK_DAMAGE, 2.0F);
        builder.add(Attributes.ARMOR, 3.0F);
        return builder;
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
    public boolean doHurtTarget(@NotNull Entity entity) {
        this.level().broadcastEntityEvent(this, (byte)4);
        if (super.doHurtTarget(entity)) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        ItemStack swordStack = new ItemStack(TAItems.AURORIAN_STONE_SWORD.get());
        swordStack.enchant(Enchantments.KNOCKBACK, 2);
        this.setItemSlot(EquipmentSlot.MAINHAND, swordStack);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 4;
    }

}