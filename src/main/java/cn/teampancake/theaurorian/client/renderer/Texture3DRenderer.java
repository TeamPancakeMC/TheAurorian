package cn.teampancake.theaurorian.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class Texture3DRenderer {
    
    /**
     * 渲染一个带厚度的贴图盒子
     * @param poseStack 渲染矩阵
     * @param width 贴图宽度（像素）
     * @param height 贴图高度（像素）
     * @param depth 盒子厚度（像素，建议1~2）
     * @param packedLight 光照
     */
    public static void render3DTexture(PoseStack poseStack, VertexConsumer vertexConsumer, int width, int height, float depth, int packedLight) {
        float halfWidth = width / 2.0f;
        float halfHeight = height / 2.0f;
        float halfDepth = depth / 2.0f;
        addQuad(vertexConsumer, poseStack, -halfWidth, -halfHeight, halfDepth, halfWidth, halfHeight, halfDepth,
            0.0f, 0.0f, 1.0f, 1.0f, packedLight, 0, 0, 1);
        addQuad(vertexConsumer, poseStack, -halfWidth, -halfHeight, -halfDepth, halfWidth, halfHeight, -halfDepth,
            0.0f, 0.0f, 1.0f, 1.0f, packedLight, 0, 0, -1);
        addQuad(vertexConsumer, poseStack, -halfWidth, -halfHeight, -halfDepth, -halfWidth, halfHeight, halfDepth,
            0.0f, 0.0f, 1.0f, 1.0f, packedLight, -1, 0, 0);
        addQuad(vertexConsumer, poseStack, halfWidth, -halfHeight, -halfDepth, halfWidth, halfHeight, halfDepth,
            0.0f, 0.0f, 1.0f, 1.0f, packedLight, 1, 0, 0);
        addQuad(vertexConsumer, poseStack, -halfWidth, halfHeight, -halfDepth, halfWidth, halfHeight, halfDepth,
            0.0f, 0.0f, 1.0f, 1.0f, packedLight, 0, 1, 0);
        addQuad(vertexConsumer, poseStack, -halfWidth, -halfHeight, -halfDepth, halfWidth, -halfHeight, halfDepth,
            0.0f, 0.0f, 1.0f, 1.0f, packedLight, 0, -1, 0);
    }

    private static void addQuad(
            VertexConsumer builder, PoseStack poseStack,
            float x0, float y0, float z0,
            float x1, float y1, float z1,
            float u0, float v0, float u1, float v1,
            int packedLight, float nx, float ny, float nz) {
        PoseStack.Pose pose = poseStack.last();
        builder.addVertex(pose.pose(), x0, y0, z0)
            .setColor(255, 255, 255, 255)
            .setUv(u0, v0).setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(packedLight).setNormal(pose, nx, ny, nz);
        builder.addVertex(pose.pose(), x0, y1, z0)
            .setColor(255, 255, 255, 255)
            .setUv(u0, v1).setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(packedLight).setNormal(pose, nx, ny, nz);
        builder.addVertex(pose.pose(), x1, y1, z1)
            .setColor(255, 255, 255, 255)
            .setUv(u1, v1).setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(packedLight).setNormal(pose, nx, ny, nz);
        builder.addVertex(pose.pose(), x1, y0, z1)
            .setColor(255, 255, 255, 255)
            .setUv(u1, v0).setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(packedLight).setNormal(pose, nx, ny, nz);
    }

} 