package cn.teampancake.theaurorian.client.gui.hud;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.renderer.Texture3DRenderer;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

/** @noinspection deprecation*/
public class ActivationAnimationRender {

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.enableBlend();
        if (minecraft.getCameraEntity() instanceof Player player && !minecraft.options.hideGui) {
            float partialTick = deltaTracker.getGameTimeDeltaPartialTick(false);
            int a = player.getData(TAAttachmentTypes.ACTIVATION_TICKS);
            int b = player.getData(TAAttachmentTypes.MAX_ACTIVATION_TICKS);
            ResourceLocation texture = player.getExistingDataOrNull(TAAttachmentTypes.ANIMATION_TEXTURE);
            if (texture != null && a > -1 && b > -1 && b > a) {
                GameRenderer gameRenderer = minecraft.gameRenderer;
                ResourceManager resourceManager = minecraft.getResourceManager();
                NativeImage image = SimpleTexture.TextureImage.load(resourceManager, texture).image;
                if (image != null) {
                    int i = b - a;
                    float f = ((float) i + partialTick) / (float) b;
                    float f1 = f * f;
                    float f2 = f * f1;
                    float f3 = 10.25F * f2 * f1 - 24.95F * f1 * f1 + 25.5F * f2 - 13.8F * f1 + 4.0F * f;
                    float f4 = f3 * (float) Math.PI;
                    float f5 = gameRenderer.itemActivationOffX * (float) (guiGraphics.guiWidth() / 4);
                    float f6 = gameRenderer.itemActivationOffY * (float) (guiGraphics.guiHeight() / 4);
                    MultiBufferSource.BufferSource buffer = guiGraphics.bufferSource();
                    PoseStack poseStack = new PoseStack();
                    poseStack.pushPose();
                    poseStack.translate((float) (guiGraphics.guiWidth() / 2) + f5 * Mth.abs(Mth.sin(f4 * 2.0F)),
                            (float) (guiGraphics.guiHeight() / 2) + f6 * Mth.abs(Mth.sin(f4 * 2.0F)), -50.0F);
                    float f7 = 50.0F + 175.0F * Mth.sin(f4);
                    poseStack.scale(f7, -f7, f7);
                    poseStack.mulPose(Axis.YP.rotationDegrees(900.0F * Mth.abs(Mth.sin(f4))));
                    poseStack.mulPose(Axis.XP.rotationDegrees(6.0F * Mth.cos(f * 8.0F)));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(6.0F * Mth.cos(f * 8.0F)));
                    guiGraphics.drawManaged(() -> Texture3DRenderer.render3DTexture(
                            poseStack, buffer.getBuffer(RenderType.entityCutout(texture)),
                            image.getWidth(), image.getHeight(), 1, 15728880));
                    poseStack.popPose();
                }
            }
        }

        RenderSystem.disableBlend();
    }

    public static void registerAnimationOverlay(RegisterGuiLayersEvent event) {
        ResourceLocation id = TheAurorian.prefix("activation_animation");
        event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, id, ActivationAnimationRender::render);
    }

}