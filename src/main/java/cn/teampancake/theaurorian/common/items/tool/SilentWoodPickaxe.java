package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.items.TAToolTiers;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class SilentWoodPickaxe extends PickaxeItem {

    public SilentWoodPickaxe() {
        super(TAToolTiers.SILENT_WOOD, 2, -1.2f, new Properties());
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        int i = stack.getMaxDamage() - stack.getDamageValue();
        if (i <= 14 && state.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return true;
        } else if (i > 14 && i <= 29 && state.is(BlockTags.NEEDS_IRON_TOOL)) {
            return true;
        } else if (i > 29 && i <= 44 && state.is(BlockTags.NEEDS_STONE_TOOL)) {
            return true;
        } else {
            return this.isCorrectToolForDrops(state);
        }
    }

}