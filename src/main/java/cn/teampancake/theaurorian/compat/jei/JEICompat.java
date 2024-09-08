package cn.teampancake.theaurorian.compat.jei;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.gui.AlchemyTableScreen;
import cn.teampancake.theaurorian.client.gui.MoonlightForgeScreen;
import cn.teampancake.theaurorian.client.gui.ScrapperScreen;
import cn.teampancake.theaurorian.common.items.crafting.AlchemyTableRecipe;
import cn.teampancake.theaurorian.common.items.crafting.MoonlightForgeRecipe;
import cn.teampancake.theaurorian.common.items.crafting.ScrapperRecipe;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.compat.RecipeViewerConstants;
import cn.teampancake.theaurorian.compat.jei.categories.AlchemyTableCategory;
import cn.teampancake.theaurorian.compat.jei.categories.MoonlightForgeCategory;
import cn.teampancake.theaurorian.compat.jei.categories.ScrapperCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEICompat implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return TheAurorian.prefix("jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<RecipeHolder<MoonlightForgeRecipe>> moonlightForgeRecipes = RecipeViewerConstants.getAllMoonlightForgeRecipes(manager);
        List<RecipeHolder<AlchemyTableRecipe>> alchemyTableRecipes = RecipeViewerConstants.getAllAlchemyTableRecipes(manager);
        List<RecipeHolder<ScrapperRecipe>> scrapperRecipes = RecipeViewerConstants.getAllScrapperRecipes(manager);
        registration.addRecipes(MoonlightForgeCategory.MOONLIGHT_FORGE_RECIPE, moonlightForgeRecipes.stream().map(RecipeHolder::value).toList());
        registration.addRecipes(AlchemyTableCategory.ALCHEMY_TABLE_RECIPE, alchemyTableRecipes.stream().map(RecipeHolder::value).toList());
        registration.addRecipes(ScrapperCategory.SCRAPPER_RECIPE, scrapperRecipes.stream().map(RecipeHolder::value).toList());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new MoonlightForgeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new AlchemyTableCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ScrapperCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(TABlocks.MOONLIGHT_FORGE.get()), MoonlightForgeCategory.MOONLIGHT_FORGE_RECIPE);
        registration.addRecipeCatalyst(new ItemStack(TABlocks.ALCHEMY_TABLE.get()), AlchemyTableCategory.ALCHEMY_TABLE_RECIPE);
        registration.addRecipeCatalyst(new ItemStack(TABlocks.SCRAPPER.get()), ScrapperCategory.SCRAPPER_RECIPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(MoonlightForgeScreen.class, 105, 32, 22, 15, MoonlightForgeCategory.MOONLIGHT_FORGE_RECIPE);
        registration.addRecipeClickArea(AlchemyTableScreen.class, 109, 34, 23, 33, AlchemyTableCategory.ALCHEMY_TABLE_RECIPE);
        registration.addRecipeClickArea(ScrapperScreen.class, 99, 17, 7, 53, ScrapperCategory.SCRAPPER_RECIPE);
    }

}