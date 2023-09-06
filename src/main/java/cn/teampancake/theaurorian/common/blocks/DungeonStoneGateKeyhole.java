package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.registry.ModItems;
import cn.teampancake.theaurorian.utils.AurorianUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class DungeonStoneGateKeyhole extends Block implements ITooltipsItem {

    private final Supplier<Item> keyItem;
    private final boolean lockPickable;

    public DungeonStoneGateKeyhole(Supplier<Item> keyItem, boolean lockPickable) {
        super(ModBlocks.breakWithQueenPickaxe());
        this.keyItem = keyItem;
        this.lockPickable = lockPickable;
    }

    public DungeonStoneGateKeyhole(Supplier<Item> keyItem) {
        this(keyItem, false);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack handStack = player.getItemInHand(hand);
        if (!player.isSteppingCarefully() && handStack.is(this.keyItem.get())) {
            this.breakGateBlock(level, pos, player);
            handStack.shrink(1);
        } else if (!player.isSteppingCarefully() && handStack.is(ModItems.LOCK_PICKS.get()) && this.lockPickable) {
            handStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            if (AurorianUtil.randomChanceOf(0.33F)) {
                this.breakGateBlock(level, pos, player);
            } else {
                level.playSound(null, player.getOnPos(), SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, 0.5F, 1F);
            }
        } else if (level.isClientSide) {
            player.sendSystemMessage(Component.translatable("string.theaurorian.block.dungeon_stone_keyhole"));
        }
        return InteractionResult.SUCCESS;
    }

    private void breakGateBlock(Level level, BlockPos pos, Player player) {
        BlockPos firstPos = pos, secondPos = pos;
        level.playSound(null, player.getOnPos(), SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS);
        for (Direction direction : Direction.values()) {
            BlockState state = level.getBlockState(pos.relative(direction));
            if (direction.getAxis().isHorizontal() && state.getBlock() instanceof DungeonStoneGate) {
                firstPos = pos.relative(direction, 2).above(2);
                secondPos = pos.relative(direction, 2).below(2);
                break;
            }
        }
        if (!level.isClientSide) {
            for (BlockPos blockPos : BlockPos.betweenClosed(firstPos, secondPos)) {
                level.destroyBlock(blockPos, false);
            }
        }
    }

}