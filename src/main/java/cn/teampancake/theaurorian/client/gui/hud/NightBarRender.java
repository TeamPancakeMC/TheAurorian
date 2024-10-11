package cn.teampancake.theaurorian.client.gui.hud;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class NightBarRender {

    protected static final ResourceLocation NightRender = TheAurorian.prefix("textures/misc/bless_render.png");
    public static int nightType;
    public static final int TEXTURE_WIDTH = 135;
    public static final int TEXTURE_HEIGHT = 128;
    public static final int BAR_WIDTH = 45;
    public static final int BAR_HEIGHT = 64;

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        RenderSystem.enableBlend();
        Minecraft minecraft = Minecraft.getInstance();
        int offsetX, offsetY;
        LocalPlayer player = minecraft.player;
        if (player != null && !minecraft.options.hideGui) {
            long dayTime = (player.level().getDayTime() + 6000L) % 24000L;
            if (player.level().dimension() == TADimensions.AURORIAN_DIMENSION) {
                if (dayTime > 6000 && dayTime <= 18000) {
                    guiGraphics.blit(NightRender, 0, 0, 90, 64, BAR_WIDTH, BAR_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT); //Render frame
                } else {
                    if (nightType <= 2) {
                        offsetX = nightType * 45;
                        offsetY = 0;
                    } else {
                        offsetX = (nightType - 3) * 45;
                        offsetY = 64;
                    }

                    guiGraphics.blit(NightRender, 0, 0, offsetX, offsetY, BAR_WIDTH, BAR_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
                }
            }
        }

        RenderSystem.disableBlend();
    }

    public static void registerNightOverlay(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.ARMOR_LEVEL, TheAurorian.prefix("night_bar"), NightBarRender::render);
    }

}