package cn.teampancake.theaurorian.client.gui.screens;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.inventory.AccessoriesMenu;
import cn.teampancake.theaurorian.client.widget.AccessoriesButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Inventory;
import top.theillusivec4.curios.api.client.ICuriosScreen;
import top.theillusivec4.curios.client.CuriosClientConfig;
import top.theillusivec4.curios.client.gui.CuriosScreen;
import top.theillusivec4.curios.client.CuriosClientConfig.Client.ButtonCorner;

import javax.annotation.Nonnull;

@SuppressWarnings("NotNullFieldNotInitialized")
public class AccessoriesScreen extends EffectRenderingInventoryScreen<AccessoriesMenu> implements RecipeUpdateListener, ICuriosScreen {

    public static final WidgetSprites BUTTON = new WidgetSprites(
            TheAurorian.prefix("accessories/button"),
            TheAurorian.prefix("accessories/button"));
    public static final ResourceLocation INVENTORY = TheAurorian.prefix("textures/gui/accessories.png");
    private final RecipeBookComponent recipeBookGui = new RecipeBookComponent();
    private AccessoriesButton buttonAccessories;
    private boolean widthTooNarrow;

    public AccessoriesScreen(AccessoriesMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    public static Tuple<Integer, Integer> getButtonOffset(ButtonCorner corner, boolean isCreative) {
        CuriosClientConfig.Client client = CuriosClientConfig.CLIENT;
        int x = 0;
        int y = 0;
        if (isCreative) {
            x += corner.getCreativeXoffset() + client.creativeButtonXOffset.get();
            y += corner.getCreativeYoffset() + client.creativeButtonYOffset.get();
        } else {
            x += corner.getXoffset() + client.buttonXOffset.get();
            y += corner.getYoffset() + client.buttonYOffset.get();
        }

        return new Tuple<>(x, y);
    }

    @Override
    public void init() {
        if (this.minecraft != null) {
            this.leftPos = (this.width - this.imageWidth) / 2;
            this.topPos = (this.height - this.imageHeight) / 2;
            this.widthTooNarrow = true;
            this.recipeBookGui.init(this.width, this.height, this.minecraft, true, this.menu);
            this.addWidget(this.recipeBookGui);
            this.setInitialFocus(this.recipeBookGui);
            if (this.getMinecraft().player != null
                    && this.getMinecraft().player.isCreative()
                    && this.recipeBookGui.isVisible()) {
                this.recipeBookGui.toggleVisibility();
            }

            Tuple<Integer, Integer> offsets = CuriosScreen.getButtonOffset(false);
            this.buttonAccessories = new AccessoriesButton(this,
                    this.getGuiLeft() + offsets.getA() - 4,
                    this.height / 2 + offsets.getB() - 2, 12, 12, BUTTON);
            this.addRenderableWidget(this.buttonAccessories);
            if (!this.menu.player.isCreative()) {
                ImageButton recipeBookButton = new ImageButton(
                        this.leftPos + 104, this.height / 2 - 22, 20, 18,
                        RecipeBookComponent.RECIPE_BUTTON_SPRITES, (button) -> {
                    this.recipeBookGui.toggleVisibility();
                    button.setPosition(this.leftPos + 104, this.height / 2 - 22);
                    this.buttonAccessories.setPosition(this.leftPos + offsets.getA() + 2, this.height / 2 + offsets.getB());
                });

                this.addRenderableWidget(recipeBookButton);
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.recipeBookGui.isVisible() && this.widthTooNarrow) {
            this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
            this.recipeBookGui.render(guiGraphics, mouseX, mouseY, partialTick);
        } else {
            super.render(guiGraphics, mouseX, mouseY, partialTick);
            this.recipeBookGui.render(guiGraphics, mouseX, mouseY, partialTick);
            this.recipeBookGui.renderGhostRecipe(guiGraphics, this.leftPos, this.topPos, false, partialTick);
        }

        this.renderTooltip(guiGraphics, mouseX, mouseY);
        this.recipeBookGui.renderTooltip(guiGraphics, this.leftPos, this.topPos, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        if (this.minecraft != null && this.minecraft.player != null) {
            int i = this.leftPos, j = this.topPos;
            guiGraphics.blit(INVENTORY_LOCATION, i, j, 0, 0, 176, this.imageHeight);
            InventoryScreen.renderEntityInInventoryFollowsMouse(
                    guiGraphics, i + 26, j + 8, i + 75, j + 78,
                    30, 0.0625F, mouseX, mouseY, this.minecraft.player);
            guiGraphics.blit(INVENTORY, i - 33 - 18 * 5, j, 0, 0, 124, 90);
        }
    }

    @Override
    protected void renderLabels(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (this.minecraft != null && this.minecraft.player != null) {
            guiGraphics.drawString(this.font, this.title, 97, 6, 4210752, false);
        }
    }

    @Override
    public void recipesUpdated() {
        this.recipeBookGui.recipesUpdated();
    }

    @Nonnull
    @Override
    public RecipeBookComponent getRecipeBookComponent() {
        return this.recipeBookGui;
    }

}