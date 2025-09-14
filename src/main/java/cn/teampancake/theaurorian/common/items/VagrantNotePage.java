package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class VagrantNotePage extends Item {

    public VagrantNotePage(int chapter) {
        super(new Properties().rarity(Rarity.RARE).stacksTo(1)
                .component(TADataComponents.NOTE_CHAPTER, chapter)
                .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE));
    }

}