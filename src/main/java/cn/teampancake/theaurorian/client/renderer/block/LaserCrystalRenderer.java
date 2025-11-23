package cn.teampancake.theaurorian.client.renderer.block;

import cn.teampancake.theaurorian.common.blocks.LaserCrystal;
import cn.teampancake.theaurorian.common.blocks.entity.LaserCrystalBlockEntity;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class LaserCrystalRenderer extends GeoBlockRenderer<LaserCrystalBlockEntity> {

    public LaserCrystalRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(TABlockEntityTypes.LASER_CRYSTAL.getId()));
    }

    @Override
    public void defaultRender(PoseStack poseStack, LaserCrystalBlockEntity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        if (animatable.getBlockState().getValue(LaserCrystal.HALF) == DoubleBlockHalf.LOWER) {
            super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
        }
    }

}