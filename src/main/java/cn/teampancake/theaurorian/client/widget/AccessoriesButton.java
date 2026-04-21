package cn.teampancake.theaurorian.client.widget;

import cn.teampancake.theaurorian.client.gui.screens.AccessoriesScreen;
import cn.teampancake.theaurorian.common.network.OpenAccessoriesC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.client.ICuriosScreen;
import top.theillusivec4.curios.client.CuriosClientConfig.Client.ButtonCorner;
import top.theillusivec4.curios.common.network.client.CPacketOpenVanilla;

import javax.annotation.Nonnull;

public class AccessoriesButton extends ImageButton {

    private final AbstractContainerScreen<?> parentGui;

    public AccessoriesButton(AbstractContainerScreen<?> parentGui, int x, int y, int width, int height, WidgetSprites sprites) {
        super(x, y, width, height, sprites, button -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                ItemStack stack = mc.player.containerMenu.getCarried();
                mc.player.containerMenu.setCarried(ItemStack.EMPTY);
                if (parentGui instanceof ICuriosScreen) {
                    InventoryScreen inventory = new InventoryScreen(mc.player);
                    mc.setScreen(inventory);
                    mc.player.containerMenu.setCarried(stack);
                    PacketDistributor.sendToServer(new CPacketOpenVanilla(stack));
                } else {
                    if (parentGui instanceof InventoryScreen inventory) {
                        RecipeBookComponent recipeBookGui = inventory.getRecipeBookComponent();
                        if (recipeBookGui.isVisible()) {
                            recipeBookGui.toggleVisibility();
                        }
                    }

                    PacketDistributor.sendToServer(new OpenAccessoriesC2SPacket(stack));
                }
            }
        });

        this.parentGui = parentGui;
    }

    @Override
    public void renderWidget(@Nonnull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        boolean isCreative = this.parentGui instanceof CreativeModeInventoryScreen;
        Tuple<Integer, Integer> offsets = AccessoriesScreen.getButtonOffset(ButtonCorner.TOP_RIGHT, isCreative);
        this.setX(this.parentGui.getGuiLeft() + offsets.getA() + 2);
        int yOffset = isCreative ? 70 : 85;
        this.setY(this.parentGui.getGuiTop() + offsets.getB() + yOffset);
        if (this.parentGui instanceof CreativeModeInventoryScreen gui) {
            boolean isInventoryTab = gui.isInventoryOpen();
            this.active = isInventoryTab;
            if (!isInventoryTab) return;
        }

        super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
    }

}
