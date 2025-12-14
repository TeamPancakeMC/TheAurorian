package cn.teampancake.theaurorian.client.renderer.block.crystal;

import cn.teampancake.theaurorian.common.blocks.crystal.AbstractLunarCrystal;
import cn.teampancake.theaurorian.common.blocks.entity.crystal.LunarSourcePrismBlockEntity;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;

public class LunarSourcePrismRenderer extends LunarCrystalRenderer<LunarSourcePrismBlockEntity> {

    public LunarSourcePrismRenderer(BlockEntityRendererProvider.Context context) {
        super(TABlockEntityTypes.LUNAR_SOURCE_PRISM.getId());
    }

    @Override
    public void renderFinal(
            PoseStack poseStack, LunarSourcePrismBlockEntity animatable, BakedGeoModel model,
            MultiBufferSource bufferSource, @Nullable VertexConsumer buffer,
            float partialTick, int packedLight, int packedOverlay, int colour) {
        BlockPos targetPos = animatable.getConnectedTarget();
        Level level = animatable.getLevel();
        if (!animatable.activated || targetPos == null || level == null) return;
        BlockPos sourcePos = animatable.getBlockPos();
        BlockState state = animatable.getBlockState();
        DoubleBlockHalf blockHalf = state.getValue(AbstractLunarCrystal.HALF);
        if (blockHalf == DoubleBlockHalf.UPPER) sourcePos = sourcePos.below();
        renderBeam(poseStack, sourcePos, targetPos, bufferSource,
                animatable.beamColor, level.getGameTime(),
                animatable.beamRadius, animatable.glowRadius, partialTick);
    }

    private static void renderBeam(
            PoseStack poseStack, BlockPos sourcePos, BlockPos targetPos, MultiBufferSource bufferSource,
            int color, long gameTime, float beamRadius, float glowRadius, float partialTick) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.2F, 0.5F);
        Vec3 startPos = Vec3.atCenterOf(sourcePos).add(0, 0.3, 0);
        Vec3 endPos = Vec3.atCenterOf(targetPos).add(0, 0.3, 0);
        Vec3 vec3d3 = endPos.subtract(startPos);
        float k = (float)(vec3d3.length() - 0.5f);
        vec3d3 = vec3d3.normalize();
        float l = (float)Math.acos(vec3d3.y);
        float m = (float)Math.atan2(vec3d3.z, vec3d3.x);
        float f = (float)Math.floorMod(gameTime, 40) + partialTick;
        poseStack.mulPose(Axis.YP.rotationDegrees((1.5707964f - m) * 57.295776f));
        poseStack.mulPose(Axis.XP.rotationDegrees(l * 57.295776f));
        poseStack.pushPose();
        float f12 = -1.0F + Mth.frac(f * 0.2F - Mth.floor(f * 0.1F));
        float f13 = k * (0.5F / beamRadius) - f12;
        RenderType renderType1 = RenderType.beaconBeam(BeaconRenderer.BEAM_LOCATION, false);
        renderPart(poseStack, bufferSource.getBuffer(renderType1), color,
                0, k, 0.0F, beamRadius, beamRadius, 0.0F, -beamRadius,
                0.0F, 0.0F, -beamRadius, 0.0F, 1.0F, f13, -f12);
        poseStack.popPose();
        poseStack.pushPose();
        RenderType renderType2 = RenderType.beaconBeam(BeaconRenderer.BEAM_LOCATION, true);
        renderPart(poseStack, bufferSource.getBuffer(renderType2),
                FastColor.ARGB32.color(128, color), 0, k,
                -glowRadius, -glowRadius, glowRadius, -glowRadius, -glowRadius,
                glowRadius, glowRadius, glowRadius, 0.0F, 1.0F, k - f12, -f12);
        poseStack.popPose();
        poseStack.popPose();
    }

    public static void renderPart(
            PoseStack poseStack, VertexConsumer consumer,
            int color, float minY, float maxY,
            float x1, float z1, float x2, float z2,
            float x3, float z3, float x4, float z4,
            float minU, float maxU, float minV, float maxV) {
        PoseStack.Pose pose = poseStack.last();
        renderQuad(pose, consumer, color, minY, maxY, x1, z1, x2, z2, minU, maxU, minV, maxV);
        renderQuad(pose, consumer, color, minY, maxY, x4, z4, x3, z3, minU, maxU, minV, maxV);
        renderQuad(pose, consumer, color, minY, maxY, x2, z2, x4, z4, minU, maxU, minV, maxV);
        renderQuad(pose, consumer, color, minY, maxY, x3, z3, x1, z1, minU, maxU, minV, maxV);
    }

    private static void renderQuad(
            PoseStack.Pose pose, VertexConsumer consumer,
            int color, float minY, float maxY,
            float minX, float minZ, float maxX, float maxZ,
            float minU, float maxU, float minV, float maxV) {
        addVertex(pose, consumer, color, maxY, minX, minZ, maxU, minV);
        addVertex(pose, consumer, color, minY, minX, minZ, maxU, maxV);
        addVertex(pose, consumer, color, minY, maxX, maxZ, minU, maxV);
        addVertex(pose, consumer, color, maxY, maxX, maxZ, minU, minV);
    }

    private static void addVertex(PoseStack.Pose pose, VertexConsumer consumer, int color, float y, float x, float z, float u, float v) {
        consumer.addVertex(pose, x, y, z).setColor(color).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(15728880).setNormal(0.0F, 1.0F, 0.0F);
    }

    @Override
    public AABB getRenderBoundingBox(LunarSourcePrismBlockEntity blockEntity) {
        return AABB.INFINITE;
    }

}