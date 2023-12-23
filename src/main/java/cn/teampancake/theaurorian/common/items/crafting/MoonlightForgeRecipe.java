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

public class MoonlightForgeRecipe implements Recipe<Container> {

    protected final ResourceLocation id;
    protected final Ingredient equipment;
    protected final Ingredient upgradeMaterial;
    protected final ItemStack result;

    public MoonlightForgeRecipe(ResourceLocation id, Ingredient equipment, Ingredient upgradeMaterial, ItemStack result) {
        this.id = id;
        this.equipment = equipment;
        this.upgradeMaterial = upgradeMaterial;
        this.result = result;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return this.equipment.test(container.getItem(0)) && this.upgradeMaterial.test(container.getItem(1));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
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