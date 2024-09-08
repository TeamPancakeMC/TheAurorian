package cn.teampancake.theaurorian.common.items.crafting;

import cn.teampancake.theaurorian.common.registry.TARecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record ScrapperRecipe(Ingredient ingredient, ItemStack result, int amount) implements Recipe<SingleRecipeInput> {

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.ingredient.test(input.getItem(0));
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        ItemStack stack = this.result.copy();
        stack.setCount(this.amount);
        return stack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        ItemStack stack = this.result;
        stack.setCount(this.amount);
        return stack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return TARecipes.SCRAPPER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return TARecipes.SCRAPPER_RECIPE.get();
    }

}