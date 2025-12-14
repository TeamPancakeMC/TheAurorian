package cn.teampancake.theaurorian.client.renderer.block.crystal;

import cn.teampancake.theaurorian.client.model.block.LunarCrystalModel;
import cn.teampancake.theaurorian.common.blocks.crystal.AbstractLunarCrystal;
import cn.teampancake.theaurorian.common.blocks.entity.crystal.AbstractLunarCrystalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class LunarCrystalRenderer<T extends AbstractLunarCrystalBlockEntity> extends GeoBlockRenderer<T> {

    public LunarCrystalRenderer(ResourceLocation assetSubpath) {
        super(new LunarCrystalModel<>(assetSubpath));
    }

    @Override
    public void defaultRender(PoseStack poseStack, T animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        if (animatable.getBlockState().getValue(AbstractLunarCrystal.HALF) == DoubleBlockHalf.LOWER) {
            super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
        }
    }

}