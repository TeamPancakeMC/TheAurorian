package cn.teampancake.theaurorian.common.data.datagen.tags;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class TABiomeTags {

    public static final TagKey<Biome> IS_AURORIAN = create("is_aurorian");
    public static final TagKey<Biome> HAS_RUINS_ALTAR = create("has_ruins_altar");
    public static final TagKey<Biome> IS_AUARORIAN_FOREST = create("is_aurorian_forest");

    private static TagKey<Biome> create(String name) {
        return TagKey.create(Registries.BIOME, TheAurorian.prefix(name));
    }

}