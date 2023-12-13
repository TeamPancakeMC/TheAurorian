package cn.teampancake.theaurorian.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;


public class SilentWoodPickaxe extends PickaxeItem {
    public SilentWoodPickaxe() {
        super(TAToolTiers.SILENT_WOOD, 2, -1.2f, new Properties());
    }


    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        Level level = player.level();
        if (level.isClientSide) return false;
        //TODO: 随着耐久度的减少，谧木镐会逐渐提高挖掘等级
        return super.onBlockStartBreak(itemstack, pos, player);
    }
}
