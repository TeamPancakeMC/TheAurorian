package cn.teampancake.theaurorian.client.gui.screens;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BooleanSupplier;

public class TAWaitingScreen extends ReceivingLevelScreen {

    private static final ResourceLocation BACKGROUND_LOCATION = TheAurorian.prefix("textures/block/aurorian_stone_bricks.png");

    public TAWaitingScreen(BooleanSupplier levelReceived, Reason reason) {
        super(levelReceived, reason);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.isLostInForest()) return;
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.isLostInForest()) return;
        renderMenuBackgroundTexture(guiGraphics, BACKGROUND_LOCATION, 0, 0, 0.0F, 0.0F, this.width, this.height);
    }

    private boolean isLostInForest() {
        if (this.minecraft != null) {
            LocalPlayer player = this.minecraft.player;
            return player != null && player.getData(TAAttachmentTypes.LOST_IN_FOREST);
        }

        return false;
    }

}