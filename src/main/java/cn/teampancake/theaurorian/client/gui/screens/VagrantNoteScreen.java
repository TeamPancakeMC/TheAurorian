package cn.teampancake.theaurorian.client.gui.screens;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.components.ChapterContent;
import cn.teampancake.theaurorian.common.network.NoteTeleportC2SPacket;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class VagrantNoteScreen extends BookViewScreen {

    private static final ResourceLocation VAGRANT_NOTE_COVER = TheAurorian.prefix("textures/gui/vagrant_note/cover.png");

    private final List<ChapterContent> chapters;
    private final List<FormattedCharSequence> allLines = new ArrayList<>();
    private final List<ChapterTocEntry> tocEntries = new ArrayList<>();
    private final List<SimpleTextButton> textButtonList = new ArrayList<>();
    private final Map<Integer, Component> titleLineMap = new HashMap<>();
    private final ResourceKey<Level> dimension;
    private final boolean hasPassport;
    private final int textAreaWidth = 100;
    private int currentSheet = 0;
    private int linesPerSide = 22;

    public VagrantNoteScreen(List<ChapterContent> chapters, boolean hasPassport, ResourceKey<Level> dimension) {
        this.chapters = chapters;
        this.hasPassport = hasPassport;
        this.dimension = dimension;
    }

    @Override
    protected void init() {
        int lineHeight = this.font.lineHeight;
        int contentHeight = 243 - 40 - this.linesPerSide;
        this.linesPerSide = contentHeight / lineHeight;
        this.allLines.clear();
        this.titleLineMap.clear();
        this.tocEntries.clear();
        int coverLeft = (this.width - 391) / 2;
        int coverTop = 2;
        int contentMarginX = 32;
        int contentMarginY = 38;
        int leftTextX = coverLeft + contentMarginX + 30;
        int tocY = coverTop + contentMarginY;
        int tocLineHeight = this.font.lineHeight + 2;
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
                List<FormattedCharSequence> titleLines = this.font.split(chapterTitle, this.textAreaWidth);
                for (FormattedCharSequence titleLine : titleLines) {
                    int lineIndex = this.allLines.size();
                    this.allLines.add(titleLine);
                    this.titleLineMap.put(lineIndex, chapterTitle);
                }

                this.allLines.addAll(this.font.split(Component.literal(" "), this.textAreaWidth));
                try (BufferedReader bufferedReader = this.minecraft.getResourceManager().openAsReader(location)) {
                    List<String> paragraphBuffer = new ArrayList<>();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String trimmed = line.trim();
                        if (trimmed.hashCode() == 125780783) continue;
                        if (trimmed.isEmpty()) {
                            this.flushParagraph(paragraphBuffer);
                        } else {
                            paragraphBuffer.add(trimmed);
                        }
                    }

                    this.flushParagraph(paragraphBuffer);
                } catch (IOException ignored) {}
                Component chapterName = MutableComponent.create(chapterTitle.getContents())
                        .append(" ").append(this.chapters.get(idx).name().getString());
                this.tocEntries.add(new ChapterTocEntry(chapter, chapterStart, chapterName));
                int remainder = this.allLines.size() % Math.max(1, this.linesPerSide);
                if (remainder != 0 && idx < this.chapters.size() - 1) {
                    int blanks = this.linesPerSide - remainder;
                    for (int b = 0; b < blanks; b++) {
                        this.allLines.addAll(this.font.split(Component.literal(" "), this.textAreaWidth));
                    }
                }
            }

            for (ChapterTocEntry entry : this.tocEntries) {
                this.textButtonList.add(this.addRenderableWidget(new SimpleTextButton(leftTextX, tocY, this.font.width(entry.title), tocLineHeight,
                        entry.title, button -> this.jumpToMainTextPage(entry.startLineIndex), 0x303030, 0x0055AA)));
                tocY += tocLineHeight;
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
            this.createPageControlButtons();
            poseStack.popPose();
            poseStack.pushPose();
			int linesPerSheet = this.linesPerSide;
			int coverLeft = (this.width - 391) / 2;
			int coverTop = 2;
			int contentMarginX = 32;
			int contentMarginY = 38;
			if (this.currentSheet == 0) {
                this.textButtonList.forEach(button -> button.visible = true);
			} else {
                this.textButtonList.forEach(button -> button.visible = false);
				int startIndex = Math.min((this.currentSheet - 1) * linesPerSheet, this.allLines.size());
				int endIndex = Math.min(startIndex + linesPerSheet, this.allLines.size());
				int y = coverTop + contentMarginY - 4;
                int leftTextX = coverLeft + contentMarginX + 30;
				int bodyTextX = leftTextX + 15;
				for (int idx = startIndex; idx < endIndex; idx++) {
					FormattedCharSequence seq = this.allLines.get(idx);
					if (this.titleLineMap.containsKey(idx)) {
						PoseStack ps = guiGraphics.pose();
						ps.pushPose();
						float scale = 1.25F;
						ps.scale(scale, scale, 1.0F);
						int sx = Mth.floor(bodyTextX / scale);
						int sy = Mth.floor(y / scale);
						guiGraphics.drawString(this.font, seq, sx, sy, 0x222222, false);
						ps.popPose();
						y += Mth.ceil(this.font.lineHeight * 1.25F);
					} else {
						guiGraphics.drawString(this.font, seq, bodyTextX, y, 0, false);
						y += this.font.lineHeight;
					}
				}
			}

			int bodyPages = (this.allLines.size() + linesPerSheet - 1) / Math.max(1, linesPerSheet);
			String footer = String.format("%d / %d", this.currentSheet + 1, bodyPages + 1);
			int leftCenterX = this.backButton.getX() + this.backButton.getWidth() / 2;
			int rightCenterX = this.forwardButton.getX() + this.forwardButton.getWidth() / 2;
			int footerX = (leftCenterX + rightCenterX - this.font.width(footer)) / 2;
			int footerY = this.backButton.getY() + (this.backButton.getHeight() - this.font.lineHeight) / 2;
			guiGraphics.drawString(this.font, footer, footerX, footerY, 0x404040, false);
			if (this.hasPassport) {
				int rightX = coverLeft + 210 + 30;
				int rightY = coverTop + contentMarginY + 30;
                int lineH = this.font.lineHeight + 6;
				Component a = Component.translatable("text.vagrant_note.aurorian_passport");
				Component b = Component.translatable("text.vagrant_note.north_passport");
				Component c = Component.translatable("text.vagrant_note.south_passport");
                this.addRenderableWidget(new FancyTextButton(rightX, rightY, this.font.width(a), lineH, a, button -> PacketDistributor.sendToServer(
                        new NoteTeleportC2SPacket(this.dimension, TADimensions.AURORIAN_DIMENSION)), 0x2255AA, 0x66CCFF, "✦"));
                this.addRenderableWidget(new FancyTextButton(rightX, rightY + lineH, this.font.width(b), lineH, b, button -> PacketDistributor.sendToServer(
                        new NoteTeleportC2SPacket(this.dimension, TADimensions.NORTHERN_DIMENSION)), 0xDDDDDD, 0xFFFFFF, "❄"));
                this.addRenderableWidget(new FancyTextButton(rightX, rightY + lineH * 2, this.font.width(c), lineH, c, button -> PacketDistributor.sendToServer(
                        new NoteTeleportC2SPacket(this.dimension, TADimensions.SOUTHERN_DIMENSION)), 0xCC3300, 0xFF6600, "☼"));
			}

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

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void flushParagraph(List<String> paragraphBuffer) {
        if (paragraphBuffer.isEmpty()) return;
        String paragraph = String.join(" ", paragraphBuffer);
        MutableComponent component = Component.literal(paragraph);
        this.allLines.addAll(this.font.split(component, this.textAreaWidth));
        this.allLines.addAll(this.font.split(Component.literal(" "), this.textAreaWidth));
        paragraphBuffer.clear();
    }

    protected void createPageControlButtons() {
        int i = (this.width - 192) / 2;
        this.forwardButton = this.addRenderableWidget(new PageButton(i + 56 + 4, 159 + 59, true, button -> this.pageForward(), this.playTurnSound));
        this.backButton = this.addRenderableWidget(new PageButton(i + 43 - 70 - 20 + 3, 159 + 59, false, button -> this.pageBack(), this.playTurnSound));
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

    private void jumpToMainTextPage(int startLineIndex) {
        this.currentSheet = Math.min(1 + startLineIndex / Math.max(1, this.linesPerSide), this.getTotalSheets() - 1);
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

    private void drawPassportLine(GuiGraphics gg, Component text, int x, int y, boolean active, boolean hovered, float partialTick, int baseColor, int glowColor, String icon) {
        long ticks = this.minecraft != null && this.minecraft.level != null ? this.minecraft.level.getGameTime() : 0L;
        int color = active ? baseColor : 0x707070;
        float t = (ticks + partialTick) * 0.12F;
        if (active) {
            color = mixColor(baseColor, glowColor, 0.5F + 0.5F * Mth.sin(t));
        }

        PoseStack ps = gg.pose();
        ps.pushPose();
        if (hovered) {
            float s = 1.08F;
            ps.translate(x, y, 0);
            ps.scale(s, s, 1.0F);
            ps.translate(-x, -y, 0);
        }

        if (active) {
            ps.translate(0.0F, Mth.sin(t) * 0.4F, 0.0F);
        }

        gg.drawString(this.font, text, x, y, color, false);
        if (active) {
            String s = text.getString();
            int total = this.font.width(s);
            int window = Math.max(14, Math.min(28, total / 2));
            int center = x + (int)((Mth.sin(t * 1.2F) * 0.5F + 0.5F) * (total + window)) - window / 2;
            int runX = x;
            for (int i = 0; i < s.length(); i++) {
                String ch = String.valueOf(s.charAt(i));
                int w = this.font.width(ch);
                int charCenter = runX + w / 2;
                int dist = Math.abs(charCenter - center);
                if (dist < window / 2) {
                    float weight = 1.0F - (dist / (window / 2.0F));
                    int alpha = (int)(80 + weight * 140);
                    int overlay = withAlpha(mixColor(baseColor, glowColor, 0.6F + 0.4F * Mth.sin(t * 1.3F)), alpha);
                    gg.drawString(this.font, ch, runX, y, overlay, false);
                }

                runX += w;
            }
        }

        if (active) {
            int starColor = mixColor(baseColor, glowColor, 0.5F + 0.5F * Mth.cos(t * 1.6F));
            int sx = x - 10 + Mth.floor(Mth.sin(t * 2.2F) * 2);
            int sy = y + Mth.floor(Mth.cos(t * 1.9F) * 1);
            gg.drawString(this.font, icon, sx, sy, starColor, false);
        }

		ps.popPose();
	}

    private int mixColor(int c1, int c2, float t) {
        t = Math.max(0.0F, Math.min(1.0F, t));
        int r1 = (c1 >> 16) & 0xFF, g1 = (c1 >> 8) & 0xFF, b1 = c1 & 0xFF;
        int r2 = (c2 >> 16) & 0xFF, g2 = (c2 >> 8) & 0xFF, b2 = c2 & 0xFF;
        int r = (int)(r1 + (r2 - r1) * t);
        int g = (int)(g1 + (g2 - g1) * t);
        int b = (int)(b1 + (b2 - b1) * t);
        return (r << 16) | (g << 8) | b;
    }

    private int withAlpha(int rgb, int alpha) {
        alpha = Math.max(0, Math.min(255, alpha));
        return (alpha << 24) | (rgb & 0xFFFFFF);
    }

    private int getTotalSheets() {
        return 1 + (this.allLines.size() + this.linesPerSide - 1) / Math.max(1, this.linesPerSide);
    }

    private record ChapterTocEntry(int chapter, int startLineIndex, Component title) { }

    private static class SimpleTextButton extends Button {

        private final int baseColor;
        private final int hoverColor;

        public SimpleTextButton(int x, int y, int width, int height, Component message, OnPress onPress, int baseColor, int hoverColor) {
            super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
            this.baseColor = baseColor;
            this.hoverColor = hoverColor;
        }

        @Override
        protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            Font font = Minecraft.getInstance().font;
            int color = this.isHoveredOrFocused() ? this.hoverColor : baseColor;
            guiGraphics.drawString(font, this.getMessage(), this.getX(), this.getY(), color, false);
        }

    }

    @OnlyIn(Dist.CLIENT)
    private class FancyTextButton extends Button {

        private final int baseColor;
        private final int glowColor;
        private final String icon;

        public FancyTextButton(int x, int y, int width, int height, Component message, OnPress onPress, int baseColor, int glowColor, String icon) {
            super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
            this.baseColor = baseColor;
            this.glowColor = glowColor;
            this.icon = icon;
        }

        @Override
        protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            drawPassportLine(guiGraphics, this.getMessage(), this.getX(), this.getY(), this.active,
                    this.isHoveredOrFocused(), partialTick, this.baseColor, this.glowColor, this.icon);
        }

    }

}