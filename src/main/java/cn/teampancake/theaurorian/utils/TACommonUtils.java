package cn.teampancake.theaurorian.utils;

import cn.teampancake.theaurorian.registry.TABlocks;
import cn.teampancake.theaurorian.registry.TAItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class TACommonUtils {

    public static Iterable<Block> getKnownBlocks() {
        return TABlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    public static Iterable<Item> getKnownItems() {
        return TAItems.ITEMS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}