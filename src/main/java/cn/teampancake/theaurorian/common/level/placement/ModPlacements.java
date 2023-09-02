package cn.teampancake.theaurorian.common.level.placement;

import cn.teampancake.theaurorian.common.level.feature.ModConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class ModPlacements {

    public static final ResourceKey<PlacedFeature> PATCH_AURORIAN_GRASS_PLAINS = PlacementUtils.createKey("patch_aurorian_grass_plains");
    public static final ResourceKey<PlacedFeature> PATCH_AURORIAN_GRASS_LIGHT_PLAINS = PlacementUtils.createKey("patch_aurorian_grass_light_plains");
    public static final ResourceKey<PlacedFeature> PATCH_AURORIAN_GRASS_FOREST = PlacementUtils.createKey("patch_aurorian_grass_forest");
    public static final ResourceKey<PlacedFeature> PATCH_AURORIAN_GRASS_LIGHT_FOREST = PlacementUtils.createKey("patch_aurorian_grass_light_forest");
    public static final ResourceKey<PlacedFeature> PATCH_AURORIAN_FLOWER_PLAINS = PlacementUtils.createKey("patch_aurorian_flower_plains");
    public static final ResourceKey<PlacedFeature> PATCH_AURORIAN_FLOWER_FOREST = PlacementUtils.createKey("patch_aurorian_flower_forest");
    public static final ResourceKey<PlacedFeature> RANDOM_URNS = PlacementUtils.createKey("random_urns");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeature = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> holder1 = configuredFeature.getOrThrow(ModConfiguredFeatures.PATCH_AURORIAN_GRASS);
        Holder<ConfiguredFeature<?, ?>> holder2 = configuredFeature.getOrThrow(ModConfiguredFeatures.PATCH_AURORIAN_GRASS_LIGHT);
        Holder<ConfiguredFeature<?, ?>> holder3 = configuredFeature.getOrThrow(ModConfiguredFeatures.PATCH_AURORIAN_FLOWER);
        PlacementUtils.register(context, PATCH_AURORIAN_GRASS_PLAINS, holder1,
                NoiseThresholdCountPlacement.of(-0.8D, 5, 10),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(context, PATCH_AURORIAN_GRASS_LIGHT_PLAINS, holder2,
                NoiseThresholdCountPlacement.of(-0.8D, 5, 10),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(context, PATCH_AURORIAN_GRASS_FOREST, holder1, VegetationPlacements.worldSurfaceSquaredWithCount(2));
        PlacementUtils.register(context, PATCH_AURORIAN_GRASS_LIGHT_FOREST, holder2, VegetationPlacements.worldSurfaceSquaredWithCount(2));
        PlacementUtils.register(context, PATCH_AURORIAN_FLOWER_FOREST, holder3, CountPlacement.of(3), RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(context, PATCH_AURORIAN_FLOWER_PLAINS, holder3, NoiseThresholdCountPlacement.of(-0.8D, 15, 4),
                RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(context, RANDOM_URNS, configuredFeature.getOrThrow(ModConfiguredFeatures.RANDOM_URNS));
    }

}