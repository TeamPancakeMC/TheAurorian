package cn.teampancake.theaurorian.client.gui.screens;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.components.ChapterContent;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class VagrantNoteScreen extends BookViewScreen {

    private static final ResourceLocation VAGRANT_NOTE_COVER = TheAurorian.prefix("textures/gui/vagrant_note/cover.png");
    private static final ResourceLocation VAGRANT_NOTE_PAGE_LEFT = TheAurorian.prefix("textures/gui/vagrant_note/page_left.png");
    private static final ResourceLocation VAGRANT_NOTE_PAGE_RIGHT = TheAurorian.prefix("textures/gui/vagrant_note/page_right.png");

    private final List<ChapterContent> chapters;
    private final List<FormattedCharSequence> allLines = new ArrayList<>();
    private final List<ChapterTocEntry> tocEntries = new ArrayList<>();
    private final Map<Integer, Component> titleLineMap = new HashMap<>();
    private final int textAreaWidth = 100;
    private final int startY = 22;
    private int currentSheet = 0; // 每个“纸张”包含左右两页（Left/Right）
    private int linesPerSide = 22; // 在 init 中基于字体高度动态计算

    public VagrantNoteScreen(List<ChapterContent> chapters) {
        this.chapters = chapters;
    }

    @Override
    protected void init() {
        int lineHeight = this.font.lineHeight;
        int contentHeight = 243 - 40 - 22;
        this.linesPerSide = contentHeight / lineHeight;
        // 预构建所有文本行，避免在每帧渲染时重复读取资源
        this.allLines.clear();
        this.titleLineMap.clear();
        this.tocEntries.clear();
        if (this.minecraft != null && !this.chapters.isEmpty()) {
            for (int idx = 0; idx < this.chapters.size(); idx++) {
                int chapter = this.chapters.get(idx).index();
                int chapterStart = this.allLines.size();
                String keyTitle = String.format("text.vagrant_note.chapter_title.%02d", chapter);
                Component chapterTitle = Component.translatable(keyTitle);
                if (chapterTitle.getString().equals(keyTitle)) {
                    chapterTitle = Component.translatable("text.vagrant_note.chapter", chapter);
                }

                String key = String.format("text.vagrant_note.chapter.%02d", chapter);
                String filename = Component.translatable(key).getString();
                ResourceLocation location = TheAurorian.prefix(String.format("texts/%s.txt", filename));
                // 在正文前插入章节标题（占据一行或多行），并记录这些行索引以放大绘制
                List<FormattedCharSequence> titleLines = this.font.split(chapterTitle, this.textAreaWidth);
                for (FormattedCharSequence titleLine : titleLines) {
                    int lineIndex = this.allLines.size();
                    this.allLines.add(titleLine);
                    this.titleLineMap.put(lineIndex, chapterTitle);
                }

                // 标题与正文之间插入一个空行
                this.allLines.addAll(this.font.split(Component.literal(" "), this.textAreaWidth));
                try (BufferedReader bufferedReader = this.minecraft.getResourceManager().openAsReader(location)) {
                    // 以空行作为段落分隔，累计段落内容
                    List<String> paragraphBuffer = new ArrayList<>();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String trimmed = line.trim();
                        // 过滤历史遗留的魔法哈希行（如有需要）
                        if (trimmed.hashCode() == 125780783) continue;
                        if (trimmed.isEmpty()) {
                            this.flushParagraph(paragraphBuffer);
                        } else {
                            paragraphBuffer.add(trimmed);
                        }
                    }

                    this.flushParagraph(paragraphBuffer);
                } catch (IOException ignored) {}
                // 记录目录项
                String displayName = this.chapters.get(idx).name().getString().replaceAll("[\\[\\]]", StringUtils.EMPTY);
                Component chapterName = MutableComponent.create(chapterTitle.getContents()).append(" ").append(displayName);
                this.tocEntries.add(new ChapterTocEntry(chapter, chapterStart, chapterName));
                // 页断开：确保下一个章节从新的一页开始
                int remainder = this.allLines.size() % Math.max(1, this.linesPerSide);
                if (remainder != 0 && idx < this.chapters.size() - 1) {
                    int blanks = this.linesPerSide - remainder;
                    for (int b = 0; b < blanks; b++) {
                        this.allLines.addAll(this.font.split(Component.literal(" "), this.textAreaWidth));
                    }
                }
            }
        }

        this.currentSheet = 0;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        for (Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        if (!this.allLines.isEmpty() && this.minecraft != null) {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.scale(1.0F, 1.17F, 1.0F);
            poseStack.translate(-0.8F, 0.0F, 0.0F);
            this.renderLeftPage(guiGraphics);
            this.renderRightPage(guiGraphics);
            this.createPageControlButtons();
            poseStack.popPose();
            poseStack.pushPose();
            // 渲染左侧目录与右侧正文
            // 计算当前页（仅右页）应渲染的文本行范围
            int linesPerSheet = this.linesPerSide;
            int startIndex = Math.min(this.currentSheet * linesPerSheet, this.allLines.size());
            int endIndex = Math.min(startIndex + linesPerSheet, this.allLines.size());
            int leftPageX = (this.width - 256) / 2; // 左页贴图左上角 X
            int tocTextX = leftPageX + 10; // 左页内侧留白
            int tocY = 40; // 目录起始 Y（相对屏幕）
            int tocLineHeight = this.font.lineHeight + 2;
            for (ChapterTocEntry entry : this.tocEntries) {
                // 高亮当前页对应的目录项
                int color = 0x303030;
                int entrySheet = Math.min(entry.startLineIndex / Math.max(1, linesPerSheet),
                        Math.max(0, (this.allLines.size() - 1) / Math.max(1, linesPerSheet)));
                if (entrySheet == this.currentSheet) {
                    color = 0x0055AA; // 蓝色高亮
                }
                guiGraphics.drawString(this.font, entry.title, tocTextX, tocY, color, false);
                tocY += tocLineHeight;
            }

            // 渲染右页（进一步向右移动，留出更大的内侧空白）
            int rightTextX = (this.width / 2) + 40;
            int y = 40;
            for (int idx = startIndex; idx < endIndex; idx++) {
                FormattedCharSequence seq = this.allLines.get(idx);
                // 若该行属于章节标题，则放大加重绘制
                if (this.titleLineMap.containsKey(idx)) {
                    PoseStack ps = guiGraphics.pose();
                    ps.pushPose();
                    float scale = 1.25F;
                    ps.scale(scale, scale, 1.0F);
                    int sx = (int) (rightTextX / scale);
                    int sy = (int) (y / scale);
                    guiGraphics.drawString(this.font, seq, sx, sy, 0x222222, false);
                    ps.popPose();
                    y += (int) Math.ceil(this.font.lineHeight * 1.25F);
                } else {
                    guiGraphics.drawString(this.font, seq, rightTextX, y, 0, false);
                    y += this.font.lineHeight;
                }
            }

            // 底部页码指示
            int totalSheets = Math.max(1, (this.allLines.size() + linesPerSheet - 1) / linesPerSheet);
            String footer = String.format("%d / %d", this.currentSheet + 1, totalSheets);
            guiGraphics.drawString(this.font, footer, (this.width - this.font.width(footer)) / 2, 255, 0x404040, false);
            poseStack.popPose();
        }
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.scale(1.0F, 0.95F, 1.0F);
        guiGraphics.blit(VAGRANT_NOTE_COVER, (this.width - 391) / 2, 2, 0, 0, 391, 300, 391, 300);
        poseStack.popPose();
    }

    private void renderLeftPage(GuiGraphics guiGraphics) {
        guiGraphics.blit(VAGRANT_NOTE_PAGE_LEFT, (this.width - 300) / 2, 13, 0, 0, 147, 231);
    }

    private void renderRightPage(GuiGraphics guiGraphics) {
        guiGraphics.blit(VAGRANT_NOTE_PAGE_RIGHT, this.width / 2 + 29, 13, 0, 0, 134, 238);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // 将一个段落构建为多行，并在段落后追加一个空行作为段落间距
    private void flushParagraph(List<String> paragraphBuffer) {
        if (paragraphBuffer.isEmpty()) return;
        String paragraph = String.join(" ", paragraphBuffer);
        MutableComponent component = Component.literal(paragraph);
        List<FormattedCharSequence> sequences = this.font.split(component, this.textAreaWidth);
        this.allLines.addAll(sequences);
        // 段落间距：追加一行空白
        this.allLines.addAll(this.font.split(Component.literal(" "), this.textAreaWidth));
        paragraphBuffer.clear();
    }

    protected void createPageControlButtons() {
        int i = (this.width - 192) / 2;
        this.forwardButton = this.addRenderableWidget(new PageButton(i + 116, 159, true, button -> this.pageForward(), this.playTurnSound));
        this.backButton = this.addRenderableWidget(new PageButton(i + 43, 159, false, button -> this.pageBack(), this.playTurnSound));
        this.updateButtonVisibility();
    }

    @Override
    protected void pageBack() {
        if (this.currentSheet > 0) {
            this.currentSheet--;
        }

        this.updateButtonVisibility();
    }

    @Override
    protected void pageForward() {
        if (this.currentSheet < this.getTotalSheets() - 1) {
            this.currentSheet++;
        }

        this.updateButtonVisibility();
    }

    private void updateButtonVisibility() {
        this.forwardButton.visible = this.currentSheet < this.getTotalSheets() - 1;
        this.backButton.visible = this.currentSheet > 0;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == InputConstants.KEY_RIGHT
                || keyCode == InputConstants.KEY_D
                || keyCode == InputConstants.KEY_PAGEDOWN) {
            if (this.currentSheet < this.getTotalSheets() - 1) {
                this.currentSheet++;
                return true;
            }
        } else if (keyCode == InputConstants.KEY_LEFT
                || keyCode == InputConstants.KEY_A
                || keyCode == InputConstants.KEY_PAGEUP) {
            if (this.currentSheet > 0) {
                this.currentSheet--;
                return true;
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            // 检测是否点击在左页目录区域
            int leftPageX = (this.width - 256) / 2;
            int tocTextX = leftPageX + 18;
            int tocWidth = 130; // 目录可点击宽度
            int tocStartY = 20;
            int tocLineHeight = this.font.lineHeight + 2;
            if (mouseX >= tocTextX && mouseX <= tocTextX + tocWidth && mouseY >= tocStartY
                    && mouseY <= tocStartY + this.tocEntries.size() * tocLineHeight) {
                int index = (int) ((mouseY - tocStartY) / tocLineHeight);
                if (index >= 0 && index < this.tocEntries.size()) {
                    ChapterTocEntry entry = this.tocEntries.get(index);
                    this.currentSheet = Math.min(entry.startLineIndex / Math.max(1, this.linesPerSide), this.getTotalSheets() - 1);
                    return true;
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private int getTotalSheets() {
        return Math.max(1, (this.allLines.size() + this.linesPerSide - 1) / this.linesPerSide);
    }

    private record ChapterTocEntry(int chapter, int startLineIndex, Component title) { }

}