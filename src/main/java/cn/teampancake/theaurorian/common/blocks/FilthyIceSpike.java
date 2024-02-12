package cn.teampancake.theaurorian.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class FilthyIceSpike extends Block implements Fallable, SimpleWaterloggedBlock {

    public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SMALL_ICE_SPIKE_UP = box(5.0D, 0.0D, 5.0D, 10.0D, 8.0D, 10.0D);
    private static final VoxelShape MEDIUM_ICE_SPIKE_UP = box(5.0D, 0.0D, 5.0D, 10.0D, 15.0D, 10.0D);
    private static final VoxelShape SMALL_ICE_SPIKE_DOWN = box(5.0D, 8.0D, 5.0D, 10.0D, 16.0D, 10.0D);
    private static final VoxelShape MEDIUM_ICE_SPIKE_DOWN = box(5.0D, 1.0D, 5.0D, 10.0D, 16.0D, 10.0D);
    private final Size size;

    public FilthyIceSpike(Size size) {
        super(Properties.copy(Blocks.ICE).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TIP_DIRECTION, Direction.UP)
                .setValue(WATERLOGGED, Boolean.FALSE));
        this.size = size;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIP_DIRECTION, WATERLOGGED);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(TIP_DIRECTION);
        BlockPos blockPos = pos.relative(direction.getOpposite());
        return level.getBlockState(blockPos).isFaceSturdy(level, blockPos, direction);
    }

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        BlockPos blockPos = hit.getBlockPos();
        boolean mayInteract = projectile.mayInteract(level, blockPos);
        boolean isTrident = projectile instanceof ThrownTrident;
        boolean flag = projectile.getDeltaMovement().length() > 0.6D;
        if (!level.isClientSide && mayInteract && isTrident && flag) {
            level.destroyBlock(blockPos, Boolean.TRUE);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        boolean flag = state.isSolid() && state.getValue(TIP_DIRECTION) == Direction.UP;
        if (flag && !this.canSurvive(state, level, pos)) {
            level.destroyBlock(pos, Boolean.TRUE);
        } else {
            BlockPos.MutableBlockPos mutablePos = pos.mutable();
            FallingBlockEntity fallingBlock = FallingBlockEntity.fall(level, mutablePos, state);
            fallingBlock.setHurtsEntities(Math.max(1 + pos.getY() - mutablePos.getY(), 6), (40));
        }
    }

    @Nullable @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        Direction direction = context.getNearestLookingVerticalDirection().getOpposite();
        Direction tipDirection = calculateTipDirection(level, clickedPos, direction);
        if (tipDirection == null) {
            return null;
        } else {
            boolean flag = level.getFluidState(clickedPos).getType() == Fluids.WATER;
            return this.defaultBlockState().setValue(TIP_DIRECTION, tipDirection).setValue(WATERLOGGED, flag);
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(Boolean.FALSE) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        boolean flag = state.getValue(TIP_DIRECTION) == Direction.UP;
        if (this.size == Size.SMALL) {
            return flag ? SMALL_ICE_SPIKE_UP : SMALL_ICE_SPIKE_DOWN;
        } else if (this.size == Size.MEDIUM) {
            return flag ? MEDIUM_ICE_SPIKE_UP : MEDIUM_ICE_SPIKE_DOWN;
        } else {
            return Shapes.block();
        }
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return false;
    }

    @Override
    public float getMaxHorizontalOffset() {
        return 0.125F;
    }

    @Override
    public DamageSource getFallDamageSource(Entity entity) {
        return entity.damageSources().fallingStalactite(entity);
    }

    @Nullable
    private static Direction calculateTipDirection(LevelReader level, BlockPos pos, Direction dir) {
        Direction direction;
        if (isValidPlacement(level, pos, dir)) {
            direction = dir;
        } else {
            if (!isValidPlacement(level, pos, dir)) {
                return null;
            }

            direction = dir.getOpposite();
        }

        return direction;
    }

    public static boolean isValidPlacement(LevelReader level, BlockPos pos, Direction dir) {
        BlockPos blockPos = pos.relative(dir.getOpposite());
        BlockState blockState = level.getBlockState(blockPos);
        return blockState.isFaceSturdy(level, blockPos, dir);
    }

    public enum Size {

        SMALL, MEDIUM, LARGE

    }

}