package cn.teampancake.theaurorian.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class FilthyIce extends HalfTransparentBlock {

    public FilthyIce() {
        super(Properties.copy(Blocks.ICE));
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(level, player, pos, state, te, stack);
        if (stack.getEnchantmentLevel(Enchantments.SILK_TOUCH) == 0) {
            BlockState blockstate = level.getBlockState(pos.below());
            if (blockstate.blocksMotion() || blockstate.liquid()) {
                level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
            }
        }
    }

}