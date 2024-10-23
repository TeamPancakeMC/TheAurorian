package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import net.minecraft.world.item.Item;

public class WorldScroll extends Item {

    public WorldScroll() {
        super(TAItemProperties.get().addItemTag(TAItemTags.IS_RARE).isSimpleModelItem());
    }

}