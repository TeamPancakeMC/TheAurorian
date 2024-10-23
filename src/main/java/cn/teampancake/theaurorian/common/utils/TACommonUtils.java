package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.api.ITAItem;
import cn.teampancake.theaurorian.common.items.TAItemProperties;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TACommonUtils {

    public static Stream<Block> getKnownBlockStream() {
        return TABlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get);
    }

    public static Stream<Item> getKnownItemStream() {
        return TAItems.ITEMS.getEntries().stream().map(DeferredHolder::get);
    }

    public static Iterable<Block> getKnownBlocks() {
        return getKnownBlockStream().collect(Collectors.toList());
    }

    public static Iterable<Item> getKnownItems() {
        return getKnownItemStream().collect(Collectors.toList());
    }

    public static TAItemProperties getItemProperties(Item item) {
        return item instanceof ITAItem taItem && taItem.TA$properties() instanceof TAItemProperties properties ? properties : new TAItemProperties();
    }

}