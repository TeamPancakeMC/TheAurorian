package cn.teampancake.theaurorian.client.renderer.level;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.event.TAEventFactory;
import com.google.common.collect.ImmutableMap;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
public class TASkyRenderer {

    public static final ResourceLocation MOON_LOCATION = TheAurorian.prefix("textures/environment/moon_phases.png");
    private static ResourceLocation currentPhase = TheAurorian.prefix("ta_cyan");
    private static VertexBuffer starBuffer = null;

    public TASkyRenderer() {
        this.createStars();
    }

    public static boolean renderSky(ClientLevel level, Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick, Camera camera, Runnable skyFogSetup) {
        LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
        skyFogSetup.run();
        PoseStack poseStack = new PoseStack();
        poseStack.mulPose(frustumMatrix);
        Vec3 vec3 = getSkyColor(level, camera.getPosition());
        FogRenderer.levelFogColor();
        Tesselator tesselator = Tesselator.getInstance();
        ShaderInstance positionShader = GameRenderer.getPositionShader();
        RenderSystem.depthMask(false);
        RenderSystem.setShaderColor((float) vec3.x, (float) vec3.y, (float) vec3.z, 1.0F);
        ShaderInstance shaderInstance = RenderSystem.getShader();
        levelRenderer.skyBuffer.bind();
        levelRenderer.skyBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, shaderInstance);
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
        RenderSystem.setShaderTexture(0, MOON_LOCATION);
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
        RenderSystem.setShaderTexture(0, MOON_LOCATION);
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
        return true;
    }

    public static final Map<ResourceLocation,Integer> DaySkyColors = getDaySkyColors();

    private static Map<ResourceLocation, Integer> getDaySkyColors() {
        Map<ResourceLocation, Integer> map = new LinkedHashMap<>();
        map.put(TheAurorian.prefix("ta_purple"), 0x8d60d7);
        map.put(TheAurorian.prefix("ta_pink"), 0xf49cae);
        map.put(TheAurorian.prefix("ta_cyan"), 0x80e3ec);
        map.put(TheAurorian.prefix("ta_orange"), 0xfff089);
        map.put(TheAurorian.prefix("ta_lime"), 0x69c941);
        TAEventFactory.onRegisterAurorianSkyColor(map);
        return ImmutableMap.copyOf(map);
    }

    public static Vec3 getSkyColor(ClientLevel level, Vec3 pos) {
        float timeOfDay = level.dimensionType().timeOfDay(1000L);
        int rgbColor = smoothColorTransition((level.dayTime() + 6000L) / 24000F);
        Vec3 vec3 = pos.subtract(2.0D, 2.0D, 2.0D).scale(0.25D);
        Vec3 vec31 = CubicSampler.gaussianSampleVec3(vec3, (x, y, z) -> Vec3.fromRGB24(rgbColor));
        float f1 = Mth.cos(timeOfDay * ((float) Math.PI * 2F)) * 2.0F + 0.5F;
        return new Vec3((float) vec31.x * f1, (float) vec31.y * f1, (float) vec31.z * f1);
    }

    public static int smoothColorTransition(float t) {
        Color currentColor = new Color(0x010e34);
        Color targetColor = new Color(DaySkyColors.get(currentPhase));
        double d = Math.sin(2.0F * Math.PI * t + 0.5F * Math.PI);
        d = (d + 1.0D) / 2.0D;
        int r = currentColor.getRed() + (int) ((targetColor.getRed() - currentColor.getRed()) * d);
        int g = currentColor.getGreen() + (int) ((targetColor.getGreen() - currentColor.getGreen()) * d);
        int b = currentColor.getBlue() + (int) ((targetColor.getBlue() - currentColor.getBlue()) * d);
        return new Color(r, g, b).getRGB();
    }

    public static void setCurrentPhase(int stateCode) {
        List<ResourceLocation> colorNames = new ArrayList<>(DaySkyColors.keySet());
        currentPhase = colorNames.get(stateCode);
    }

    private void createStars() {
        if (starBuffer != null) {
            starBuffer.close();
        }

        Tesselator builder = Tesselator.getInstance();
        starBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        starBuffer.bind();
        starBuffer.upload(drawStars(builder));
        VertexBuffer.unbind();
    }

    public static MeshData drawStars(Tesselator tesselator) {
        RandomSource randomSource = RandomSource.create(10842L);
        BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        for (int j = 0; j < 5000; j++) {
            float f1 = randomSource.nextFloat() * 2.0F - 1.0F;
            float f2 = randomSource.nextFloat() * 2.0F - 1.0F;
            float f3 = randomSource.nextFloat() * 2.0F - 1.0F;
            float f4 = 0.15F + randomSource.nextFloat() * 0.1F;
            float f5 = Mth.lengthSquared(f1, f2, f3);
            if (!(f5 <= 0.010000001F) && !(f5 >= 1.0F)) {
                Vector3f vector3f = new Vector3f(f1, f2, f3).normalize(100.0F);
                float f6 = (float)(randomSource.nextDouble() * (float) Math.PI * 2.0);
                Quaternionf quaternionf = new Quaternionf().rotateTo(new Vector3f(0.0F, 0.0F, -1.0F), vector3f).rotateZ(f6);
                bufferBuilder.addVertex(vector3f.add(new Vector3f(f4, -f4, 0.0F).rotate(quaternionf)));
                bufferBuilder.addVertex(vector3f.add(new Vector3f(f4, f4, 0.0F).rotate(quaternionf)));
                bufferBuilder.addVertex(vector3f.add(new Vector3f(-f4, f4, 0.0F).rotate(quaternionf)));
                bufferBuilder.addVertex(vector3f.add(new Vector3f(-f4, -f4, 0.0F).rotate(quaternionf)));
            }
        }

        return bufferBuilder.buildOrThrow();
    }

}