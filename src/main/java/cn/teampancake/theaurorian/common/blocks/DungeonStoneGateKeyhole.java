package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.entity.DungeonStoneGateBlockEntity;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class DungeonStoneGateKeyhole extends DungeonStoneGate {

    private final Supplier<Item> keyItem;
    private final Supplier<Block> gateBlock;
    private final boolean lockPickable;

    public DungeonStoneGateKeyhole(Supplier<Item> keyItem, Supplier<Block> gateBlock, boolean lockPickable) {
        super(TABlocks.dungeonBlockProperties(TABlockTags.MOON_TEMPLE_BLOCKS));
        this.keyItem = keyItem;
        this.gateBlock = gateBlock;
        this.lockPickable = lockPickable;
    }

    public DungeonStoneGateKeyhole(Supplier<Item> keyItem, Supplier<Block> gateBlock) {
        this(keyItem, gateBlock, false);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        ItemStack handStack = player.getItemInHand(player.getUsedItemHand());
        int amount = player.getAbilities().instabuild ? 0 : 1;
        if (!level.isClientSide() && !player.isSteppingCarefully() && state.hasBlockEntity()) {
            if (level.getBlockEntity(pos) instanceof DungeonStoneGateBlockEntity blockEntity && !blockEntity.isUnlock()) {
                boolean canUnlock = this.lockPickable && level.random.nextFloat() <= 0.33F;
                if (handStack.is(this.keyItem.get()) || (handStack.is(TAItems.LOCK_PICKS.get()) && canUnlock)) {
                    level.setBlockAndUpdate(pos, state.setValue(UNLOCKED, true));
                    blockEntity.setUnlockInterval(level.random.nextInt(20));
                    int preset = level.random.nextInt(40);
                    int interval = blockEntity.getUnlockInterval();
                    blockEntity.setDestroyCountdown(preset < interval ? interval + preset : preset);
                    blockEntity.setGateState(this.gateBlock.get().defaultBlockState());
                    blockEntity.setUnlock(true);
                    if (handStack.isDamageableItem()) {
                        handStack.hurtAndBreak(amount, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
                    } else {
                        handStack.shrink(amount);
                    }
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

}