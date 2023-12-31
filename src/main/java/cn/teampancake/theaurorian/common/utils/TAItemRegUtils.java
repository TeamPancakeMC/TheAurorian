package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.common.items.TASpecialItem;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class TAItemRegUtils {

    public static RegistryObject<Item> normal(String name, boolean hasTooltips) {
        return TAItems.ITEMS.register(name, () -> new TASpecialItem(new Item.Properties(), hasTooltips));
    }

    public static RegistryObject<Item> food(String name, int nutrition, float saturation, boolean hasTooltips) {
        return TAItems.ITEMS.register(name, () -> new TASpecialItem(new Item.Properties().food(
                new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturation).build()), hasTooltips));
    }

    public static RegistryObject<Item> alias(String name, RegistryObject<Block> block, Item.Properties properties) {
        return TAItems.ITEMS.register(name, () -> new ItemNameBlockItem(block.get(), properties));
    }

    public static RegistryObject<Item> spawnEgg(String name, Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor) {
        return TAItems.ITEMS.register(name + "_spawn_egg", () -> new ForgeSpawnEggItem(type, backgroundColor, highlightColor, new Item.Properties()));
    }

}
