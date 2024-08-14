package cn.teampancake.theaurorian.common.data.datagen.tags;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public class TAStructureTags {

    public static final TagKey<Structure> RUNESTONE_DUNGEON = create("runestone_dungeon");
    public static final TagKey<Structure> DARKSTONE_DUNGEON = create("darkstone_dungeon");
    public static final TagKey<Structure> MOON_TEMPLE = create("moon_temple");

    private static TagKey<Structure> create(String name) {
        return TagKey.create(Registries.STRUCTURE, TheAurorian.prefix(name));
    }

}