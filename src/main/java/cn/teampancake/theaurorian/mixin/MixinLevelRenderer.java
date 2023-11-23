package cn.teampancake.theaurorian.mixin;

import cn.teampancake.theaurorian.registry.TADimensions;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer {

    @Shadow @Final private Minecraft minecraft;
    @Shadow @Nullable private ClientLevel level;
    @Shadow @Nullable private VertexBuffer skyBuffer;
    @Shadow @Nullable private VertexBuffer starBuffer;
    @Shadow @Nullable private VertexBuffer darkBuffer;
    @Shadow @Final private static ResourceLocation MOON_LOCATION;
    @Shadow protected abstract boolean doesMobEffectBlockSky(Camera camera);

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "renderSky", at = @At(value = "INVOKE", target = "Ljava/lang/Runnable;run()V", shift = At.Shift.AFTER, ordinal = 0), cancellable = true)
    public void renderSky(PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean isFoggy, Runnable skyFogSetup, CallbackInfo ci) {
        if (!isFoggy) {
            FogType fogtype = camera.getFluidInCamera();
            ClientLevel clientLevel = this.minecraft.level;
            boolean flag1 = fogtype != FogType.POWDER_SNOW && fogtype != FogType.LAVA && !this.doesMobEffectBlockSky(camera);
            boolean flag2 = clientLevel != null && clientLevel.effects().skyType() == DimensionSpecialEffects.SkyType.NORMAL;
            boolean flag3 = this.level != null && this.level.dimension() == TADimensions.AURORIAN_DIMENSION;
            if (flag1 && flag2 && flag3 && this.skyBuffer != null) {
                Vec3 vec3 = this.level.getSkyColor(this.minecraft.gameRenderer.getMainCamera().getPosition(), partialTick);
                FogRenderer.levelFogColor();
                BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
                ShaderInstance positionShader = GameRenderer.getPositionShader();
                RenderSystem.depthMask(false);
                RenderSystem.setShaderColor((float) vec3.x, (float) vec3.y, (float) vec3.z, 1.0F);
                ShaderInstance shaderInstance = RenderSystem.getShader();
                this.skyBuffer.bind();
                this.skyBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, shaderInstance);
                VertexBuffer.unbind();
                RenderSystem.enableBlend();
                RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                poseStack.pushPose();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(this.level.getTimeOfDay(partialTick) * 360.0F));
                Matrix4f matrix4f1 = poseStack.last().pose();
                float f12 = 20.0F;
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, MOON_LOCATION);
                int k = (int)(this.level.dayTime() / 24000L % 8L + 8L) % 8;
                int l = k % 4;
                int i1 = k / 4 % 2;
                float f13 = (float)(l) / 4.0F;
                float f14 = (float)(i1) / 2.0F;
                float f15 = (float)(l + 1) / 4.0F;
                float f16 = (float)(i1 + 1) / 2.0F;
                bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                bufferBuilder.vertex(matrix4f1, -f12, -100.0F, f12).uv(f15, f16).endVertex();
                bufferBuilder.vertex(matrix4f1, f12, -100.0F, f12).uv(f13, f16).endVertex();
                bufferBuilder.vertex(matrix4f1, f12, -100.0F, -f12).uv(f13, f14).endVertex();
                bufferBuilder.vertex(matrix4f1, -f12, -100.0F, -f12).uv(f15, f14).endVertex();
                BufferUploader.drawWithShader(bufferBuilder.end());
                RenderSystem.setShaderTexture(0, MOON_LOCATION);
                bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                bufferBuilder.vertex(matrix4f1, -f12, 100.0F, -f12).uv(f15, f16).endVertex();
                bufferBuilder.vertex(matrix4f1, f12, 100.0F, -f12).uv(f13, f16).endVertex();
                bufferBuilder.vertex(matrix4f1, f12, 100.0F, f12).uv(f13, f14).endVertex();
                bufferBuilder.vertex(matrix4f1, -f12, 100.0F, f12).uv(f15, f14).endVertex();
                BufferUploader.drawWithShader(bufferBuilder.end());
                if (this.starBuffer != null && positionShader != null) {
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    FogRenderer.setupNoFog();
                    this.starBuffer.bind();
                    this.starBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, positionShader);
                    VertexBuffer.unbind();
                    skyFogSetup.run();
                }

                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.disableBlend();
                RenderSystem.defaultBlendFunc();
                poseStack.popPose();
                RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
                double d0 = this.minecraft.player.getEyePosition(partialTick).y - this.level.getLevelData().getHorizonHeight(this.level);
                if (d0 < 0.0D && this.darkBuffer != null && shaderInstance != null) {
                    poseStack.pushPose();
                    poseStack.translate(0.0F, 12.0F, 0.0F);
                    this.darkBuffer.bind();
                    this.darkBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, shaderInstance);
                    VertexBuffer.unbind();
                    poseStack.popPose();
                }

                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.depthMask(true);
                ci.cancel();
            }
        }
    }

}