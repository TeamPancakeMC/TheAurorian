package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.stream.Collectors;

public class TACommonUtils {

    public static Iterable<Block> getKnownBlocks() {
        return TABlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toList());
    }

    public static Iterable<Item> getKnownItems() {
        return TAItems.ITEMS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toList());
    }

}