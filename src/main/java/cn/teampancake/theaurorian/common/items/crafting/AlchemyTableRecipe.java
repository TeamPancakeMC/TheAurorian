package cn.teampancake.theaurorian.common.items.crafting;

import cn.teampancake.theaurorian.common.blocks.entity.AlchemyTableBlockEntity;
import cn.teampancake.theaurorian.common.registry.TARecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class AlchemyTableRecipe implements Recipe<AlchemyTableBlockEntity> {

    public final ResourceLocation id;
    public final Ingredient input1;
    public final Ingredient input2;
    public final Ingredient input3;
    public final Ingredient material;
    public final ItemStack result;
    public int alchemyTime;

    public AlchemyTableRecipe(
            ResourceLocation id, Ingredient input1, Ingredient input2, Ingredient input3, Ingredient material, ItemStack result, int alchemyTime) {
        this.id = id;
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.material = material;
        this.result = result;
        this.alchemyTime = alchemyTime;
    }

    @Override
    public boolean matches(AlchemyTableBlockEntity table, Level level) {
        boolean b1 = false, b2 = false, b3 = false;
        for(int i=0;i<3;i++){
            ItemStack item = table.getItem(i);
            if(item.isEmpty()) return false;
            if(input1.test(item)) b1=true;
            else if(input2.test(item)) b2=true;
            else if(input3.test(item)) b3=true;
        }
        if(!b1 || !b2 || !b3) return false;
        return material.test(table.getItem(3));
    }

    @Override
    public ItemStack assemble(AlchemyTableBlockEntity table, RegistryAccess registryAccess) {
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
        return TARecipes.ALCHEMY_TABLE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return TARecipes.ALCHEMY_TABLE_RECIPE.get();
    }

    public void consumeIngredients(AlchemyTableBlockEntity table){
        for(int i=0;i<4;i++){
            var item = table.getItem(i);
            if(item.hasCraftingRemainingItem()){
                if(item.getCount()==1)
                    table.setItem(i,item.getCraftingRemainingItem());
                else{
                    Containers.dropItemStack(table.getLevel(),table.getBlockPos().getX() + 0.5,table.getBlockPos().getY() + 1.2,table.getBlockPos().getZ() + 0.5,item.getCraftingRemainingItem());
                    item.shrink(1);
                }
            } else item.shrink(1);
        }
    }

}