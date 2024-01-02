package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;

public class TAWaterGrassBlock extends SeagrassBlock {

    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL;

    public TAWaterGrassBlock(Properties properties) {
        super(properties.lightLevel(state -> state.getValue(LEVEL)));
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 15));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        BlockState blockState = super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        if (!blockState.isAir()) {
            level.scheduleTick(currentPos, TAFluids.MOON_WATER_STILL.get(), TAFluids.MOON_WATER_STILL.get().getTickDelay(level));
        }

        return blockState;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return TAFluids.MOON_WATER_STILL.get().getSource(false);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockState blockState = TABlocks.TALL_AURORIAN_WATER_GRASS.get().defaultBlockState();
        BlockState blockState1 = blockState.setValue(TATallWaterGrassBlock.HALF, DoubleBlockHalf.UPPER);
        BlockPos abovePos = pos.above();
        if (level.getBlockState(abovePos).is(TABlocks.MOON_WATER.get())) {
            level.setBlock(pos, blockState, 2);
            level.setBlock(abovePos, blockState1, 2);
        }
    }

}