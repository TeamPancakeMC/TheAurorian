package cn.teampancake.theaurorian.compat.jei.categories;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.items.crafting.ScrapperRecipe;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ScrapperCategory implements IRecipeCategory<ScrapperRecipe> {

    public static final RecipeType<ScrapperRecipe> SCRAPPER_RECIPE =
            RecipeType.create(TheAurorian.MOD_ID, "scrapper", ScrapperRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final Component localizedName;

    public ScrapperCategory(IGuiHelper guiHelper) {
        ResourceLocation location = TheAurorian.prefix("textures/gui/scrapper_jei.png");
        this.background = guiHelper.createDrawable(location, 0, 0, 170, 80);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(TABlocks.SCRAPPER.get()));
        this.localizedName = Component.translatable(TheAurorian.MOD_ID + ".container.scrapper");
    }

    @Override
    public RecipeType<ScrapperRecipe> getRecipeType() {
        return SCRAPPER_RECIPE;
    }

    @Override
    public Component getTitle() {
        return this.localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ScrapperRecipe recipe, IFocusGroup focuses) {
        ItemStack result = recipe.result();
        result.setCount(recipe.amount());
        builder.addSlot(RecipeIngredientRole.INPUT, 77, 14).addIngredients(recipe.ingredient());
        builder.addSlot(RecipeIngredientRole.INPUT, 37, 34).addItemStack(new ItemStack(TAItems.CRYSTAL.get()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 77, 55).addItemStack(result);
    }

}