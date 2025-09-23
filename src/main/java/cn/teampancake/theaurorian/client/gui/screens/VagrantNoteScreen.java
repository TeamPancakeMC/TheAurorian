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
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.network.NoteTeleportC2SPacket;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.Unit;
import net.neoforged.neoforge.network.PacketDistributor;
import net.minecraft.world.level.Level;

@OnlyIn(Dist.CLIENT)
public class VagrantNoteScreen extends BookViewScreen {

    private static final ResourceLocation VAGRANT_NOTE_COVER = TheAurorian.prefix("textures/gui/vagrant_note/cover.png");

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
            this.createPageControlButtons();
            poseStack.popPose();
            poseStack.pushPose();
			// 渲染目录与正文（正文在左侧，第一页为目录，第二页开始正文）
			int linesPerSheet = this.linesPerSide;
			// 以封面中心作为参考，统一布局
			int coverLeft = (this.width - 391) / 2;
			int coverTop = 2;
			int contentMarginX = 32;
			int contentMarginY = 38;
			int leftTextX = coverLeft + contentMarginX + 30;
			int tocY = coverTop + contentMarginY;
			int tocLineHeight = this.font.lineHeight + 2;
			// 第1页（currentSheet == 0）仅渲染目录
			if (this.currentSheet == 0) {
				int tocWidth = 130;
				for (ChapterTocEntry entry : this.tocEntries) {
					int color = 0x303030;
					int entrySheet = Math.min(entry.startLineIndex / Math.max(1, linesPerSheet),
							Math.max(0, (this.allLines.size() - 1) / Math.max(1, linesPerSheet)));
					if (entrySheet + 1 == this.currentSheet) { // 不会命中，仅保留逻辑占位
						color = 0x0055AA;
					}
					boolean hovered = mouseX >= leftTextX && mouseX <= leftTextX + tocWidth && mouseY >= tocY && mouseY < tocY + tocLineHeight;
					if (hovered) {
						PoseStack ps = guiGraphics.pose();
						ps.pushPose();
						float s = 1.06F;
						ps.translate(leftTextX, tocY, 0);
						ps.scale(s, s, 1.0F);
						ps.translate(-leftTextX, -tocY, 0);
													guiGraphics.drawString(this.font, entry.title, leftTextX, tocY, color, false);
						ps.popPose();
					} else {
						guiGraphics.drawString(this.font, entry.title, leftTextX, tocY, color, false);
					}
					tocY += tocLineHeight;
				}
			} else {
				// 正文从第二页开始：页索引减一后再分页
				int startIndex = Math.min((this.currentSheet - 1) * linesPerSheet, this.allLines.size());
				int endIndex = Math.min(startIndex + linesPerSheet, this.allLines.size());
				int y = coverTop + contentMarginY - 4;
				int bodyTextX = leftTextX + 15;
				for (int idx = startIndex; idx < endIndex; idx++) {
					FormattedCharSequence seq = this.allLines.get(idx);
					if (this.titleLineMap.containsKey(idx)) {
						PoseStack ps = guiGraphics.pose();
						ps.pushPose();
						float scale = 1.25F;
						ps.scale(scale, scale, 1.0F);
						int sx = (int) (bodyTextX / scale);
						int sy = (int) (y / scale);
						guiGraphics.drawString(this.font, seq, sx, sy, 0x222222, false);
						ps.popPose();
						y += (int) Math.ceil(this.font.lineHeight * 1.25F);
					} else {
						guiGraphics.drawString(this.font, seq, bodyTextX, y, 0, false);
						y += this.font.lineHeight;
					}
				}
			}

			// 页码指示：位于左右翻页按钮的中心对称位置，Y 与按钮对齐
			int bodyPages = (this.allLines.size() + linesPerSheet - 1) / Math.max(1, linesPerSheet);
			int totalSheets = 1 + bodyPages; // +1 为目录页
			String footer = String.format("%d / %d", this.currentSheet + 1, totalSheets);
			int footerWidth = this.font.width(footer);
			int leftCenterX = this.backButton != null ? this.backButton.getX() + this.backButton.getWidth() / 2 : coverLeft + 80;
			int rightCenterX = this.forwardButton != null ? this.forwardButton.getX() + this.forwardButton.getWidth() / 2 : coverLeft + 391 - 80;
			int midX = (leftCenterX + rightCenterX) / 2;
			int footerX = midX - footerWidth / 2;
			int footerY = this.backButton != null ? this.backButton.getY() + (this.backButton.getHeight() - this.font.lineHeight) / 2 : coverTop + 280;
			guiGraphics.drawString(this.font, footer, footerX, footerY, 0x404040, false);

			// 右侧渲染传送目录（需获得通行证组件）
			ItemStack held = this.minecraft.player != null ? this.minecraft.player.getMainHandItem() : ItemStack.EMPTY;
			boolean hasPassport = held.getOrDefault(TADataComponents.NOTE_PASSPORT, null) == Unit.INSTANCE;
			if (hasPassport) {
				int rightX = coverLeft + 210 + 30; // 与正文同列
				int rightY = coverTop + contentMarginY + 30;
				Component a = Component.literal("极光幽境通行证");
				Component b = Component.literal("北方诸国通行证");
				Component c = Component.literal("南方诸国通行证");
				int lineH = this.font.lineHeight + 6;
				// 当前维度
				Level level = this.minecraft.level;
				boolean inAurorian = level != null && level.dimension().equals(TADimensions.AURORIAN_DIMENSION);
				boolean inNorth = level != null && level.dimension().equals(TADimensions.NORTHERN_DIMENSION);
				boolean inSouth = level != null && level.dimension().equals(TADimensions.SOUTHERN_DIMENSION);

				int bColor = 0x2255AA, gColor = 0x66CCFF;
				if (inNorth) { bColor = 0xDDDDDD; gColor = 0xFFFFFF; }
				else if (inSouth) { bColor = 0xCC3300; gColor = 0xFF6600; }
				boolean hoveredA = this.isMouseOverLine(rightX, rightY, 140, lineH, mouseX, mouseY);
				boolean hoveredB = this.isMouseOverLine(rightX, rightY + lineH, 140, lineH, mouseX, mouseY);
				boolean hoveredC = this.isMouseOverLine(rightX, rightY + lineH * 2, 140, lineH, mouseX, mouseY);
				int gray = 0x707070;
				// 极光：蓝色系（✦）
				this.drawPassportLine(guiGraphics, a, rightX, rightY, inAurorian, hoveredA, partialTick,
						0x2255AA, 0x66CCFF, gray, "✦");
				// 北方：白色系（❄）
				this.drawPassportLine(guiGraphics, b, rightX, rightY + lineH, inNorth, hoveredB, partialTick,
						0xDDDDDD, 0xFFFFFF, gray, "❄");
				// 南方：岩浆红（☼）
				this.drawPassportLine(guiGraphics, c, rightX, rightY + lineH * 2, inSouth, hoveredC, partialTick,
						0xCC3300, 0xFF6600, gray, "☼");
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
        if (button == 0 && this.currentSheet == 0) { // 仅在目录页可点击目录
            int coverLeft = (this.width - 391) / 2;
            int coverTop = 2;
            int contentMarginX = 32;
            int contentMarginY = 38;
            int tocTextX = coverLeft + contentMarginX + 30;
            int tocWidth = 130;
            int tocStartY = coverTop + contentMarginY;
            int tocLineHeight = this.font.lineHeight + 2;
            if (mouseX >= tocTextX && mouseX <= tocTextX + tocWidth && mouseY >= tocStartY
                    && mouseY <= tocStartY + this.tocEntries.size() * tocLineHeight) {
                int index = (int) ((mouseY - tocStartY) / tocLineHeight);
                if (index >= 0 && index < this.tocEntries.size()) {
                    ChapterTocEntry entry = this.tocEntries.get(index);
                    // 目录跳转到正文对应页：+1（第2页开始为正文）
                    this.currentSheet = Math.min(1 + entry.startLineIndex / Math.max(1, this.linesPerSide), this.getTotalSheets() - 1);
                    return true;
                }
            }

            // 右侧通行证点击区域
            ItemStack held = this.minecraft.player != null ? this.minecraft.player.getMainHandItem() : ItemStack.EMPTY;
            boolean hasPassport = held.getOrDefault(TADataComponents.NOTE_PASSPORT, null) == Unit.INSTANCE;
            if (hasPassport) {
                int rightX = coverLeft + 210 + 30;
                int rightY = coverTop + contentMarginY + 30;
                int width = 140;
                int h = this.font.lineHeight + 6;
                if (mouseX >= rightX && mouseX <= rightX + width) {
                    if (mouseY >= rightY && mouseY < rightY + h) {
                        PacketDistributor.sendToServer(new NoteTeleportC2SPacket(TADimensions.AURORIAN_DIMENSION));
                        return true;
                    } else if (mouseY >= rightY + h && mouseY < rightY + 2 * h) {
                        PacketDistributor.sendToServer(new NoteTeleportC2SPacket(TADimensions.NORTHERN_DIMENSION));
                        return true;
                    } else if (mouseY >= rightY + 2 * h && mouseY < rightY + 3 * h) {
                        PacketDistributor.sendToServer(new NoteTeleportC2SPacket(TADimensions.SOUTHERN_DIMENSION));
                        return true;
                    }
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean isMouseOverLine(int x, int y, int w, int h, double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY < y + h;
    }

    private void drawPassportLine(GuiGraphics gg, Component text, int x, int y, boolean active, boolean hovered, float partialTick,
								  int baseColor, int glowColor, int inactiveColor, String icon) {
		int color = active ? baseColor : inactiveColor;
		long ticks = this.minecraft != null && this.minecraft.level != null ? this.minecraft.level.getGameTime() : 0L;
		float t = (ticks + partialTick) * 0.12F;
		if (active) {
			color = mixColor(baseColor, glowColor, (float)(0.5F + 0.5F * Math.sin(t)));
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
			ps.translate(0.0F, (float)Math.sin(t) * 0.4F, 0.0F);
		}

		// 基础文字
		gg.drawString(this.font, text, x, y, color, false);

		// 流光效果（按字符覆盖高亮，无下划线）
		if (active) {
			String s = text.getString();
			int total = this.font.width(s);
			int window = Math.max(14, Math.min(28, total / 2));
			int center = x + (int)((Math.sin(t * 1.2F) * 0.5F + 0.5F) * (total + window)) - window / 2;
			int runX = x;
			for (int i = 0; i < s.length(); i++) {
				String ch = String.valueOf(s.charAt(i));
				int w = this.font.width(ch);
				int charCenter = runX + w / 2;
				int dist = Math.abs(charCenter - center);
				if (dist < window / 2) {
					float weight = 1.0F - (dist / (window / 2.0F));
					int alpha = (int)(80 + weight * 140); // 0x50-0xDC
					int overlay = withAlpha(mixColor(baseColor, glowColor, (float)(0.6F + 0.4F * Math.sin(t * 1.3F))), alpha);
					gg.drawString(this.font, ch, runX, y, overlay, false);
				}
				runX += w;
			}
		}

		// 符号与小光点（仅激活）
		if (active) {
			int starColor = mixColor(baseColor, glowColor, (float)(0.5F + 0.5F * Math.cos(t * 1.6F)));
			int sx = x - 10 + (int)(Math.sin(t * 2.2F) * 2);
			int sy = y + (int)(Math.cos(t * 1.9F) * 1);
			gg.drawString(this.font, icon, sx, sy, starColor, false);
			int textWidth = this.font.width(text);
			/* removed dot glint as requested */
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
        int bodyPages = (this.allLines.size() + this.linesPerSide - 1) / Math.max(1, this.linesPerSide);
        return 1 + bodyPages; // 1 页目录 + 正文页数
    }

    private record ChapterTocEntry(int chapter, int startLineIndex, Component title) { }

}