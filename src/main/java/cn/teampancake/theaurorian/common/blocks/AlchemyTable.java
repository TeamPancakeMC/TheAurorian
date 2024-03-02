package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.state.properties.AlchemyTablePart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class AlchemyTable extends HorizontalDirectionalBlock implements EntityBlock {

    public static final EnumProperty<AlchemyTablePart> PART = EnumProperty.create("part", AlchemyTablePart.class);

    public AlchemyTable() {
        super(Properties.copy(Blocks.CRAFTING_TABLE).noOcclusion().pushReaction(PushReaction.DESTROY));
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, AlchemyTablePart.LEFT));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (facing == getNeighbourDirection(state.getValue(PART), state.getValue(FACING))) {
            return facingState.is(this) && facingState.getValue(PART) != state.getValue(PART) ? state : Blocks.AIR.defaultBlockState();
        } else {
            return state;
        }
    }

    private static Direction getNeighbourDirection(AlchemyTablePart part, Direction direction) {
        return part == AlchemyTablePart.LEFT ? direction.getClockWise() : direction.getCounterClockWise();
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative()) {
            AlchemyTablePart part = state.getValue(PART);
            if (part == AlchemyTablePart.LEFT) {
                BlockPos relative = pos.relative(getNeighbourDirection(part, state.getValue(FACING)));
                BlockState relativeState = level.getBlockState(relative);
                if (relativeState.is(this) && relativeState.getValue(PART) == AlchemyTablePart.RIGHT) {
                    level.setBlock(relative, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, relative, Block.getId(relativeState));
                }
            }
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Nullable @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        WorldBorder border = level.getWorldBorder();
        BlockPos clickedPos = context.getClickedPos();
        Direction direction = context.getHorizontalDirection();
        BlockPos relative1 = clickedPos.relative(direction.getClockWise());
        BlockPos relative2 = clickedPos.relative(direction.getCounterClockWise());
        boolean flag1 = level.getBlockState(relative1).canBeReplaced(context) && border.isWithinBounds(relative1);
        boolean flag2 = level.getBlockState(relative2).canBeReplaced(context) && border.isWithinBounds(relative2);
        return flag1 || flag2 ? this.defaultBlockState().setValue(FACING, direction) : null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide) {
            Direction facing = state.getValue(FACING);
            BlockPos presetPos1 = pos.relative(facing.getClockWise());
            BlockPos presetPos2 = pos.relative(facing.getCounterClockWise());
            BlockState left = state.setValue(PART, AlchemyTablePart.LEFT);
            BlockState right = state.setValue(PART, AlchemyTablePart.RIGHT);
            if (level.getBlockState(presetPos1).canBeReplaced()) {
                level.setBlockAndUpdate(presetPos1, right);
            } else if (level.getBlockState(presetPos2).canBeReplaced()) {
                level.setBlockAndUpdate(presetPos2, left);
                level.setBlockAndUpdate(pos, right);
                pos = presetPos2;
            }

            level.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(level, pos, 3);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return state.getValue(PART) == AlchemyTablePart.LEFT ? RenderShape.MODEL : RenderShape.INVISIBLE;
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

}