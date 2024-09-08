package cn.teampancake.theaurorian.compat.jei.categories;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.items.crafting.AlchemyTableRecipe;
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

public class AlchemyTableCategory implements IRecipeCategory<AlchemyTableRecipe> {

    public static final RecipeType<AlchemyTableRecipe> ALCHEMY_TABLE_RECIPE =
            RecipeType.create(TheAurorian.MOD_ID, "alchemy_table", AlchemyTableRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final Component localizedName;

    public AlchemyTableCategory(IGuiHelper guiHelper) {
        ResourceLocation location = TheAurorian.prefix("textures/gui/alchemy_table_jei.png");
        this.background = guiHelper.createDrawable(location, 0, 0, 176, 82);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(TABlocks.ALCHEMY_TABLE.get()));
        this.localizedName = Component.translatable(TheAurorian.MOD_ID + ".container.alchemy_table");
    }

    @Override
    public RecipeType<AlchemyTableRecipe> getRecipeType() {
        return ALCHEMY_TABLE_RECIPE;
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
    public void setRecipe(IRecipeLayoutBuilder builder, AlchemyTableRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 20, 16).addIngredients(recipe.input1());
        builder.addSlot(RecipeIngredientRole.INPUT, 44, 16).addIngredients(recipe.input2());
        builder.addSlot(RecipeIngredientRole.INPUT, 69, 16).addIngredients(recipe.input3());
        builder.addSlot(RecipeIngredientRole.INPUT, 137, 16).addIngredients(recipe.material());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 89, 42).addItemStack(recipe.result());
    }

}