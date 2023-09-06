package cn.teampancake.theaurorian.common.level.feature;

import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.registry.ModFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_AURORIAN_GRASS = FeatureUtils.createKey("patch_aurorian_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_AURORIAN_GRASS_LIGHT = FeatureUtils.createKey("patch_aurorian_grass_light");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_AURORIAN_FLOWER = FeatureUtils.createKey("patch_aurorian_flower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RANDOM_URNS = FeatureUtils.createKey("random_urns");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(context, PATCH_AURORIAN_GRASS, Feature.RANDOM_PATCH, VegetationFeatures.grassPatch(BlockStateProvider.simple(ModBlocks.AURORIAN_GRASS.get()), 32));
        FeatureUtils.register(context, PATCH_AURORIAN_GRASS_LIGHT, Feature.RANDOM_PATCH, VegetationFeatures.grassPatch(BlockStateProvider.simple(ModBlocks.AURORIAN_GRASS_LIGHT.get()), 32));
        FeatureUtils.register(context, PATCH_AURORIAN_FLOWER, Feature.FLOWER, new RandomPatchConfiguration(96, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new NoiseProvider(2345L,
                        new NormalNoise.NoiseParameters(0, 1.0D), 0.020833334F, List.of(ModBlocks.LAVENDER_CROP.get().defaultBlockState(),
                        ModBlocks.PETUNIA_PLANT.get().defaultBlockState(), ModBlocks.SILK_BERRY_CROP.get().defaultBlockState()))))));
        FeatureUtils.register(context, RANDOM_URNS, ModFeatures.RANDOM_URNS.get());
    }

}