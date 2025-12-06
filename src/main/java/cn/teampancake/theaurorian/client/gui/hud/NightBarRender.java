package cn.teampancake.theaurorian.client.gui.hud;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameRules;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class NightBarRender {

    private static final ResourceLocation EMPTY = TheAurorian.prefix("empty");
    public static ResourceLocation nightType = EMPTY;
    public static final int BAR_WIDTH = 45;
    public static final int BAR_HEIGHT = 64;

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        RenderSystem.enableBlend();
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null && !minecraft.options.hideGui) {
            long dayTime = (player.level().getDayTime() + 6000L) % 24000L;
            if (TACommonUtils.isAurorianDimension(player.level())) {
                GameRules gameRules = player.level().getGameRules();
                if (dayTime == 6000 && gameRules.getBoolean(GameRules.RULE_DAYLIGHT) && nightType != EMPTY) {
                    String key = "commands.theaurorian.night_phase.changed";
                    String name = Component.translatable("night_phase.theaurorian." + nightType.getPath()).getString();
                    player.sendSystemMessage(Component.translatable(key, name));
                }

                if (dayTime > 6000 && dayTime <= 18000) {
                    String path = String.format("textures/misc/bless/%s.png", nightType.getPath());
                    ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(nightType.getNamespace(), path);
                    guiGraphics.blit(texture, 0, 0, 0, 0, BAR_WIDTH, BAR_HEIGHT, BAR_WIDTH, BAR_HEIGHT);
                } else {
                    ResourceLocation empty = TheAurorian.prefix("textures/misc/bless/empty.png");
                    guiGraphics.blit(empty, 0, 0, 90, 64, BAR_WIDTH, BAR_HEIGHT, BAR_WIDTH, BAR_HEIGHT);
                }
            }
        }

        RenderSystem.disableBlend();
    }

    public static void registerNightOverlay(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.ARMOR_LEVEL, TheAurorian.prefix("night_bar"), NightBarRender::render);
    }

}