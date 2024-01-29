package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.EntityType;
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
    public static final ResourceKey<Biome> LAVENDER_PLAINS = createKey("lavender_plains");
    public static final ResourceKey<Biome> WEEPING_WILLOW_FOREST = createKey("weeping_willow_forest");
    public static final ResourceKey<Biome> BRIGHT_MOON_DESERT = createKey("bright_moon_desert");
    public static final ResourceKey<Biome> UNDERGROUND = createKey("underground");
    public static final ResourceKey<Biome> UNDERWATER = createKey("underwater");

    private static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(Registries.BIOME, AurorianMod.prefix(name));
    }

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> featureGetter = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carverGetter = context.lookup(Registries.CONFIGURED_CARVER);
        GenerationStep.Decoration vegetalDecoration = GenerationStep.Decoration.VEGETAL_DECORATION;
        context.register(AURORIAN_FOREST, biomeOfNormalForests(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)).build());
        context.register(AURORIAN_FOREST_HILL, biomeOfNormalForests(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)).build());
        context.register(AURORIAN_PLAINS, biomeWithParticle(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)
                .addFeature(vegetalDecoration, TAPlacedFeatures.PATCH_AURORIAN_FLOWER_PLAINS)
                .addFeature(vegetalDecoration, TAPlacedFeatures.PATCH_AURORIAN_GRASS_PLAINS), ParticleTypes.FIREWORK, 0.00375F).build());
        context.register(AURORIAN_BEACH, biomeWithDefaults(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)).build());
        context.register(AURORIAN_RIVER, biomeWithDefaults(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)
                .addFeature(vegetalDecoration, TAPlacedFeatures.RANDOM_WATER_SURFACE_PLANT)
                .addFeature(vegetalDecoration, TAPlacedFeatures.ORE_MOON_SAND_RIVER), defaultFishSpawning().build()).build());
        context.register(AURORIAN_LAKE, biomeWithDefaults(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)
                .addFeature(vegetalDecoration, TAPlacedFeatures.ORE_MOON_SAND_RIVER), defaultFishSpawning().build()).build());
        context.register(EQUINOX_FLOWER_PLAINS, biomeWithParticle(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)
                .addFeature(vegetalDecoration, TAPlacedFeatures.PATCH_EQUINOX_FLOWER), ParticleTypes.SOUL,
                equinoxFlowerPlainsMobSpawning().build(), 0.0025F).build());
        context.register(LAVENDER_PLAINS, biomeWithDefaults(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)
                .addFeature(vegetalDecoration, TAPlacedFeatures.PATCH_AURORIAN_GRASS_PLAINS)
                .addFeature(vegetalDecoration, TAPlacedFeatures.PATCH_LAVENDER)).build());
        context.register(WEEPING_WILLOW_FOREST, biomeWithDefaults(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)
                .addFeature(vegetalDecoration, TAPlacedFeatures.PATCH_AURORIAN_GRASS_LIGHT_FOREST)
                .addFeature(vegetalDecoration, TAPlacedFeatures.TREES_WEEPING_WILLOW_FOREST))
                .mobSpawnSettings(defaultMobSpawning().build()).build());
        context.register(BRIGHT_MOON_DESERT, biomeWithDefaults(new BiomeGenerationSettings.Builder(featureGetter, carverGetter))
                .mobSpawnSettings(desertMobSpawning().build()).build());
        context.register(UNDERGROUND, biomeWithDefaults(new BiomeGenerationSettings.Builder(featureGetter, carverGetter)).build());
        context.register(UNDERWATER, biomeWithDefaults(new BiomeGenerationSettings.Builder(featureGetter, carverGetter), defaultFishSpawning().build()).build());
    }

    private static Biome.BiomeBuilder biomeOfNormalForests(BiomeGenerationSettings.Builder biomeGenerationSettings) {
        GenerationStep.Decoration vegetalDecoration = GenerationStep.Decoration.VEGETAL_DECORATION;
        return biomeWithDefaults(biomeGenerationSettings
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, TAPlacedFeatures.SMALL_AURORIAN_FOREST_RUINS)
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, TAPlacedFeatures.MEDIUM_AURORIAN_FOREST_RUINS)
                .addFeature(vegetalDecoration, TAPlacedFeatures.PATCH_AURORIAN_FLOWER_FOREST)
                .addFeature(vegetalDecoration, TAPlacedFeatures.PATCH_AURORIAN_GRASS_FOREST)
                .addFeature(vegetalDecoration, TAPlacedFeatures.RANDOM_FALLEN_SILENT_LOG)
                .addFeature(vegetalDecoration, TAPlacedFeatures.TREES_AURORIAN_FOREST)
                .addFeature(vegetalDecoration, TAPlacedFeatures.RANDOM_CRYSTAL_CLUSTER)
                .addFeature(vegetalDecoration, TAPlacedFeatures.RANDOM_WEAK_GRASS))
                .mobSpawnSettings(defaultMobSpawning().addSpawn(MobCategory.CREATURE,
                        new MobSpawnSettings.SpawnerData(EntityType.ALLAY, (1), (1), (2))).build());
    }

    private static Biome.BiomeBuilder biomeWithDefaults(BiomeGenerationSettings.Builder biomeGenerationSettings) {
        return biomeWithDefaults(biomeGenerationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TAPlacedFeatures.RANDOM_RIVERSIDE_PLANT)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TAPlacedFeatures.RIVERSIDE_MOON_SAND), defaultMobSpawning().build());
    }

    private static Biome.BiomeBuilder biomeWithDefaults(BiomeGenerationSettings.Builder biomeGenerationSettings, MobSpawnSettings mobSpawnSettings) {
        return new Biome.BiomeBuilder().hasPrecipitation(Boolean.FALSE).temperature((0.2F)).downfall((0.0F))
                .specialEffects(defaultAmbientBuilder().build()).mobSpawnSettings(mobSpawnSettings)
                .generationSettings(defaultOreBuilder(biomeGenerationSettings).build());
    }

    private static Biome.BiomeBuilder biomeWithParticle(BiomeGenerationSettings.Builder biomeGenerationSettings, ParticleOptions options,MobSpawnSettings mobSpawnSettings, float probability) {
        return new Biome.BiomeBuilder().hasPrecipitation(Boolean.FALSE).temperature((0.2F)).downfall((0.0F))
                .specialEffects(defaultAmbientWithParticleBuilder(options, probability).build()).mobSpawnSettings(mobSpawnSettings)
                .generationSettings(defaultOreBuilder(biomeGenerationSettings).build());
    }

    private static Biome.BiomeBuilder biomeWithParticle(BiomeGenerationSettings.Builder biomeGenerationSettings, ParticleOptions options, float probability) {
        return biomeWithParticle(biomeGenerationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TAPlacedFeatures.RANDOM_RIVERSIDE_PLANT)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TAPlacedFeatures.RIVERSIDE_MOON_SAND),
                options, defaultMobSpawning().build(), probability);
    }

    private static BiomeGenerationSettings.Builder defaultOreBuilder(BiomeGenerationSettings.Builder biomeGenerationSettings) {
        GenerationStep.Decoration undergroundOre = GenerationStep.Decoration.UNDERGROUND_ORES;
        return biomeGenerationSettings.addCarver(GenerationStep.Carving.AIR, TAConfiguredCarvers.TA_CAVE_CONFIGURED)
                .addCarver(GenerationStep.Carving.AIR, TAConfiguredCarvers.TA_CAVE_EXTRA_UNDERGROUND_CONFIGURED)
                .addFeature(undergroundOre, TAPlacedFeatures.ORE_AURORIAN_GRANITE)
                .addFeature(undergroundOre, TAPlacedFeatures.ORE_AURORIAN_DIORITE)
                .addFeature(undergroundOre, TAPlacedFeatures.ORE_AURORIAN_ANDESITE)
                .addFeature(undergroundOre, TAPlacedFeatures.ORE_AURORIAN_PERIDOTITE)
                .addFeature(undergroundOre, TAPlacedFeatures.ORE_AURORIAN_DIRT)
                .addFeature(undergroundOre, TAPlacedFeatures.ORE_AURORIAN_COAL)
                .addFeature(undergroundOre, TAPlacedFeatures.ORE_MOONSTONE)
                .addFeature(undergroundOre, TAPlacedFeatures.ORE_CERULEAN)
                .addFeature(undergroundOre, TAPlacedFeatures.ORE_GEODE);
    }

    private static BiomeSpecialEffects.Builder defaultAmbientBuilder() {
        return new BiomeSpecialEffects.Builder().fogColor((0xC0FFD8)).waterColor(Color.WHITE.getRGB())
                .waterFogColor(Color.WHITE.getRGB()).skyColor(OverworldBiomes.calculateSkyColor((0.2F)))
                .backgroundMusic(new Music(TASoundEvents.BACKGROUND_MUSIC.getHolder().get(),
                        600, 12000, Boolean.FALSE));
    }

    private static BiomeSpecialEffects.Builder defaultAmbientWithParticleBuilder(ParticleOptions options, float probability) {
        return defaultAmbientBuilder().ambientParticle(new AmbientParticleSettings(options, probability));
    }

    private static MobSpawnSettings.Builder defaultMobSpawning() {
        MobSpawnSettings.Builder spawnInfo = new MobSpawnSettings.Builder();
        spawnInfo.creatureGenerationProbability(0.3F);
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TAEntityTypes.AURORIAN_RABBIT.get(), 5, 1, 3));
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TAEntityTypes.AURORIAN_SHEEP.get(), 5, 1, 3));
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TAEntityTypes.AURORIAN_PIG.get(), 5, 1, 3));
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(TAEntityTypes.AURORIAN_COW.get(), 5, 1, 3));
        spawnInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TAEntityTypes.CRYSTALLINE_SPRITE.get(), 65, 2, 2));
        spawnInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TAEntityTypes.DISTURBED_HOLLOW.get(), 35, 1, 4));
        spawnInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TAEntityTypes.MOON_ACOLYTE.get(), 35, 1, 2));
        return spawnInfo;
    }

    private static MobSpawnSettings.Builder desertMobSpawning(){
        MobSpawnSettings.Builder spawnInfo = new MobSpawnSettings.Builder();
        spawnInfo.creatureGenerationProbability(0.25F);
        spawnInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TAEntityTypes.CRYSTALLINE_SPRITE.get(), 65, 2, 2));
        spawnInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TAEntityTypes.DISTURBED_HOLLOW.get(), 35, 1, 4));
        spawnInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TAEntityTypes.MOON_ACOLYTE.get(), 35, 1, 2));
        return spawnInfo;
    }

    private static MobSpawnSettings.Builder equinoxFlowerPlainsMobSpawning() {
        MobSpawnSettings.Builder spawnInfo = new MobSpawnSettings.Builder();
        spawnInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TAEntityTypes.SPIRIT.get(), 2, 1, 2));
        return spawnInfo;
    }

    private static MobSpawnSettings.Builder defaultFishSpawning() {
        MobSpawnSettings.Builder spawnInfo = new MobSpawnSettings.Builder();
        spawnInfo.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(TAEntityTypes.MOON_FISH.get(), 5, 2, 4));
        spawnInfo.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(TAEntityTypes.AURORIAN_WINGED_FISH.get(), 5, 2, 4));
        return spawnInfo;
    }

}