package cn.teampancake.theaurorian.data.tags;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {

    public static final TagKey<Item> DUNGEON_KEY = create("dungeon_key");
    public static final TagKey<Item> SPECTRAL_ARMOR = create("spectral_armor");
    public static final TagKey<Item> AURORIAN_SLIME_BOOTS = create("aurorian_slime_boots");
    public static final TagKey<Item> AUROTIAN_ANIMAL_UNSPAWNABLE_ON = create("aurotian_animal_unspawnable_on");

    private static TagKey<Item> create(String name) {
        return ItemTags.create(AurorianMod.prefix(name));
    }

}