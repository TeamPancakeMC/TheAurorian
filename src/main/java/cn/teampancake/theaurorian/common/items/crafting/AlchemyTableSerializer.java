package cn.teampancake.theaurorian.common.items.crafting;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public record AlchemyTableSerializer<T extends AlchemyTableRecipe>(Factory<T> factory) implements RecipeSerializer<T> {

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
        ItemStack input1 = this.getItemStack(serializedRecipe, "input1");
        ItemStack input2 = this.getItemStack(serializedRecipe, "input2");
        ItemStack input3 = this.getItemStack(serializedRecipe, "input3");
        ItemStack material = this.getItemStack(serializedRecipe, "material");
        ItemStack result = this.getItemStack(serializedRecipe, "result");
        int input1Amount = this.getAmount(serializedRecipe, "input1");
        int input2Amount = this.getAmount(serializedRecipe, "input2");
        int input3Amount = this.getAmount(serializedRecipe, "input3");
        int resultAmount = this.getAmount(serializedRecipe, "result");
        int alchemyTime = GsonHelper.getAsInt(serializedRecipe, "alchemy_time", 1);
        return this.factory.create(recipeId, input1, input2, input3, material, result,
                input1Amount, input2Amount, input3Amount, resultAmount, alchemyTime);
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

    private int getAmount(JsonObject serializedRecipe, String memberName) {
        return GsonHelper.getAsInt(serializedRecipe.getAsJsonObject(memberName), "amount", 1);
    }

    @Override
    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        ItemStack input1 = buffer.readItem();
        ItemStack input2 = buffer.readItem();
        ItemStack input3 = buffer.readItem();
        ItemStack material = buffer.readItem();
        ItemStack result = buffer.readItem();
        int input1Amount = buffer.readVarInt();
        int input2Amount = buffer.readVarInt();
        int input3Amount = buffer.readVarInt();
        int resultAmount = buffer.readVarInt();
        int alchemyTime = buffer.readVarInt();
        return this.factory.create(recipeId, input1, input2, input3, material, result,
                input1Amount, input2Amount, input3Amount, resultAmount, alchemyTime);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        buffer.writeItem(recipe.input1);
        buffer.writeItem(recipe.input2);
        buffer.writeItem(recipe.input3);
        buffer.writeItem(recipe.material);
        buffer.writeItem(recipe.result);
        buffer.writeVarInt(recipe.input1Amount);
        buffer.writeVarInt(recipe.input2Amount);
        buffer.writeVarInt(recipe.input3Amount);
        buffer.writeVarInt(recipe.resultAmount);
        buffer.writeVarInt(recipe.alchemyTime);
    }

    public interface Factory<T extends AlchemyTableRecipe> {
        T create(ResourceLocation id, ItemStack input1, ItemStack input2, ItemStack input3, ItemStack material,
                 ItemStack result, int input1Amount, int input2Amount, int input3Amount, int resultAmount, int alchemyTime);
    }

}