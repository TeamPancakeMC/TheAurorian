package cn.teampancake.theaurorian.common.level.biome;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.chunk.TATerrainColumn;
import cn.teampancake.theaurorian.common.level.legacy.area.LazyArea;
import cn.teampancake.theaurorian.common.level.legacy.context.LazyAreaContext;
import cn.teampancake.theaurorian.common.level.legacy.layer.BiomeLayerFactory;
import cn.teampancake.theaurorian.common.registry.TABiomeLayerStack;
import cn.teampancake.theaurorian.common.registry.TABiomes;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
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

    public static final MapCodec<TABiomeSource> TA_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            TATerrainColumn.CODEC.listOf().fieldOf("biome_landscape").xmap(
                    l -> l.stream().collect(Collectors.toMap(TATerrainColumn::getResourceKey, Function.identity())),
                    m -> m.values().stream().sorted(Comparator.comparing(TATerrainColumn::getResourceKey)).toList()).forGetter(o -> o.biomeList),
            Codec.FLOAT.fieldOf("base_offset").forGetter(o -> o.baseOffset),
            Codec.FLOAT.fieldOf("base_factor").forGetter(o -> o.baseFactor),
            TABiomeLayerStack.HOLDER_CODEC.fieldOf("biome_layer_config").orElseGet((String s) -> TheAurorian.LOGGER.warn(s),
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
        this.genBiomes = Suppliers.memoize(() -> this.genBiomeConfig.value().build(salt -> new LazyAreaContext(25, salt)));
        this.baseOffset = offset;
        this.baseFactor = factor;
        this.biomeList = list;
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return this.biomeList.values().stream().flatMap(TATerrainColumn::getBiomes);
    }

    @Override
    protected MapCodec<? extends BiomeSource> codec() {
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
        ResourceKey<Biome> biomeKey = this.genBiomes.get().getBiome(x, z);
        TATerrainColumn column = this.biomeList.get(biomeKey);
        if (column == null) {
            TheAurorian.LOGGER.warn("找不到生物群系地形列 (getTerrainColumn x,z): {}", biomeKey);
            return Optional.of(this.getDefaultTerrainColumn());
        }
        return Optional.of(column);
    }

    public Optional<TATerrainColumn> getTerrainColumn(ResourceKey<Biome> biome) {
        Optional<TATerrainColumn> column = this.biomeList.values().stream().filter(p -> p.is(biome)).findFirst();
        if (column.isEmpty()) {
            TheAurorian.LOGGER.warn("找不到生物群系地形列 (getTerrainColumn): {}", biome);
        }
        return column;
    }

    public <T> T getBiomeValue(ResourceKey<Biome> biome, Function<TATerrainColumn, T> function, T other) {
        return this.getTerrainColumn(biome).map(function).orElse(other);
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        ResourceKey<Biome> biomeKey = this.genBiomes.get().getBiome(x, z);
        TATerrainColumn column = this.biomeList.get(biomeKey);
        if (column == null) {
            // 如果找不到对应的地形列，记录日志并使用默认生物群系
            TheAurorian.LOGGER.warn("找不到生物群系地形列: {}", biomeKey);
            column = this.getDefaultTerrainColumn();
        }
        return column.getBiome(y);
    }

    private Holder<BiomeLayerFactory> getBiomeConfig() {
        return this.genBiomeConfig;
    }

    /**
     * 获取默认生物群系，当找不到指定生物群系时使用
     */
    private TATerrainColumn getDefaultTerrainColumn() {
        // 优先使用极光森林作为默认生物群系
        TATerrainColumn defaultColumn = this.biomeList.get(TABiomes.AURORIAN_FOREST);
        if (defaultColumn == null) {
            // 如果极光森林不存在，使用第一个可用的生物群系
            defaultColumn = this.biomeList.values().stream().findFirst().orElseThrow();
        }
        return defaultColumn;
    }

}