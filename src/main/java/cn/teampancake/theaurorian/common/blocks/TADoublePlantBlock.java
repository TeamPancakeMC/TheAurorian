package cn.teampancake.theaurorian.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class TADoublePlantBlock extends DoublePlantBlock {

    private final Supplier<Block> block;

    public TADoublePlantBlock(Properties properties, Supplier<Block> block) {
        super(properties);
        this.block = block;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getBlock() == this.block.get();
    }

}