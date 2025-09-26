package cn.teampancake.theaurorian.client.gui.screens;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.gui.AurorianEventGui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class StarSignsScreen extends Screen {

    private static final ResourceLocation STAR_SIGNS = TheAurorian.prefix("textures/gui/star_signs.png");
    private static int forecast1 = -1, forecast2 = -1, forecast3 = -1;

    // 三页：左上U 分别为 0 / 142 / 284；尺寸一致
    private static final int PAGE_W = 142;
    private static final int PAGE_H = 188;

    // 纹理总尺寸：512×188
    private static final int TEX_W = 512;
    private static final int TEX_H = 188;

    // 徽记 UV 与尺寸：左上(426,0)，右下(483,88) → 宽58，高89
    private static final int EMBLEM_U = 426;
    private static final int EMBLEM_V = 0;
    private static final int EMBLEM_W = 58;
    private static final int EMBLEM_H = 89;

    // 翻页点击区域大小（距页底角内缩）
    private static final int CORNER_W = 36;
    private static final int CORNER_H = 28;
    private static final int EDGE_INSET = 4;

    private int currentPage = 0;

    public StarSignsScreen() {
        super(Component.empty());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        int dstX = (this.width - PAGE_W) / 2;
        int dstY = (this.height - PAGE_H) / 2;
        int srcU = this.currentPage * PAGE_W;
        graphics.blit(STAR_SIGNS, dstX, dstY, srcU, 0, PAGE_W, PAGE_H, TEX_W, TEX_H);
        if (forecast1 >= 0) {
            AurorianEventGui.Card card = this.getCard();
            if (card != null) {
                card.blit(graphics, dstX, dstY);
            }
        } else {
            Component placeholder = Component.translatable("gui.theaurorian.star_signs.loading");
            graphics.drawString(this.font, placeholder, dstX + 8, dstY + 8, 0x404040, false);
        }

        // 徽记：叠加到页右上角（贴边对齐）
        int emblemDstX = dstX + PAGE_W - EMBLEM_W + 7;
        int emblemDstY = dstY + 6;
        graphics.blit(STAR_SIGNS, emblemDstX, emblemDstY, EMBLEM_U, EMBLEM_V, EMBLEM_W, EMBLEM_H, TEX_W, TEX_H);
        // 页码：y=174，x=页中心（相对页左侧71），文本居中，缩放 0.80
        String footer = (this.currentPage + 1) + " / 3";
        int pageCenterX = dstX + (PAGE_W / 2);
        int footerY = dstY + 174;
        int footerX = pageCenterX - this.font.width(footer) / 2;
        var pose = graphics.pose();
        pose.pushPose();
        pose.translate(pageCenterX, footerY, 0.0F);
        pose.scale(0.80F, 0.80F, 1.0F);
        pose.translate(-pageCenterX, -footerY, 0.0F);
        graphics.drawString(this.font, footer, footerX, footerY, 0x404040, false);
        pose.popPose();
    }

    private AurorianEventGui.@Nullable Card getCard() {
        int phase = this.currentPage == 0 ? forecast1 : (this.currentPage == 1 ? forecast2 : forecast3);
        return switch (phase) {
            case 0 -> AurorianEventGui.Card.COMBAT_NIGHT;
            case 1 -> AurorianEventGui.Card.PROTECTION_NIGHT;
            case 2 -> AurorianEventGui.Card.EXPLORATION_NIGHT;
            case 3 -> AurorianEventGui.Card.MINING_NIGHT;
            case 4 -> AurorianEventGui.Card.GROWTH_NIGHT;
            default -> null;
        };
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int dstX = (this.width - PAGE_W) / 2;
            int dstY = (this.height - PAGE_H) / 2;
            int pageRight = dstX + PAGE_W;
            int pageBottom = dstY + PAGE_H;
            // 仅在页内点击才处理
            if (mouseX >= dstX && mouseX <= pageRight && mouseY >= dstY && mouseY <= pageBottom) {
                int leftCornerX0 = dstX + EDGE_INSET;
                int leftCornerY0 = pageBottom - CORNER_H - EDGE_INSET;
                int leftCornerX1 = leftCornerX0 + CORNER_W;
                int leftCornerY1 = pageBottom - EDGE_INSET;
                int rightCornerX1 = pageRight - EDGE_INSET;
                int rightCornerY1 = pageBottom - EDGE_INSET;
                int rightCornerX0 = rightCornerX1 - CORNER_W;
                int rightCornerY0 = rightCornerY1 - CORNER_H;
                boolean inLeftBottom = mouseX >= leftCornerX0 && mouseX <= leftCornerX1 && mouseY >= leftCornerY0 && mouseY <= leftCornerY1;
                boolean inRightBottom = mouseX >= rightCornerX0 && mouseX <= rightCornerX1 && mouseY >= rightCornerY0 && mouseY <= rightCornerY1;
                if (inRightBottom && currentPage < 2) {
                    this.currentPage++;
                    return true;
                }
                
                if (inLeftBottom && currentPage > 0) {
                    this.currentPage--;
                    return true;
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public static void setForecast(int d1, int d2, int d3) {
        forecast1 = d1;
        forecast2 = d2;
        forecast3 = d3;
    }

} 