package cn.teampancake.theaurorian.common.items.developer;

import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;

public class StarOceanCrossbow extends CrossbowItem implements IDeveloperItem, ITooltipsItem {

    public StarOceanCrossbow() {
        super((new Item.Properties()).stacksTo(1).durability(500));
    }

}