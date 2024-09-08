package cn.teampancake.theaurorian.compat.jei.categories;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.items.crafting.MoonlightForgeRecipe;
import cn.teampancake.theaurorian.common.registry.TABlocks;
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

public class MoonlightForgeCategory implements IRecipeCategory<MoonlightForgeRecipe> {

    public static final RecipeType<MoonlightForgeRecipe> MOONLIGHT_FORGE_RECIPE =
            RecipeType.create(TheAurorian.MOD_ID, "moonlight_forge", MoonlightForgeRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final Component localizedName;

    public MoonlightForgeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = TheAurorian.prefix("textures/gui/moonlight_forge_jei.png");
        this.background = guiHelper.createDrawable(location, 0, 0, 170, 80);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(TABlocks.MOONLIGHT_FORGE.get()));
        this.localizedName = Component.translatable(TheAurorian.MOD_ID + ".container.moonlight_forge");
    }

    @Override
    public RecipeType<MoonlightForgeRecipe> getRecipeType() {
        return MOONLIGHT_FORGE_RECIPE;
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
    public void setRecipe(IRecipeLayoutBuilder builder, MoonlightForgeRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 19, 32).addIngredients(recipe.equipment());
        builder.addSlot(RecipeIngredientRole.INPUT, 81, 32).addIngredients(recipe.upgradeMaterial());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 139, 32).addItemStack(recipe.result());
    }

}