package cn.teampancake.theaurorian.common.entities.monster;

import cn.teampancake.theaurorian.common.entities.ai.ZombieLikeAttackGoal;
import cn.teampancake.theaurorian.config.AurorianConfig;
import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.registry.ModItems;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;

public class MoonAcolyte extends Monster {

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
    }

    public static boolean checkMoonAcolyteRules(EntityType<MoonAcolyte> moonAcolyte, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(ModBlocks.MOON_TEMPLE_BRICK.get()) && checkAnyLightMonsterSpawnRules(moonAcolyte, level, spawnType, pos, random);
    }

    public static AttributeSupplier.Builder createAttributes() {
        boolean nightmareMode = AurorianConfig.CONFIG_NIGHTMARE_MODE.get();
        double multiplier = AurorianConfig.CONFIG_NIGHTMARE_MODE_MULTIPLIER.get();
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder.add(Attributes.MAX_HEALTH, nightmareMode ? 40.0F * multiplier : 20.0F);
        builder.add(Attributes.MOVEMENT_SPEED, nightmareMode ? 0.5F * multiplier : 0.25F);
        builder.add(Attributes.ATTACK_DAMAGE, nightmareMode ? 4.0F * multiplier : 2.0F);
        builder.add(Attributes.ARMOR, 3.0F);
        return builder;
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
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
        ItemStack swordStack = new ItemStack(ModItems.AURORIAN_STONE_SWORD.get());
        swordStack.enchant(Enchantments.KNOCKBACK, 2);
        this.setItemSlot(EquipmentSlot.MAINHAND, swordStack);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 4 * AurorianConfig.CONFIG_MOON_TEMPLE_MOB_DENSITY.get();
    }

}