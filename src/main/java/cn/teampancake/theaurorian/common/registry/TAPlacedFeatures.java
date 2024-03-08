package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.feature.ruin.SmallRuinFeature;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class TAPlacedFeatures {

    public static final ResourceKey<PlacedFeature> PATCH_AURORIAN_GRASS_PLAINS = createKey("patch_aurorian_grass_plains");
    public static final ResourceKey<PlacedFeature> PATCH_AURORIAN_GRASS_FOREST = createKey("patch_aurorian_grass_forest");
    public static final ResourceKey<PlacedFeature> PATCH_AURORIAN_GRASS_LIGHT_FOREST = createKey("patch_aurorian_grass_light_forest");
    public static final ResourceKey<PlacedFeature> PATCH_AURORIAN_FLOWER_PLAINS = createKey("patch_aurorian_flower_plains");
    public static final ResourceKey<PlacedFeature> PATCH_AURORIAN_FLOWER_FOREST = createKey("patch_aurorian_flower_forest");
    public static final ResourceKey<PlacedFeature> PATCH_FLOWER_SNOWFIELD = createKey("patch_flower_snowfield");
    public static final ResourceKey<PlacedFeature> PATCH_EQUINOX_FLOWER = createKey("patch_equinox_flower");
    public static final ResourceKey<PlacedFeature> PATCH_LAVENDER = createKey("patch_lavender");
    public static final ResourceKey<PlacedFeature> TREES_AURORIAN_FOREST = createKey("trees_aurorian_forest");
    public static final ResourceKey<PlacedFeature> TREES_WEEPING_WILLOW_FOREST = createKey("trees_weeping_willow_forest");
    public static final ResourceKey<PlacedFeature> SMALL_AURORIAN_FOREST_RUINS = createKey("small_aurorian_forest_ruins");
    public static final ResourceKey<PlacedFeature> MEDIUM_AURORIAN_FOREST_RUINS = createKey("medium_aurorian_forest_ruins");
    public static final ResourceKey<PlacedFeature> FILTHY_WATER_LAKE = createKey("filthy_water_lake");
    public static final ResourceKey<PlacedFeature> FILTHY_FREEZE_TOP_LAYER = createKey("filthy_freeze_top_layer");
    public static final ResourceKey<PlacedFeature> RANDOM_FALLEN_SILENT_LOG = createKey("random_fallen_silent_log");
    public static final ResourceKey<PlacedFeature> RANDOM_WATER_SURFACE_PLANT = createKey("random_water_surface_plant");
    public static final ResourceKey<PlacedFeature> RANDOM_CRYSTAL_CLUSTER = createKey("random_crystal_cluster");
    public static final ResourceKey<PlacedFeature> RANDOM_WEAK_GRASS = createKey("random_weak_grass");
    public static final ResourceKey<PlacedFeature> RANDOM_RIVERSIDE_PLANT = createKey("random_riverside_plant");
    public static final ResourceKey<PlacedFeature> RIVERSIDE_MOON_SAND = createKey("riverside_moon_sand");
    public static final ResourceKey<PlacedFeature> ORE_MOON_SAND_RIVER = createKey("ore_moon_sand_river");
    public static final ResourceKey<PlacedFeature> ORE_AURORIAN_GRANITE = createKey("ore_aurorian_granite");
    public static final ResourceKey<PlacedFeature> ORE_AURORIAN_DIORITE = createKey("ore_aurorian_diorite");
    public static final ResourceKey<PlacedFeature> ORE_AURORIAN_ANDESITE = createKey("ore_aurorian_andesite");
    public static final ResourceKey<PlacedFeature> ORE_AURORIAN_PERIDOTITE = createKey("ore_aurorian_peridotite");
    public static final ResourceKey<PlacedFeature> ORE_AURORIAN_DIRT = createKey("ore_aurorian_dirt");
    public static final ResourceKey<PlacedFeature> ORE_AURORIAN_COAL = createKey("ore_aurorian_coal");
    public static final ResourceKey<PlacedFeature> ORE_MOONSTONE = createKey("ore_moonstone");
    public static final ResourceKey<PlacedFeature> ORE_CERULEAN = createKey("ore_cerulean");
    public static final ResourceKey<PlacedFeature> ORE_GEODE = createKey("ore_geode");
    public static final ResourceKey<PlacedFeature> ORE_IRON_UPPER = createKey("ore_iron_upper");
    public static final ResourceKey<PlacedFeature> ORE_IRON_MIDDLE = createKey("ore_iron_middle");
    public static final ResourceKey<PlacedFeature> ORE_IRON_SMALL = createKey("ore_iron_small");
    public static final ResourceKey<PlacedFeature> ORE_GOLD_EXTRA = createKey("ore_gold_extra");
    public static final ResourceKey<PlacedFeature> ORE_GOLD = createKey("ore_gold");
    public static final ResourceKey<PlacedFeature> ORE_GOLD_LOWER = createKey("ore_gold_lower");
    public static final ResourceKey<PlacedFeature> ORE_REDSTONE = createKey("ore_redstone");
    public static final ResourceKey<PlacedFeature> ORE_REDSTONE_LOWER = createKey("ore_redstone_lower");
    public static final ResourceKey<PlacedFeature> ORE_DIAMOND = createKey("ore_diamond");
    public static final ResourceKey<PlacedFeature> ORE_DIAMOND_LARGE = createKey("ore_diamond_large");
    public static final ResourceKey<PlacedFeature> ORE_DIAMOND_BURIED = createKey("ore_diamond_buried");
    public static final ResourceKey<PlacedFeature> ORE_LAPIS = createKey("ore_lapis");
    public static final ResourceKey<PlacedFeature> ORE_LAPIS_BURIED = createKey("ore_lapis_buried");
    public static final ResourceKey<PlacedFeature> ORE_EMERALD = createKey("ore_emerald");
    public static final ResourceKey<PlacedFeature> ORE_COPPER = createKey("ore_copper");
    public static final ResourceKey<PlacedFeature> ORE_COPPER_LARGE = createKey("ore_copper_large");
    public static final ResourceKey<PlacedFeature> RANDOM_URNS = createKey("random_urns");
    public static final ResourceKey<PlacedFeature> SILENT_TREE = createKey("silent_tree");
    public static final ResourceKey<PlacedFeature> WEEPING_WILLOW_TREE = createKey("weeping_willow_tree");
    public static final ResourceKey<PlacedFeature> AURORIAN_FOREST_SPRING = createKey("aurorian_forest_spring");
    public static final ResourceKey<PlacedFeature> AURORIAN_FOREST_REMAINS = createKey("aurorian_forest_remains");
    public static final ResourceKey<PlacedFeature> AURORIAN_FOREST_MEMORY_LOOP = createKey("aurorian_forest_memory_loop");
    public static final ResourceKey<PlacedFeature> AURORIAN_FOREST_RUINED_PORTAL = createKey("aurorian_forest_ruined_portal");
    public static final ResourceKey<PlacedFeature> AURORIAN_FOREST_SHATTERED_WREATH = createKey("aurorian_forest_shattered_wreath");
    public static final ResourceKey<PlacedFeature> AURORIAN_FOREST_SHATTERED_PILLAR = createKey("aurorian_forest_shattered_pillar");
    public static final ResourceKey<PlacedFeature> AURORIAN_FOREST_SHATTERED_FOREST_PILLAR = createKey("aurorian_forest_shattered_forest_pillar");
    public static final List<ResourceKey<PlacedFeature>> AURORIAN_FOREST_SMALL_RUINS = new ArrayList<>();

    private static ResourceKey<PlacedFeature> createKey(String key) {
        return ResourceKey.create(Registries.PLACED_FEATURE, AurorianMod.prefix(key));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        List<BlockPredicate> riversidePredicates = new ArrayList<>();
        for (int x = -3; x <= 3; x++) {
            for (int y = 1; y <= 5; y++) {
                for (int z = -3; z <= 3; z++) {
                    BlockPos blockPos = new BlockPos(x, -y, z);
                    riversidePredicates.add(BlockPredicate.matchesFluids(blockPos, Fluids.WATER, Fluids.FLOWING_WATER));
                }
            }
        }

        List<ResourceKey<ConfiguredFeature<?, ?>>> smallRuinConfigList = TAConfiguredFeatures.AURORIAN_FOREST_SMALL_RUINS;
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeature = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> patchAurorianGrassHolder = configuredFeature.getOrThrow(TAConfiguredFeatures.PATCH_AURORIAN_GRASS);
        Holder<ConfiguredFeature<?, ?>> patchAurorianGrassLightHolder = configuredFeature.getOrThrow(TAConfiguredFeatures.PATCH_AURORIAN_GRASS_LIGHT);
        PlacementUtils.register(context, PATCH_AURORIAN_GRASS_PLAINS, patchAurorianGrassHolder, NoiseThresholdCountPlacement.of((-0.8D), (5), (10)),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(context, PATCH_AURORIAN_GRASS_FOREST, patchAurorianGrassHolder, VegetationPlacements.worldSurfaceSquaredWithCount(3));
        PlacementUtils.register(context, PATCH_AURORIAN_GRASS_LIGHT_FOREST, patchAurorianGrassLightHolder, VegetationPlacements.worldSurfaceSquaredWithCount(3));
        PlacementUtils.register(context, PATCH_AURORIAN_FLOWER_FOREST, configuredFeature.getOrThrow(TAConfiguredFeatures.PATCH_AURORIAN_FLOWER_FOREST), VegetationPlacements.worldSurfaceSquaredWithCount(3));
        PlacementUtils.register(context, PATCH_AURORIAN_FLOWER_PLAINS, configuredFeature.getOrThrow(TAConfiguredFeatures.PATCH_AURORIAN_FLOWER_PLAINS), VegetationPlacements.worldSurfaceSquaredWithCount(3));
        PlacementUtils.register(context, PATCH_FLOWER_SNOWFIELD, configuredFeature.getOrThrow(TAConfiguredFeatures.PATCH_FLOWER_SNOWFIELD), VegetationPlacements.worldSurfaceSquaredWithCount(3));
        PlacementUtils.register(context, PATCH_EQUINOX_FLOWER, configuredFeature.getOrThrow(TAConfiguredFeatures.PATCH_EQUINOX_FLOWER), VegetationPlacements.worldSurfaceSquaredWithCount(3));
        PlacementUtils.register(context, PATCH_LAVENDER, configuredFeature.getOrThrow(TAConfiguredFeatures.PATCH_LAVENDER), VegetationPlacements.worldSurfaceSquaredWithCount(4));
        PlacementUtils.register(context, TREES_AURORIAN_FOREST, configuredFeature.getOrThrow(TAConfiguredFeatures.TREES_AURORIAN_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra((5), (0.1F), (1))));
        PlacementUtils.register(context, TREES_WEEPING_WILLOW_FOREST, configuredFeature.getOrThrow(TAConfiguredFeatures.TREES_WEEPING_WILLOW_FOREST), VegetationPlacements.treePlacement(PlacementUtils.countExtra((5), (0.1F), (1))));
        PlacementUtils.register(context, SMALL_AURORIAN_FOREST_RUINS, configuredFeature.getOrThrow(TAConfiguredFeatures.SMALL_AURORIAN_FOREST_RUINS), ImmutableList.<PlacementModifier>builder()
                .add(RarityFilter.onAverageOnceEvery(20), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()).build());
        PlacementUtils.register(context, MEDIUM_AURORIAN_FOREST_RUINS, configuredFeature.getOrThrow(TAConfiguredFeatures.MEDIUM_AURORIAN_FOREST_RUINS), ImmutableList.<PlacementModifier>builder()
                .add(RarityFilter.onAverageOnceEvery(20), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()).build());
        PlacementUtils.register(context, FILTHY_WATER_LAKE, configuredFeature.getOrThrow(TAConfiguredFeatures.FILTHY_WATER_LAKE),
                RarityFilter.onAverageOnceEvery(15), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(context, FILTHY_FREEZE_TOP_LAYER, configuredFeature.getOrThrow(TAConfiguredFeatures.FILTHY_FREEZE_TOP_LAYER), BiomeFilter.biome());
        PlacementUtils.register(context, RANDOM_FALLEN_SILENT_LOG, configuredFeature.getOrThrow(TAConfiguredFeatures.RANDOM_FALLEN_SILENT_LOG), VegetationPlacements.worldSurfaceSquaredWithCount(1));
        PlacementUtils.register(context, RANDOM_WATER_SURFACE_PLANT, configuredFeature.getOrThrow(TAConfiguredFeatures.RANDOM_WATER_SURFACE_PLANT), VegetationPlacements.worldSurfaceSquaredWithCount(1));
        PlacementUtils.register(context, RANDOM_CRYSTAL_CLUSTER, configuredFeature.getOrThrow(TAConfiguredFeatures.RANDOM_CRYSTAL_CLUSTER), VegetationPlacements.worldSurfaceSquaredWithCount(1));
        PlacementUtils.register(context, RANDOM_WEAK_GRASS, configuredFeature.getOrThrow(TAConfiguredFeatures.RANDOM_WEAK_GRASS), VegetationPlacements.worldSurfaceSquaredWithCount(1));
        PlacementUtils.register(context, RANDOM_RIVERSIDE_PLANT, configuredFeature.getOrThrow(TAConfiguredFeatures.RANDOM_RIVERSIDE_PLANT), VegetationPlacements.worldSurfaceSquaredWithCount(1));
        PlacementUtils.register(context, RIVERSIDE_MOON_SAND, configuredFeature.getOrThrow(TAConfiguredFeatures.RIVERSIDE_MOON_SAND), CountPlacement.of(10),
                InSquarePlacement.spread(), BiomeFilter.biome(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BlockPredicateFilter.forPredicate(BlockPredicate.anyOf(riversidePredicates)));
        PlacementUtils.register(context, SILENT_TREE, configuredFeature.getOrThrow(TAConfiguredFeatures.SILENT_TREE), PlacementUtils.filteredByBlockSurvival(TABlocks.SILENT_TREE_SAPLING.get()));
        PlacementUtils.register(context, WEEPING_WILLOW_TREE, configuredFeature.getOrThrow(TAConfiguredFeatures.WEEPING_WILLOW_TREE),
                BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.matchesBlocks(TABlocks.LIGHT_AURORIAN_GRASS_BLOCK.get()))));
        PlacementUtils.register(context, AURORIAN_FOREST_SPRING, configuredFeature.getOrThrow(TAConfiguredFeatures.AURORIAN_FOREST_SPRING), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        PlacementUtils.register(context, AURORIAN_FOREST_REMAINS, configuredFeature.getOrThrow(TAConfiguredFeatures.AURORIAN_FOREST_REMAINS), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        PlacementUtils.register(context, AURORIAN_FOREST_MEMORY_LOOP, configuredFeature.getOrThrow(TAConfiguredFeatures.AURORIAN_FOREST_MEMORY_LOOP), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        PlacementUtils.register(context, AURORIAN_FOREST_RUINED_PORTAL, configuredFeature.getOrThrow(TAConfiguredFeatures.AURORIAN_FOREST_RUINED_PORTAL), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        PlacementUtils.register(context, AURORIAN_FOREST_SHATTERED_WREATH, configuredFeature.getOrThrow(TAConfiguredFeatures.AURORIAN_FOREST_SHATTERED_WREATH), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        PlacementUtils.register(context, AURORIAN_FOREST_SHATTERED_PILLAR, configuredFeature.getOrThrow(TAConfiguredFeatures.AURORIAN_FOREST_SHATTERED_PILLAR), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        PlacementUtils.register(context, AURORIAN_FOREST_SHATTERED_FOREST_PILLAR, configuredFeature.getOrThrow(TAConfiguredFeatures.AURORIAN_FOREST_SHATTERED_FOREST_PILLAR), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
        PlacementUtils.register(context, ORE_MOON_SAND_RIVER, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_MOON_SAND_RIVER), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
        PlacementUtils.register(context, ORE_AURORIAN_GRANITE, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_AURORIAN_GRANITE),
                OrePlacements.commonOrePlacement(6, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT));
        PlacementUtils.register(context, ORE_AURORIAN_DIORITE, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_AURORIAN_DIORITE),
                OrePlacements.commonOrePlacement(6, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT));
        PlacementUtils.register(context, ORE_AURORIAN_ANDESITE, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_AURORIAN_ANDESITE),
                OrePlacements.commonOrePlacement(6, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT));
        PlacementUtils.register(context, ORE_AURORIAN_PERIDOTITE, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_AURORIAN_PERIDOTITE),
                OrePlacements.commonOrePlacement(10, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT));
        PlacementUtils.register(context, ORE_AURORIAN_DIRT, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_AURORIAN_DIRT),
                OrePlacements.commonOrePlacement(10, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT));
        PlacementUtils.register(context, ORE_AURORIAN_COAL, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_AURORIAN_COAL),
                OrePlacements.commonOrePlacement(20, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT));
        PlacementUtils.register(context, ORE_MOONSTONE, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_MOONSTONE),
                OrePlacements.commonOrePlacement(2, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT));
        PlacementUtils.register(context, ORE_CERULEAN, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_CERULEAN),
                OrePlacements.commonOrePlacement(13, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(-32))));
        PlacementUtils.register(context, ORE_GEODE, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_GEODE),
                OrePlacements.commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(-16))));
        PlacementUtils.register(context,ORE_GOLD,configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_GOLD),
                OrePlacements.commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))));
        PlacementUtils.register(context,ORE_GOLD_LOWER,configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_GOLD_BURIED),
                OrePlacements.orePlacement(CountPlacement.of(UniformInt.of(0, 1)), HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-48))));
        PlacementUtils.register(context,ORE_GOLD_EXTRA,configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_GOLD),
                OrePlacements.commonOrePlacement(50, HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(256))));
        PlacementUtils.register(context,ORE_IRON_UPPER,configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_IRON),
                OrePlacements.commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(384))));
        PlacementUtils.register(context,ORE_IRON_MIDDLE,configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_IRON),
                OrePlacements.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
        PlacementUtils.register(context,ORE_IRON_SMALL,configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_IRON),
                OrePlacements.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(72))));
        PlacementUtils.register(context,ORE_REDSTONE,configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_REDSTONE),
                OrePlacements.commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(15))));
        PlacementUtils.register(context,ORE_REDSTONE_LOWER,configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_REDSTONE),
                OrePlacements.commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-32), VerticalAnchor.aboveBottom(32))));
        PlacementUtils.register(context,ORE_DIAMOND,configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_DIAMOND_SMALL),
                OrePlacements.commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-32), VerticalAnchor.aboveBottom(32))));
        PlacementUtils.register(context,ORE_DIAMOND_BURIED,configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_DIAMOND_BURIED),
                OrePlacements.commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        PlacementUtils.register(context, ORE_DIAMOND_LARGE, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_DIAMOND_LARGE),
                OrePlacements.rareOrePlacement(9, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        PlacementUtils.register(context, ORE_LAPIS,configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_LAPIS),
                OrePlacements.commonOrePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(32))));
        PlacementUtils.register(context, ORE_LAPIS_BURIED, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_LAPIS_BURIED),
                OrePlacements.commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(64))));
        PlacementUtils.register(context, ORE_EMERALD, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_EMERALD),
                OrePlacements.commonOrePlacement(100, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(480))));
        PlacementUtils.register(context, ORE_COPPER, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_COPPPER_SMALL),
                OrePlacements.commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112))));
        PlacementUtils.register(context, ORE_COPPER_LARGE, configuredFeature.getOrThrow(TAConfiguredFeatures.ORE_COPPER_LARGE),
                OrePlacements.commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112))));
        PlacementUtils.register(context, RANDOM_URNS, configuredFeature.getOrThrow(TAConfiguredFeatures.RANDOM_URN));
        if (!smallRuinConfigList.isEmpty() && !AURORIAN_FOREST_SMALL_RUINS.isEmpty()) {
            for (int i = 0; i < AURORIAN_FOREST_SMALL_RUINS.size(); i++) {
                Holder<ConfiguredFeature<?, ?>> smallRuinHolder = configuredFeature.getOrThrow(smallRuinConfigList.get(i));
                PlacementUtils.register(context, AURORIAN_FOREST_SMALL_RUINS.get(i), smallRuinHolder, PlacementUtils.HEIGHTMAP_WORLD_SURFACE);
            }
        }
    }

    static {
        List<RegistryObject<SmallRuinFeature>> list = TAFeatures.AURORIAN_FOREST_SMALL_RUINS;
        list.forEach(object -> AURORIAN_FOREST_SMALL_RUINS.add(createKey(object.getId().getPath())));
    }

}