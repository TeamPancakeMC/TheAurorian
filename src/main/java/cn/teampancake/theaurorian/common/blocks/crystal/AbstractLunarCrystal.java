package cn.teampancake.theaurorian.common.blocks.crystal;

import cn.teampancake.theaurorian.common.blocks.entity.crystal.AbstractLunarCrystalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("NullableProblems")
public abstract class AbstractLunarCrystal extends BaseEntityBlock {

    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

    public AbstractLunarCrystal(Properties properties) {
        super(properties.destroyTime(50.0F).explosionResistance(1200.0F).noOcclusion().noLootTable());
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(ACTIVATED, false));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        double maxY = state.getValue(HALF) == DoubleBlockHalf.UPPER ? 10.0D : 16.0D;
        return box(0.0D, 0.0D, 0.0D, 16.0D, maxY, 16.0D);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(ACTIVATED) ? 15 : 0;
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative()) {
            DoublePlantBlock.preventDropFromBottomPart(level, pos, state, player);
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        DoubleBlockHalf half = state.getValue(HALF);
        if (facing.getAxis() != Direction.Axis.Y
                || half == DoubleBlockHalf.LOWER != (facing == Direction.UP)
                || facingState.is(this) && facingState.getValue(HALF) != half) {
            return half == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(level, currentPos)
                    ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos clickedPos = context.getClickedPos();
        Level level = context.getLevel();
        if (clickedPos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(clickedPos.above()).canBeReplaced(context)) {
            return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        level.setBlockAndUpdate(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockEntity blockEntity = level.getBlockEntity(state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos : pos.below());
        if (!level.isClientSide && blockEntity instanceof AbstractLunarCrystalBlockEntity entity && !entity.activating && !entity.activated) {
            entity.triggerAnim("active_controller", "active_animation");
            entity.activating = true;
            entity.activeTime = 40;
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos.below());
        boolean flag = blockState.isFaceSturdy(level, pos.below(), Direction.UP);
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? flag : blockState.is(this);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, ACTIVATED);
    }

    protected boolean shouldEmptyTicker(Level level, BlockState state) {
        return level.isClientSide || state.getValue(HALF) == DoubleBlockHalf.UPPER || !level.tickRateManager().runsNormally();
    }

}