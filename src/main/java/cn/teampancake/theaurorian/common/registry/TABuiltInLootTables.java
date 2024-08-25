package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class TABuiltInLootTables {

    public static final ResourceKey<LootTable> AURORIAN_VILLAGE_HOUSE = createKey("chests/aurorian_village_house");
    public static final ResourceKey<LootTable> RUNESTONE_DUNGEON = createKey("chests/runestone_dungeon");

    private static ResourceKey<LootTable> createKey(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, TheAurorian.prefix(name));
    }

}