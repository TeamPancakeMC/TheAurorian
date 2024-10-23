package cn.teampancake.theaurorian.client.gui;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BooleanSupplier;

public class TAWaitingScreen extends ReceivingLevelScreen {

    private static final ResourceLocation BACKGROUND_LOCATION = TheAurorian.prefix("textures/block/aurorian_stone_bricks.png");

    public TAWaitingScreen(BooleanSupplier levelReceived, Reason reason) {
        super(levelReceived, reason);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderMenuBackgroundTexture(guiGraphics, BACKGROUND_LOCATION, 0, 0, 0.0F, 0.0F, this.width, this.height);
    }

}