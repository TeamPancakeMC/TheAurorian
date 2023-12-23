package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.utils.AurorianUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class DungeonStoneGateKeyhole extends DungeonStoneGate {

    private final Supplier<Item> keyItem;
    private final Supplier<Block> gateBlock;
    private final boolean lockPickable;

    public DungeonStoneGateKeyhole(Supplier<Item> keyItem, Supplier<Block> gateBlock, boolean lockPickable) {
        super(TABlocks.breakWithQueenPickaxe());
        this.keyItem = keyItem;
        this.gateBlock = gateBlock;
        this.lockPickable = lockPickable;
    }

    public DungeonStoneGateKeyhole(Supplier<Item> keyItem, Supplier<Block> gateBlock) {
        this(keyItem, gateBlock, false);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack handStack = player.getItemInHand(hand);
        int amount = player.getAbilities().instabuild ? 0 : 1;
        if (!player.isSteppingCarefully() && handStack.is(this.keyItem.get())) {
            this.unlockRelativeSameBlock(state, level, pos);
            handStack.shrink(amount);
        } else if (!player.isSteppingCarefully() && handStack.is(TAItems.LOCK_PICKS.get()) && this.lockPickable) {
            handStack.hurtAndBreak(amount, player, p -> p.broadcastBreakEvent(hand));
            if (AurorianUtil.randomChanceOf(0.33F)) {
                this.unlockRelativeSameBlock(state, level, pos);
            } else {
                level.playSound(null, player.getOnPos(), SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, 0.5F, 1F);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void unlockRelativeSameBlock(BlockState state, Level level, BlockPos pos) {
        BlockState relativeState = level.getBlockState(pos);
        if (relativeState.getBlock() == this.gateBlock.get() && !this.isDestroyed(relativeState) && !relativeState.getValue(UNLOCKED)) {
            level.setBlockAndUpdate(pos, relativeState.setValue(UNLOCKED, true));
            level.scheduleTick(pos, relativeState.getBlock(), 2 + level.getRandom().nextInt(5));
        }
    }

}