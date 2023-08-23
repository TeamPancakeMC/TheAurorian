package cn.teampancake.theaurorian.common.items;

import net.minecraft.world.item.Item;

public class AbstractTooltipsItem extends Item implements ITooltipsItem {

    private final boolean hasTooltips;

    public AbstractTooltipsItem(Properties pProperties, boolean hasTooltips) {
        super(pProperties);
        this.hasTooltips = hasTooltips;
    }

    @Override
    public boolean isHasTooltips() {
        return this.hasTooltips;
    }

}