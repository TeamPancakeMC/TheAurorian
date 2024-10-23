package cn.teampancake.theaurorian.common.blocks.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class VerticalStairBlockWithBase extends Block implements SimpleWaterloggedBlock, IHasBaseBlock {

    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape[] AABB = {
            Shapes.or(Shapes.create(0.0D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D),
                    Shapes.create(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 1.0D)),
            Shapes.or(Shapes.create(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 1.0D),
                    Shapes.create(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D)),
            Shapes.or(Shapes.create(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D),
                    Shapes.create(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)),
            Shapes.or(Shapes.create(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
                    Shapes.create(0.0D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D))};
    private final Block base;

    public VerticalStairBlockWithBase(Block base, Properties properties) {
        super(properties);
        this.base = base;
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABB[state.getValue(FACING).get2DDataValue()];
    }

    @Nullable @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, Direction.fromYRot(context.getRotation() - 45.0F))
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return state;
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public Block getBase() {
        return this.base;
    }

}