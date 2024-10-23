package cn.teampancake.theaurorian.common.blocks.base;

import cn.teampancake.theaurorian.common.blocks.state.properties.VerticalSlabShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import javax.annotation.Nullable;

import java.util.Locale;

public class VerticalSlabBlockWithBase extends Block implements SimpleWaterloggedBlock, IHasBaseBlock {

    public static final EnumProperty<VerticalSlabShape> SHAPE = EnumProperty.create("shape", VerticalSlabShape.class);
    public static final EnumProperty<Connection> CONNECTION = EnumProperty.create("connection", Connection.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape[] AABB = {
            Shapes.create(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D),
            Shapes.create(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
            Shapes.create(0.0D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D),
            Shapes.create(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 1.0D), Shapes.block()};
    public static final VoxelShape[] CONNECTED_AABB = {
            Shapes.create(0.5D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D),
            Shapes.create(0.0D, 0.0D, 0.5D, 0.5D, 1.0D, 1.0D),
            Shapes.create(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 0.5D),
            Shapes.create(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D)};
    private final Block base;

    public VerticalSlabBlockWithBase(Block base, Properties properties) {
        super(properties);
        this.base = base;
        this.registerDefaultState(this.defaultBlockState()
                .setValue(SHAPE, VerticalSlabShape.NORTH)
                .setValue(CONNECTION, Connection.NONE)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SHAPE, CONNECTION, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VerticalSlabShape shape = state.getValue(SHAPE);
        Connection connection = state.getValue(CONNECTION);
        return shape == VerticalSlabShape.FULL || connection == Connection.NONE ? AABB[state.getValue(SHAPE).ordinal()] :
                CONNECTED_AABB[(connection == Connection.LEFT ? shape.getDirection() : shape.getDirection().getClockWise()).get2DDataValue()];
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState clickedState = context.getLevel().getBlockState(context.getClickedPos());
        if (clickedState.is(this) && clickedState.getValue(SHAPE) != VerticalSlabShape.FULL) {
            return this.defaultBlockState().setValue(SHAPE, VerticalSlabShape.FULL).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE);
        } else {
            Direction facing = context.getHorizontalDirection();
            FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
            return this.defaultBlockState().setValue(SHAPE, VerticalSlabShape.fromDirection(facing))
                    .setValue(CONNECTION, getProperConnectionType(context.getLevel(), context.getClickedPos(), facing))
                    .setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
        }
    }

    private static Connection getProperConnectionType(BlockGetter level, BlockPos pos, Direction facing) {
        BlockState backState = level.getBlockState(pos.relative(facing));
        if (backState.getBlock() instanceof VerticalSlabBlockWithBase && backState.getValue(SHAPE) != VerticalSlabShape.FULL){
            Direction direction = backState.getValue(SHAPE).getDirection();
            Connection connection = backState.getValue(CONNECTION);
            BlockState leftState = level.getBlockState(pos.relative(facing.getCounterClockWise()));
            if ((!(leftState.getBlock() instanceof VerticalSlabBlockWithBase) || !facing.equals(leftState.getValue(SHAPE).getDirection())) &&
                    direction.equals(facing.getClockWise()) && (connection == Connection.NONE || connection == Connection.RIGHT)) {
                return Connection.RIGHT;
            }

            BlockState rightState = level.getBlockState(pos.relative(facing.getClockWise()));
            if ((!(rightState.getBlock() instanceof VerticalSlabBlockWithBase) || !facing.equals(rightState.getValue(SHAPE).getDirection())) &&
                    direction.equals(facing.getCounterClockWise()) && (connection == Connection.NONE || connection == Connection.LEFT)) {
                return Connection.LEFT;
            }
        }

        return Connection.NONE;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(Boolean.FALSE) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        return state.getValue(SHAPE) != VerticalSlabShape.FULL && SimpleWaterloggedBlock.super.placeLiquid(level, pos, state, fluidState);
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        return state.getValue(SHAPE) != VerticalSlabShape.FULL && fluid == Fluids.WATER;
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        VerticalSlabShape shape = state.getValue(SHAPE);
        return shape != VerticalSlabShape.FULL && useContext.getItemInHand().is(this.asItem()) &&
                shape.getDirection().getOpposite() == useContext.getClickedFace();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        VerticalSlabShape shape = state.getValue(SHAPE);
        return shape == VerticalSlabShape.FULL ? state : state.setValue(CONNECTION, getProperConnectionType(level, currentPos, shape.getDirection()));
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return type == PathComputationType.WATER && state.getFluidState().is(FluidTags.WATER);
    }

    @Override
    public Block getBase() {
        return this.base;
    }

    public enum Connection implements StringRepresentable {

        LEFT, RIGHT, NONE;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }

    }

}