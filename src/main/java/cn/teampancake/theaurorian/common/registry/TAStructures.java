package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABiomeTags;
import cn.teampancake.theaurorian.common.level.structure.structures.LargeJigsawStructure;
import cn.teampancake.theaurorian.common.level.structure.structures.RuinsAltarStructure;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TAStructures {

    public static final ResourceKey<Structure> RUINS_ALTAR = createKey("ruins_altar");
    public static final ResourceKey<Structure> AURORIAN_VILLAGE = createKey("aurorian_village");
    public static final ResourceKey<Structure> RUNESTONE_DUNGEON = createKey("runestone_dungeon");

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, TheAurorian.prefix(name));
    }

    public static void bootstrap(BootstrapContext<Structure> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        context.register(RUINS_ALTAR, new RuinsAltarStructure(new Structure.StructureSettings(
                biomes.getOrThrow(TABiomeTags.HAS_RUINS_ALTAR), Arrays.stream(MobCategory.values())
                .collect(Collectors.toMap(category -> category, category -> new StructureSpawnOverride(
                        StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create()))),
                GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));
        context.register(AURORIAN_VILLAGE, new JigsawStructure(new Structure.StructureSettings.Builder(
                HolderSet.direct(biomes.getOrThrow(TABiomes.AURORIAN_PLAINS)))
                .generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                .terrainAdapation(TerrainAdjustment.BEARD_THIN).build(),
                templatePools.getOrThrow(TATemplatePools.AURORIAN_VILLAGE_SET), (Optional.empty()), 6,
                ConstantHeight.of(VerticalAnchor.absolute(-8)), Boolean.FALSE,
                Optional.of(Heightmap.Types.WORLD_SURFACE_WG), (100), List.of(),
                DimensionPadding.ZERO, LiquidSettings.APPLY_WATERLOGGING));
        context.register(RUNESTONE_DUNGEON, new LargeJigsawStructure(new Structure.StructureSettings.Builder(
                HolderSet.direct(biomes.getOrThrow(TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD)))
                .generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                .terrainAdapation(TerrainAdjustment.BEARD_THIN).build(),
                templatePools.getOrThrow(TATemplatePools.RUNESTONE_DUNGEON_START), (Optional.empty()), 7,
                ConstantHeight.of(VerticalAnchor.absolute(0)), Boolean.FALSE,
                Optional.of(Heightmap.Types.WORLD_SURFACE_WG), (348), List.of(),
                DimensionPadding.ZERO, LiquidSettings.APPLY_WATERLOGGING));
    }

}