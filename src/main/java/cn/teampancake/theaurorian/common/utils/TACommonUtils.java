package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TACommonUtils {

    public static Stream<Block> getKnownBlockStream() {
        return TheAurorian.REGISTRATE.getAll(Registries.BLOCK).stream().map(Supplier::get);
    }

    public static Stream<Item> getKnownItemStream() {
        return TheAurorian.REGISTRATE.getAll(Registries.ITEM).stream().map(Supplier::get);
    }

    public static Iterable<Block> getKnownBlocks() {
        return getKnownBlockStream().collect(Collectors.toList());
    }

    public static Iterable<Item> getKnownItems() {
        return getKnownItemStream().collect(Collectors.toList());
    }

    public static boolean isAurorianDimension(Level level) {
        return level.dimension().location().getNamespace().equals(TheAurorian.MOD_ID);
    }

}