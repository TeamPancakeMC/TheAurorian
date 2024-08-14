package cn.teampancake.theaurorian.client.renderer.block;

import cn.teampancake.theaurorian.common.blocks.entity.MoonlightForgeBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoonlightForgeRenderer implements BlockEntityRenderer<MoonlightForgeBlockEntity> {

    private final ItemRenderer itemRenderer;

    public MoonlightForgeRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(MoonlightForgeBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        int index = blockEntity.getItem(2).isEmpty() ? 0 : 2;
        ItemStack stack = blockEntity.getItem(index);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.656F, 0.5F);
            poseStack.mulPose(Axis.YP.rotationDegrees(90));
            poseStack.mulPose(Axis.XP.rotationDegrees(-90));
            poseStack.scale(0.8F, 0.8F, 0.8F);
            this.itemRenderer.renderStatic(stack, ItemDisplayContext.NONE, packedLight,
                    OverlayTexture.NO_OVERLAY, poseStack, buffer, blockEntity.getLevel(), 0);
            poseStack.popPose();
        }
        poseStack.popPose();
    }

}