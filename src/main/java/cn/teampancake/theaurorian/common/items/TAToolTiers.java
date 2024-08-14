package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.api.ISpecialty;
import cn.teampancake.theaurorian.common.event.subscriber.ToolEventSubscriber;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import com.google.common.base.Suppliers;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

@SuppressWarnings({"SpellCheckingInspection"})
public enum TAToolTiers implements Tier, ISpecialty {

    SILENT_WOOD(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 59, 3.0F, 0.0F, 20, () -> Ingredient.of(TABlocks.SILENT_TREE_PLANKS.get())),
    AURORIAN_STONE(BlockTags.INCORRECT_FOR_STONE_TOOL, 131, 4.5F, 1.5F, 14, () -> Ingredient.of(TABlocks.AURORIAN_STONE.get())),
    MOONSTONE(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 7.0F, 2.5F, 14, () -> Ingredient.of(TABlocks.MOONSTONE_BLOCK.get())),
    AURORIANITE(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1000, 8.0F, 3.0F, 20, () -> Ingredient.of(TAItems.AURORIANITE_INGOT.get())),
    UMBRA(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1000, 8.0F, 3.0F, 20, () -> Ingredient.of(TAItems.UMBRA_INGOT.get())),
    CRYSTALLINE(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1000, 8.0F, 3.0F, 20, () -> Ingredient.of(TAItems.CRYSTALLINE_INGOT.get())),
    AURORIAN_STEEL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1500, 8.5F, 3.5F, 10, () -> Ingredient.of(TAItems.AURORIAN_STEEL.get())) {
        @Override
        public void doSpecialty(ItemStack stack) {
            ToolEventSubscriber.aurorianSteelSpecialty(stack);
        }
    },
    TSLAT(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2000, 1.9F, 7.0F, 15, Ingredient::of);

    private final TagKey<Block> incorrectBlocksForDrops;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    TAToolTiers(
            TagKey<Block> incorrectBlocksForDrops,
            int uses, float speed, float damage, int enchantmentValue,
            Supplier<Ingredient> repairIngredient) {
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.incorrectBlocksForDrops = incorrectBlocksForDrops;
        this.repairIngredient = Suppliers.memoize(repairIngredient::get);
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return this.incorrectBlocksForDrops;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    @Nonnull
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

}