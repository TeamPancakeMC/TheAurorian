package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.TAItemProperties;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.state.BlockState;

public class SilentWoodPickaxe extends PickaxeItem {

    public SilentWoodPickaxe() {
        super(TAToolTiers.SILENT_WOOD, TAItemProperties.get().attributes(createAttributes(TAToolTiers.SILENT_WOOD, (2), (-1.2F))).addItemTag(ItemTags.PICKAXES, TAItemTags.IS_EPIC));
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
            return super.isCorrectToolForDrops(stack, state);
        }
    }

}