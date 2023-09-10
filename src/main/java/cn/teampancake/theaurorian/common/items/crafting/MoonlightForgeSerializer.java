package cn.teampancake.theaurorian.common.items.crafting;

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

public record MoonlightForgeSerializer<T extends MoonlightForgeRecipe>(MoonlightForgeSerializer.Factory<T> factory) implements RecipeSerializer<T> {

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
        Ingredient equipment = Ingredient.fromJson(GsonHelper.getNonNull(serializedRecipe, "equipment"));
        Ingredient upgradeMaterial = Ingredient.fromJson(GsonHelper.getNonNull(serializedRecipe, "upgrade_material"));
        ItemStack resultStack;
        if (serializedRecipe.get("result").isJsonObject()) {
            resultStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(serializedRecipe, "result"));
        } else {
            String s1 = GsonHelper.getAsString(serializedRecipe, "result");
            ResourceLocation resourceLocation = new ResourceLocation(s1);
            resultStack = new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(resourceLocation)));
        }
        return this.factory.create(recipeId, equipment, upgradeMaterial, resultStack);
    }

    @Override
    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        Ingredient equipment = Ingredient.fromNetwork(buffer);
        Ingredient upgradeMaterial = Ingredient.fromNetwork(buffer);
        ItemStack resultStack = buffer.readItem();
        return this.factory.create(recipeId, equipment, upgradeMaterial, resultStack);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        recipe.equipment.toNetwork(buffer);
        recipe.upgradeMaterial.toNetwork(buffer);
        buffer.writeItem(recipe.result);
    }

    public interface Factory<T extends MoonlightForgeRecipe> {
        T create(ResourceLocation id, Ingredient equipment, Ingredient upgradeMaterial, ItemStack result);
    }

}