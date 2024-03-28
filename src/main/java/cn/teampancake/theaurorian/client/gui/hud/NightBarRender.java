package cn.teampancake.theaurorian.client.gui.hud;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;

public class NightBarRender {
    static Minecraft minecraft = Minecraft.getInstance();
    protected static final ResourceLocation NightRender = AurorianMod.prefix("textures/misc/bless_render.png");
    public static int nightType;
    public static final int TEXTURE_WIDTH = 135;
    public static final int TEXTURE_HEIGHT = 128;
    public static final int BAR_WIDTH = 45;
    public static final int BAR_HEIGHT = 64;

    public static IGuiOverlay NIGHT_BAR_OVERLAY = (forgeGui, guiGraphics, partialTicks, screenWidth, screenHeight) ->
            render(forgeGui, guiGraphics, screenWidth, screenHeight, minecraft.player);

    @SuppressWarnings("unused")
    public static void render(ForgeGui forgeGui, GuiGraphics guiGraphics, int width, int height, Player player) {
        RenderSystem.enableBlend();
        int offsetX, offsetY;
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

        RenderSystem.disableBlend();
    }

    public static void registerNightOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.ARMOR_LEVEL.id(), "night_bar", NIGHT_BAR_OVERLAY);
    }

}