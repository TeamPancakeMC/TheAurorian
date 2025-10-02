package cn.teampancake.theaurorian.client.renderer.block;

import cn.teampancake.theaurorian.common.blocks.entity.SilentCampfireBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CampfireBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SilentCampfireRenderer implements BlockEntityRenderer<SilentCampfireBlockEntity> {

    private final ItemRenderer itemRenderer;

    public SilentCampfireRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    public void render(SilentCampfireBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Direction facing = blockEntity.getBlockState().getValue(CampfireBlock.FACING);
        NonNullList<ItemStack> items = blockEntity.getItems();
        int seed = (int) blockEntity.getBlockPos().asLong();
        for(int i = 0; i < items.size(); ++i) {
            ItemStack itemStack = items.get(i);
            if (itemStack != ItemStack.EMPTY) {
                poseStack.pushPose();
                poseStack.translate(0.5F, 0.44921875F, 0.5F);
                int index = (i + facing.get2DDataValue()) % 4;
                Direction direction = Direction.from2DDataValue(index);
                poseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.translate(-0.3125F, -0.3125F, 0.0F);
                poseStack.scale(0.375F, 0.375F, 0.375F);
                this.itemRenderer.renderStatic(
                        itemStack, ItemDisplayContext.FIXED,
                        packedLight, packedOverlay, poseStack,
                        buffer, blockEntity.getLevel(), seed + i);
                poseStack.popPose();
            }
        }
    }

}