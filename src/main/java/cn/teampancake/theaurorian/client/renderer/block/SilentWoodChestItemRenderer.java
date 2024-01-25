package cn.teampancake.theaurorian.client.renderer.block;

import cn.teampancake.theaurorian.common.blocks.SilentWoodChest;
import cn.teampancake.theaurorian.common.blocks.entity.SilentWoodChestBlockEntity;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class SilentWoodChestItemRenderer extends BlockEntityWithoutLevelRenderer {

    public SilentWoodChestItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (Block.byItem(stack.getItem()) instanceof SilentWoodChest) {
            SilentWoodChestBlockEntity silentWoodChest = new SilentWoodChestBlockEntity(BlockPos.ZERO, TABlocks.SILENT_WOOD_CHEST.get().defaultBlockState());
            Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(silentWoodChest, poseStack, buffer, packedLight, packedOverlay);
        } else {
            super.renderByItem(stack, displayContext, poseStack, buffer, packedLight, packedOverlay);
        }
    }

}