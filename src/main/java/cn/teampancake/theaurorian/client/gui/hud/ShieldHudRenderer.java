package cn.teampancake.theaurorian.client.gui.hud;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.config.TAClientConfig;
import cn.teampancake.theaurorian.common.config.TAClientConfig.Style;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import cn.teampancake.theaurorian.common.shields.BaseShield;
import cn.teampancake.theaurorian.common.shields.ShieldStack;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import java.util.Map;

public class ShieldHudRenderer {

    private static final Map<Style, Pair<Integer, Integer>> OUT_MAP =
            Map.of(Style.HORIZONTAL, Pair.of(78, 12), Style.VERTICAL, Pair.of(18, 74));
    private static final Map<Style, Pair<Integer, Integer>> IN_MAP =
            Map.of(Style.HORIZONTAL, Pair.of(73, 8), Style.VERTICAL, Pair.of(8, 56));
    private static final String PREFIX = "textures/misc/shield";

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null && !minecraft.options.hideGui && TACommonUtils.isAurorianDimension(player.level())) {
            ShieldStack shieldStack = player.getData(TAAttachmentTypes.CURRENT_SHIELD);
            Style style = TAClientConfig.SHIELD_STYLE.get();
            String styleName = style.name().toLowerCase();
            int guiHeight = guiGraphics.guiHeight();
            int outWidth = OUT_MAP.get(style).getFirst();
            int outHeight = OUT_MAP.get(style).getSecond();
            int frameY = guiHeight - outHeight - 5;
            if (shieldStack == ShieldStack.EMPTY) {
                ResourceLocation texture = TheAurorian.prefix(String.format("%s/common_%s_frame.png", PREFIX, styleName));
                guiGraphics.blit(texture, 5, frameY, 0, 0, outWidth, outHeight, outWidth, outHeight);
                return;
            }

            float maxShield = shieldStack.getOrDefault(TADataComponents.MAX_SHIELD, 0.0F);
            float shield = shieldStack.getOrDefault(TADataComponents.SHIELD, 0.0F);
            Holder<BaseShield> holder = shieldStack.getShield();
            if (holder.getKey() == null) return;
            ResourceLocation location = holder.getKey().location();
            String namespace = location.getNamespace();
            String outPath = String.format("%s/%s_%s_frame.png", PREFIX, location.getPath(), styleName);
            ResourceLocation out = ResourceLocation.parse(String.format("%s:%s", namespace, outPath));
            guiGraphics.blit(out, 5, frameY, 0, 0, outWidth, outHeight, outWidth, outHeight);
            String format = player.hasEffect(TAMobEffects.BROKEN) ? "%s/%s_broken_%s" : "%s/%s_%s";
            String inPath = String.format(format + ".png", PREFIX, location.getPath(), styleName);
            ResourceLocation in = ResourceLocation.parse(String.format("%s:%s", namespace, inPath));
            int inWidth = IN_MAP.get(style).getFirst(), inHeight = IN_MAP.get(style).getSecond();
            if (style == Style.HORIZONTAL) {
                int width = Mth.floor(inWidth * (shield / maxShield));
                guiGraphics.blit(in, 8, frameY, 0, 0, width, inHeight, inWidth, inHeight);
            } else {
                int height = Mth.floor(inHeight * (shield / maxShield));
                int y = guiHeight - height - 16;
                guiGraphics.blit(in, 11, y, 0, inHeight - height, inWidth, height, inWidth, inHeight);
            }
        }
    }

    public static void registerShieldOverlay(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, TheAurorian.prefix("shield"), ShieldHudRenderer::render);
    }

}