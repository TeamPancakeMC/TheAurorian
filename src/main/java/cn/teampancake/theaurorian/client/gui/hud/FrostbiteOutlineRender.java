package cn.teampancake.theaurorian.client.gui.hud;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class FrostbiteOutlineRender {

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getProfiler().push("snowfield_frostbite");
        RenderSystem.enableBlend();
        if (minecraft.getCameraEntity() instanceof Player player) {
            int i = player.getData(TAAttachmentTypes.TICKS_FROSTBITE);
            int j = player.getTicksRequiredToFreeze();
            if (player.getTicksFrozen() <= 0) {
                float percentFrozen = (float) Math.min(i, j) / (float) j;
                minecraft.gui.renderTextureOverlay(guiGraphics, Gui.POWDER_SNOW_OUTLINE_LOCATION, percentFrozen);
            }
        }

        RenderSystem.disableBlend();
        minecraft.getProfiler().pop();
    }

    public static void registerFrostbiteOverlay(RegisterGuiLayersEvent event) {
        ResourceLocation id = TheAurorian.prefix("snowfield_frostbite");
        event.registerBelow(VanillaGuiLayers.HOTBAR, id, FrostbiteOutlineRender::render);
    }

}