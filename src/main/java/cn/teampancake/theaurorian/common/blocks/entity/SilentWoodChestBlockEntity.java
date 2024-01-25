package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SilentWoodChestBlockEntity extends ChestBlockEntity {

    public SilentWoodChestBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.SILENT_WOOD_CHEST.get(), pos, blockState);
    }

}