package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.grower.SilentTreeGrower;
import cn.teampancake.theaurorian.registry.ModBlocks;
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
        return state.is(ModBlocks.AURORIAN_GRASS_BLOCK.get()) || state.is(ModBlocks.AURORIAN_DIRT.get());
    }

}