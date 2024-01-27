package cn.teampancake.theaurorian.common.data.datagen.tags;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class TAItemTags {

    public static final TagKey<Item> IS_UNCOMMON = create("is_uncommon");
    public static final TagKey<Item> IS_RARE = create("is_rare");
    public static final TagKey<Item> IS_EPIC = create("is_epic");
    public static final TagKey<Item> IS_LEGENDARY = create("is_legendary");
    public static final TagKey<Item> IS_MYTHICAL = create("is_mythical");
    public static final TagKey<Item> RUNESTONE = create("runestone");
    public static final TagKey<Item> DUNGEON_KEY = create("dungeon_key");
    public static final TagKey<Item> SPECTRAL_ARMOR = create("spectral_armor");
    public static final TagKey<Item> VERTICAL_STAIRS = create("vertical_stairs");
    public static final TagKey<Item> VERTICAL_SLABS = create("vertical_slabs");
    public static final TagKey<Item> DUNGEON_BRICKS = create("dungeon_bricks");
    public static final TagKey<Item> RUNE_STONE_BLOCK = create("rune_stone_block");
    public static final TagKey<Item> SILENT_TREE_LOGS = create("silent_tree_logs");
    public static final TagKey<Item> WEEPING_WILLOW_LOGS = create("weeping_willow_logs");
    public static final TagKey<Item> CURTAIN_TREE_LOGS = create("curtain_tree_logs");
    public static final TagKey<Item> AURORIAN_GRASS_BLOCK = create("aurorian_grass_block");
    public static final TagKey<Item> HAS_CUSTOM_TOOLTIPS = create("has_custom_tooltips");
    public static final TagKey<Item> AURORIAN_CARVER_REPLACEABLES = create("aurorian_carver_replaceables");
    public static final TagKey<Item> AUROTIAN_ANIMAL_UNSPAWNABLE_ON = create("aurotian_animal_unspawnable_on");

    private static TagKey<Item> create(String name) {
        return ItemTags.create(AurorianMod.prefix(name));
    }

}