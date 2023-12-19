package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class WickGrass extends DoublePlantBlock {

    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL;

    public WickGrass() {
        super(Properties.copy(Blocks.SUNFLOWER).lightLevel((state) -> state.getValue(LEVEL)));
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 15));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, LEVEL);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(TABlocks.AURORIAN_GRASS_BLOCK.get());
    }

}