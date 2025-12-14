package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.entity.SacrificeTableBlockEntity;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class SacrificeTable extends BaseEntityBlockWithState {

    private static final VoxelShape NORTH_AABB = Block.box(0.0F, 0.0F, 6.0F, 16.0F, 8.0F, 16.0F);
    private static final VoxelShape SOUTH_AABB = Block.box(0.0F, 0.0F, 0.0F, 16.0F, 8.0F, 10.0F);
    private static final VoxelShape EAST_AABB = Block.box(0.0F, 0.0F, 0.0F, 10.0F, 8.0F, 16.0F);
    private static final VoxelShape WEST_AABB = Block.box(6.0F, 0.0F, 0.0F, 16.0F, 8.0F, 16.0F);

    private static final Map<Direction, VoxelShape> VOXEL_SHAPE_MAP = Map.of(
            Direction.NORTH, NORTH_AABB, Direction.SOUTH, SOUTH_AABB,
            Direction.EAST, EAST_AABB, Direction.WEST, WEST_AABB);

    public SacrificeTable(Properties properties) {
        super(properties.mapColor(MapColor.COLOR_GRAY).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(SacrificeTable::new);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof SacrificeTableBlockEntity blockEntity) {
            ItemStack itemInHand = player.getItemInHand(hand);
            if (!level.isClientSide && blockEntity.placeFood(player, itemInHand)) {
                player.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
                return ItemInteractionResult.SUCCESS;
            }

            return ItemInteractionResult.CONSUME;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof SacrificeTableBlockEntity blockEntity) {
                Containers.dropContents(level, pos, blockEntity.getItems());
            }

            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return VOXEL_SHAPE_MAP.get(state.getValue(FACING));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SacrificeTableBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, TABlockEntityTypes.SACRIFICE_TABLE.get(), SacrificeTableBlockEntity::serverTick);
    }

}