package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import cn.teampancake.theaurorian.compat.registrate.TARegistrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.tags.TagKey;
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

    public static<T extends Item> ItemBuilder<T, TARegistrate> registerBuilder(String name, NonNullFunction<Item.Properties, T> factory) {
        return TheAurorian.REGISTRATE.item(name, factory);
    }

    public static<T extends Item> ItemBuilder<T, TARegistrate> toolBuilder(String name, NonNullFunction<Item.Properties, T> factory) {
        return registerBuilder(name, factory).model((ctx, prov) -> prov.handheld(ctx::getEntry));
    }

    public static<T extends Item> ItemEntry<T> register(String name, NonNullFunction<Item.Properties, T> factory) {
        return registerBuilder(name, factory).defaultModel().register();
    }

    public static ItemEntry<Item> simple(String name, Supplier<Item.Properties> properties) {
        return registerBuilder(name, p -> new Item(properties.get())).defaultModel().register();
    }

    @SafeVarargs
    public static ItemEntry<Item> food(String name, Supplier<Item.Properties> properties, int nutrition, float saturation, TagKey<Item>... values) {
        return registerBuilder(name, p -> new Item(properties.get().component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)
                .food(new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation).build()))).defaultModel().tag(values).register();
    }

    public static<T extends Block> ItemEntry<ItemNameBlockItem> alias(String name, DeferredHolder<Block, T> block, Supplier<Item.Properties> properties) {
        return registerBuilder(name, p -> new ItemNameBlockItem(block.get(), properties.get())).defaultModel().register();
    }

    public static<T extends Mob> ItemEntry<DeferredSpawnEggItem> spawnEgg(DeferredHolder<EntityType<?>, EntityType<T>> type, int backgroundColor, int highlightColor) {
        NonNullFunction<Item.Properties, DeferredSpawnEggItem> factory = p -> new DeferredSpawnEggItem(type, backgroundColor, highlightColor, p);
        return registerBuilder(type.getId().getPath() + "_spawn_egg", factory).model((ctx, prov) -> prov.spawnEggItem(ctx.get())).register();
    }

}