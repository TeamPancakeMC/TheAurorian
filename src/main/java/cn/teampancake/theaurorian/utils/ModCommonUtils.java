package cn.teampancake.theaurorian.utils;

import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class ModCommonUtils {

    public static Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    public static Iterable<Item> getKnownItems() {
        return ModItems.ITEMS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}