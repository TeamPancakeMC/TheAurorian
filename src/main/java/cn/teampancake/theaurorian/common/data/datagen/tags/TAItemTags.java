package cn.teampancake.theaurorian.common.data.datagen.tags;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class TAItemTags {

    public static final TagKey<Item> COOKED_MEAT = create("cooked_meat");
    public static final TagKey<Item> VERTICAL_STAIRS = create("vertical_stairs");
    public static final TagKey<Item> VERTICAL_SLABS = create("vertical_slabs");
    public static final TagKey<Item> SILENT_TREE_LOGS = create("silent_tree_logs");
    public static final TagKey<Item> WEEPING_WILLOW_LOGS = create("weeping_willow_logs");
    public static final TagKey<Item> CURTAIN_TREE_LOGS = create("curtain_tree_logs");
    public static final TagKey<Item> CURSED_FROST_TREE_LOGS = create("cursed_frost_tree_logs");
    public static final TagKey<Item> AURORIAN_PLANKS = create("aurorian_planks");
    public static final TagKey<Item> AURORIAN_GRASS_BLOCK = create("aurorian_grass_block");

    private static TagKey<Item> create(String name) {
        return ItemTags.create(TheAurorian.prefix(name));
    }

}