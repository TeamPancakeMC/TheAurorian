package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
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
                .attributes(createAttributes(TAToolTiers.AURORIAN_STEEL, 5, -1.2F))
                .component(TADataComponents.ITEM_TAGS, List.of(ItemTags.PICKAXES))
                .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        boolean flag = state.is(TABlockTags.DUNGEON_BLOCKS);
        float destroySpeed = super.getDestroySpeed(stack, state);
        return flag ? destroySpeed * 16.0F : destroySpeed;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return super.isCorrectToolForDrops(stack, state) || state.is(TABlockTags.DUNGEON_BLOCKS) || state.is(TABlockTags.MOON_TEMPLE_BLOCKS);
    }

}