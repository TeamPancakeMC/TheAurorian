package cn.teampancake.theaurorian.common.blocks.base;

import cn.teampancake.theaurorian.common.blocks.state.TAVerticalSlabType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.StringRepresentable;
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
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

@SuppressWarnings("deprecation")
public class VerticalSlabBlockWithBase extends Block implements SimpleWaterloggedBlock, IHasBaseBlock, ISimpleBlockItem {

    public static final EnumProperty<TAVerticalSlabType> SHAPE = EnumProperty.create("shape", TAVerticalSlabType.class);
    public static final EnumProperty<TAConnection> CONNECTION = EnumProperty.create("connection", TAConnection.class);
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
                .setValue(SHAPE, TAVerticalSlabType.NORTH)
                .setValue(CONNECTION, TAConnection.NONE)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SHAPE, CONNECTION, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        TAVerticalSlabType shape = state.getValue(SHAPE);
        TAConnection connection = state.getValue(CONNECTION);
        return shape == TAVerticalSlabType.FULL || connection == TAConnection.NONE ? AABB[state.getValue(SHAPE).ordinal()]
                : CONNECTED_AABB[(connection == TAConnection.LEFT ? shape.getDirection() : shape.getDirection().getClockWise()).get2DDataValue()];
    }

    @Nullable @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState clickedState = context.getLevel().getBlockState(context.getClickedPos());
        if (clickedState.is(this) && clickedState.getValue(SHAPE) != TAVerticalSlabType.FULL) {
            return this.defaultBlockState().setValue(SHAPE, TAVerticalSlabType.FULL).setValue(WATERLOGGED, Boolean.FALSE);
        } else {
            Direction facing = context.getHorizontalDirection();
            FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
            TAConnection connection = getProperConnectionType(context.getLevel(), context.getClickedPos(), facing);
            return this.defaultBlockState().setValue(SHAPE, TAVerticalSlabType.fromDirection(facing)).setValue(CONNECTION, connection)
                    .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
        }
    }

    private static TAConnection getProperConnectionType(BlockGetter level, BlockPos pos, Direction facing){
        BlockState backState = level.getBlockState(pos.relative(facing));
        if (backState.getBlock() instanceof VerticalSlabBlockWithBase && backState.getValue(SHAPE) != TAVerticalSlabType.FULL) {
            Direction direction = backState.getValue(SHAPE).getDirection();
            TAConnection connection = backState.getValue(CONNECTION);
            BlockState leftState = level.getBlockState(pos.relative(facing.getCounterClockWise()));
            BlockState rightState = level.getBlockState(pos.relative(facing.getClockWise()));
            if ((!(leftState.getBlock() instanceof VerticalSlabBlockWithBase) || !facing.equals(leftState.getValue(SHAPE).getDirection()))
                    && direction.equals(facing.getClockWise()) && (connection == TAConnection.NONE || connection == TAConnection.RIGHT)) {
                return TAConnection.RIGHT;
            }

            if ((!(rightState.getBlock() instanceof VerticalSlabBlockWithBase) || !facing.equals(rightState.getValue(SHAPE).getDirection()))
                    && direction.equals(facing.getCounterClockWise()) && (connection == TAConnection.NONE || connection == TAConnection.LEFT)) {
                return TAConnection.LEFT;
            }
        }

        return TAConnection.NONE;
    }

    @Override
    public FluidState getFluidState(BlockState state){
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        return state.getValue(SHAPE) != TAVerticalSlabType.FULL && SimpleWaterloggedBlock.super.placeLiquid(level, pos, state, fluidState);
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter level, BlockPos pos, BlockState state, Fluid fluid){
        return state.getValue(SHAPE) != TAVerticalSlabType.FULL && fluid == Fluids.WATER;
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        TAVerticalSlabType type = state.getValue(SHAPE);
        boolean flag = type.getDirection().getOpposite() == useContext.getClickedFace();
        return type != TAVerticalSlabType.FULL && useContext.getItemInHand().is(this.asItem()) && flag;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        TAVerticalSlabType shape = state.getValue(SHAPE);
        TAConnection connection = getProperConnectionType(level, currentPos, shape.getDirection());
        return shape == TAVerticalSlabType.FULL ? state : state.setValue(CONNECTION, connection);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type){
        return type == PathComputationType.WATER && level.getFluidState(pos).is(FluidTags.WATER);
    }

    public Block getBase() {
        return this.base;
    }

    public enum TAConnection implements StringRepresentable {

        LEFT, RIGHT, NONE;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }

    }

}