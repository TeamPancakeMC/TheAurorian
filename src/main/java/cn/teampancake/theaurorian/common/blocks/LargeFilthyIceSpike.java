package cn.teampancake.theaurorian.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LargeFilthyIceSpike extends FilthyIceSpike {

    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    private static final VoxelShape LOWER_ICE_SPIKE_UP = box(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D);
    private static final VoxelShape LOWER_ICE_SPIKE_DOWN = box(2.0D, 1.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape UPPER_ICE_SPIKE_UP = box(5.0D, 0.0D, 5.0D, 10.0D, 15.0D, 10.0D);
    private static final VoxelShape UPPER_ICE_SPIKE_DOWN = box(5.0D, 1.0D, 5.0D, 10.0D, 16.0D, 10.0D);

    public LargeFilthyIceSpike() {
        super(Size.LARGE);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(TIP_DIRECTION, Direction.UP)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Nullable @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        BlockPos clickedPos = context.getClickedPos();
        Level level = context.getLevel();
        int y = clickedPos.getY();
        int max = level.getMaxBuildHeight();
        int min = level.getMinBuildHeight();
        if (clickedFace == Direction.UP && y < max - 1 && level.getBlockState(clickedPos.above()).canBeReplaced(context)) {
            return this.defaultBlockState().setValue(TIP_DIRECTION, Direction.UP).setValue(HALF, DoubleBlockHalf.LOWER);
        } else if (clickedFace == Direction.DOWN && y > min + 1 && level.getBlockState(clickedPos.below()).canBeReplaced(context)) {
            return this.defaultBlockState().setValue(TIP_DIRECTION, Direction.DOWN).setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, TIP_DIRECTION, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        boolean flag = state.getValue(TIP_DIRECTION) == Direction.UP;
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return flag ? LOWER_ICE_SPIKE_UP : LOWER_ICE_SPIKE_DOWN;
        } else {
            return flag ? UPPER_ICE_SPIKE_UP : UPPER_ICE_SPIKE_DOWN;
        }
    }

}