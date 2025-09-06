package cn.teampancake.theaurorian.client.renderer.block;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.blocks.SacrificeTable;
import cn.teampancake.theaurorian.common.blocks.entity.SacrificeTableBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SacrificeTableRenderer extends GeoBlockRenderer<SacrificeTableBlockEntity> {

    private final ItemRenderer itemRenderer;

    public SacrificeTableRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(TheAurorian.prefix("sacrifice_table")));
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void renderFinal(
            PoseStack poseStack, SacrificeTableBlockEntity blockEntity, BakedGeoModel model,
            MultiBufferSource bufferSource, @Nullable VertexConsumer buffer,
            float partialTick, int packedLight, int packedOverlay, int colour) {
        Direction direction = blockEntity.getBlockState().getValue(SacrificeTable.FACING);
        NonNullList<ItemStack> itemStacks = blockEntity.getItems();
        int seed = (int) blockEntity.getBlockPos().asLong();
        float yRot;
        switch (direction) {
            case SOUTH -> yRot = 180.0F;
            case WEST -> yRot = 90.0F;
            case EAST -> yRot = -90.0F;
            default -> yRot = 0.0F;
        }

        float spacing = 0.3F;
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.44921875F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        int visibleIndex = 0;
        for (int j = 0; j < itemStacks.size(); j++) {
            ItemStack itemStack = itemStacks.get(j);
            float offsetX = (visibleIndex - 1.0F) * spacing + 0.3F;
            poseStack.pushPose();
            poseStack.translate(offsetX, 0.0F, 0.5F);
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            poseStack.translate(-0.3125F, -0.3125F, 0.0F);
            poseStack.scale(0.375F, 0.375F, 0.375F);
            this.itemRenderer.renderStatic(
                    itemStack, ItemDisplayContext.FIXED, packedLight,
                    packedOverlay, poseStack, bufferSource,
                    blockEntity.getLevel(), seed + j);
            poseStack.popPose();
            visibleIndex++;
        }

        poseStack.popPose();
    }

}
