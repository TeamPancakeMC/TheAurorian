package cn.teampancake.theaurorian.common.items.crafting;

import cn.teampancake.theaurorian.common.registry.TARecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record MoonlightForgeRecipe(Ingredient equipment, Ingredient upgradeMaterial, ItemStack result) implements Recipe<SingleRecipeInput> {

    @Override
    public boolean matches(SingleRecipeInput input, Level level) {
        return this.equipment.test(input.getItem(0)) && this.upgradeMaterial.test(input.getItem(1));
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
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
        return TARecipes.MOONLIGHT_FORGE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return TARecipes.MOONLIGHT_FORGE_RECIPE.get();
    }

}