package cn.teampancake.theaurorian.common.registry;

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
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;
import java.util.OptionalLong;

import static net.minecraft.world.level.levelgen.SurfaceRules.*;

@SuppressWarnings("SpellCheckingInspection")
public class TADimensions {

    public static long seed;
    public static final ResourceKey<NoiseGeneratorSettings> AURORIAN_NOISE_SETTINGS = ResourceKey.create(Registries.NOISE_SETTINGS, AurorianMod.prefix("the_aurorian_noise"));
    public static final ResourceKey<DimensionType> AURORIAN_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, AurorianMod.prefix("the_aurorian_type"));
    public static final ResourceKey<LevelStem> AURORIAN_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, AurorianMod.prefix("the_aurorian"));
    public static final ResourceKey<Level> AURORIAN_DIMENSION = ResourceKey.create(Registries.DIMENSION, AurorianMod.prefix("the_aurorian"));

    public static void bootstrapNoise(BootstapContext<NoiseGeneratorSettings> context) {
        NoiseGeneratorSettings settings = new NoiseGeneratorSettings(NoiseSettings.OVERWORLD_NOISE_SETTINGS,
                TABlocks.AURORIAN_STONE.get().defaultBlockState(), Blocks.WATER.defaultBlockState(),
                noiseCaveRouter(context.lookup(Registries.DENSITY_FUNCTION), context.lookup(Registries.NOISE)),
                createSurfaceRule(), List.of(), 62, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
        context.register(AURORIAN_NOISE_SETTINGS, settings);
    }

    public static void bootstrapType(BootstapContext<DimensionType> context) {
        DimensionType dimensionType = new DimensionType(OptionalLong.empty(), Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, 1.0D,
                Boolean.TRUE, Boolean.FALSE, -64, 384, 384, BlockTags.INFINIBURN_OVERWORLD, AurorianMod.prefix("aurorian"), 0.0F,
                new DimensionType.MonsterSettings(Boolean.FALSE, Boolean.FALSE, UniformInt.of(ConstantInt.ZERO.getValue(), 7), ConstantInt.ZERO.getValue()));
        context.register(AURORIAN_DIMENSION_TYPE, dimensionType);
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biome = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimensionType = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
        NoiseBasedChunkGenerator chunkGenerator = new NoiseBasedChunkGenerator(new TABiomeSource(
                TABiomeBuilder.makeBiomeList(biome), -1.25F, 2.5F,
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
        RuleSource lightAurorianGrassBlock = SurfaceRuleData.makeStateRule(TABlocks.LIGHT_AURORIAN_GRASS_BLOCK.get());
        RuleSource snowaurorianGrassBlock = SurfaceRuleData.makeStateRule(TABlocks.SNOW_AURORIAN_GRASS_BLOCK.get());
        RuleSource redAurorianGrassBlock = SurfaceRuleData.makeStateRule(TABlocks.RED_AURORIAN_GRASS_BLOCK.get());
        RuleSource brightMoonSand = SurfaceRuleData.makeStateRule(TABlocks.BRIGHT_MOON_SAND.get());
        RuleSource brightMoonSandstone = SurfaceRuleData.makeStateRule(TABlocks.BRIGHT_MOON_SANDSTONE.get());
        ConditionSource notUnderWater = waterBlockCheck(-1, ConstantInt.ZERO.getValue());
        ConditionSource notUnderDeepWater = waterStartCheck(-6, (-1));
        RuleSource overworldLike = sequence(
                ifTrue(ON_FLOOR, sequence(ifTrue(notUnderWater, sequence(
                        ifTrue(isBiome(TABiomes.WEEPING_WILLOW_FOREST), lightAurorianGrassBlock),
                        ifTrue(isBiome(TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD), snowaurorianGrassBlock),
                        ifTrue(isBiome(TABiomes.EQUINOX_FLOWER_PLAINS), redAurorianGrassBlock),
                        ifTrue(isBiome(TABiomes.BRIGHT_MOON_DESERT), brightMoonSand), aurorianGrassBlock)))),
                ifTrue(notUnderDeepWater, sequence(ifTrue(UNDER_FLOOR, sequence(
                        ifTrue(isBiome(TABiomes.BRIGHT_MOON_DESERT), brightMoonSandstone), aurorianDirt)))));
        RuleSource bedrockFloor = ifTrue(verticalGradient("bedrock_floor", VerticalAnchor.bottom(),
                VerticalAnchor.aboveBottom(5)), SurfaceRuleData.BEDROCK);
        builder.add(bedrockFloor).add(overworldLike);
        return sequence(builder.build().toArray(RuleSource[]::new));
    }

    private static NoiseRouter noiseCaveRouter(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noiseParameters) {
        DensityFunction densityfunction10 = NoiseRouterData.noiseGradientDensity(
                DensityFunctions.cache2d(NoiseRouterData.getFunction(densityFunctions, NoiseRouterData.FACTOR)),
                NoiseRouterData.getFunction(densityFunctions, NoiseRouterData.DEPTH));
        DensityFunction slopedCheeseFunction = NoiseRouterData.getFunction(densityFunctions, NoiseRouterData.SLOPED_CHEESE);
        DensityFunction densityfunction12 = DensityFunctions.min(slopedCheeseFunction,
                DensityFunctions.mul(DensityFunctions.constant((5.0D)),
                NoiseRouterData.getFunction(densityFunctions, NoiseRouterData.ENTRANCES)));
        DensityFunction densityfunction13 = DensityFunctions.rangeChoice(slopedCheeseFunction, (-1000000.0D), (1.5625D),
                densityfunction12, NoiseRouterData.underground(densityFunctions, noiseParameters, slopedCheeseFunction));
        DensityFunction densityfunction14 = DensityFunctions.min(NoiseRouterData.postProcess(
                NoiseRouterData.slideOverworld(Boolean.FALSE, densityfunction13)),
                NoiseRouterData.getFunction(densityFunctions, NoiseRouterData.NOODLE));
        return new NoiseRouter(DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(),
                DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(),
                DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero(),
                NoiseRouterData.slideOverworld(Boolean.FALSE, DensityFunctions.add(densityfunction10,
                        DensityFunctions.constant((-0.703125D))).clamp((-64.0D), (64.0D))),
                densityfunction14, DensityFunctions.zero(), DensityFunctions.zero(), DensityFunctions.zero());
    }

}