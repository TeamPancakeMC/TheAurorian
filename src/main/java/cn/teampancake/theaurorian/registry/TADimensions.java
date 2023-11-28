package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.biome.TABiomeBuilder;
import cn.teampancake.theaurorian.common.level.biome.TABiomeSource;
import cn.teampancake.theaurorian.common.level.chunk.TAChunkGenerator;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import java.util.List;
import java.util.OptionalLong;

import static net.minecraft.world.level.levelgen.SurfaceRules.*;

@SuppressWarnings("SpellCheckingInspection")
public class TADimensions {

    public static final ResourceKey<NoiseGeneratorSettings> AURORIAN_NOISE_SETTINGS = ResourceKey.create(Registries.NOISE_SETTINGS, AurorianMod.prefix("the_aurorian_noise"));
    public static final ResourceKey<DimensionType> AURORIAN_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, AurorianMod.prefix("the_aurorian_type"));
    public static final ResourceKey<LevelStem> AURORIAN_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, AurorianMod.prefix("the_aurorian"));
    public static final ResourceKey<Level> AURORIAN_DIMENSION = ResourceKey.create(Registries.DIMENSION, AurorianMod.prefix("the_aurorian"));

    public static void bootstrapNoise(BootstapContext<NoiseGeneratorSettings> context) {
        NoiseGeneratorSettings settings = new NoiseGeneratorSettings(NoiseSettings.OVERWORLD_NOISE_SETTINGS,
                TABlocks.AURORIAN_STONE.get().defaultBlockState(), Blocks.WATER.defaultBlockState(), NoiseRouterData.none(),
                createSurfaceRule(), List.of(), 3, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
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
        NoiseBasedChunkGenerator chunkGenerator = new NoiseBasedChunkGenerator(new TABiomeSource(
                TABiomeBuilder.makeBiomeList(biome, biome.getOrThrow(TABiomes.UNDERGROUND)), -1.25F, 2.5F,
                context.lookup(TABiomeLayerStack.BIOME_STACK_KEY).getOrThrow(TABiomeLayerStack.BIOMES_ALONG_STREAMS)),
                noiseSettings.getOrThrow(AURORIAN_NOISE_SETTINGS));
        LevelStem levelStem = new LevelStem(dimensionType.getOrThrow(AURORIAN_DIMENSION_TYPE),
                new TAChunkGenerator(chunkGenerator, noiseSettings.getOrThrow(AURORIAN_NOISE_SETTINGS)));
        context.register(AURORIAN_LEVEL_STEM, levelStem);
    }

    private static RuleSource createSurfaceRule() {
        ImmutableList.Builder<RuleSource> builder = ImmutableList.builder();
        RuleSource aurorianDirt = SurfaceRuleData.makeStateRule(TABlocks.AURORIAN_DIRT.get());
        RuleSource aurorianGrassBlock = SurfaceRuleData.makeStateRule(TABlocks.AURORIAN_GRASS_BLOCK.get());
        RuleSource aurorianGrassLightBlock = SurfaceRuleData.makeStateRule(TABlocks.AURORIAN_GRASS_LIGHT_BLOCK.get());
        RuleSource moonSand = SurfaceRuleData.makeStateRule(TABlocks.MOON_SAND.get());
        RuleSource moonSandstone1 = SurfaceRuleData.makeStateRule(TABlocks.MOON_SAND_STONE_1.get());
        RuleSource moonSandstone2 = SurfaceRuleData.makeStateRule(TABlocks.MOON_SAND_STONE_2.get());
        RuleSource moonSandstone3 = SurfaceRuleData.makeStateRule(TABlocks.MOON_SAND_STONE_3.get());
        ConditionSource notUnderWater = waterBlockCheck(-1, ConstantInt.ZERO.getValue());
//        ConditionSource notUnderDeepWater = waterStartCheck(-6, (-1));
        RuleSource overworldLike = sequence(
                ifTrue(ON_FLOOR, sequence(
                        ifTrue(isBiome(TABiomes.AURORIAN_RIVER),
                                sequence(ifTrue(ON_CEILING, aurorianDirt), ifTrue(notUnderWater, aurorianGrassBlock), aurorianDirt)),
                        ifTrue(notUnderWater, sequence(
                                ifTrue(isBiome(TABiomes.AURORIAN_PLAINS, TABiomes.AURORIAN_FOREST), aurorianGrassBlock),
                                ifTrue(isBiome(TABiomes.WEEPING_WILLOW_FOREST), aurorianGrassLightBlock),
                                ifTrue(isBiome(TABiomes.MOON_DESERT), moonSand))),
                ifTrue(notUnderWater, sequence(ifTrue(UNDER_FLOOR, sequence(
                        ifTrue(not(isBiome(TABiomes.MOON_DESERT)), aurorianDirt))),
                        ifTrue(isBiome(TABiomes.MOON_DESERT), sequence(ifTrue(UNDER_FLOOR, moonSandstone1),
                                ifTrue(stoneDepthCheck(0, Boolean.TRUE, (4), CaveSurface.FLOOR), moonSandstone2),
                                ifTrue(stoneDepthCheck(0, Boolean.TRUE, (6), CaveSurface.FLOOR), moonSandstone3)
                        )))))));
        RuleSource bedrockFloor = ifTrue(verticalGradient("bedrock_floor", VerticalAnchor.bottom(),
                VerticalAnchor.aboveBottom(5)), SurfaceRuleData.BEDROCK);
        builder.add(bedrockFloor).add(overworldLike);
        return sequence(builder.build().toArray(RuleSource[]::new));
    }

}