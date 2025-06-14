package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.TAToolTiers;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class QueensChipper extends PickaxeItem {

    public QueensChipper() {
        super(TAToolTiers.AURORIAN_STEEL, new Item.Properties().rarity(Rarity.RARE)
                .attributes(createAttributes(TAToolTiers.AURORIAN_STEEL, (5), (-1.2f)))
                .component(TADataComponents.ITEM_TAGS, List.of(ItemTags.PICKAXES, TAItemTags.IS_EPIC)));
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return super.isCorrectToolForDrops(stack, state) || state.is(TABlockTags.DUNGEON_BLOCKS) || state.is(TABlockTags.MOON_TEMPLE_BLOCKS);
    }

}