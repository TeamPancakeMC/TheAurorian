package cn.teampancake.theaurorian.client.renderer.level;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

@SuppressWarnings({"ConstantConditions", "SameParameterValue"})
public class TASkyRenderer {

    private static VertexBuffer starBuffer;

    public TASkyRenderer() {
        this.createStars();
    }

    public static boolean renderSky(ClientLevel level, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, Camera camera, Runnable skyFogSetup) {
        LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
        skyFogSetup.run();
        Vec3 vec3 = level.getSkyColor(camera.getPosition(), partialTick);
        FogRenderer.levelFogColor();
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        ShaderInstance positionShader = GameRenderer.getPositionShader();
        RenderSystem.depthMask(false);
        RenderSystem.setShaderColor((float) vec3.x, (float) vec3.y, (float) vec3.z, 1.0F);
        ShaderInstance shaderInstance = RenderSystem.getShader();
        levelRenderer.skyBuffer.bind();
        levelRenderer.skyBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, shaderInstance);
        VertexBuffer.unbind();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        poseStack.pushPose();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(level.getTimeOfDay(partialTick) * 360.0F));
        Matrix4f matrix4f1 = poseStack.last().pose();
        float moonSize = 50.0F;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, LevelRenderer.MOON_LOCATION);
        int k = (int)(level.dayTime() / 24000L % 8L + 8L) % 8;
        int l = k % 4;
        int i1 = k / 4 % 2;
        float f13 = (float)(l) / 4.0F;
        float f14 = (float)(i1) / 2.0F;
        float f15 = (float)(l + 1) / 4.0F;
        float f16 = (float)(i1 + 1) / 2.0F;
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f1, -moonSize, -100.0F, moonSize).uv(f15, f16).endVertex();
        bufferBuilder.vertex(matrix4f1, moonSize, -100.0F, moonSize).uv(f13, f16).endVertex();
        bufferBuilder.vertex(matrix4f1, moonSize, -100.0F, -moonSize).uv(f13, f14).endVertex();
        bufferBuilder.vertex(matrix4f1, -moonSize, -100.0F, -moonSize).uv(f15, f14).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
        RenderSystem.setShaderTexture(0, LevelRenderer.MOON_LOCATION);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f1, -moonSize, 100.0F, -moonSize).uv(f15, f16).endVertex();
        bufferBuilder.vertex(matrix4f1, moonSize, 100.0F, -moonSize).uv(f13, f16).endVertex();
        bufferBuilder.vertex(matrix4f1, moonSize, 100.0F, moonSize).uv(f13, f14).endVertex();
        bufferBuilder.vertex(matrix4f1, -moonSize, 100.0F, moonSize).uv(f15, f14).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
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
        return true;
    }

    private void createStars() {
        RenderSystem.setShader(GameRenderer::getPositionShader);
        if (starBuffer != null) {
            starBuffer.close();
        }

        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        starBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        starBuffer.bind();
        starBuffer.upload(this.drawStars(builder));
        VertexBuffer.unbind();
    }

    private BufferBuilder.RenderedBuffer drawStars(BufferBuilder builder) {
        RandomSource randomSource = RandomSource.create(10842L);
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        for (int i = 0; i < 4500; ++i) {
            double d0 = randomSource.nextFloat() * 2.0F - 1.0F;
            double d1 = randomSource.nextFloat() * 2.0F - 1.0F;
            double d2 = randomSource.nextFloat() * 2.0F - 1.0F;
            double d3 = 0.15F + randomSource.nextFloat() * 0.1F;
            double d4 = d0 * d0 + d1 * d1 + d2 * d2;
            if (d4 < 1.0D && d4 > 0.01D) {
                d4 = 1.0D / Math.sqrt(d4);
                d0 *= d4;
                d1 *= d4;
                d2 *= d4;
                double d5 = d0 * 100.0D;
                double d6 = d1 * 100.0D;
                double d7 = d2 * 100.0D;
                double d8 = Math.atan2(d0, d2);
                double d9 = Math.sin(d8);
                double d10 = Math.cos(d8);
                double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
                double d12 = Math.sin(d11);
                double d13 = Math.cos(d11);
                double d14 = randomSource.nextDouble() * Math.PI * 2.0D;
                double d15 = Math.sin(d14);
                double d16 = Math.cos(d14);
                for (int j = 0; j < 4; ++j) {
                    double d18 = (double)((j & 2) - 1) * d3;
                    double d19 = (double)((j + 1 & 2) - 1) * d3;
                    double d21 = d18 * d16 - d19 * d15;
                    double d22 = d19 * d16 + d18 * d15;
                    double d23 = d21 * d12 + 0.0D * d13;
                    double d24 = 0.0D * d12 - d21 * d13;
                    double d25 = d24 * d9 - d22 * d10;
                    double d26 = d22 * d9 + d24 * d10;
                    builder.vertex(d5 + d25, d6 + d23, d7 + d26).endVertex();
                }
            }
        }

        return builder.end();
    }

}