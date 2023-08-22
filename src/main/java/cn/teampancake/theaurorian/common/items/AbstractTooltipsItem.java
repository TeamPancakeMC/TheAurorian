package cn.teampancake.theaurorian.common.items;

import net.minecraft.world.item.Item;

public class AbstractTooltipsItem extends Item {

    private final boolean hasTooltips;

    public AbstractTooltipsItem(Properties properties, boolean hasTooltips) {
        super(properties);
        this.hasTooltips = hasTooltips;
    }

    public boolean isHasTooltips() {
        return this.hasTooltips;
    }

}