package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABiomeTags;
import cn.teampancake.theaurorian.common.level.structure.structures.RuinsAltarStructure;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TAStructures {

    public static final ResourceKey<Structure> RUINS_ALTAR = createKey("ruins_altar");

    //Village
    public static final ResourceKey<Structure> VILLAGE_FOUNTAIN = createKey("village_fountain");
    public static final ResourceKey<Structure> VILLAGE_HOUSE = createKey("village_house");
    public static final ResourceKey<Structure> VILLAGE_STALL = createKey("village_stall");
    public static final ResourceKey<Structure> VILLAGE_STREET_LIGHT = createKey("village_street_light");
    public static final ResourceKey<Structure> VILLAGE_STREET_SIGN = createKey("village_street_sign");
    public static final ResourceKey<Structure> WINDMILL = createKey("windmill");

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, AurorianMod.prefix(name));
    }

    public static void bootstrap(BootstapContext<Structure> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        Structure.StructureSettings ruinsAltarSettings = new Structure.StructureSettings(biomes.getOrThrow(TABiomeTags.HAS_RUINS_ALTAR),
                Arrays.stream(MobCategory.values()).collect(Collectors.toMap(category -> category, category -> new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create()))),
                GenerationStep.Decoration.SURFACE_STRUCTURES,
                TerrainAdjustment.NONE);
        context.register(RUINS_ALTAR, new RuinsAltarStructure(ruinsAltarSettings));
    }

}