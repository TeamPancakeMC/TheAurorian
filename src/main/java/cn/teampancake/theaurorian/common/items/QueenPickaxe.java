package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.data.tags.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.state.BlockState;

public class QueenPickaxe extends PickaxeItem {
    public QueenPickaxe() {
        super(ModToolTiers.AURORIAN_STEEL, 5, -1.2f, new Properties().rarity(Rarity.RARE));
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        return super.onBlockStartBreak(itemstack, pos, player);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return super.isCorrectToolForDrops(stack, state) || state.is(ModBlockTags.DUNGEON_BRICKS);
    }
}
