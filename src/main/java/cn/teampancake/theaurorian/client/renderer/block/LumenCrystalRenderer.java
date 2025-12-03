package cn.teampancake.theaurorian.client.renderer.block;

import cn.teampancake.theaurorian.client.model.block.LumenCrystalModel;
import cn.teampancake.theaurorian.common.blocks.crystal.AbstractLumenCrystal;
import cn.teampancake.theaurorian.common.blocks.entity.crystal.AbstractLumenCrystalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class LumenCrystalRenderer<T extends AbstractLumenCrystalBlockEntity> extends GeoBlockRenderer<T> {

    public LumenCrystalRenderer(ResourceLocation assetSubpath) {
        super(new LumenCrystalModel<>(assetSubpath));
    }

    @Override
    public void defaultRender(PoseStack poseStack, T animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        if (animatable.getBlockState().getValue(AbstractLumenCrystal.HALF) == DoubleBlockHalf.LOWER) {
            super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
        }
    }

}