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

public record ScrapperSerializer<T extends ScrapperRecipe>(Factory<T> factory) implements RecipeSerializer<T> {

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
        JsonElement jsonElement = GsonHelper.isArrayNode(serializedRecipe, "ingredient") ?
                GsonHelper.getAsJsonArray(serializedRecipe, "ingredient") :
                GsonHelper.getAsJsonObject(serializedRecipe, "ingredient");
        Ingredient ingredient = Ingredient.fromJson(jsonElement, Boolean.FALSE);
        ItemStack resultStack;
        if (serializedRecipe.get("result").isJsonObject()) {
            resultStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "result"));
        } else {
            String s1 = GsonHelper.getAsString(serializedRecipe, "result");
            ResourceLocation resourceLocation = new ResourceLocation(s1);
            resultStack = new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(resourceLocation)));
        }
        int amount = GsonHelper.getAsInt(serializedRecipe, "amount", 1);
        return this.factory.create(recipeId, ingredient, resultStack, amount);
    }

    @Override
    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        ItemStack resultStack = buffer.readItem();
        int amount = buffer.readVarInt();
        return this.factory.create(recipeId, ingredient, resultStack, amount);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        recipe.ingredient.toNetwork(buffer);
        buffer.writeItem(recipe.result);
        buffer.writeVarInt(recipe.amount);
    }

    public interface Factory<T extends ScrapperRecipe> {
        T create(ResourceLocation id, Ingredient ingredient, ItemStack result, int amount);
    }

}