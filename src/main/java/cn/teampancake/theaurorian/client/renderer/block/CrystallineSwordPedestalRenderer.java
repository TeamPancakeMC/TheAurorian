package cn.teampancake.theaurorian.client.renderer.block;

import cn.teampancake.theaurorian.common.blocks.entity.CrystallineSwordPedestalBlockEntity;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.util.RenderUtil;

@SuppressWarnings({"removal", "UnstableApiUsage", "unused"})
public class CrystallineSwordPedestalRenderer extends GeoBlockRenderer<CrystallineSwordPedestalBlockEntity> {

    public CrystallineSwordPedestalRenderer(BlockEntityRendererProvider.Context context) {
        super(new DefaultedBlockGeoModel<>(TABlocks.CRYSTALLINE_SWORD_PEDESTAL.getId()));
    }

    @Override
    public void renderRecursively(
            PoseStack poseStack, CrystallineSwordPedestalBlockEntity animatable, GeoBone bone,
            RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
            boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        poseStack.pushPose();
        RenderUtil.prepMatrixForBone(poseStack, bone);
        buffer = this.checkAndRefreshBuffer(isReRender, buffer, bufferSource, renderType);
        if (bone.getName().equals("tentacle")) {
            if (animatable.sealing) {
                bone.setHidden(false);
            } else if (!animatable.isSeal()) {
                bone.setHidden(true);
            }

        } else if (bone.getName().equals("sword")) {
            ItemStack stack = animatable.getItems().getFirst();
            boolean flag = stack.is(TAItems.CRYSTALLINE_SWORD);
            if (stack.isEmpty() || !flag) {
                bone.setHidden(true);
            } else {
                bone.setHidden(false);
                if (EnchantmentHelper.hasAnyEnchantments(stack)) {
                    VertexConsumer foilBuffer = bufferSource.getBuffer(RenderType.glint());
                    buffer = VertexMultiConsumer.create(foilBuffer, bufferSource.getBuffer(renderType));
                }
            }
        }

        this.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, colour);
        this.renderChildBones(poseStack, animatable, bone, renderType, bufferSource,
                buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        poseStack.popPose();
    }

}