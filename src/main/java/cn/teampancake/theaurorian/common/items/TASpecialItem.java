package cn.teampancake.theaurorian.common.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TASpecialItem extends Item implements ITooltipsItem {

    private final boolean hasTooltips;
    private final boolean hasFoil;

    public TASpecialItem(Properties properties, boolean hasTooltips, boolean hasFoil) {
        super(properties);
        this.hasTooltips = hasTooltips;
        this.hasFoil = hasFoil;
    }

    public TASpecialItem(Properties properties, boolean hasTooltips) {
        this(properties, hasTooltips, false);
    }

    @Override
    public boolean isHasTooltips() {
        return this.hasTooltips;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return this.hasFoil;
    }

}