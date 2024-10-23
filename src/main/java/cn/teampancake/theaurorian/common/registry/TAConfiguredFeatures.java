package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.blocks.AurorianWaterSurfacePlant;
import cn.teampancake.theaurorian.common.blocks.BlueberryBush;
import cn.teampancake.theaurorian.common.blocks.TAClusterBlock;
import cn.teampancake.theaurorian.common.blocks.state.TABlockProperties;
import cn.teampancake.theaurorian.common.level.feature.FilthyLakeFeature;
import cn.teampancake.theaurorian.common.level.feature.config.FallenLogConfig;
import cn.teampancake.theaurorian.common.level.feature.ruin.SmallRuinFeature;
import cn.teampancake.theaurorian.common.level.feature.tree.decorators.CrystalBudDecorator;
import cn.teampancake.theaurorian.common.level.feature.tree.foliage.HemisphereFoliagePlacer;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class TAConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_AURORIAN_GRASS = createKey("patch_aurorian_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_AURORIAN_GRASS_LIGHT = createKey("patch_aurorian_grass_light");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_AURORIAN_FLOWER_FOREST = createKey("patch_aurorian_flower_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_AURORIAN_FLOWER_PLAINS = createKey("patch_aurorian_flower_plains");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FLOWER_SNOWFIELD = createKey("patch_flower_snowfield");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_EQUINOX_FLOWER = createKey("patch_equinox_flower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_LAVENDER = createKey("patch_lavender");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_AURORIAN_FOREST = createKey("trees_aurorian_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_WEEPING_WILLOW_FOREST = createKey("trees_weeping_willow_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_AURORIAN_FOREST_RUINS = createKey("small_aurorian_forest_ruins");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEDIUM_AURORIAN_FOREST_RUINS = createKey("medium_aurorian_forest_ruins");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FILTHY_WATER_LAKE = createKey("filthy_water_lake");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FILTHY_FREEZE_TOP_LAYER = createKey("filthy_freeze_top_layer");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_INDIGO_MUSHROOM = createKey("huge_indigo_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RANDOM_FALLEN_SILENT_LOG = createKey("random_fallen_silent_log");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RANDOM_WATER_SURFACE_PLANT = createKey("random_water_surface_plant");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RANDOM_CRYSTAL_CLUSTER = createKey("random_crystal_cluster");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RANDOM_WEAK_GRASS = createKey("random_weak_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RANDOM_RIVERSIDE_PLANT = createKey("random_riverside_plant");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RIVERSIDE_MOON_SAND = createKey("riverside_moon_sand");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MOON_SAND_RIVER = createKey("ore_moon_sand_river");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AURORIAN_GRANITE = createKey("ore_aurorian_granite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AURORIAN_DIORITE = createKey("ore_aurorian_diorite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AURORIAN_ANDESITE = createKey("ore_aurorian_andesite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AURORIAN_PERIDOTITE = createKey("ore_aurorian_peridotite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AURORIAN_DIRT = createKey("ore_aurorian_dirt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AURORIAN_COAL = createKey("ore_aurorian_coal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MOONSTONE = createKey("ore_moonstone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_CERULEAN = createKey("ore_cerulean");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GEODE = createKey("ore_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_IRON = createKey("ore_iron");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_IRON_SMALL = createKey("ore_iron_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GOLD = createKey("ore_gold");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GOLD_BURIED = createKey("ore_gold_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_REDSTONE = createKey("ore_redstone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DIAMOND_SMALL = createKey("ore_diamond_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DIAMOND_LARGE = createKey("ore_diamond_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DIAMOND_BURIED = createKey("ore_diamond_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LAPIS = createKey("ore_lapis");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LAPIS_BURIED = createKey("ore_lapis_buried");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_EMERALD = createKey("ore_emerald");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_COPPPER_SMALL = createKey("ore_copper_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_COPPER_LARGE = createKey("ore_copper_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RANDOM_URN = createKey("random_urn");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILENT_TREE = createKey("silent_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WEEPING_WILLOW_TREE = createKey("weeping_willow_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AURORIAN_FOREST_SPRING = createKey("aurorian_forest_spring");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AURORIAN_FOREST_REMAINS = createKey("aurorian_forest_remains");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AURORIAN_FOREST_MEMORY_LOOP = createKey("aurorian_forest_memory_loop");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AURORIAN_FOREST_RUINED_PORTAL = createKey("aurorian_forest_ruined_portal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AURORIAN_FOREST_SHATTERED_WREATH = createKey("aurorian_forest_shattered_wreath");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AURORIAN_FOREST_SHATTERED_PILLAR = createKey("aurorian_forest_shattered_pillar");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AURORIAN_FOREST_SHATTERED_FOREST_PILLAR = createKey("aurorian_forest_shattered_forest_pillar");
    public static final List<ResourceKey<ConfiguredFeature<?, ?>>> AURORIAN_FOREST_SMALL_RUINS = new ArrayList<>();

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, TheAurorian.prefix(name));
    }

    private static TreeConfiguration.TreeConfigurationBuilder silentTree() {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(TABlocks.SILENT_TREE_LOG.get()),
                new CherryTrunkPlacer(10, 6, 0,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(1), 1)
                                .add(ConstantInt.of(2), 1)
                                .add(ConstantInt.of(3), 1).build()),
                        UniformInt.of((2), (4)), UniformInt.of((-4), (-3)), UniformInt.of((-1), (0))),
                BlockStateProvider.simple(TABlocks.SILENT_TREE_LEAVES.get()),
                new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.ZERO,
                        ConstantInt.of(5), (0.25F), (0.5F), (0.16666667F), (0.33333334F)),
                new TwoLayersFeatureSize(1, 0, 2)));
    }

    private static TreeConfiguration.TreeConfigurationBuilder weepingWillowTree() {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(TABlocks.WEEPING_WILLOW_LOG.get()),
                new StraightTrunkPlacer((8), (5), (0)), BlockStateProvider.simple(TABlocks.WEEPING_WILLOW_LEAVES.get()),
                new HemisphereFoliagePlacer(ConstantInt.ZERO, ConstantInt.ZERO), new TwoLayersFeatureSize((1), (0), (2))));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        IntegerProperty levelProperty = BlockStateProperties.LEVEL;
        Collection<Integer> levelValues = levelProperty.getPossibleValues();
        List<DeferredHolder<Feature<?>, SmallRuinFeature>> smallRuinRegList = TAFeatures.AURORIAN_FOREST_SMALL_RUINS;
        List<ResourceKey<ConfiguredFeature<?, ?>>> smallRuinConfigList = AURORIAN_FOREST_SMALL_RUINS;
        List<ResourceKey<PlacedFeature>> smallRuinPlaceList = TAPlacedFeatures.AURORIAN_FOREST_SMALL_RUINS;
        HolderGetter<PlacedFeature> placedFeature = context.lookup(Registries.PLACED_FEATURE);
        RuleTest defaultStoneRuleTest = new BlockMatchTest(TABlocks.AURORIAN_STONE.get());
        RuleTest defaultDirtRuleTest = new BlockMatchTest(TABlocks.AURORIAN_DIRT.get());
        RuleTest deepslateOreReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        List<OreConfiguration.TargetBlockState> ironTarget = List.of(OreConfiguration.target(defaultStoneRuleTest, TABlocks.AURORIAN_IRON_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreReplaceables, TABlocks.EROSIVE_AURORIAN_IRON_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> goldTarget = List.of(OreConfiguration.target(defaultStoneRuleTest, TABlocks.AURORIAN_GOLD_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreReplaceables, TABlocks.EROSIVE_AURORIAN_GOLD_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> diamondTarget = List.of(OreConfiguration.target(defaultStoneRuleTest, TABlocks.AURORIAN_DIAMOND_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreReplaceables, TABlocks.EROSIVE_AURORIAN_DIAMOND_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> lapisTarget = List.of(OreConfiguration.target(defaultStoneRuleTest, TABlocks.AURORIAN_LAPIS_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreReplaceables, TABlocks.EROSIVE_AURORIAN_LAPIS_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> copperTarget = List.of(OreConfiguration.target(defaultStoneRuleTest, TABlocks.AURORIAN_COPPER_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreReplaceables, TABlocks.EROSIVE_AURORIAN_COPPER_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> redstoneTarget = List.of(OreConfiguration.target(defaultStoneRuleTest, TABlocks.AURORIAN_REDSTONE_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreReplaceables, TABlocks.EROSIVE_AURORIAN_REDSTONE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> ceruleanTarget = List.of(OreConfiguration.target(defaultStoneRuleTest, TABlocks.CERULEAN_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreReplaceables, TABlocks.EROSIVE_CERULEAN_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> moonstoneTarget = List.of(OreConfiguration.target(defaultStoneRuleTest, TABlocks.MOONSTONE_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreReplaceables, TABlocks.EROSIVE_MOONSTONE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> geodeTarget = List.of(OreConfiguration.target(defaultStoneRuleTest, TABlocks.GEODE_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateOreReplaceables, TABlocks.EROSIVE_GEODE_ORE.get().defaultBlockState()));

        BlockState wickGrass = TABlocks.WICK_GRASS.get().defaultBlockState();
        BlockState tallWickGrass = TABlocks.TALL_WICK_GRASS.get().defaultBlockState();
        BlockState blueberryBush = TABlocks.BLUEBERRY_BUSH.get().defaultBlockState();
        SimpleWeightedRandomList.Builder<BlockState> wickGrassBuilder = SimpleWeightedRandomList.builder();
        levelValues.forEach(level -> wickGrassBuilder.add(wickGrass.setValue(levelProperty, level), 2));
        levelValues.forEach(level -> wickGrassBuilder.add(tallWickGrass.setValue(levelProperty, level), 1));
        Holder<PlacedFeature> silentTreeLikeSpruce = placedFeature.getOrThrow(TAPlacedFeatures.SILENT_TREE);
        Holder<PlacedFeature> weepingWillowTree = placedFeature.getOrThrow(TAPlacedFeatures.WEEPING_WILLOW_TREE);
        Holder<PlacedFeature> mediumRuinHolder1 = placedFeature.getOrThrow(TAPlacedFeatures.AURORIAN_FOREST_SPRING);
        Holder<PlacedFeature> mediumRuinHolder2 = placedFeature.getOrThrow(TAPlacedFeatures.AURORIAN_FOREST_REMAINS);
        Holder<PlacedFeature> mediumRuinHolder3 = placedFeature.getOrThrow(TAPlacedFeatures.AURORIAN_FOREST_MEMORY_LOOP);
        Holder<PlacedFeature> mediumRuinHolder4 = placedFeature.getOrThrow(TAPlacedFeatures.AURORIAN_FOREST_RUINED_PORTAL);
        Holder<PlacedFeature> mediumRuinHolder5 = placedFeature.getOrThrow(TAPlacedFeatures.AURORIAN_FOREST_SHATTERED_WREATH);
        Holder<PlacedFeature> mediumRuinHolder6 = placedFeature.getOrThrow(TAPlacedFeatures.AURORIAN_FOREST_SHATTERED_PILLAR);
        Holder<PlacedFeature> mediumRuinHolder7 = placedFeature.getOrThrow(TAPlacedFeatures.AURORIAN_FOREST_SHATTERED_FOREST_PILLAR);
        SimpleWeightedRandomList.Builder<BlockState> waterSurfacePlantBuilder = SimpleWeightedRandomList.builder();
        SimpleWeightedRandomList.Builder<BlockState> riversidePlantBuilder = SimpleWeightedRandomList.builder();
        SimpleWeightedRandomList.Builder<BlockState> clusterBuilder = SimpleWeightedRandomList.builder();
        for (Block block : TACommonUtils.getKnownBlocks()) {
            if (block instanceof AurorianWaterSurfacePlant waterSurfacePlant) {
                BlockState state = waterSurfacePlant.defaultBlockState();
                levelValues.forEach(level -> waterSurfacePlantBuilder.add(state.setValue(levelProperty, level), 1));
            } else if (block.properties() instanceof TABlockProperties properties && properties.isRiversidePlant) {
                levelValues.forEach(level -> riversidePlantBuilder.add(block.defaultBlockState().setValue(levelProperty, level), 1));
            } else if (block instanceof TAClusterBlock clusterBlock) {
                BlockState state = clusterBlock.defaultBlockState().setValue(TAClusterBlock.WATERLOGGED, Boolean.FALSE).setValue(TAClusterBlock.FACING, Direction.UP);
                levelValues.forEach(level -> clusterBuilder.add(state.setValue(levelProperty, level), 1));
            }
        }

        List<BlockPredicate> riversidePredicates = new ArrayList<>();
        for (int x = -3; x <= 3; x++) {
            for (int y = 1; y <= 5; y++) {
                for (int z = -3; z <= 3; z++) {
                    BlockPos blockPos = new BlockPos(x, -y, z);
                    riversidePredicates.add(BlockPredicate.matchesFluids(blockPos, Fluids.WATER, Fluids.FLOWING_WATER));
                }
            }
        }

        FeatureUtils.register(context, PATCH_AURORIAN_GRASS, Feature.RANDOM_PATCH, VegetationFeatures.grassPatch(
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(TABlocks.AURORIAN_GRASS.get().defaultBlockState(), 7)
                        .add(TABlocks.TALL_AURORIAN_GRASS.get().defaultBlockState()
                                .setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 1)), 24));
        FeatureUtils.register(context, PATCH_AURORIAN_GRASS_LIGHT, Feature.RANDOM_PATCH, VegetationFeatures.grassPatch(
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(TABlocks.AURORIAN_GRASS_LIGHT.get().defaultBlockState(), 3)
                        .add(TABlocks.TALL_AURORIAN_GRASS_LIGHT.get().defaultBlockState()
                                .setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 1)), 24));
        FeatureUtils.register(context, PATCH_AURORIAN_FLOWER_FOREST, Feature.FLOWER, VegetationFeatures.grassPatch(
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(TABlocks.PETUNIA_PLANT.get().defaultBlockState(), 2)
                        .add(TABlocks.SILK_BERRY_CROP.get().defaultBlockState(), 2)
                        .add(TABlocks.NEBULA_BLOSSOM_CLUSTER.get().defaultBlockState(), 2)
                        .add(TABlocks.MOON_FROST_FLOWER.get().defaultBlockState(), 2)
                        .add(TABlocks.VOID_CANDLE_FLOWER.get().defaultBlockState(), 2)
                        .add(blueberryBush.setValue(BlueberryBush.AGE, 0), 1)
                        .add(blueberryBush.setValue(BlueberryBush.AGE, 1), 1)
                        .add(blueberryBush.setValue(BlueberryBush.AGE, 2), 1)
                        .add(blueberryBush.setValue(BlueberryBush.AGE, 3), 1).build()), 6));
        FeatureUtils.register(context, PATCH_AURORIAN_FLOWER_PLAINS, Feature.FLOWER, VegetationFeatures.grassPatch(
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(TABlocks.PETUNIA_PLANT.get().defaultBlockState(), 1)
                        .add(TABlocks.SILK_BERRY_CROP.get().defaultBlockState(), 1)
                        .add(TABlocks.NEBULA_BLOSSOM_CLUSTER.get().defaultBlockState(), 1)
                        .add(TABlocks.MOON_FROST_FLOWER.get().defaultBlockState(), 1)
                        .add(TABlocks.VOID_CANDLE_FLOWER.get().defaultBlockState(), 1).build()), 6));
        FeatureUtils.register(context, PATCH_FLOWER_SNOWFIELD, Feature.FLOWER, VegetationFeatures.grassPatch(
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(TABlocks.DREAMSCAPE_PISTIL.get().defaultBlockState(), 1)
                        .add(TABlocks.FROST_TEARS_FLOWER.get().defaultBlockState(), 1)
                        .add(TABlocks.CRISPED_MALLOW.get().defaultBlockState(), 1)
                        .add(TABlocks.FROST_SNOW_GRASS.get().defaultBlockState(), 1)
                        .add(TABlocks.ICE_CALENDULA.get().defaultBlockState(), 1)
                        .add(TABlocks.WINTER_ROOT.get().defaultBlockState(), 1).build()), 5));
        FeatureUtils.register(context, PATCH_EQUINOX_FLOWER, Feature.FLOWER, VegetationFeatures.grassPatch(
                BlockStateProvider.simple(TABlocks.EQUINOX_FLOWER.get()), 16));
        FeatureUtils.register(context, PATCH_LAVENDER, Feature.FLOWER, VegetationFeatures.grassPatch(
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(TABlocks.LAVENDER_PLANT.get().defaultBlockState(), 3)
                        .add(TABlocks.TALL_LAVENDER_PLANT.get().defaultBlockState()
                                .setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 1)), 32));
        FeatureUtils.register(context, TREES_AURORIAN_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
                List.of(new WeightedPlacedFeature(silentTreeLikeSpruce, 0.3F)), silentTreeLikeSpruce));
        FeatureUtils.register(context, TREES_WEEPING_WILLOW_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
                List.of(new WeightedPlacedFeature(weepingWillowTree, 0.4F)), weepingWillowTree));
        FeatureUtils.register(context, MEDIUM_AURORIAN_FOREST_RUINS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
                List.of(new WeightedPlacedFeature(mediumRuinHolder1, 0.125F), new WeightedPlacedFeature(mediumRuinHolder2, 0.125F),
                        new WeightedPlacedFeature(mediumRuinHolder3, 0.125F), new WeightedPlacedFeature(mediumRuinHolder4, 0.125F),
                        new WeightedPlacedFeature(mediumRuinHolder5, 0.125F), new WeightedPlacedFeature(mediumRuinHolder6, 0.125F),
                        new WeightedPlacedFeature(mediumRuinHolder7, 0.125F)), mediumRuinHolder1));
        FeatureUtils.register(context, FILTHY_WATER_LAKE, TAFeatures.FILTHY_WATER_LAKE.get(),
                new FilthyLakeFeature.Configuration(BlockStateProvider.simple(Blocks.WATER),
                        BlockStateProvider.simple(TABlocks.AURORIAN_DIRT.get()),
                        BlockStateProvider.simple(TABlocks.SNOW_AURORIAN_GRASS_BLOCK.get())));
        FeatureUtils.register(context, FILTHY_FREEZE_TOP_LAYER, TAFeatures.FILTHY_FREEZE_TOP_LAYER.get());
        FeatureUtils.register(context, HUGE_INDIGO_MUSHROOM, TAFeatures.HUGE_INDIGO_MUSHROOM.get(),
                new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(TABlocks.INDIGO_MUSHROOM_BLOCK.get()),
                        BlockStateProvider.simple(TABlocks.INDIGO_MUSHROOM_STEM.get()), 5));
        FeatureUtils.register(context, RANDOM_FALLEN_SILENT_LOG, TAFeatures.RANDOM_FALLEN_LOGS.get(), new FallenLogConfig(BlockStateProvider.simple(TABlocks.SILENT_TREE_LOG.get()), (0.1F)));
        FeatureUtils.register(context, RANDOM_WATER_SURFACE_PLANT, Feature.RANDOM_PATCH, VegetationFeatures.grassPatch(new WeightedStateProvider(waterSurfacePlantBuilder.build()), 10));
        FeatureUtils.register(context, RANDOM_CRYSTAL_CLUSTER, Feature.FLOWER, VegetationFeatures.grassPatch(new WeightedStateProvider(clusterBuilder.build()), 10));
        FeatureUtils.register(context, RANDOM_WEAK_GRASS, Feature.FLOWER, VegetationFeatures.grassPatch(new WeightedStateProvider(wickGrassBuilder.build()), 4));
        FeatureUtils.register(context, RANDOM_RIVERSIDE_PLANT, Feature.RANDOM_PATCH, new RandomPatchConfiguration(20, 4, 0,
                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, VegetationFeatures.grassPatch(new WeightedStateProvider(riversidePlantBuilder.build()), 6),
                        BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.anyOf(riversidePredicates))))));
        FeatureUtils.register(context, RIVERSIDE_MOON_SAND, Feature.ORE, new OreConfiguration(ImmutableList.of(
                OreConfiguration.target(new BlockMatchTest(TABlocks.AURORIAN_GRASS_BLOCK.get()), TABlocks.MOON_SAND.get().defaultBlockState()),
                OreConfiguration.target(new BlockMatchTest(TABlocks.LIGHT_AURORIAN_GRASS_BLOCK.get()), TABlocks.MOON_SAND.get().defaultBlockState()),
                OreConfiguration.target(new BlockMatchTest(TABlocks.SNOW_AURORIAN_GRASS_BLOCK.get()), TABlocks.MOON_SAND.get().defaultBlockState()),
                OreConfiguration.target(new BlockMatchTest(TABlocks.RED_AURORIAN_GRASS_BLOCK.get()), TABlocks.MOON_SAND.get().defaultBlockState()),
                OreConfiguration.target(new BlockMatchTest(TABlocks.AURORIAN_DIRT.get()), TABlocks.MOON_SANDSTONE.get().defaultBlockState())), 64));
        FeatureUtils.register(context, SILENT_TREE, Feature.TREE, silentTree()
                .dirt(BlockStateProvider.simple(TABlocks.AURORIAN_DIRT.get())).ignoreVines()
                .decorators(ImmutableList.of(new CrystalBudDecorator(0.05F))).build());
        FeatureUtils.register(context, WEEPING_WILLOW_TREE, Feature.TREE, weepingWillowTree()
                .dirt(BlockStateProvider.simple(TABlocks.AURORIAN_DIRT.get())).ignoreVines()
                .decorators(ImmutableList.of(new CrystalBudDecorator(0.05F))).build());
        FeatureUtils.register(context, ORE_MOON_SAND_RIVER, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(defaultStoneRuleTest, TABlocks.MOON_SAND_RIVER.get().defaultBlockState()),
                OreConfiguration.target(defaultDirtRuleTest, TABlocks.MOON_SAND_RIVER.get().defaultBlockState())), 48));
        FeatureUtils.register(context, ORE_AURORIAN_PERIDOTITE, Feature.ORE, new OreConfiguration(defaultStoneRuleTest, TABlocks.AURORIAN_PERIDOTITE.get().defaultBlockState(), 33));
        FeatureUtils.register(context, ORE_AURORIAN_DIRT, Feature.ORE, new OreConfiguration(defaultStoneRuleTest, TABlocks.AURORIAN_DIRT.get().defaultBlockState(), 33));
        FeatureUtils.register(context, ORE_AURORIAN_GRANITE, Feature.ORE, new OreConfiguration(defaultStoneRuleTest, TABlocks.AURORIAN_GRANITE.get().defaultBlockState(), 33));
        FeatureUtils.register(context, ORE_AURORIAN_DIORITE, Feature.ORE, new OreConfiguration(defaultStoneRuleTest, TABlocks.AURORIAN_DIORITE.get().defaultBlockState(), 33));
        FeatureUtils.register(context, ORE_AURORIAN_ANDESITE, Feature.ORE, new OreConfiguration(defaultStoneRuleTest, TABlocks.AURORIAN_ANDESITE.get().defaultBlockState(), 33));
        FeatureUtils.register(context, ORE_AURORIAN_COAL, Feature.ORE, new OreConfiguration(defaultStoneRuleTest, TABlocks.AURORIAN_COAL_ORE.get().defaultBlockState(), 16));
        FeatureUtils.register(context, ORE_MOONSTONE, Feature.ORE, new OreConfiguration(moonstoneTarget, 9));
        FeatureUtils.register(context, ORE_CERULEAN, Feature.ORE, new OreConfiguration(ceruleanTarget, 7));
        FeatureUtils.register(context, ORE_GEODE, Feature.ORE, new OreConfiguration(geodeTarget, 5));
        FeatureUtils.register(context, ORE_IRON, Feature.ORE, new OreConfiguration(ironTarget, 9));
        FeatureUtils.register(context, ORE_IRON_SMALL, Feature.ORE, new OreConfiguration(ironTarget, 4));
        FeatureUtils.register(context, ORE_GOLD, Feature.ORE, new OreConfiguration(goldTarget,9));
        FeatureUtils.register(context, ORE_GOLD_BURIED, Feature.ORE, new OreConfiguration(goldTarget, 9, 0.5F));
        FeatureUtils.register(context, ORE_REDSTONE, Feature.ORE, new OreConfiguration(redstoneTarget, 8));
        FeatureUtils.register(context, ORE_DIAMOND_SMALL, Feature.ORE, new OreConfiguration(diamondTarget, 4, 0.5F));
        FeatureUtils.register(context, ORE_DIAMOND_LARGE, Feature.ORE, new OreConfiguration(diamondTarget, 12, 0.7F));
        FeatureUtils.register(context, ORE_DIAMOND_BURIED, Feature.ORE, new OreConfiguration(diamondTarget, 8, 1.0F));
        FeatureUtils.register(context, ORE_LAPIS, Feature.ORE, new OreConfiguration(lapisTarget, 7));
        FeatureUtils.register(context, ORE_LAPIS_BURIED, Feature.ORE, new OreConfiguration(lapisTarget, 7, 1.0F));
        FeatureUtils.register(context, ORE_EMERALD, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(defaultStoneRuleTest, TABlocks.AURORIAN_EMERALD_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateOreReplaceables, TABlocks.EROSIVE_AURORIAN_EMERALD_ORE.get().defaultBlockState())), 3));
        FeatureUtils.register(context, ORE_COPPPER_SMALL, Feature.ORE, new OreConfiguration(copperTarget, 10));
        FeatureUtils.register(context, ORE_COPPER_LARGE, Feature.ORE, new OreConfiguration(copperTarget, 20));
        FeatureUtils.register(context, AURORIAN_FOREST_SPRING, TAFeatures.AURORIAN_FOREST_SPRING.get(), NoneFeatureConfiguration.NONE);
        FeatureUtils.register(context, AURORIAN_FOREST_REMAINS, TAFeatures.AURORIAN_FOREST_REMAINS.get(), NoneFeatureConfiguration.NONE);
        FeatureUtils.register(context, AURORIAN_FOREST_MEMORY_LOOP, TAFeatures.AURORIAN_FOREST_MEMORY_LOOP.get(), NoneFeatureConfiguration.NONE);
        FeatureUtils.register(context, AURORIAN_FOREST_RUINED_PORTAL, TAFeatures.AURORIAN_FOREST_RUINED_PORTAL.get(), NoneFeatureConfiguration.NONE);
        FeatureUtils.register(context, AURORIAN_FOREST_SHATTERED_WREATH, TAFeatures.AURORIAN_FOREST_SHATTERED_WREATH.get(), NoneFeatureConfiguration.NONE);
        FeatureUtils.register(context, AURORIAN_FOREST_SHATTERED_PILLAR, TAFeatures.AURORIAN_FOREST_SHATTERED_PILLAR.get(), NoneFeatureConfiguration.NONE);
        FeatureUtils.register(context, AURORIAN_FOREST_SHATTERED_FOREST_PILLAR, TAFeatures.AURORIAN_FOREST_SHATTERED_FOREST_PILLAR.get(), NoneFeatureConfiguration.NONE);
        FeatureUtils.register(context, RANDOM_URN, TAFeatures.RANDOM_URN.get());
        if (!smallRuinRegList.isEmpty() && !smallRuinConfigList.isEmpty()) {
            for (int i = 0; i < smallRuinConfigList.size(); i++) {
                FeatureUtils.register(context, smallRuinConfigList.get(i), smallRuinRegList.get(i).get(), NoneFeatureConfiguration.NONE);
            }
        }

        if (!smallRuinPlaceList.isEmpty()) {
            List<WeightedPlacedFeature> list = new ArrayList<>();
            List<Holder<PlacedFeature>> holderList = new ArrayList<>();
            for (int i = 0; i < smallRuinPlaceList.size(); i++) {
                Holder<PlacedFeature> smallRuinHolder = placedFeature.getOrThrow(smallRuinPlaceList.get(i));
                list.add(new WeightedPlacedFeature(smallRuinHolder, (3.0F / (smallRuinPlaceList.size() + 2))));
                holderList.add(smallRuinHolder);
            }

            if (!list.isEmpty() && !holderList.isEmpty()) {
                RandomFeatureConfiguration config = new RandomFeatureConfiguration(list, holderList.getFirst());
                FeatureUtils.register(context, SMALL_AURORIAN_FOREST_RUINS, Feature.RANDOM_SELECTOR, config);
            }
        }
    }

    static {
        List<DeferredHolder<Feature<?>, SmallRuinFeature>> list = TAFeatures.AURORIAN_FOREST_SMALL_RUINS;
        list.forEach(object -> AURORIAN_FOREST_SMALL_RUINS.add(createKey(object.getId().getPath())));
    }

}