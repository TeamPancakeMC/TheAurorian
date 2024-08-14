package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class TABiomeModifiers {

    public static final ResourceKey<BiomeModifier> MOON_ACOLYTE_SPAWN = createKey("moon_acolyte_spawn");
    public static final ResourceKey<BiomeModifier> SPIRIT_SPAWN = createKey("spirit_spawn");

    private static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, TheAurorian.prefix(name));
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderSet.Named<Biome> biomesHolderSet = context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD);
        MobSpawnSettings.SpawnerData moonAcolyteSpawn = new MobSpawnSettings.SpawnerData(TAEntityTypes.MOON_ACOLYTE.get(), 70, 1, 2);
        MobSpawnSettings.SpawnerData spiritSpawn = new MobSpawnSettings.SpawnerData(TAEntityTypes.SPIRIT.get(), 90, 1, 2);
        context.register(MOON_ACOLYTE_SPAWN, new BiomeModifiers.AddSpawnsBiomeModifier(biomesHolderSet, List.of(moonAcolyteSpawn)));
        context.register(SPIRIT_SPAWN, new BiomeModifiers.AddSpawnsBiomeModifier(biomesHolderSet, List.of(spiritSpawn)));
    }

}