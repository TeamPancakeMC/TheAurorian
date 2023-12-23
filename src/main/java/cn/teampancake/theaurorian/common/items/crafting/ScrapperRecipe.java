package cn.teampancake.theaurorian.common.items.crafting;

import cn.teampancake.theaurorian.common.registry.TARecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class ScrapperRecipe implements Recipe<Container> {

    protected final ResourceLocation id;
    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final int amount;

    public ScrapperRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, int amount) {
        this.id = id;
        this.ingredient = ingredient;
        this.result = result;
        this.amount = amount;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return this.ingredient.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack stack = this.result.copy();
        stack.setCount(this.amount);
        return stack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        ItemStack stack = this.result;
        stack.setCount(this.amount);
        return stack;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
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