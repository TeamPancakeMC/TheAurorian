package cn.teampancake.theaurorian.client.gui;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.inventory.MoonlightForgeMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class MoonlightForgeScreen extends AbstractContainerScreen<MoonlightForgeMenu> {

    private static final ResourceLocation CONTAINER_LOCATION = TheAurorian.prefix("textures/gui/moonlight_forge.png");
    private float craftRotation = 0.0F;

    public MoonlightForgeScreen(MoonlightForgeMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        int i = (this.width - this.imageWidth) / 2 + 49;
        int j = (this.height - this.imageHeight) / 2 + 31;
        String s = "tooltips.block.theaurorian.moonlight_forge.gui.";
        if (mouseX >= i && mouseX <= i + 24 && mouseY >= j && mouseY <= j + 24) {
            if (this.menu.isPowered()) {
                s += "redstone";
            } else if (!this.menu.hasMoonlight()) {
                s += "no_moonlight";
            } else {
                s += "has_moonlight";
            }

            List<Component> tooltipLines = List.of(Component.translatable(s));
            guiGraphics.renderComponentTooltip(this.font, tooltipLines, mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        PoseStack poseStack = guiGraphics.pose();
        guiGraphics.blit(CONTAINER_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if (this.menu.isPowered()) {
            guiGraphics.blit(CONTAINER_LOCATION, i + 49, j + 31, 176, 41, 24, 24);
        } else if (!this.menu.hasMoonlight()) {
            guiGraphics.blit(CONTAINER_LOCATION, i + 49, j + 31, 176, 0, 24, 24);
        }

        if (this.menu.getCraftProgress() > 0) {
            if (!this.menu.isPowered() && this.menu.hasMoonlight()) {
                poseStack.pushPose();
                float x = (float) (i + 49 + 12);
                float y = (float) (j + 31 + 12);
                poseStack.translate(x, y, 0.0F);
                poseStack.mulPose(Axis.ZP.rotation(this.craftRotation));
                this.craftRotation += 0.5F;
                poseStack.translate(-x, -y, 0.0F);
                guiGraphics.blit(CONTAINER_LOCATION, i + 49, j + 31, 176, 65, 24, 24);
                poseStack.popPose();
            } else if (this.craftRotation != 0.0f) {
                this.craftRotation = 0.0f;
            }

            int scaled = (int) (((float) this.menu.getCraftProgress() / 100) * 24);
            guiGraphics.blit(CONTAINER_LOCATION, i + 107, j + 35, 176, 24, scaled, 17);
        }
    }

}