package cn.teampancake.theaurorian.common.items.crafting;

import cn.teampancake.theaurorian.common.registry.TARecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class AlchemyTableRecipe implements Recipe<Container> {

    public final ResourceLocation id;
    public final ItemStack input1;
    public final ItemStack input2;
    public final ItemStack input3;
    public final ItemStack material;
    public final ItemStack result;
    public int input1Amount;
    public int input2Amount;
    public int input3Amount;
    public int resultAmount;
    public int alchemyTime;

    public AlchemyTableRecipe(
            ResourceLocation id, ItemStack input1, ItemStack input2, ItemStack input3, ItemStack material, ItemStack result,
            int input1Amount, int input2Amount, int input3Amount, int resultAmount, int alchemyTime) {
        this.id = id;
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.material = material;
        this.result = result;
        this.input1Amount = input1Amount;
        this.input2Amount = input2Amount;
        this.input3Amount = input3Amount;
        this.resultAmount = resultAmount;
        this.alchemyTime = alchemyTime;
    }

    public Map<ItemStack, Integer> getInputAndAmount() {
        Map<ItemStack, Integer> map = new HashMap<>();
        map.put(this.input1, this.input1Amount);
        map.put(this.input2, this.input2Amount);
        map.put(this.input3, this.input3Amount);
        return map;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return true;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        ItemStack stack = this.result.copy();
        stack.setCount(this.resultAmount);
        return stack;
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
        return TARecipes.ALCHEMY_TABLE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return TARecipes.ALCHEMY_TABLE_RECIPE.get();
    }

}