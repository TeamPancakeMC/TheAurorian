package cn.teampancake.theaurorian.client.renderer.block;

import cn.teampancake.theaurorian.common.blocks.MysteriumWoolBed;
import cn.teampancake.theaurorian.common.blocks.entity.MysteriumWoolBedBlockEntity;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MysteriumWoolBedItemRenderer extends BlockEntityWithoutLevelRenderer {

    public MysteriumWoolBedItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (Block.byItem(stack.getItem()) instanceof MysteriumWoolBed) {
            MysteriumWoolBedBlockEntity bedBlockEntity = new MysteriumWoolBedBlockEntity(BlockPos.ZERO, TABlocks.MYSTERIUM_WOOL_BED.get().defaultBlockState());
            Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(bedBlockEntity, poseStack, buffer, packedLight, packedOverlay);
        } else {
            super.renderByItem(stack, displayContext, poseStack, buffer, packedLight, packedOverlay);
        }
    }

}