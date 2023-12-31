package cn.teampancake.theaurorian.common.level.placement;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.feature.TAConfiguredFeatures;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class TAPlacements {

    public static final ResourceKey<PlacedFeature> PATCH_AURORIAN_GRASS_PLAINS = createKey("patch_aurorian_grass_plains");
    public static final ResourceKey<PlacedFeature> PATCH_AURORIAN_GRASS_LIGHT_PLAINS = createKey("patch_aurorian_grass_light_plains");
    public static final ResourceKey<PlacedFeature> PATCH_AURORIAN_GRASS_FOREST = createKey("patch_aurorian_grass_forest");
    public static final ResourceKey<PlacedFeature> PATCH_AURORIAN_GRASS_LIGHT_FOREST = createKey("patch_aurorian_grass_light_forest");
    public static final ResourceKey<PlacedFeature> PATCH_AURORIAN_FLOWER_PLAINS = createKey("patch_aurorian_flower_plains");
    public static final ResourceKey<PlacedFeature> PATCH_AURORIAN_FLOWER_FOREST = createKey("patch_aurorian_flower_forest");
    public static final ResourceKey<PlacedFeature> PATCH_EQUINOX_FLOWER = createKey("patch_equinox_flower");
    public static final ResourceKey<PlacedFeature> PATCH_LAVENDER = createKey("patch_lavender");
    public static final ResourceKey<PlacedFeature> TREES_AURORIAN_FOREST = createKey("trees_aurorian_forest");
    public static final ResourceKey<PlacedFeature> MEDIUM_AURORIAN_FOREST_RUINS = createKey("medium_aurorian_forest_ruins");
    public static final ResourceKey<PlacedFeature> RANDOM_FALLEN_SILENT_LOG = createKey("random_fallen_silent_log");
    public static final ResourceKey<PlacedFeature> RANDOM_WATER_SURFACE_PLANT = createKey("random_water_surface_plant");
    public static final ResourceKey<PlacedFeature> RANDOM_CRYSTAL_CLUSTER = createKey("random_crystal_cluster");
    public static final ResourceKey<PlacedFeature> RANDOM_WEAK_GRASS = createKey("random_weak_grass");
    public static final ResourceKey<PlacedFeature> RANDOM_RIVERSIDE_PLANT = createKey("random_riverside_plant");
    public static final ResourceKey<PlacedFeature> ORE_AURORIAN_PERIDOTITE = createKey("ore_aurorian_peridotite");
    public static final ResourceKey<PlacedFeature> ORE_AURORIAN_DIRT = createKey("ore_aurorian_dirt");
    public static final ResourceKey<PlacedFeature> ORE_AURORIAN_COAL = createKey("ore_aurorian_coal");
    public static final ResourceKey<PlacedFeature> ORE_MOONSTONE = createKey("ore_moonstone");
    public static final ResourceKey<PlacedFeature> ORE_CERULEAN = createKey("ore_cerulean");
    public static final ResourceKey<PlacedFeature> ORE_GEODE = createKey("ore_geode");
    public static final ResourceKey<PlacedFeature> RANDOM_URNS = createKey("random_urns");
    public static final ResourceKey<PlacedFeature> SILENT_TREE = createKey("silent_tree");
    public static final ResourceKey<PlacedFeature> AURORIAN_FOREST_SPRING = createKey("aurorian_forest_spring");
    public static final ResourceKey<PlacedFeature> AURORIAN_FOREST_REMAINS = createKey("aurorian_forest_remains");
    public static final ResourceKey<PlacedFeature> AURORIAN_FOREST_MEMORY_LOOP = createKey("aurorian_forest_memory_loop");
    public static final ResourceKey<PlacedFeature> AURORIAN_FOREST_RUINED_PORTAL = createKey("aurorian_forest_ruined_portal");
    public static final ResourceKey<PlacedFeature> AURORIAN_FOREST_SHATTERED_WREATH = createKey("aurorian_forest_shattered_wreath");
    public static final ResourceKey<PlacedFeature> AURORIAN_FOREST_SHATTERED_PILLAR = createKey("aurorian_forest_shattered_pillar");
    public static final ResourceKey<PlacedFeature> AURORIAN_FOREST_SHATTERED_FOREST_PILLAR = createKey("aurorian_forest_shattered_forest_pillar");

    private static ResourceKey<PlacedFeature> createKey(String key) {
        return ResourceKey.create(Registries.PLACED_FEATURE, AurorianMod.prefix(key));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeature = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> patchAurorianGrassHolder = configuredFeature.getOrThrow(TAConfiguredFeatures.PATCH_AURORIAN_GRASS);
        Holder<ConfiguredFeature<?, ?>> patchAurorianGrassLightHolder = configuredFeature.getOrThrow(TAConfiguredFeatures.PATCH_AURORIAN_GRASS_LIGHT);
        Holder<ConfiguredFeature<?, ?>> patchAurorianFlowerHolder = configuredFeature.getOrThrow(TAConfiguredFeatures.PATCH_AURORIAN_FLOWER);
        Holder<ConfiguredFeature<?, ?>> patchEquinoxFlowerHolder = configuredFeature.getOrThrow(TAConfiguredFeatures.PATCH_EQUINOX_FLOWER);
        Holder<ConfiguredFeature<?, ?>> patchLavenderHolder = configuredFeature.getOrThrow(TAConfiguredFeatures.PATCH_LAVENDER);
        Holder<ConfiguredFeature<?, ?>> treesAurorianForestHolder = configuredFeature.getOrThrow(TAConfiguredFeatures.TREES_AURORIAN_FOREST);
        Holder<ConfiguredFeature<?, ?>> mediumAurorianForestRuinsHolder = configuredFeature.getOrThrow(TAConfiguredFeatures.MEDIUM_AURORIAN_FOREST_RUINS);
        Holder<ConfiguredFeature<?, ?>> randomFallenSilentLogHolder = configuredFeature.getOrThrow(TAConfiguredFeatures.RANDOM_FALLEN_SILENT_LOG);
        Holder<ConfiguredFeature<?, ?>> randomWaterSurfacePlantHolder = configuredFeature.getOrThrow(TAConfiguredFeatures.RANDOM_WATER_SURFACE_PLANT);
        Holder<ConfiguredFeature<?, ?>> randomCrystalClusterHolder = configuredFeature.getOrThrow(TAConfiguredFeatures.RANDOM_CRYSTAL_CLUSTER);
        Holder<ConfiguredFeature<?, ?>> randomWickGrassHolder = configuredFeature.getOrThrow(TAConfiguredFeatures.RANDOM_WEAK_GRASS);
        Holder<ConfiguredFeature<?, ?>> randomRiversidePlantHolder = configuredFeature.getOrThrow(TAConfiguredFeatures.RANDOM_RIVERSIDE_PLANT);
        Holder<ConfiguredFeature<?, ?>> silentTreeHolder = configuredFeature.getOrThrow(TAConfiguredFeatures.SILENT_TREE);
        Holder<ConfiguredFeature<?, ?>> mediumRuinHolder1 = configuredFeature.getOrThrow(TAConfiguredFeatures.AURORIAN_FOREST_SPRING);
        Holder<ConfiguredFeature<?, ?>> mediumRuinHolder2 = configuredFeature.getOrThrow(TAConfiguredFeatures.AURORIAN_FOREST_REMAINS);
        Holder<ConfiguredFeature<?, ?>> mediumRuinHolder3 = configuredFeature.getOrThrow(TAConfiguredFeatures.AURORIAN_FOREST_MEMORY_LOOP);
        Holder<ConfiguredFeature<?, ?>> mediumRuinHolder4 = configuredFeature.getOrThrow(TAConfiguredFeatures.AURORIAN_FOREST_RUINED_PORTAL);
        Holder<ConfiguredFeature<?, ?>> mediumRuinHolder5 = configuredFeature.getOrThrow(TAConfiguredFeatures.AURORIAN_FOREST_SHATTERED_WREATH);
        Holder<ConfiguredFeature<?, ?>> mediumRuinHolder6 = configuredFeature.getOrThrow(TAConfiguredFeatures.AURORIAN_FOREST_SHATTERED_PILLAR);
        Holder<ConfiguredFeature<?, ?>> mediumRuinHolder7 = configuredFeature.getOrThrow(TAConfiguredFeatures.AURORIAN_FOREST_SHATTERED_FOREST_PILLAR);
        PlacementUtils.register(context, PATCH_AURORIAN_GRASS_PLAINS, patchAurorianGrassHolder, NoiseThresholdCountPlacement.of((-0.8D), (5), (10)),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(context, PATCH_AURORIAN_GRASS_LIGHT_PLAINS, patchAurorianGrassLightHolder, NoiseThresholdCountPlacement.of((-0.8D), (5), (10)),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(context, PATCH_AURORIAN_GRASS_FOREST, patchAurorianGrassHolder, VegetationPlacements.worldSurfaceSquaredWithCount(3));
        PlacementUtils.register(context, PATCH_AURORIAN_GRASS_LIGHT_FOREST, patchAurorianGrassLightHolder, VegetationPlacements.worldSurfaceSquaredWithCount(2));
        PlacementUtils.register(context, PATCH_AURORIAN_FLOWER_FOREST, patchAurorianFlowerHolder, VegetationPlacements.worldSurfaceSquaredWithCount(5));
        PlacementUtils.register(context, PATCH_AURORIAN_FLOWER_PLAINS, patchAurorianFlowerHolder, VegetationPlacements.worldSurfaceSquaredWithCount(3));
        PlacementUtils.register(context, PATCH_EQUINOX_FLOWER, patchEquinoxFlowerHolder, VegetationPlacements.worldSurfaceSquaredWithCount(3));
        PlacementUtils.register(context, PATCH_LAVENDER, patchLavenderHolder, VegetationPlacements.worldSurfaceSquaredWithCount(6));
        PlacementUtils.register(context, TREES_AURORIAN_FOREST, treesAurorianForestHolder, VegetationPlacements.treePlacement(PlacementUtils.countExtra((5), (0.1F), (1))));
        PlacementUtils.register(context, MEDIUM_AURORIAN_FOREST_RUINS, mediumAurorianForestRuinsHolder, ImmutableList.<PlacementModifier>builder()
                .add(RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()).build());
        PlacementUtils.register(context, RANDOM_FALLEN_SILENT_LOG, randomFallenSilentLogHolder, VegetationPlacements.worldSurfaceSquaredWithCount(1));
        PlacementUtils.register(context, RANDOM_WATER_SURFACE_PLANT, randomWaterSurfacePlantHolder, VegetationPlacements.worldSurfaceSquaredWithCount(1));
        PlacementUtils.register(context, RANDOM_CRYSTAL_CLUSTER, randomCrystalClusterHolder, VegetationPlacements.worldSurfaceSquaredWithCount(1));
        PlacementUtils.register(context, RANDOM_WEAK_GRASS, randomWickGrassHolder, VegetationPlacements.worldSurfaceSquaredWithCount(1));
        PlacementUtils.register(context, RANDOM_RIVERSIDE_PLANT, randomRiversidePlantHolder, VegetationPlacements.worldSurfaceSquaredWithCount(1));
        PlacementUtils.register(context, SILENT_TREE, silentTreeHolder, PlacementUtils.filteredByBlockSurvival(TABlocks.SILENT_TREE_SAPLING.get()));
        PlacementUtils.register(context, AURORIAN_FOREST_SPRING, mediumRuinHolder1, PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        PlacementUtils.register(context, AURORIAN_FOREST_REMAINS, mediumRuinHolder2, PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        PlacementUtils.register(context, AURORIAN_FOREST_MEMORY_LOOP, mediumRuinHolder3, PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        PlacementUtils.register(context, AURORIAN_FOREST_RUINED_PORTAL, mediumRuinHolder4, PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        PlacementUtils.register(context, AURORIAN_FOREST_SHATTERED_WREATH, mediumRuinHolder5, PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        PlacementUtils.register(context, AURORIAN_FOREST_SHATTERED_PILLAR, mediumRuinHolder6, PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        PlacementUtils.register(context, AURORIAN_FOREST_SHATTERED_FOREST_PILLAR, mediumRuinHolder7, PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        PlacementUtils.register(context, ORE_AURORIAN_PERIDOTITE, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_AURORIAN_PERIDOTITE),
                OrePlacements.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.belowTop(100))));
        PlacementUtils.register(context, ORE_AURORIAN_DIRT, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_AURORIAN_DIRT),
                OrePlacements.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.top())));
        PlacementUtils.register(context, ORE_AURORIAN_COAL, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_AURORIAN_COAL),
                OrePlacements.commonOrePlacement(13, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(50), VerticalAnchor.belowTop(160))));
        PlacementUtils.register(context, ORE_MOONSTONE, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_AURORIAN_COAL),
                OrePlacements.commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.belowTop(38))));
        PlacementUtils.register(context, ORE_CERULEAN, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_AURORIAN_COAL),
                OrePlacements.commonOrePlacement(13, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.belowTop(75))));
        PlacementUtils.register(context, ORE_GEODE, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_AURORIAN_COAL),
                OrePlacements.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(50), VerticalAnchor.belowTop(160))));
        PlacementUtils.register(context, RANDOM_URNS, configuredFeature.getOrThrow(TAConfiguredFeatures.RANDOM_URN));
    }

}