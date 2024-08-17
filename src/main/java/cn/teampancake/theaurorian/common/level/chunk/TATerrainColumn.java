package cn.teampancake.theaurorian.common.level.chunk;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.doubles.Double2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectSortedMap;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public final class TATerrainColumn {

    public static final Codec<Double> DOUBLE_STRING = Codec.STRING.comapFlatMap(TATerrainColumn::parseString2Double, f -> Double.toString(f));
    public static final Codec<TATerrainColumn> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryFixedCodec.create(Registries.BIOME).fieldOf("key_biome").forGetter(o -> o.keyBiome),
            doubleTreeCodec(Biome.CODEC).fieldOf("biome_layers").forGetter(o -> o.biomes),
            Codec.FLOAT.fieldOf("depth").forGetter(o -> o.noiseDepth),
            Codec.FLOAT.fieldOf("scale").forGetter(o -> o.noiseScale)
    ).apply(instance, TATerrainColumn::new));
    private final ResourceKey<Biome> resourceKey;
    private final Holder<Biome> keyBiome;
    private final Double2ObjectSortedMap<Holder<Biome>> biomes;
    private final float noiseDepth;
    private final float noiseScale;

    public TATerrainColumn(Holder<Biome> keyBiome, Double2ObjectSortedMap<Holder<Biome>> biomes, float noiseDepth, float noiseScale) {
        this.keyBiome = keyBiome;
        this.resourceKey = this.keyBiome.unwrapKey().orElseThrow();
        this.biomes = biomes;
        this.noiseDepth = noiseDepth;
        this.noiseScale = noiseScale;
        if (biomes instanceof Double2ObjectAVLTreeMap<Holder<Biome>> treeMap) {
            treeMap.defaultReturnValue(this.keyBiome);
        }
    }

    public static <T> Codec<Double2ObjectSortedMap<T>> doubleTreeCodec(Codec<T> elementCodec) {
        return Codec.compoundList(DOUBLE_STRING, elementCodec).xmap(floatEList ->
                floatEList.stream().collect(Double2ObjectAVLTreeMap::new,
                        (map, pair) -> map.put(pair.getFirst(), pair.getSecond()),
                        Double2ObjectAVLTreeMap::putAll), map -> map.entrySet().stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue())).toList());
    }

    private static DataResult<Double> parseString2Double(String string) {
        try {
            return DataResult.success(Double.valueOf(string));
        } catch (Throwable e) {
            return DataResult.error(e::getMessage);
        }
    }

    public Stream<Holder<Biome>> getBiomes() {
        return this.biomes.double2ObjectEntrySet().stream().map(Map.Entry::getValue);
    }

    public boolean is(Holder<Biome> biome) {
        return this.keyBiome.value().equals(biome.value());
    }

    public boolean is(ResourceKey<Biome> biome) {
        return this.keyBiome.is(biome);
    }

    public Holder<Biome> getBiome(int biomeElevation) {
        return this.reduce((a, b) -> {
            double aDelta = a.getDoubleKey() - biomeElevation;
            double bDelta = b.getDoubleKey() - biomeElevation;
            return Math.abs(aDelta) <= Math.abs(bDelta) ? a : b;
        }, this.keyBiome);
    }

    private Holder<Biome> reduce(BinaryOperator<Double2ObjectMap.Entry<Holder<Biome>>> reducer, Holder<Biome> other) {
        return this.biomes.double2ObjectEntrySet().stream().reduce(reducer).map(Map.Entry::getValue).orElse(other);
    }

    public float depth() {
        return this.noiseDepth;
    }

    public float scale() {
        return this.noiseScale;
    }

    public ResourceKey<Biome> getResourceKey() {
        return this.resourceKey;
    }

}