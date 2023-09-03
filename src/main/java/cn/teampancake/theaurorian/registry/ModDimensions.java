package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.biome.AurorianBiomeBuilder;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.OptionalLong;
import java.util.function.Function;

@SuppressWarnings("SpellCheckingInspection")
public class ModDimensions {

    public static final ResourceKey<NoiseGeneratorSettings> AURORIAN_NOISE_SETTINGS = ResourceKey.create(Registries.NOISE_SETTINGS, AurorianMod.prefix("aurorian_noise"));
    public static final ResourceKey<DimensionType> AURORIAN_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, AurorianMod.prefix("the_aurorian_type"));
    public static final ResourceKey<LevelStem> AURORIAN_LEVEL_STEM =  ResourceKey.create(Registries.LEVEL_STEM, AurorianMod.prefix("the_aurorian"));
    public static final ResourceKey<Level> AURORIAN_DIMENSION = ResourceKey.create(Registries.DIMENSION, AURORIAN_LEVEL_STEM.location());

    public static final MultiNoiseBiomeSourceParameterList.Preset AURORIAN =
            new MultiNoiseBiomeSourceParameterList.Preset(AurorianMod.prefix("aurorian"),
            new MultiNoiseBiomeSourceParameterList.Preset.SourceProvider() {
        public <T> Climate.@NotNull ParameterList<T> apply(@NotNull Function<ResourceKey<Biome>, T> function) {
            ImmutableList.Builder<Pair<Climate.ParameterPoint, T>> builder = ImmutableList.builder();
            (new AurorianBiomeBuilder()).addBiomes((pair) -> builder.add(pair.mapSecond(function)));
            return new Climate.ParameterList<>(builder.build());
        }
    });

    public static void bootstrapNoise(BootstapContext<NoiseGeneratorSettings> context) {
        NoiseGeneratorSettings settings = new NoiseGeneratorSettings(NoiseSettings.OVERWORLD_NOISE_SETTINGS, Blocks.STONE.defaultBlockState(),
                ModBlocks.MOON_WATER.get().defaultBlockState(), NoiseRouterData.overworld(context.lookup(Registries.DENSITY_FUNCTION),
                context.lookup(Registries.NOISE), Boolean.FALSE, Boolean.FALSE), createSurfaceRule(),
                List.of(), 63, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        context.register(AURORIAN_NOISE_SETTINGS, settings);
    }

    public static void bootstrapType(BootstapContext<DimensionType> context) {
        DimensionType dimensionType = new DimensionType(OptionalLong.empty(), Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, 1.0D,
                Boolean.TRUE, Boolean.FALSE, -64, 384, 384, BlockTags.INFINIBURN_OVERWORLD, BuiltinDimensionTypes.OVERWORLD_EFFECTS, 0.0F,
                new DimensionType.MonsterSettings(Boolean.FALSE, Boolean.TRUE, UniformInt.of(ConstantInt.ZERO.getValue(), 7), ConstantInt.ZERO.getValue()));
        context.register(AURORIAN_DIMENSION_TYPE, dimensionType);
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biome = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimensionType = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
        MultiNoiseBiomeSource biomeSource = MultiNoiseBiomeSource.createFromList(AURORIAN.provider().apply(biome::getOrThrow));
        NoiseBasedChunkGenerator chunkGenerator = new NoiseBasedChunkGenerator(biomeSource, noiseSettings.getOrThrow(AURORIAN_NOISE_SETTINGS));
        LevelStem levelStem = new LevelStem(dimensionType.getOrThrow(AURORIAN_DIMENSION_TYPE), chunkGenerator);
        context.register(AURORIAN_LEVEL_STEM, levelStem);
    }

    private static SurfaceRules.RuleSource createSurfaceRule() {
        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
        SurfaceRules.RuleSource forestRules = createNormalRuleSource(ModBiomes.AURORIAN_FOREST, ModBlocks.AURORIAN_GRASS_BLOCK.get());
        SurfaceRules.RuleSource plainRules = createNormalRuleSource(ModBiomes.AURORIAN_PLAINS, ModBlocks.AURORIAN_GRASS_BLOCK.get());
        SurfaceRules.RuleSource forestHillsRules = createNormalRuleSource(ModBiomes.AURORIAN_FOREST_HILLS, ModBlocks.AURORIAN_GRASS_BLOCK.get());
        SurfaceRules.RuleSource weepingWillowForestRules = createNormalRuleSource(ModBiomes.WEEPING_WILLOW_FOREST, ModBlocks.AURORIAN_GRASS_LIGHT_BLOCK.get());
        builder.add(forestRules, plainRules, forestHillsRules, weepingWillowForestRules);
        return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
    }

    private static SurfaceRules.RuleSource createNormalRuleSource(ResourceKey<Biome> resourceKey, Block surfaceBlock) {
        return SurfaceRules.ifTrue(SurfaceRules.isBiome(resourceKey), SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(),
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(
                        SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, ConstantInt.ZERO.getValue()),
                                SurfaceRuleData.makeStateRule(surfaceBlock)), SurfaceRuleData.makeStateRule(ModBlocks.AURORIAN_DIRT.get()))),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRuleData.makeStateRule(ModBlocks.AURORIAN_DIRT.get())))));
    }

}