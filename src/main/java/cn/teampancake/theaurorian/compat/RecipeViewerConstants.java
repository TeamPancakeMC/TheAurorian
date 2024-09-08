package cn.teampancake.theaurorian.compat;

import cn.teampancake.theaurorian.common.items.crafting.AlchemyTableRecipe;
import cn.teampancake.theaurorian.common.items.crafting.MoonlightForgeRecipe;
import cn.teampancake.theaurorian.common.items.crafting.ScrapperRecipe;
import cn.teampancake.theaurorian.common.registry.TARecipes;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;

public class RecipeViewerConstants {

    public static List<RecipeHolder<MoonlightForgeRecipe>> getAllMoonlightForgeRecipes(RecipeManager manager) {
        return new ArrayList<>(manager.getAllRecipesFor(TARecipes.MOONLIGHT_FORGE_RECIPE.get()));
    }

    public static List<RecipeHolder<AlchemyTableRecipe>> getAllAlchemyTableRecipes(RecipeManager manager) {
        return new ArrayList<>(manager.getAllRecipesFor(TARecipes.ALCHEMY_TABLE_RECIPE.get()));
    }

    public static List<RecipeHolder<ScrapperRecipe>> getAllScrapperRecipes(RecipeManager manager) {
        return new ArrayList<>(manager.getAllRecipesFor(TARecipes.SCRAPPER_RECIPE.get()));
    }

}