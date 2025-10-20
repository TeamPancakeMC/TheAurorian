package cn.teampancake.theaurorian.client.gui.hud;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import cn.teampancake.theaurorian.common.shields.BaseShield;
import cn.teampancake.theaurorian.common.shields.ShieldStack;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class ShieldHudRenderer {

    private static final ResourceLocation FRAME = TheAurorian.prefix("textures/misc/shield/frame.png");
    private static final int SIZE = 22, WIDTH = 42, HEIGHT = 34;

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        int height = guiGraphics.guiHeight();
        if (player != null && !minecraft.options.hideGui && TACommonUtils.isAurorianDimension(player.level())) {
            guiGraphics.blit(FRAME, 3, height - HEIGHT, 0, 0, WIDTH, HEIGHT, WIDTH, HEIGHT);
            ShieldStack shieldStack = player.getData(TAAttachmentTypes.CURRENT_SHIELD);
            if (shieldStack != ShieldStack.EMPTY) {
                DataComponentType<Float> maxShieldComponent = TADataComponents.MAX_SHIELD.get();
                DataComponentType<Float> shieldComponent = TADataComponents.SHIELD.get();
                Float maxShield = shieldStack.get(maxShieldComponent);
                Float shield = shieldStack.get(shieldComponent);
                if (maxShield != null && shield != null) {
                    int v = Mth.floor(SIZE * (shield / maxShield));
                    int y = height - HEIGHT + 11 + SIZE - v;
                    ResourceKey<BaseShield> key = shieldStack.getShield().getKey();
                    if (player.hasEffect(TAMobEffects.BROKEN)) {
                        ResourceLocation broken = TheAurorian.prefix("textures/misc/shield/broken.png");
                        guiGraphics.blit(broken, 13, height - HEIGHT + 11, 0, 0, SIZE, SIZE, SIZE, SIZE);
                    } else if (key != null) {
                        ResourceLocation location = key.location();
                        String path = String.format("textures/misc/shield/%s.png", location.getPath());
                        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(location.getNamespace(), path);
                        guiGraphics.blit(texture, 11, y, 64, SIZE - v, SIZE, v, SIZE, SIZE);
                    }
                }
            }
        }
    }

    public static void registerShieldOverlay(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, TheAurorian.prefix("shield"), ShieldHudRenderer::render);
    }

}