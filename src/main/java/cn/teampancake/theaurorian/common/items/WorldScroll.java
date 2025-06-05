package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;

import java.util.List;

public class WorldScroll extends Item {

    public WorldScroll() {
        super(new Item.Properties()
                .component(TADataComponents.ITEM_TAGS, List.of(TAItemTags.IS_RARE))
                .component(TADataComponents.SIMPLE_MODEL, Unit.INSTANCE));
    }

}