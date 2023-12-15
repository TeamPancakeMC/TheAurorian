package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.placement.TAPlacements;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.awt.*;

@SuppressWarnings({"SpellCheckingInspection", "OptionalGetWithoutIsPresent"})
public class TABiomes {

    public static final ResourceKey<Biome> AURORIAN_FOREST = createKey("aurorian_forest");
    public static final ResourceKey<Biome> AURORIAN_PLAINS = createKey("aurorian_plains");
    public static final ResourceKey<Biome> AURORIAN_BEACH = createKey("aurorian_beach");
    public static final ResourceKey<Biome> AURORIAN_RIVER = createKey("aurorian_river");
    public static final ResourceKey<Biome> AURORIAN_LAKE = createKey("aurorian_lake");
    public static final ResourceKey<Biome> AURORIAN_FOREST_HILL = createKey("aurorian_forest_hill");
    public static final ResourceKey<Biome> EQUINOX_FLOWER_PLAINS = createKey("equinox_flower_plains");
    public static final ResourceKey<Biome> WEEPING_WILLOW_FOREST = createKey("weeping_willow_forest");
    public static final ResourceKey<Biome> BRIGHT_MOON_DESERT = createKey("bright_moon_desert");
    public static final ResourceKey<Biome> UNDERGROUND = createKey("underground");

    private static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(Registries.BIOME, AurorianMod.prefix(name));
    }

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> featureGetter = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carverGetter = context.lookup(Registries.CONFIGURED_CARVER);
        GenerationStep.Decoration vegetalDecoration = GenerationStep.Decoration.VEGETAL_DECORATION;
        context.register(AURORIAN_FOREST, biomeOfForests(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)
                .addFeature(vegetalDecoration, TAPlacements.TREES_AURORIAN_FOREST)).build());
        context.register(AURORIAN_FOREST_HILL, biomeOfForests(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)
                .addFeature(vegetalDecoration, TAPlacements.TREES_AURORIAN_FOREST)).build());
        context.register(AURORIAN_PLAINS, biomeWithParticle(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)
                .addFeature(vegetalDecoration, TAPlacements.PATCH_AURORIAN_FLOWER_PLAINS)
                .addFeature(vegetalDecoration, TAPlacements.PATCH_AURORIAN_GRASS_LIGHT_PLAINS)
                .addFeature(vegetalDecoration, TAPlacements.PATCH_AURORIAN_GRASS_PLAINS),
                ParticleTypes.FIREWORK, 0.00375F).build());
        context.register(AURORIAN_BEACH, biomeWithDefaults(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)).build());
        context.register(AURORIAN_RIVER, biomeWithDefaults(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)).build());
        context.register(AURORIAN_LAKE, biomeWithDefaults(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)).build());
        context.register(EQUINOX_FLOWER_PLAINS, biomeWithParticle(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)
                .addFeature(vegetalDecoration, TAPlacements.PATCH_EQUINOX_FLOWER), ParticleTypes.SOUL, 0.0025F).build());
        context.register(WEEPING_WILLOW_FOREST, biomeOfForests(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)).build());
        context.register(BRIGHT_MOON_DESERT, biomeWithDefaults(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)).build());
        context.register(UNDERGROUND, biomeWithDefaults(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)).build());
    }

    private static Biome.BiomeBuilder biomeOfForests(BiomeGenerationSettings.Builder biomeGenerationSettings) {
        GenerationStep.Decoration vegetalDecoration = GenerationStep.Decoration.VEGETAL_DECORATION;
        return biomeWithParticle(biomeGenerationSettings
                        .addFeature(vegetalDecoration, TAPlacements.PATCH_AURORIAN_FLOWER_FOREST)
                        .addFeature(vegetalDecoration, TAPlacements.PATCH_AURORIAN_GRASS_LIGHT_FOREST)
                        .addFeature(vegetalDecoration, TAPlacements.PATCH_AURORIAN_GRASS_FOREST),
                ParticleTypes.FIREWORK, 0.00625F);
    }

    private static Biome.BiomeBuilder biomeWithDefaults(BiomeGenerationSettings.Builder biomeGenerationSettings) {
        return new Biome.BiomeBuilder().hasPrecipitation(Boolean.FALSE).temperature(0.2F).downfall(0.0F)
                .specialEffects(defaultAmbientBuilder().build()).mobSpawnSettings(defaultMobSpawning().build())
                .generationSettings(defaultOreBuilder(biomeGenerationSettings).build())
                .temperatureAdjustment(Biome.TemperatureModifier.NONE);
    }

    private static Biome.BiomeBuilder biomeWithParticle(
            BiomeGenerationSettings.Builder biomeGenerationSettings, ParticleOptions options, float probability) {
        return new Biome.BiomeBuilder().hasPrecipitation(Boolean.FALSE).temperature(0.2F).downfall(0.0F)
                .specialEffects(defaultAmbientWithParticleBuilder(options, probability).build())
                .mobSpawnSettings(defaultMobSpawning().build())
                .generationSettings(defaultOreBuilder(biomeGenerationSettings).build())
                .temperatureAdjustment(Biome.TemperatureModifier.NONE);
    }

    private static BiomeGenerationSettings.Builder defaultOreBuilder(BiomeGenerationSettings.Builder biomeGenerationSettings) {
        GenerationStep.Decoration undergroundOre = GenerationStep.Decoration.UNDERGROUND_ORES;
        return biomeGenerationSettings.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND)
                .addFeature(undergroundOre, TAPlacements.ORE_AURORIAN_PERIDOTITE)
                .addFeature(undergroundOre, TAPlacements.ORE_AURORIAN_DIRT)
                .addFeature(undergroundOre, TAPlacements.ORE_AURORIAN_COAL)
                .addFeature(undergroundOre, TAPlacements.ORE_MOONSTONE)
                .addFeature(undergroundOre, TAPlacements.ORE_CERULEAN)
                .addFeature(undergroundOre, TAPlacements.ORE_GEODE);
    }

    private static BiomeSpecialEffects.Builder defaultAmbientBuilder() {
        return new BiomeSpecialEffects.Builder().fogColor(0xC0FFD8).waterColor(Color.WHITE.getRGB())
                .waterFogColor(Color.WHITE.getRGB()).skyColor(OverworldBiomes.calculateSkyColor(0.2F))
                .backgroundMusic(new Music(TASoundEvents.BACKGROUND_MUSIC.getHolder().get(),
                        Musics.THIRTY_SECONDS, Musics.TEN_MINUTES, Boolean.FALSE));
    }

    private static BiomeSpecialEffects.Builder defaultAmbientWithParticleBuilder(ParticleOptions options, float probability) {
        return defaultAmbientBuilder().ambientParticle(new AmbientParticleSettings(options, probability));
    }

    private static MobSpawnSettings.Builder defaultMobSpawning() {
        MobSpawnSettings.Builder spawnInfo = new MobSpawnSettings.Builder();
        spawnInfo.creatureGenerationProbability(0.3F);
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TAEntityTypes.AURORIAN_RABBIT.get(), 4, 1, 2));
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TAEntityTypes.AURORIAN_SHEEP.get(), 5, 1, 3));
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TAEntityTypes.AURORIAN_PIG.get(), 5, 1, 3));
        spawnInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TAEntityTypes.CRYSTALLINE_SPRITE.get(), 65, 2, 2));
        spawnInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TAEntityTypes.DISTURBED_HOLLOW.get(), 45, 1, 4));
        spawnInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TAEntityTypes.MOON_ACOLYTE.get(), 35, 1, 2));
        spawnInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TAEntityTypes.SPIRIT.get(), 2, 1, 2));
        return spawnInfo;
    }

}