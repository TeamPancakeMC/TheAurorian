package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class TAItemRegUtils {

    public static DeferredHolder<Item, Item> normal(String name, Supplier<Item.Properties> properties) {
        return TAItems.ITEMS.register(name, () -> new Item(properties.get().component(TADataComponents.SIMPLE_MODEL, Unit.INSTANCE)));
    }

    public static DeferredHolder<Item, Item> food(String name, Supplier<Item.Properties> properties, int nutrition, float saturation) {
        return TAItems.ITEMS.register(name, () -> new Item(properties.get().component(TADataComponents.SIMPLE_MODEL, Unit.INSTANCE)
                .food(new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation).build())));
    }

    public static DeferredHolder<Item, Item> alias(String name, DeferredHolder<Block, Block> block, Supplier<Item.Properties> properties) {
        return TAItems.ITEMS.register(name, () -> new ItemNameBlockItem(block.get(), properties.get()));
    }

    public static DeferredHolder<Item, Item> spawnEgg(String name, Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor) {
        return TAItems.ITEMS.register(name + "_spawn_egg", () -> new DeferredSpawnEggItem(type, backgroundColor, highlightColor, new Item.Properties()));
    }

}