package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.client.renderer.level.TASkyRenderer;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABiomeTags;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@SuppressWarnings("ConstantConditions")
@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {

    @Shadow @Final public Minecraft minecraft;
    @Shadow @Nullable public ClientLevel level;
    @Shadow @Nullable public VertexBuffer skyBuffer;
    @Shadow @Nullable public VertexBuffer starBuffer;

    @Inject(method = "renderEntity", at = @At(value = "HEAD"), cancellable = true)
    private void renderEntity(Entity entity, double camX, double camY, double camZ, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, CallbackInfo ci) {
        if (Minecraft.getInstance().getCameraEntity() instanceof LocalPlayer player && player.hasEffect(TAMobEffects.SHADOWED_SIGHT) && entity != player) {
            ci.cancel();
        }
    }

    @Inject(method = "addParticle(Lnet/minecraft/core/particles/ParticleOptions;ZDDDDDD)V", at = @At(value = "HEAD"), cancellable = true)
    public void addParticle(ParticleOptions options, boolean force, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, CallbackInfo ci) {
        if (Minecraft.getInstance().getCameraEntity() instanceof LocalPlayer player && player.hasEffect(TAMobEffects.SHADOWED_SIGHT)) {
            if (options.getType() == ParticleTypes.ENTITY_EFFECT) {
                ci.cancel();
            }
        }
    }

    // This special effect is only use for exhibition hall and will remove after TeaCon is over.
    @Inject(method = "renderSky", at = @At(value = "INVOKE", target = "Ljava/lang/Runnable;run()V", shift = At.Shift.AFTER), cancellable = true)
    public void renderSky(Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean isFoggy, Runnable skyFogSetup, CallbackInfo ci) {
        Entity cameraEntity = this.minecraft.getCameraEntity();
        ClientLevel clientLevel = this.minecraft.level;
        if (clientLevel != null && cameraEntity instanceof LocalPlayer localPlayer) {
            if (clientLevel.effects().skyType() == DimensionSpecialEffects.SkyType.NORMAL) {
                if (clientLevel.getBiome(localPlayer.getOnPos()).is(TABiomeTags.IS_AURORIAN)) {
                    PoseStack poseStack = new PoseStack();
                    poseStack.mulPose(frustumMatrix);
                    Vec3 vec3 = TASkyRenderer.getBlueSkyColor(this.level, camera.getPosition());
                    FogRenderer.levelFogColor();
                    Tesselator tesselator = Tesselator.getInstance();
                    ShaderInstance positionShader = GameRenderer.getPositionShader();
                    RenderSystem.depthMask(false);
                    RenderSystem.setShaderColor((float) vec3.x, (float) vec3.y, (float) vec3.z, 1.0F);
                    ShaderInstance shaderInstance = RenderSystem.getShader();
                    this.skyBuffer.bind();
                    this.skyBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, shaderInstance);
                    VertexBuffer.unbind();
                    RenderSystem.enableBlend();
                    RenderSystem.blendFuncSeparate(
                            GlStateManager.SourceFactor.SRC_ALPHA,
                            GlStateManager.DestFactor.ONE,
                            GlStateManager.SourceFactor.ONE,
                            GlStateManager.DestFactor.ZERO);
                    poseStack.pushPose();
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                    poseStack.mulPose(Axis.XP.rotationDegrees(level.getTimeOfDay(partialTick) * 360.0F));
                    Matrix4f matrix4f1 = poseStack.last().pose();
                    float moonSize = 70.0F;
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderTexture(0, TASkyRenderer.MOON_LOCATION);
                    int k = level.getMoonPhase();
                    int l = k % 4;
                    int i1 = k / 4 % 2;
                    float f13 = (float)(l) / 4.0F;
                    float f14 = (float)(i1) / 2.0F;
                    float f15 = (float)(l + 1) / 4.0F;
                    float f16 = (float)(i1 + 1) / 2.0F;
                    BufferBuilder bufferBuilder1 = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                    bufferBuilder1.addVertex(matrix4f1, -moonSize, -100.0F, moonSize).setUv(f15, f16);
                    bufferBuilder1.addVertex(matrix4f1, moonSize, -100.0F, moonSize).setUv(f13, f16);
                    bufferBuilder1.addVertex(matrix4f1, moonSize, -100.0F, -moonSize).setUv(f13, f14);
                    bufferBuilder1.addVertex(matrix4f1, -moonSize, -100.0F, -moonSize).setUv(f15, f14);
                    BufferUploader.drawWithShader(bufferBuilder1.buildOrThrow());
                    RenderSystem.setShaderTexture(0, TASkyRenderer.MOON_LOCATION);
                    BufferBuilder bufferBuilder2 = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                    bufferBuilder2.addVertex(matrix4f1, -moonSize, 100.0F, -moonSize).setUv(f15, f16);
                    bufferBuilder2.addVertex(matrix4f1, moonSize, 100.0F, -moonSize).setUv(f13, f16);
                    bufferBuilder2.addVertex(matrix4f1, moonSize, 100.0F, moonSize).setUv(f13, f14);
                    bufferBuilder2.addVertex(matrix4f1, -moonSize, 100.0F, moonSize).setUv(f15, f14);
                    BufferUploader.drawWithShader(bufferBuilder2.buildOrThrow());
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    FogRenderer.setupNoFog();
                    starBuffer.bind();
                    starBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, positionShader);
                    VertexBuffer.unbind();
                    skyFogSetup.run();
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    RenderSystem.disableBlend();
                    RenderSystem.defaultBlendFunc();
                    poseStack.popPose();
                    RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    RenderSystem.depthMask(true);
                    ci.cancel();
                }
            }
        }
    }
    
}