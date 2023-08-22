package cn.teampancake.theaurorian.common.items;

import net.minecraft.world.item.Item;

public interface ITooltipsItem{
    default boolean isHasTooltips() {
        return true;
    }
}