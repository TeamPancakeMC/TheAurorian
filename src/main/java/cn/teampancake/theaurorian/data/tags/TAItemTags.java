package cn.teampancake.theaurorian.data.tags;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class TAItemTags {

    public static final TagKey<Item> DUNGEON_KEY = create("dungeon_key");
    public static final TagKey<Item> SPECTRAL_ARMOR = create("spectral_armor");
    public static final TagKey<Item> DUNGEON_BRICKS = create("dungeon_bricks");
    public static final TagKey<Item> SILENT_TREE_LOGS = create("silent_tree_logs");
    public static final TagKey<Item> WEEPING_WILLOW_LOGS = create("weeping_willow_logs");
    public static final TagKey<Item> AURORIAN_SLIME_BOOTS = create("aurorian_slime_boots");
    public static final TagKey<Item> HAS_CUSTOM_ARMOR_MODEL = create("has_custom_armor_model");
    public static final TagKey<Item> AUROTIAN_ANIMAL_UNSPAWNABLE_ON = create("aurotian_animal_unspawnable_on");

    private static TagKey<Item> create(String name) {
        return ItemTags.create(AurorianMod.prefix(name));
    }

}