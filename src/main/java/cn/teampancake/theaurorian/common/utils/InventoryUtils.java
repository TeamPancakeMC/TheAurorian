package cn.teampancake.theaurorian.common.utils;

import com.google.common.collect.Lists;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InventoryUtils {
    public static List<ItemStack> getInventoryItems(Inventory inventory, Function<ItemStack,Boolean> function) {
        ArrayList<ItemStack> stacks = Lists.newArrayList();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (function.apply(stack)) {
                stacks.add(stack);
            }
        }
        return stacks;
    }
}
