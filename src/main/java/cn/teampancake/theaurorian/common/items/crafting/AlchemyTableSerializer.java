package cn.teampancake.theaurorian.common.items.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public record AlchemyTableSerializer<T extends AlchemyTableRecipe>(Factory<T> factory) implements RecipeSerializer<T> {

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {

        Ingredient input1 = Ingredient.fromJson(serializedRecipe.get("input1"), false);
        Ingredient input2 = Ingredient.fromJson(serializedRecipe.get("input2"), false);
        Ingredient input3 = Ingredient.fromJson(serializedRecipe.get("input3"), false);
        Ingredient material = Ingredient.fromJson(serializedRecipe.get("material"), false);
        ItemStack result = this.getItemStack(serializedRecipe, "result");
        int alchemyTime = GsonHelper.getAsInt(serializedRecipe, "alchemy_time", 1);
        return this.factory.create(recipeId, input1, input2, input3, material, result, alchemyTime);
    }


    private ItemStack getItemStack(JsonObject serializedRecipe, String memberName) {
        ItemStack itemStack;
        if (serializedRecipe.get(memberName).isJsonObject()) {
            itemStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, memberName));
        } else {
            String s1 = GsonHelper.getAsString(serializedRecipe, memberName);
            ResourceLocation resourceLocation = new ResourceLocation(s1);
            itemStack = new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(resourceLocation)));
        }

        return itemStack;
    }

    @Override
    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        Ingredient input1 = Ingredient.fromNetwork(buffer);
        Ingredient input2 = Ingredient.fromNetwork(buffer);
        Ingredient input3 = Ingredient.fromNetwork(buffer);
        Ingredient material = Ingredient.fromNetwork(buffer);
        ItemStack result = buffer.readItem();
        int alchemyTime = buffer.readVarInt();
        return this.factory.create(recipeId, input1, input2, input3, material, result, alchemyTime);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {

        recipe.input1.toNetwork(buffer);
        recipe.input2.toNetwork(buffer);
        recipe.input3.toNetwork(buffer);
        recipe.material.toNetwork(buffer);
        buffer.writeItem(recipe.result);
        buffer.writeVarInt(recipe.alchemyTime);
    }

    public interface Factory<T extends AlchemyTableRecipe> {
        T create(ResourceLocation id, Ingredient input1, Ingredient input2, Ingredient input3, Ingredient material,
                 ItemStack result, int alchemyTime);
    }

}