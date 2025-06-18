package cn.teampancake.theaurorian.common.items.crafting;

import cn.teampancake.theaurorian.common.registry.TARecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public record AlchemyTableRecipe(NonNullList<Ingredient> ingredients, Ingredient material, ItemStack result, int alchemyTime) implements Recipe<AlchemyTableRecipeInput> {

    @Override
    public boolean matches(AlchemyTableRecipeInput input, Level level) {
        List<ItemStack> inputItems = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ItemStack item = input.getItem(i);
            if (!item.isEmpty()) {
                inputItems.add(item);
            }
        }

        if (inputItems.size() != this.ingredients.size()) {
            return false;
        }

        List<Ingredient> ingredientsCopy = new ArrayList<>(this.ingredients);
        for (ItemStack stack : inputItems) {
            boolean matched = false;
            Iterator<Ingredient> it = ingredientsCopy.iterator();
            while (it.hasNext()) {
                Ingredient ing = it.next();
                if (ing.test(stack)) {
                    it.remove();
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                return false;
            }
        }

        return this.material.test(input.getItem(3));
    }

    @Override
    public ItemStack assemble(AlchemyTableRecipeInput table, HolderLookup.Provider registries) {
        return this.getResultItem(registries);
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

}