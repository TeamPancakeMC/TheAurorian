package cn.teampancake.theaurorian.common.items.developer;

import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class WhiteChocolate extends Item implements IDeveloperItem, ITooltipsItem {

    public WhiteChocolate() {
        super(new Properties().food(new FoodProperties.Builder().build()));
    }

}