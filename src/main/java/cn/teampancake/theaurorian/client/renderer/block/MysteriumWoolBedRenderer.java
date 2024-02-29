package cn.teampancake.theaurorian.client.renderer.block;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.blocks.MysteriumWoolBed;
import cn.teampancake.theaurorian.common.blocks.entity.MysteriumWoolBedBlockEntity;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MysteriumWoolBedRenderer implements BlockEntityRenderer<MysteriumWoolBedBlockEntity> {

    private final ModelPart headRoot;
    private final ModelPart footRoot;

    public MysteriumWoolBedRenderer(BlockEntityRendererProvider.Context context) {
        this.headRoot = context.bakeLayer(ModelLayers.BED_HEAD);
        this.footRoot = context.bakeLayer(ModelLayers.BED_FOOT);
    }

    @Override
    public void render(MysteriumWoolBedBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Material material = new Material(Sheets.BED_SHEET, AurorianMod.prefix("entity/bed/mysterium_wool_bed"));
        Level level = blockEntity.getLevel();
        if (level != null) {
            BlockState blockState = blockEntity.getBlockState();
            DoubleBlockCombiner.NeighborCombineResult<? extends MysteriumWoolBedBlockEntity> neighborCombineResult =
                    DoubleBlockCombiner.combineWithNeigbour(TABlockEntityTypes.MYSTERIUM_WOOL_BED.get(),
                            MysteriumWoolBed::getBlockType, MysteriumWoolBed::getConnectedDirection, ChestBlock.FACING,
                            blockState, level, blockEntity.getBlockPos(), (accessor, blockPos) -> false);
            int i = neighborCombineResult.apply(new BrightnessCombiner<>()).get(packedLight);
            this.renderPiece(poseStack, buffer, blockState.getValue(BedBlock.PART) == BedPart.HEAD ? this.headRoot : this.footRoot,
                    blockState.getValue(BedBlock.FACING), material, i, packedOverlay, false);
        } else {
            this.renderPiece(poseStack, buffer, this.headRoot, Direction.SOUTH, material, packedLight, packedOverlay, false);
            this.renderPiece(poseStack, buffer, this.footRoot, Direction.SOUTH, material, packedLight, packedOverlay, true);
        }
    }

    private void renderPiece(PoseStack poseStack, MultiBufferSource bufferSource, ModelPart modelPart, Direction direction, Material material, int packedLight, int packedOverlay, boolean foot) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.5625F, foot ? -1.0F : 0.0F);
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        poseStack.translate(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F + direction.toYRot()));
        poseStack.translate(-0.5F, -0.5F, -0.5F);
        VertexConsumer vertexconsumer = material.buffer(bufferSource, RenderType::entitySolid);
        modelPart.render(poseStack, vertexconsumer, packedLight, packedOverlay);
        poseStack.popPose();
    }

}