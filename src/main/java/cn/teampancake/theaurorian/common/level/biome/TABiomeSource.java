package cn.teampancake.theaurorian.common.level.biome;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.chunk.TATerrainColumn;
import cn.teampancake.theaurorian.common.level.legacy.area.LazyArea;
import cn.teampancake.theaurorian.common.level.legacy.context.LazyAreaContext;
import cn.teampancake.theaurorian.common.level.legacy.layer.BiomeLayerFactory;
import cn.teampancake.theaurorian.common.registry.TABiomeLayerStack;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TABiomeSource extends BiomeSource {

    public static final Codec<TABiomeSource> TA_CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            TATerrainColumn.CODEC.listOf().fieldOf("biome_landscape").xmap(
                    l -> l.stream().collect(Collectors.toMap(TATerrainColumn::getResourceKey, Function.identity())),
                    m -> m.values().stream().sorted(Comparator.comparing(TATerrainColumn::getResourceKey)).toList()).forGetter(o -> o.biomeList),
            Codec.FLOAT.fieldOf("base_offset").forGetter(o -> o.baseOffset),
            Codec.FLOAT.fieldOf("base_factor").forGetter(o -> o.baseFactor),
            TABiomeLayerStack.HOLDER_CODEC.fieldOf("biome_layer_config").orElseGet((String s) -> AurorianMod.LOGGER.warn(s),
                    TABiomeLayerStack::getDefaultLayer).forGetter(TABiomeSource::getBiomeConfig)
    ).apply(instance, instance.stable(TABiomeSource::new)));

    private final Map<ResourceKey<Biome>, TATerrainColumn> biomeList;
    private final float baseOffset;
    private final float baseFactor;

    private final Holder<BiomeLayerFactory> genBiomeConfig;
    private final Supplier<LazyArea> genBiomes;

    public TABiomeSource(List<TATerrainColumn> list, float offset, float factor, Holder<BiomeLayerFactory> biomeLayerFactory) {
        this(list.stream().collect(Collectors.toMap(TATerrainColumn::getResourceKey, Function.identity())), offset, factor, biomeLayerFactory);
    }

    public TABiomeSource(Map<ResourceKey<Biome>, TATerrainColumn> list, float offset, float factor, Holder<BiomeLayerFactory> biomeLayerFactory) {
        super();
        this.genBiomeConfig = biomeLayerFactory;
        this.genBiomes = Suppliers.memoize(() -> this.genBiomeConfig.get().build(salt -> new LazyAreaContext(25, salt)));
        this.baseOffset = offset;
        this.baseFactor = factor;
        this.biomeList = list;
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return this.biomeList.values().stream().flatMap(TATerrainColumn::getBiomes);
    }

    @Override
    protected Codec<? extends BiomeSource> codec() {
        return TA_CODEC;
    }

    public float getBaseOffset() {
        return this.baseOffset;
    }

    public float getBaseFactor() {
        return this.baseFactor;
    }

    public float getBiomeDepth(int x, int z) {
        return this.getBiomeDepth(this.genBiomes.get().getBiome(x, z));
    }

    public float getBiomeDepth(ResourceKey<Biome> biome) {
        return this.getBiomeValue(biome, TATerrainColumn::depth, 0f);
    }

    public Optional<TATerrainColumn> getTerrainColumn(int x, int z) {
        return this.getTerrainColumn(this.genBiomes.get().getBiome(x, z));
    }

    public Optional<TATerrainColumn> getTerrainColumn(ResourceKey<Biome> biome) {
        return this.biomeList.values().stream().filter(p -> p.is(biome)).findFirst();
    }

    public <T> T getBiomeValue(ResourceKey<Biome> biome, Function<TATerrainColumn, T> function, T other) {
        return this.getTerrainColumn(biome).map(function).orElse(other);
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        return this.biomeList.get(this.genBiomes.get().getBiome(x, z)).getBiome(y);
    }

    private Holder<BiomeLayerFactory> getBiomeConfig() {
        return this.genBiomeConfig;
    }

}