package cn.teampancake.theaurorian.common.level.biome;

import cn.teampancake.theaurorian.common.level.chunk.TATerrainColumn;
import cn.teampancake.theaurorian.registry.TABiomes;
import it.unimi.dsi.fastutil.floats.Float2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectSortedMap;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.List;
import java.util.function.Consumer;

public class TABiomeBuilder {

    public static List<TATerrainColumn> makeBiomeList(HolderGetter<Biome> biomeRegistry, Holder<Biome> undergroundBiome) {
        return List.of(
                biomeColumnWithUnderground(0.05F, 0.1F, biomeRegistry, TABiomes.AURORIAN_PLAINS, undergroundBiome),
                biomeColumnWithUnderground(0.06F, 0.1F, biomeRegistry, TABiomes.AURORIAN_FOREST, undergroundBiome),
                biomeColumnWithUnderground(0.07F, 0.05F, biomeRegistry, TABiomes.WEEPING_WILLOW_FOREST, undergroundBiome),
                biomeColumnWithUnderground(0.06F, 0.03F, biomeRegistry, TABiomes.MOON_DESERT, undergroundBiome),
                biomeColumnWithUnderground(-1.65F, 0.25F, biomeRegistry, TABiomes.AURORIAN_RIVER, undergroundBiome)
        );
    }

    private static TATerrainColumn biomeColumnWithUnderground(float noiseDepth, float noiseScale, HolderGetter<Biome> biomeRegistry, ResourceKey<Biome> key, Holder<Biome> undergroundBiome) {
        Holder.Reference<Biome> biomeHolder = biomeRegistry.getOrThrow(key);
        biomeHolder.bindKey(key);
        return makeColumn(noiseDepth, noiseScale, biomeHolder, treeMap -> {
            treeMap.put(Math.min(noiseDepth - 1, -1), biomeHolder);
            treeMap.put(Math.min(noiseDepth - 3, -3), undergroundBiome);
        });
    }

    private static TATerrainColumn makeColumn(float noiseDepth, float noiseScale, Holder<Biome> biomeHolder, Consumer<Float2ObjectSortedMap<Holder<Biome>>> layerBuilder) {
        return new TATerrainColumn(biomeHolder, Util.make(new Float2ObjectAVLTreeMap<>(), layerBuilder), noiseDepth, noiseScale);
    }

}