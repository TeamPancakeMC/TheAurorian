package cn.teampancake.theaurorian.client.gui.screens;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class StarSignsScreen extends Screen {

    private static final ResourceLocation STAR_SIGNS = TheAurorian.prefix("textures/gui/star_signs.png");

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
    private static final int EDGE_INSET = 4; // 与边缘留白，避免误触

    private int currentPage = 0; // 0,1,2

    public StarSignsScreen() {
        super(Component.literal("Star Signs"));
    }

    @Override
    protected void init() {
        // 无翻页UI，无徽记按钮
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics gg, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gg, mouseX, mouseY, partialTick);
        int dstX = (this.width - PAGE_W) / 2;
        int dstY = (this.height - PAGE_H) / 2;
        int srcU = currentPage * PAGE_W; // 0 / 142 / 284
        gg.blit(STAR_SIGNS, dstX, dstY, srcU, 0, PAGE_W, PAGE_H, TEX_W, TEX_H);

        // 徽记：叠加到页右上角（贴边对齐）
        int emblemDstX = dstX + PAGE_W - EMBLEM_W + 7;
        int emblemDstY = dstY + 6;
        gg.blit(STAR_SIGNS, emblemDstX, emblemDstY, EMBLEM_U, EMBLEM_V, EMBLEM_W, EMBLEM_H, TEX_W, TEX_H);

        // 页码：y=174，x=页中心（相对页左侧71），文本居中，缩放 0.80
        String footer = (currentPage + 1) + " / 3";
        int pageCenterX = dstX + (PAGE_W / 2); // 71 像素相对页左侧
        int footerY = dstY + 174;
        int footerX = pageCenterX - this.font.width(footer) / 2;
        var pose = gg.pose();
        pose.pushPose();
        pose.translate(pageCenterX, footerY, 0.0F);
        pose.scale(0.80F, 0.80F, 1.0F);
        pose.translate(-pageCenterX, -footerY, 0.0F);
        gg.drawString(this.font, footer, footerX, footerY, 0x404040, false);
        pose.popPose();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int dstX = (this.width - PAGE_W) / 2;
            int dstY = (this.height - PAGE_H) / 2;
            int pageLeft = dstX;
            int pageTop = dstY;
            int pageRight = dstX + PAGE_W;
            int pageBottom = dstY + PAGE_H;
            // 仅在页内点击才处理
            if (mouseX >= pageLeft && mouseX <= pageRight && mouseY >= pageTop && mouseY <= pageBottom) {
                // 左下角区域
                int leftCornerX0 = pageLeft + EDGE_INSET;
                int leftCornerY0 = pageBottom - CORNER_H - EDGE_INSET;
                int leftCornerX1 = leftCornerX0 + CORNER_W;
                int leftCornerY1 = pageBottom - EDGE_INSET;
                // 右下角区域
                int rightCornerX1 = pageRight - EDGE_INSET;
                int rightCornerY1 = pageBottom - EDGE_INSET;
                int rightCornerX0 = rightCornerX1 - CORNER_W;
                int rightCornerY0 = rightCornerY1 - CORNER_H;

                boolean inLeftBottom = mouseX >= leftCornerX0 && mouseX <= leftCornerX1 && mouseY >= leftCornerY0 && mouseY <= leftCornerY1;
                boolean inRightBottom = mouseX >= rightCornerX0 && mouseX <= rightCornerX1 && mouseY >= rightCornerY0 && mouseY <= rightCornerY1;

                if (inRightBottom && currentPage < 2) {
                    currentPage++;
                    return true;
                }
                if (inLeftBottom && currentPage > 0) {
                    currentPage--;
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
} 