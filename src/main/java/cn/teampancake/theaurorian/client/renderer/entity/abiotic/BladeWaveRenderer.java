package cn.teampancake.theaurorian.client.renderer.entity.abiotic;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.entities.projectile.blade_waves.BladeWave;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BladeWaveRenderer extends EntityRenderer<BladeWave> {

    private static final ResourceLocation TEXTURE_LOCATION = TheAurorian.prefix("textures/entity/blade_wave.png");

    public BladeWaveRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(BladeWave entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        float f = 15.0F;
        poseStack.scale(f, f, f);
        poseStack.mulPose(Axis.XN.rotationDegrees(90.0F));
        poseStack.mulPose(Axis.ZN.rotationDegrees(entity.yRotO));
        PoseStack.Pose pose = poseStack.last();
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE_LOCATION));
        vertex(consumer, pose, packedLight, 0.0F, 0.0F, 0.0F, 1.0F);
        vertex(consumer, pose, packedLight, 1.0F, 0.0F, 1.0F, 1.0F);
        vertex(consumer, pose, packedLight, 1.0F, 1.0F, 1.0F, 0.0F);
        vertex(consumer, pose, packedLight, 0.0F, 1.0F, 0.0F, 0.0F);
        poseStack.popPose();
    }

    @Override
    protected int getBlockLightLevel(BladeWave entity, BlockPos pos) {
        return 15;
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight, float x, float y, float u, float v) {
        consumer.addVertex(pose, x - 0.5F, y - 0.25F, 0.0F).setColor(-1).setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(BladeWave entity) {
        return TEXTURE_LOCATION;
    }

}