package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.entity.AlchemyTableBlockEntity;
import cn.teampancake.theaurorian.common.blocks.state.properties.AlchemyTablePart;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class AlchemyTable extends HorizontalDirectionalBlock implements EntityBlock {

    public static final EnumProperty<AlchemyTablePart> PART = EnumProperty.create("part", AlchemyTablePart.class);

    public AlchemyTable() {
        super(Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.5F)
                .sound(SoundType.WOOD).ignitedByLava().noOcclusion().pushReaction(PushReaction.DESTROY));
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, AlchemyTablePart.LEFT));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return simpleCodec(p -> new AlchemyTable());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof AlchemyTableBlockEntity blockEntity) {
            AlchemyTablePart part = state.getValue(PART);
            if (part == AlchemyTablePart.LEFT) {
                BlockPos relative = pos.relative(getNeighbourDirection(part, state.getValue(FACING)));
                BlockState relativeState = level.getBlockState(relative);
                boolean isRight = relativeState.getValue(PART) == AlchemyTablePart.RIGHT;
                if (relativeState.is(this) && isRight && level.getBlockEntity(relative) instanceof AlchemyTableBlockEntity blockEntity2) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.openMenu(blockEntity2, extraData -> extraData.writeBlockPos(pos));
                    }
                }
            } else {
                if (player instanceof ServerPlayer serverPlayer) {
                    serverPlayer.openMenu(blockEntity, extraData -> extraData.writeBlockPos(pos));
                }
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof AlchemyTableBlockEntity scrapper) {
                Containers.dropContents(level, pos, scrapper);
                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (facing == getNeighbourDirection(state.getValue(PART), state.getValue(FACING))) {
            return facingState.is(this) && facingState.getValue(PART) != state.getValue(PART) ? state : Blocks.AIR.defaultBlockState();
        } else {
            return state;
        }
    }

    public static Direction getNeighbourDirection(AlchemyTablePart part, Direction direction) {
        return part == AlchemyTablePart.LEFT ? direction.getClockWise() : direction.getCounterClockWise();
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
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

        return super.playerWillDestroy(level, pos, state, player);
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
        return new AlchemyTableBlockEntity(pos, state);
    }

    @Nullable @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide() || state.getValue(PART) == AlchemyTablePart.LEFT ? null: createTickerHelper(blockEntityType, TABlockEntityTypes.ALCHEMY_TABLE.get(), AlchemyTableBlockEntity::serverTick);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? (BlockEntityTicker<A>)ticker : null;
    }

}