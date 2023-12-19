package cn.teampancake.theaurorian.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import org.joml.Vector2ic;

import java.util.List;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("deprecation")
public class TATooltipRenderUtils {

    public static void renderTooltips(RenderTooltipEvent.Pre event, ResourceLocation tooltips, boolean isAdvanced, int outerColor, int intermediateColor, int innerColor, int[] xOffset, int[] yOffset, int[] uOffset, int[] vOffset, int[] uWidth, int[] vHeight) {
        ClientTooltipPositioner tooltipPositioner = event.getTooltipPositioner();
        List<ClientTooltipComponent> components = event.getComponents();
        GuiGraphics graphics = event.getGraphics();
        Font font = event.getFont();
        graphics.pose().pushPose();
        int mouseX = event.getX();
        int mouseY = event.getY();
        int width = event.getScreenWidth();
        int height = event.getScreenHeight();
        int i0 = 0;
        int j0 = components.size() == 1 ? -2 : 0;
        for (ClientTooltipComponent component : components) {
            j0 += component.getHeight();
            int k = component.getWidth(font);
            if (k > i0) {
                i0 = k;
            }
        }
        int tooltipWidth = i0;
        int tooltipHeight = j0;
        Vector2ic vector2ic = tooltipPositioner.positionTooltip(width, height, mouseX, mouseY, tooltipWidth, tooltipHeight);
        int tooltipPosX = vector2ic.x();
        int tooltipPosY = vector2ic.y();
        graphics.drawManaged(() -> {
            int i = tooltipPosX - 3;
            int j = tooltipPosY - 3;
            int k = tooltipWidth + 3 + 3;
            int l = tooltipHeight + 3 + 7;
            TooltipRenderUtil.renderRectangle(graphics, i, j, k, l, 400, -267386864);
            TooltipRenderUtil.renderHorizontalLine(graphics, i, j - 1, k, 400, outerColor);
            TooltipRenderUtil.renderHorizontalLine(graphics, i, j + l, k, 400, outerColor);
            TooltipRenderUtil.renderHorizontalLine(graphics, i + 2, j - 1 + 2, k - 2, 400, innerColor);
            TooltipRenderUtil.renderHorizontalLine(graphics, i + 2, j + l - 2, k - 2, 400, innerColor);
            TooltipRenderUtil.renderVerticalLineGradient(graphics, i - 1, j, l, 400, outerColor, outerColor);
            TooltipRenderUtil.renderVerticalLineGradient(graphics, i + k, j, l, 400, outerColor, outerColor);
            TooltipRenderUtil.renderVerticalLineGradient(graphics, i - 1 + 2, j, l - 2, 400, innerColor, innerColor);
            TooltipRenderUtil.renderVerticalLineGradient(graphics, i + k - 2, j, l - 2, 400, innerColor, innerColor);
            TooltipRenderUtil.renderFrameGradient(graphics, i, j + 1, k, l, 400, intermediateColor, intermediateColor);
        });
        graphics.pose().translate(0.0F, 0.0F, 400.0F);
        int k1 = tooltipPosY + 2;
        for (int l1 = 0; l1 < components.size(); ++l1) {
            ClientTooltipComponent component = components.get(l1);
            int textWidth = component.getWidth(font);
            int k0 = tooltipPosX + (tooltipWidth - textWidth) / 2;
            component.renderText(font, k0, k1, graphics.pose().last().pose(), graphics.bufferSource());
            k1 += component.getHeight() + (l1 == 0 ? 2 : 0);
        }
        k1 = tooltipPosY + 2;
        RenderSystem.enableBlend();
        int x1 = tooltipPosX + (tooltipWidth - uWidth[4]) / 2;
        graphics.blit(tooltips, mouseX + xOffset[0], mouseY + yOffset[0], uOffset[0], vOffset[0], uWidth[0], vHeight[0], 48, 32);
        graphics.blit(tooltips, mouseX + xOffset[1], mouseY + tooltipHeight + yOffset[1], uOffset[1], vOffset[1], uWidth[1], vHeight[1], 48, 32);
        graphics.blit(tooltips, mouseX + tooltipWidth + xOffset[2], mouseY + yOffset[2], uOffset[2], vOffset[2], uWidth[2], vHeight[2], 48, 32);
        graphics.blit(tooltips, mouseX + tooltipWidth + xOffset[3], mouseY + tooltipHeight + yOffset[3], uOffset[3], vOffset[3], uWidth[3], vHeight[3], 48, 32);
        graphics.blit(tooltips, x1, mouseY + yOffset[4], uOffset[4], vOffset[4], uWidth[4], vHeight[4], 48, 32);
        if (isAdvanced) {
            int x2 = tooltipPosX + (tooltipWidth - uWidth[5]) / 2;
            graphics.blit(tooltips, x2, mouseY + tooltipHeight + yOffset[5], uOffset[5], vOffset[5], uWidth[5], vHeight[5], 48, 32);
        }
        RenderSystem.disableBlend();
        for (int k2 = 0; k2 < components.size(); ++k2) {
            ClientTooltipComponent component = components.get(k2);
            component.renderImage(font, tooltipPosX, k1, graphics);
            k1 += component.getHeight() + (k2 == 0 ? 2 : 0);
        }
        graphics.pose().popPose();
    }

}