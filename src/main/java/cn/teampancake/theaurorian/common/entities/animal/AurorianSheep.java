package cn.teampancake.theaurorian.common.entities.animal;

import cn.teampancake.theaurorian.common.entities.ai.goal.AnimalHurtByTargetGoal;
import cn.teampancake.theaurorian.common.entities.ai.goal.AnimalMeleeAttackGoal;
import cn.teampancake.theaurorian.common.entities.ai.goal.AnimalNearestAttackableTargetGoal;
import cn.teampancake.theaurorian.common.entities.ai.goal.AnimalPanicGoal;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

public class AurorianSheep extends Sheep {

    @SuppressWarnings("NotNullFieldNotInitialized")
    private EatAurorianBlockGoal eatBlockGoal;

    public AurorianSheep(EntityType<? extends AurorianSheep> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Sheep.createAttributes().add(Attributes.ATTACK_DAMAGE, 1.0F);
    }

    @Nullable @Override
    public Sheep getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return TAEntityTypes.AURORIAN_SHEEP.get().create(level);
    }

    @Override
    protected void registerGoals() {
        this.eatBlockGoal = new EatAurorianBlockGoal(this);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AnimalMeleeAttackGoal(this, 1.0F));
        this.goalSelector.addGoal(2, new AnimalPanicGoal(this, 2.0F));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.1D,
                Ingredient.of(TAItems.LAVENDER.get()), false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, this.eatBlockGoal);
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new AnimalHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new AnimalNearestAttackableTargetGoal<>(this, Player.class));
    }

    protected void customServerAiStep() {
        this.eatAnimationTick = this.eatBlockGoal.getEatAnimationTick();
        if (this.getAge() != 0) {
            this.resetLove();
        }
    }

    @Override
    public ResourceKey<LootTable> getDefaultLootTable() {
        return this.getType().getDefaultLootTable();
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(TAItems.LAVENDER.get());
    }

    @Override
    public boolean isShearable(@Nullable Player player, ItemStack item, Level level, BlockPos pos) {
        return false;
    }

    private static class EatAurorianBlockGoal extends EatBlockGoal {

        public EatAurorianBlockGoal(Mob mob) {
            super(mob);
        }

        static {
            IS_TALL_GRASS = BlockStatePredicate.forBlock(Blocks.SHORT_GRASS)
                    .or(BlockStatePredicate.forBlock(TABlocks.AURORIAN_GRASS.get()))
                    .or(BlockStatePredicate.forBlock(TABlocks.AURORIAN_GRASS_LIGHT.get()))
                    .or(BlockStatePredicate.forBlock(TABlocks.WICK_GRASS.get()));
        }

    }

}