package cn.teampancake.theaurorian.client.gui.hud;

import cn.teampancake.theaurorian.common.registry.TACapability;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;

public class FrostbiteOutlineRender {

    static Minecraft minecraft = Minecraft.getInstance();

    public static void render(ForgeGui forgeGui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        minecraft.getProfiler().push("snowfield_frostbite");
        RenderSystem.enableBlend();
        if (forgeGui.getMinecraft().getCameraEntity() instanceof Player player) {
            player.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT -> {
                int i = miscNBT.getTicksFrostbite();
                int j = player.getTicksRequiredToFreeze();
                if (player.getTicksFrozen() <= 0) {
                    float percentFrozen = (float) Math.min(i, j) / (float) j;
                    forgeGui.renderTextureOverlay(guiGraphics, Gui.POWDER_SNOW_OUTLINE_LOCATION, percentFrozen);
                }
            });
        }

        RenderSystem.disableBlend();
        minecraft.getProfiler().pop();
    }

    public static void registerFrostbiteOverlay(RegisterGuiOverlaysEvent event) {
        event.registerBelow(VanillaGuiOverlay.HOTBAR.id(), "snowfield_frostbite", FrostbiteOutlineRender::render);
    }

}