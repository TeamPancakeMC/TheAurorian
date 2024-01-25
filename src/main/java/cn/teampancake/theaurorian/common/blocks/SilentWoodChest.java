package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.entity.SilentWoodChestBlockEntity;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SilentWoodChest extends ChestBlock {

    public SilentWoodChest() {
        super(Properties.copy(Blocks.CHEST), TABlockEntityTypes.SILENT_WOOD_CHEST::get);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SilentWoodChestBlockEntity(pos, state);
    }

}