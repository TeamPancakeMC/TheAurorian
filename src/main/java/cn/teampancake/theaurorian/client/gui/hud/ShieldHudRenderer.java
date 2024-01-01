package cn.teampancake.theaurorian.client.gui.hud;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.api.IShield;
import cn.teampancake.theaurorian.common.capability.ShieldCap;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;

import java.util.Map;

public class ShieldHudRenderer {

    static Minecraft minecraft = Minecraft.getInstance();
    public static ResourceLocation SHIELD = AurorianMod.prefix("textures/gui/shield.png");
    public static final int TEXTURE_WIDTH = 108;
    public static final int TEXTURE_HEIGHT = 34;
    public static IGuiOverlay SHIELD_OVERLAY = (forgeGui, guiGraphics, partialTicks, screenWidth, screenHeight) ->
            render(forgeGui, guiGraphics, screenWidth, screenHeight, minecraft.player);

    @SuppressWarnings("unused")
    public static void render(ForgeGui forgeGui, GuiGraphics guiGraphics, int width, int height, Player player) {
        RenderSystem.enableBlend();
        guiGraphics.blit(SHIELD, 3, height - TEXTURE_HEIGHT, 0, 0, 42, 34, TEXTURE_WIDTH, TEXTURE_HEIGHT); //Render frame
//        guiGraphics.blit(SHIELD, 13, height - TEXTURE_HEIGHT + 8, 42, 0, 22, 22, TEXTURE_WIDTH, TEXTURE_HEIGHT); //Render gray bar
//        guiGraphics.blit(SHIELD, 10, height - TEXTURE_HEIGHT + 8, 86, 0, 22, 22, TEXTURE_WIDTH, TEXTURE_HEIGHT); //Render purple bar

       ShieldCap.getCapability(player).ifPresent(shieldCap -> {
           Map<ResourceLocation, IShield> shieldMap = shieldCap.getShieldMap();
           if (shieldMap.isEmpty()) return;
           float maxShieldValue = shieldCap.getMaxShieldValue();
           for (IShield value : shieldMap.values()) {
               float shield = value.getShield();
               float v = shield / maxShieldValue;
               int v1 = (int) Math.floor(22 * v);
               int y = height - TEXTURE_HEIGHT + 11 + 22 - v1;
               guiGraphics.blit(SHIELD, 13, y, 64, 22 - v1, 22, v1, TEXTURE_WIDTH, TEXTURE_HEIGHT); //Render dynamic blue bar
           }
       });
        RenderSystem.disableBlend();
    }

    public static void registerShieldOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.FOOD_LEVEL.id(), "shield", SHIELD_OVERLAY);
    }

}