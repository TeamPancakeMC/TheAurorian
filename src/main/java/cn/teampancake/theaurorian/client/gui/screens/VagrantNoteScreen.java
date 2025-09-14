package cn.teampancake.theaurorian.client.gui.screens;

import cn.teampancake.theaurorian.TheAurorian;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class VagrantNoteScreen extends BookViewScreen {

    private static final ResourceLocation VAGRANT_NOTE_COVER = TheAurorian.prefix("textures/gui/vagrant_note_cover.png");
    private static final ResourceLocation VAGRANT_NOTE_PAGE_LEFT = TheAurorian.prefix("textures/gui/vagrant_note_page_left.png");
    private static final ResourceLocation VAGRANT_NOTE_PAGE_RIGHT = TheAurorian.prefix("textures/gui/vagrant_note_page_right.png");

    private final List<Integer> chapters;

    public VagrantNoteScreen(List<Integer> chapters) {
        this.chapters = chapters;
    }

    @Override
    protected void init() {

    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        for (Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        if (!this.chapters.isEmpty() && this.minecraft != null) {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.scale(1.0F, 1.17F, 1.0F);
            poseStack.translate(-0.8F, 0.0F, 0.0F);
            this.renderLeftPage(guiGraphics);
            this.renderRightPage(guiGraphics);
            poseStack.popPose();
            poseStack.pushPose();
            int i = (this.width - 163) / 2;
            for (int chapter : this.chapters) {
                String key = String.format("text.vagrant_note.chapter.%02d", chapter);
                String filename = Component.translatable(key).getString();
                ResourceLocation location = TheAurorian.prefix(String.format("texts/%s.txt", filename));
                try (BufferedReader bufferedReader = this.minecraft.getResourceManager().openAsReader(location)) {
                    List<String> paragraphs = bufferedReader.lines().map(String::trim)
                            .filter(string -> string.hashCode() != 125780783).toList();
                    if (paragraphs.isEmpty()) break;
                    for (String paragraph : paragraphs) {
                        MutableComponent component = Component.literal(paragraph);
                        List<FormattedCharSequence> sequences = this.font.split(component, 120);
                        for (int l = 0; l < sequences.size(); l++) {
                            int y = 12 + l * sequences.size() * 9;
                            FormattedCharSequence sequence = sequences.get(l);
                            guiGraphics.drawString(this.font, sequence, i - 36, y, 0, false);
                        }
                    }
                } catch (IOException ignored) {}
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
        guiGraphics.blit(VAGRANT_NOTE_COVER, (this.width - 256) / 2, 2, 0, 0, 256, 257, 256, 257);
        poseStack.popPose();
    }

    private void renderLeftPage(GuiGraphics guiGraphics) {
        int x = (this.width - 256) / 2;
        guiGraphics.blit(VAGRANT_NOTE_PAGE_LEFT, x, 7, 0, 0, 163, 243);
    }

    private void renderRightPage(GuiGraphics guiGraphics) {
        int x = this.width / 2;
        guiGraphics.blit(VAGRANT_NOTE_PAGE_RIGHT, x, 7, 0, 0, 163, 243);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}