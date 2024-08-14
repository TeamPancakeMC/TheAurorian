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
public class SilentCampfireRender implements BlockEntityRenderer<SilentCampfireBlockEntity> {

    private static final float SIZE = 0.375F;
    private final ItemRenderer itemRenderer;

    public SilentCampfireRender(BlockEntityRendererProvider.Context pContext) {
        this.itemRenderer = pContext.getItemRenderer();
    }

    public void render(SilentCampfireBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Direction $$6 = pBlockEntity.getBlockState().getValue(CampfireBlock.FACING);
        NonNullList<ItemStack> $$7 = pBlockEntity.getItems();
        int $$8 = (int)pBlockEntity.getBlockPos().asLong();
        for(int $$9 = 0; $$9 < $$7.size(); ++$$9) {
            ItemStack $$10 = $$7.get($$9);
            if ($$10 != ItemStack.EMPTY) {
                pPoseStack.pushPose();
                pPoseStack.translate(0.5F, 0.44921875F, 0.5F);
                Direction $$11 = Direction.from2DDataValue(($$9 + $$6.get2DDataValue()) % 4);
                float $$12 = -$$11.toYRot();
                pPoseStack.mulPose(Axis.YP.rotationDegrees($$12));
                pPoseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                pPoseStack.translate(-0.3125F, -0.3125F, 0.0F);
                pPoseStack.scale(0.375F, 0.375F, 0.375F);
                this.itemRenderer.renderStatic($$10, ItemDisplayContext.FIXED, pPackedLight, pPackedOverlay, pPoseStack, pBuffer, pBlockEntity.getLevel(), $$8 + $$9);
                pPoseStack.popPose();
            }
        }
    }

}