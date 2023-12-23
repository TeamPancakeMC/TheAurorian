package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.grower.SilentTreeGrower;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SilentTreeSapling extends SaplingBlock {

    public SilentTreeSapling(Properties properties) {
        super(new SilentTreeGrower(), properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(TABlocks.AURORIAN_GRASS_BLOCK.get()) || state.is(TABlocks.AURORIAN_DIRT.get());
    }

}