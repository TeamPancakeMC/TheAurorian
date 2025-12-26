package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.entity.CrystallineSwordPedestalBlockEntity;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class CrystallineSwordPedestal extends BaseEntityBlockWithState {

    public CrystallineSwordPedestal(Properties properties) {
        super(properties.destroyTime(-1.0F).explosionResistance(1200.0F).noLootTable().noOcclusion());
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(CrystallineSwordPedestal::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CrystallineSwordPedestalBlockEntity(pos, state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof CrystallineSwordPedestalBlockEntity blockEntity) {
            ItemStack itemInHand = player.getItemInHand(hand);
            NonNullList<ItemStack> items = blockEntity.getItems();
            if (blockEntity.sealing || blockEntity.unsealing) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }

            if (blockEntity.isSeal()) {
                blockEntity.triggerAnim("Unseal", "unseal_animation");
                blockEntity.unsealTick = 10;
                blockEntity.unsealing = true;
            } else {
                ItemStack first = items.getFirst();
                Consumer<CrystallineSwordPedestalBlockEntity> consumer = entity -> {
                    entity.triggerAnim("Seal", "seal_animation");
                    entity.sealTick = 12;
                    entity.sealing = true;
                };

                if (first.isEmpty()) {
                    if (itemInHand.is(TAItems.CRYSTALLINE_SWORD)) {
                        items.set(0, itemInHand);
                        itemInHand.consume(1, player);
                        consumer.accept(blockEntity);
                    }
                } else {
                    player.addItem(first);
                    items.clear();
                    consumer.accept(blockEntity);
                }

                blockEntity.markUpdated();
            }

            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.CONSUME;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof CrystallineSwordPedestalBlockEntity blockEntity) {
                Containers.dropContents(level, pos, blockEntity.getItems());
            }

            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, TABlockEntityTypes.CRYSTALLINE_SWORD_PEDESTAL.get(), CrystallineSwordPedestalBlockEntity::serverTick);
    }

}