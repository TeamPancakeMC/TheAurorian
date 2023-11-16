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

public class MoonlightForgeSerializer implements RecipeSerializer<MoonlightForgeRecipe> {

    @Override
    public MoonlightForgeRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
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
        return new MoonlightForgeRecipe(recipeId, equipment, upgradeMaterial, resultStack);
    }

    @Override
    public MoonlightForgeRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        Ingredient equipment = Ingredient.fromNetwork(buffer);
        Ingredient upgradeMaterial = Ingredient.fromNetwork(buffer);
        ItemStack resultStack = buffer.readItem();
        return new MoonlightForgeRecipe(recipeId, equipment, upgradeMaterial, resultStack);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, MoonlightForgeRecipe recipe) {
        recipe.equipment.toNetwork(buffer);
        recipe.upgradeMaterial.toNetwork(buffer);
        buffer.writeItem(recipe.result);
    }

}