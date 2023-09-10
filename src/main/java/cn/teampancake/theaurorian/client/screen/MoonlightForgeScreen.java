package cn.teampancake.theaurorian.client.screen;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.inventory.MoonlightForgeMenu;
import cn.teampancake.theaurorian.common.blocks.entity.MoonlightForgeBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
public class MoonlightForgeScreen extends AbstractContainerScreen<MoonlightForgeMenu> {

    private static final ResourceLocation CONTAINER_LOCATION = AurorianMod.prefix("textures/gui/moonlight_forge.png");
    private float craftRotation = 0.0f;

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
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        int i = (this.width - this.imageWidth) / 2 + 49;
        int j = (this.height - this.imageHeight) / 2 + 31;
        String s = "string.theaurorian.gui.moonlight_forge.";
        MoonlightForgeBlockEntity blockEntity = this.menu.getBlockEntity();
        if (mouseX >= i && mouseX <= i + 24 && mouseY >= j && mouseY <= j + 24) {
            if (blockEntity.isPowered || !blockEntity.hasMoonLight) {
                s += blockEntity.isPowered ? "redstone" : "no_moonlight";
                List<Component> tooltipLines = List.of(Component.translatable(s));
                guiGraphics.renderComponentTooltip(this.font, tooltipLines, mouseX, mouseY);
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        PoseStack poseStack = guiGraphics.pose();
        MoonlightForgeBlockEntity blockEntity = this.menu.getBlockEntity();
        guiGraphics.blit(CONTAINER_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if (blockEntity.isPowered) {
            guiGraphics.blit(CONTAINER_LOCATION, i + 49, j + 31, 176, 41, 24, 24);
        } else if (!blockEntity.hasMoonLight) {
            guiGraphics.blit(CONTAINER_LOCATION, i + 49, j + 31, 176, 41, 0, 24);
        }

        if (blockEntity.craftProgress > 0) {
            if (!blockEntity.isPowered && blockEntity.hasMoonLight) {
                poseStack.pushPose();
                float x = (float) (i + 49 + 12);
                float y = (float) (j + 31 + 12);
                poseStack.translate(x, y, 0.0F);
                poseStack.mulPose(Axis.ZP.rotationDegrees(this.craftRotation));
                this.craftRotation += 0.25F;
                poseStack.translate(-x, -y, 0.0F);
                guiGraphics.blit(CONTAINER_LOCATION, i + 49, j + 31, 176, 65, 24, 24);
                poseStack.popPose();
            } else if (this.craftRotation != 0.0f) {
                this.craftRotation = 0.0f;
            }

            int scaled = (int) (((float) blockEntity.craftProgress / 100) * 24);
            guiGraphics.blit(CONTAINER_LOCATION, i + 107, j + 35, 176, 24, scaled, 17);
        }
    }

}