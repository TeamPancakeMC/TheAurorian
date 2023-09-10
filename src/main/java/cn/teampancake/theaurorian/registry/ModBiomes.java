package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

@SuppressWarnings("SpellCheckingInspection")
public class ModBiomes {

    public static final ResourceKey<Biome> AURORIAN_FOREST = makeKey("aurorian_forest");
    public static final ResourceKey<Biome> AURORIAN_PLAINS = makeKey("aurorian_plains");
    public static final ResourceKey<Biome> AURORIAN_FOREST_HILLS = makeKey("aurorian_forest_hills");
    public static final ResourceKey<Biome> WEEPING_WILLOW_FOREST = makeKey("weeping_willow_forest");

    private static ResourceKey<Biome> makeKey(String name) {
        return ResourceKey.create(Registries.BIOME, AurorianMod.prefix(name));
    }

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> featureGetter = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carverGetter = context.lookup(Registries.CONFIGURED_CARVER);
        BiomeGenerationSettings.Builder biomes = new BiomeGenerationSettings.Builder(featureGetter, carverGetter);
        context.register(AURORIAN_FOREST, biomeWithDefaults(biomes).build());
        context.register(AURORIAN_PLAINS, biomeWithDefaults(biomes).build());
        context.register(AURORIAN_FOREST_HILLS, biomeWithDefaults(biomes).build());
        context.register(WEEPING_WILLOW_FOREST, biomeWithDefaults(biomes).build());
    }

    private static Biome.BiomeBuilder biomeWithDefaults(BiomeGenerationSettings.Builder biomeGenerationSettings) {
        return new Biome.BiomeBuilder().hasPrecipitation(Boolean.FALSE).temperature(0.2F).downfall(0.0F)
                .specialEffects(defaultAmbientBuilder().build()).mobSpawnSettings(defaultMobSpawning().build())
                .generationSettings(biomeGenerationSettings.build()).temperatureAdjustment(Biome.TemperatureModifier.NONE);
    }

    private static BiomeSpecialEffects.Builder defaultAmbientBuilder() {
        return new BiomeSpecialEffects.Builder().fogColor(0xC0FFD8).waterColor(0xFFFFFF)
                .waterFogColor(0xFFFFFF).skyColor(OverworldBiomes.calculateSkyColor(0.2F));
    }

    private static MobSpawnSettings.Builder defaultMobSpawning() {
        MobSpawnSettings.Builder spawnInfo = new MobSpawnSettings.Builder();
        spawnInfo.creatureGenerationProbability(0.3F);
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.AURORIAN_RABBIT.get(), 4, 1, 2));
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.AURORIAN_SHEEP.get(), 5, 1, 3));
        spawnInfo.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.AURORIAN_PIG.get(), 5, 1, 3));
        spawnInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.CRYSTALLINE_SPRITE.get(), 65, 2, 2));
        spawnInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.DISTURBED_HOLLOW.get(), 95, 1, 4));
        spawnInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.MOON_ACOLYTE.get(), 35, 1, 2));
        spawnInfo.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.SPIRIT.get(), 2, 1, 2));
        return spawnInfo;
    }

}