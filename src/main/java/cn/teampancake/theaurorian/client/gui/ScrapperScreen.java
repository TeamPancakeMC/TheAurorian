package cn.teampancake.theaurorian.client.gui;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.inventory.ScrapperMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScrapperScreen extends AbstractContainerScreen<ScrapperMenu> {

    private static final ResourceLocation CONTAINER_LOCATION = AurorianMod.prefix("textures/gui/scrapper.png");
    private float craftRotation = 0.0f;

    public ScrapperScreen(ScrapperMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        int scrapTime = this.menu.getScrapTime();
        guiGraphics.blit(CONTAINER_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        float x = (float) (i + 62 + 8);
        float y = (float) (j + 37 + 8);
        poseStack.translate(x, y, 0.0F);
        if (scrapTime > 0) {
            poseStack.mulPose(Axis.ZP.rotation(this.craftRotation));
            this.craftRotation += 0.25F;
        }

        poseStack.translate(-x, -y, 0.0F);
        guiGraphics.blit(CONTAINER_LOCATION, i + 62, j + 37, 176, 55, 16, 16);
        poseStack.popPose();
        int scaled = (int) (((float) scrapTime / 100) * 55);
        guiGraphics.blit(CONTAINER_LOCATION, i + 98, j + 16, 176, 0, 9, scaled);
    }

}