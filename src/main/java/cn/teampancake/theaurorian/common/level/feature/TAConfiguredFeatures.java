package cn.teampancake.theaurorian.common.level.feature;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.blocks.BlueBerryBush;
import cn.teampancake.theaurorian.common.blocks.TAClusterBlock;
import cn.teampancake.theaurorian.common.blocks.WickGrass;
import cn.teampancake.theaurorian.common.level.feature.config.FallenLogConfig;
import cn.teampancake.theaurorian.common.level.feature.tree.decorators.CrystalBudDecorator;
import cn.teampancake.theaurorian.common.level.placement.TAPlacements;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAFeatures;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;

public class TAConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_AURORIAN_GRASS = createKey("patch_aurorian_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_AURORIAN_GRASS_LIGHT = createKey("patch_aurorian_grass_light");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_AURORIAN_FLOWER = createKey("patch_aurorian_flower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_EQUINOX_FLOWER = createKey("patch_equinox_flower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_LAVENDER = createKey("patch_lavender");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_AURORIAN_FOREST = createKey("trees_aurorian_forest");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_INDIGO_MUSHROOM = createKey("huge_indigo_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RANDOM_FALLEN_SILENT_LOG = createKey("random_fallen_silent_log");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RANDOM_CRYSTAL_CLUSTER = createKey("random_crystal_cluster");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RANDOM_WEAK_GRASS = createKey("random_weak_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WATER_GRASS_SIMPLE = createKey("water_grass_simple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WATER_GRASS_SLIGHTLY_LESS_SHORT = createKey("water_grass_slightly_less_short");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WATER_GRASS_SHORT = createKey("water_grass_short");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WATER_GRASS_MID = createKey("water_grass_mid");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WATER_GRASS_TALL = createKey("water_grass_tall");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AURORIAN_PERIDOTITE = createKey("ore_aurorian_peridotite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AURORIAN_DIRT = createKey("ore_aurorian_dirt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_AURORIAN_COAL = createKey("ore_aurorian_coal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MOONSTONE = createKey("ore_moonstone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_CERULEAN = createKey("ore_cerulean");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GEODE = createKey("ore_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RANDOM_URN = createKey("random_urn");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILENT_TREE = createKey("silent_tree");

    private static TreeConfiguration.TreeConfigurationBuilder silentTree() {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(TABlocks.SILENT_TREE_LOG.get()),
                new CherryTrunkPlacer(10, 6, 0,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(1), 1)
                                .add(ConstantInt.of(2), 1)
                                .add(ConstantInt.of(3), 1).build()),
                        UniformInt.of(2, 4), UniformInt.of(-4, -3), UniformInt.of(-1, 0)),
                BlockStateProvider.simple(TABlocks.SILENT_TREE_LEAVES.get()),
                new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0),
                        ConstantInt.of(5), 0.25F, 0.5F, 0.16666667F, 0.33333334F),
                new TwoLayersFeatureSize(1, 0, 2))).ignoreVines();
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, AurorianMod.prefix(name));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest ruleTest = new BlockMatchTest(TABlocks.AURORIAN_STONE.get());
        BlockState wickGrass = TABlocks.WICK_GRASS.get().defaultBlockState();
        BlockState blueberryBush = TABlocks.BLUEBERRY_BUSH.get().defaultBlockState();
        SimpleWeightedRandomList.Builder<BlockState> wickGrassBuilder = SimpleWeightedRandomList.builder();
        WickGrass.LEVEL.getPossibleValues().forEach(level -> wickGrassBuilder.add(wickGrass.setValue(WickGrass.LEVEL, level), 1));
        Holder<PlacedFeature> silentTreeLikeSpruce = context.lookup(Registries.PLACED_FEATURE).getOrThrow(TAPlacements.SILENT_TREE);
        SimpleWeightedRandomList.Builder<BlockState> clusterBuilder = SimpleWeightedRandomList.builder();
        for (Block block : TACommonUtils.getKnownBlocks()) {
            if (block instanceof TAClusterBlock clusterBlock) {
                BlockState state = clusterBlock.defaultBlockState().setValue(TAClusterBlock.WATERLOGGED, Boolean.FALSE).setValue(TAClusterBlock.FACING, Direction.UP);
                TAClusterBlock.LEVEL.getPossibleValues().forEach(level -> clusterBuilder.add(state.setValue(TAClusterBlock.LEVEL, level), 1));
            }
        }

        FeatureUtils.register(context, PATCH_AURORIAN_GRASS, Feature.RANDOM_PATCH, VegetationFeatures.grassPatch(
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(TABlocks.AURORIAN_GRASS.get().defaultBlockState(), 1)
                        .add(TABlocks.TALL_AURORIAN_GRASS.get().defaultBlockState()
                                .setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 1)), 32));
        FeatureUtils.register(context, PATCH_AURORIAN_GRASS_LIGHT, Feature.RANDOM_PATCH, VegetationFeatures.grassPatch(
                BlockStateProvider.simple(TABlocks.AURORIAN_GRASS_LIGHT.get()), 32));
        FeatureUtils.register(context, PATCH_AURORIAN_FLOWER, Feature.FLOWER, VegetationFeatures.grassPatch(
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(TABlocks.PETUNIA_PLANT.get().defaultBlockState(), 1)
                        .add(TABlocks.SILK_BERRY_CROP.get().defaultBlockState(), 1)
                        .add(TABlocks.AURORIAN_FLOWER_1.get().defaultBlockState(), 1)
                        .add(TABlocks.AURORIAN_FLOWER_2.get().defaultBlockState(), 1)
                        .add(TABlocks.AURORIAN_FLOWER_3.get().defaultBlockState(), 1)
                        .add(blueberryBush.setValue(BlueBerryBush.AGE, 0), 1)
                        .add(blueberryBush.setValue(BlueBerryBush.AGE, 1), 1)
                        .add(blueberryBush.setValue(BlueBerryBush.AGE, 2), 1)
                        .add(blueberryBush.setValue(BlueBerryBush.AGE, 3), 1).build()), 10));
        FeatureUtils.register(context, PATCH_EQUINOX_FLOWER, Feature.FLOWER, VegetationFeatures.grassPatch(
                BlockStateProvider.simple(TABlocks.EQUINOX_FLOWER.get()), 16));
        FeatureUtils.register(context, PATCH_LAVENDER, Feature.FLOWER, VegetationFeatures.grassPatch(
                new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(TABlocks.LAVENDER_PLANT.get().defaultBlockState(), 1)
                        .add(TABlocks.TALL_LAVENDER_PLANT.get().defaultBlockState()
                                .setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), 1)), 32));
        FeatureUtils.register(context, TREES_AURORIAN_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
                List.of(new WeightedPlacedFeature(silentTreeLikeSpruce, 0.3F)), silentTreeLikeSpruce));
        FeatureUtils.register(context, HUGE_INDIGO_MUSHROOM, TAFeatures.HUGE_INDIGO_MUSHROOM.get(),
                new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(TABlocks.INDIGO_MUSHROOM_BLOCK.get()),
                BlockStateProvider.simple(TABlocks.INDIGO_MUSHROOM_STEM.get()), 5));
        FeatureUtils.register(context, RANDOM_FALLEN_SILENT_LOG, TAFeatures.RANDOM_FALLEN_LOGS.get(), new FallenLogConfig(BlockStateProvider.simple(TABlocks.SILENT_TREE_LOG.get()), (0.005F)));
        FeatureUtils.register(context, RANDOM_CRYSTAL_CLUSTER, Feature.FLOWER, VegetationFeatures.grassPatch(new WeightedStateProvider(clusterBuilder.build()), 10));
        FeatureUtils.register(context, RANDOM_WEAK_GRASS, Feature.FLOWER, VegetationFeatures.grassPatch(new WeightedStateProvider(wickGrassBuilder.build()), 6));
        FeatureUtils.register(context, WATER_GRASS_SIMPLE, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(TABlocks.AURORIAN_WATER_GRASS.get())));
        FeatureUtils.register(context, WATER_GRASS_SLIGHTLY_LESS_SHORT, TAFeatures.WATER_GRASS.get(), new ProbabilityFeatureConfiguration(0.4F));
        FeatureUtils.register(context, WATER_GRASS_SHORT, TAFeatures.WATER_GRASS.get(), new ProbabilityFeatureConfiguration(0.3F));
        FeatureUtils.register(context, WATER_GRASS_MID, TAFeatures.WATER_GRASS.get(), new ProbabilityFeatureConfiguration(0.6F));
        FeatureUtils.register(context, WATER_GRASS_TALL, TAFeatures.WATER_GRASS.get(), new ProbabilityFeatureConfiguration(0.8F));
        FeatureUtils.register(context, SILENT_TREE, Feature.TREE, silentTree().dirt(BlockStateProvider.simple(TABlocks.AURORIAN_DIRT.get()))
                .decorators(ImmutableList.of(new CrystalBudDecorator(0.05F))).ignoreVines().build());
        FeatureUtils.register(context, ORE_AURORIAN_PERIDOTITE, Feature.ORE, new OreConfiguration(ruleTest, TABlocks.AURORIAN_PERIDOTITE.get().defaultBlockState(), 33));
        FeatureUtils.register(context, ORE_AURORIAN_DIRT, Feature.ORE, new OreConfiguration(ruleTest, TABlocks.AURORIAN_DIRT.get().defaultBlockState(), 33));
        FeatureUtils.register(context, ORE_AURORIAN_COAL, Feature.ORE, new OreConfiguration(ruleTest, TABlocks.AURORIAN_COAL_ORE.get().defaultBlockState(), 12));
        FeatureUtils.register(context, ORE_MOONSTONE, Feature.ORE, new OreConfiguration(ruleTest, TABlocks.MOONSTONE_ORE.get().defaultBlockState(), 9));
        FeatureUtils.register(context, ORE_CERULEAN, Feature.ORE, new OreConfiguration(ruleTest, TABlocks.CERULEAN_ORE.get().defaultBlockState(), 7));
        FeatureUtils.register(context, ORE_GEODE, Feature.ORE, new OreConfiguration(ruleTest, TABlocks.GEODE_ORE.get().defaultBlockState(), 5));
        FeatureUtils.register(context, RANDOM_URN, TAFeatures.RANDOM_URN.get());
    }

}