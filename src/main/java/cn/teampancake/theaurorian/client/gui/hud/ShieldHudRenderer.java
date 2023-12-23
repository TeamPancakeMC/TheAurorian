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
    public static int[] size = {4,8,10,12,12,14,14,14,16,16,16,16,16,16,16,16,16,16,16};
    public static int textureWidth = 24;
    public static int textureHeight = 28;
    public static IGuiOverlay SHIELD_OVERLAY = (forgeGui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {

        render(forgeGui, guiGraphics, screenWidth, screenHeight,minecraft.player);
    };
    public static void render(ForgeGui forgeGui, GuiGraphics guiGraphics, int width, int height, Player player) {
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, SHIELD);
        guiGraphics.blit(SHIELD, width - textureWidth, height - textureHeight, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);


        int xOffset = 1;
        int yOffset = 5;
        int yStart = height - yOffset;

        int length = size.length;

        ShieldCap.getCapability(player).ifPresent(shieldCap -> {

        });



        ShieldCap shieldCap = ShieldCap.getCapability(player).orElse(null);

        if (shieldCap == null) {
            return;
        }
        Map<ResourceLocation, IShield> shieldMap = shieldCap.getShieldMap();
        float maxShieldValue = shieldCap.getMaxShieldValue();

        int count = 0;
        int sizeCount = 0;
        for (IShield value : shieldMap.values()) {
            int color = value.getColor();
            float shield = value.getShield();
            float v = shield / maxShieldValue;
            int v1 = (int) Math.floor(length * v);
            count += v1;

            if(count > length){
                v1 = length - (count - v1);
            }

            for (int i = 0; i < v1; i++) {
                int i1 = size[sizeCount];
                int xStart = width - (textureWidth - i1) / 2 - i1 - xOffset;
                guiGraphics.hLine(xStart, xStart + i1, yStart, color);
                yStart--;
                sizeCount++;
            }
        }

    }

    public static void registerThirstOverlay(RegisterGuiOverlaysEvent event)
    {
        event.registerAbove(VanillaGuiOverlay.FOOD_LEVEL.id(), "shield", SHIELD_OVERLAY);
    }
}