package cn.teampancake.theaurorian.common.items.crafting;

import cn.teampancake.theaurorian.common.blocks.entity.AlchemyTableBlockEntity;
import cn.teampancake.theaurorian.common.registry.TARecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record AlchemyTableRecipe(Ingredient input1, Ingredient input2, Ingredient input3, Ingredient material,
                                 ItemStack result, int alchemyTime) implements Recipe<AlchemyTableRecipeInput> {

    @Override
    public boolean matches(AlchemyTableRecipeInput table, Level level) {
        boolean b1 = false, b2 = false, b3 = false;
        for (int i = 0; i < 3; i++) {
            ItemStack item = table.getItem(i);
            if (item.isEmpty()) return false;
            if (this.input1.test(item)) b1 = true;
            else if (this.input2.test(item)) b2 = true;
            else if (this.input3.test(item)) b3 = true;
        }

        if (!b1 || !b2 || !b3) return false;
        return this.material.test(table.getItem(3));
    }

    @Override
    public ItemStack assemble(AlchemyTableRecipeInput table, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return TARecipes.ALCHEMY_TABLE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return TARecipes.ALCHEMY_TABLE_RECIPE.get();
    }

    public void consumeIngredients(AlchemyTableBlockEntity table) {
        for (int i = 0; i < 4; i++) {
            var item = table.getItem(i);
            if (item.hasCraftingRemainingItem()) {
                Level level = table.getLevel();
                if (item.getCount() == 1) {
                    table.setItem(i, item.getCraftingRemainingItem());
                } else {
                    if (level != null) {
                        Containers.dropItemStack(level,
                                table.getBlockPos().getX() + 0.5,
                                table.getBlockPos().getY() + 1.2,
                                table.getBlockPos().getZ() + 0.5,
                                item.getCraftingRemainingItem());
                        item.shrink(1);
                    }
                }
            } else item.shrink(1);
        }
    }

}