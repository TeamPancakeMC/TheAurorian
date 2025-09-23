package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.biome.layer.FilthyIceBiomeLayer;
import cn.teampancake.theaurorian.common.level.biome.layer.FilteredBiomeLayer;
import cn.teampancake.theaurorian.common.level.biome.layer.RandomBiomeLayer;
import cn.teampancake.theaurorian.common.level.biome.layer.SeamLayer;
import cn.teampancake.theaurorian.common.level.legacy.layer.BiomeLayerFactory;
import cn.teampancake.theaurorian.common.level.legacy.layer.BiomeLayerType;
import cn.teampancake.theaurorian.common.level.legacy.layer.SmoothLayer;
import cn.teampancake.theaurorian.common.level.legacy.layer.ZoomLayer;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("SpellCheckingInspection")
public class TABiomeLayerStack {

    public static final ResourceKey<Registry<BiomeLayerFactory>> BIOME_STACK_KEY = ResourceKey.createRegistryKey(TheAurorian.namedRegistry("biome_layer_stack"));
    public static final Codec<BiomeLayerFactory> DISPATCH_CODEC = TABiomeLayers.CODEC.dispatch("layer_type", BiomeLayerFactory::getType, BiomeLayerType::getCodec);
    public static final Codec<Holder<BiomeLayerFactory>> HOLDER_CODEC = RegistryFileCodec.create(BIOME_STACK_KEY, DISPATCH_CODEC, true);
    public static final ResourceKey<BiomeLayerFactory> RANDOM_FOREST_BIOMES = registerKey("random_forest_biomes");
    public static final ResourceKey<BiomeLayerFactory> BIOMES_ALONG_STREAMS = registerKey("biomes_along_streams");
    public static final ResourceKey<BiomeLayerFactory> FILTHY_ICE_BIOME = registerKey("filthy_ice_biome");
    // 新增：北方/南方专用层
    public static final ResourceKey<BiomeLayerFactory> NORTH_RANDOM_BIOMES = registerKey("north_random_biomes");
    public static final ResourceKey<BiomeLayerFactory> NORTH_BIOMES_ALONG_STREAMS = registerKey("north_biomes_along_streams");
    public static final ResourceKey<BiomeLayerFactory> SOUTH_RANDOM_BIOMES = registerKey("south_random_biomes");
    public static final ResourceKey<BiomeLayerFactory> SOUTH_BIOMES_ALONG_STREAMS = registerKey("south_biomes_along_streams");

    private static ResourceKey<BiomeLayerFactory> registerKey(String name) {
        return ResourceKey.create(BIOME_STACK_KEY, TheAurorian.prefix(name));
    }

    public static void bootstrap(BootstrapContext<BiomeLayerFactory> context) {
        // 原主维度层
        Pair<BiomeLayerFactory,BiomeLayerFactory> biomeFactory = getRiverLayer();
        BiomeLayerFactory riverLayer = biomeFactory.getFirst();
        riverLayer = new SmoothLayer.Factory(7000L, Holder.direct(riverLayer));
        Holder.Reference<BiomeLayerFactory> randomBiomes = context.register(RANDOM_FOREST_BIOMES, biomeFactory.getSecond());
        context.register(BIOMES_ALONG_STREAMS, new FilteredBiomeLayer.Factory(100L,
                TABiomes.AURORIAN_RIVER, Holder.direct(riverLayer), randomBiomes));
        context.register(FILTHY_ICE_BIOME, new FilthyIceBiomeLayer.Factory(3000L, Optional.empty()));

        // 北方诸国：仅冰雪三系 + 少量湖泊，叠加河流
        Pair<BiomeLayerFactory,BiomeLayerFactory> northFactory = getRiverLayerNorth();
        BiomeLayerFactory northRiver = new SmoothLayer.Factory(7000L, Holder.direct(northFactory.getFirst()));
        Holder.Reference<BiomeLayerFactory> northRandom = context.register(NORTH_RANDOM_BIOMES, northFactory.getSecond());
        context.register(NORTH_BIOMES_ALONG_STREAMS, new FilteredBiomeLayer.Factory(100L,
                TABiomes.AURORIAN_RIVER, Holder.direct(northRiver), northRandom));

        // 南方维度：仅沙漠 + 少量湖泊，叠加河流
        Pair<BiomeLayerFactory,BiomeLayerFactory> southFactory = getRiverLayerSouth();
        BiomeLayerFactory southRiver = new SmoothLayer.Factory(7000L, Holder.direct(southFactory.getFirst()));
        Holder.Reference<BiomeLayerFactory> southRandom = context.register(SOUTH_RANDOM_BIOMES, southFactory.getSecond());
        context.register(SOUTH_BIOMES_ALONG_STREAMS, new FilteredBiomeLayer.Factory(100L,
                TABiomes.AURORIAN_RIVER, Holder.direct(southRiver), southRandom));
    }

    public static Holder<BiomeLayerFactory> getDefaultLayer() {
        Pair<BiomeLayerFactory, BiomeLayerFactory> biomeFactory = getRiverLayer();
        BiomeLayerFactory riverLayer = biomeFactory.getFirst();
        return Holder.direct(new FilteredBiomeLayer.Factory(100L, TABiomes.AURORIAN_RIVER,
                Holder.direct(riverLayer), Holder.direct(biomeFactory.getSecond())));
    }

    public static Pair<BiomeLayerFactory, BiomeLayerFactory> getRiverLayer() {
        // 创建基础生物群系分布 - 黯晶群系作为一个整体随机生成
        BiomeLayerFactory biomes = new RandomBiomeLayer.Factory(1L, 30,
                ImmutableList.of(TABiomes.AURORIAN_PLAINS, TABiomes.AURORIAN_FOREST,
                        TABiomes.AURORIAN_FOREST_HILL, TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD, // 黯晶雪原作为代表
                        TABiomes.CURSED_FROST_FOREST),
                ImmutableList.of(TABiomes.AURORIAN_LAKE, TABiomes.LAVENDER_PLAINS, TABiomes.WEEPING_WILLOW_FOREST,
                        TABiomes.BRIGHT_MOON_DESERT, TABiomes.EQUINOX_FLOWER_PLAINS));
        
        // 应用多层缩放
        biomes = new ZoomLayer.Factory(1000L, false, Holder.direct(biomes));
        biomes = new ZoomLayer.Factory(1001L, false, Holder.direct(biomes));
        biomes = new ZoomLayer.Factory(1002L, false, Holder.direct(biomes));
        biomes = new ZoomLayer.Factory(1003L, false, Holder.direct(biomes));
        biomes = new ZoomLayer.Factory(1004L, false, Holder.direct(biomes));
        biomes = new ZoomLayer.Factory(1005L, false, Holder.direct(biomes));
        
        // 替换黯晶雪原为黯晶群系分布
        biomes = new FilthyIceBiomeLayer.Factory(2000L, Optional.of(Holder.direct(biomes)));
        
        BiomeLayerFactory riverLayer = getBiomeLayerFactory(biomes);
        return new Pair<>(riverLayer, biomes);
    }

    private static Pair<BiomeLayerFactory, BiomeLayerFactory> getRiverLayerNorth() {
        // 只在冰雪三系之间随机，湖泊为稀有
        BiomeLayerFactory biomes = new RandomBiomeLayer.Factory(2L, 30,
                ImmutableList.of(TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD, TABiomes.FILTHY_ICE_HILLS, TABiomes.FILTHY_ICE_MOUNTAIN),
                ImmutableList.of(TABiomes.AURORIAN_LAKE));
        biomes = new ZoomLayer.Factory(1100L, false, Holder.direct(biomes));
        biomes = new ZoomLayer.Factory(1101L, false, Holder.direct(biomes));
        biomes = new ZoomLayer.Factory(1102L, false, Holder.direct(biomes));
        BiomeLayerFactory riverLayer = new SeamLayer.Factory(2L, TABiomes.AURORIAN_RIVER,
                List.of(),
                List.of(Pair.of(TABiomes.FILTHY_ICE_MOUNTAIN, TABiomes.FILTHY_ICE_HILLS),
                        Pair.of(TABiomes.FILTHY_ICE_HILLS, TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD)),
                Holder.direct(biomes));
        return new Pair<>(riverLayer, biomes);
    }

    private static Pair<BiomeLayerFactory, BiomeLayerFactory> getRiverLayerSouth() {
        // 仅沙漠为常见，湖泊为稀有
        BiomeLayerFactory biomes = new RandomBiomeLayer.Factory(3L, 30,
                ImmutableList.of(TABiomes.BRIGHT_MOON_DESERT),
                ImmutableList.of(TABiomes.AURORIAN_LAKE));
        biomes = new ZoomLayer.Factory(1200L, false, Holder.direct(biomes));
        biomes = new ZoomLayer.Factory(1201L, false, Holder.direct(biomes));
        BiomeLayerFactory riverLayer = new SeamLayer.Factory(3L, TABiomes.AURORIAN_RIVER,
                List.of(),
                List.of(Pair.of(TABiomes.BRIGHT_MOON_DESERT, TABiomes.BRIGHT_MOON_DESERT)),
                Holder.direct(biomes));
        return new Pair<>(riverLayer, biomes);
    }

    private static @NotNull BiomeLayerFactory getBiomeLayerFactory(BiomeLayerFactory biomes) {
        BiomeLayerFactory riverLayer = new SeamLayer.Factory(1L, TABiomes.AURORIAN_RIVER,
                List.of(TABiomes.BRIGHT_MOON_DESERT, TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD),
                List.of(Pair.of(TABiomes.AURORIAN_PLAINS, TABiomes.AURORIAN_PLAINS),
                        Pair.of(TABiomes.AURORIAN_FOREST_HILL, TABiomes.AURORIAN_FOREST_HILL),
                        Pair.of(TABiomes.EQUINOX_FLOWER_PLAINS, TABiomes.EQUINOX_FLOWER_PLAINS),
                        Pair.of(TABiomes.LAVENDER_PLAINS, TABiomes.LAVENDER_PLAINS),
                        Pair.of(TABiomes.FILTHY_ICE_MOUNTAIN, TABiomes.FILTHY_ICE_HILLS),
                        Pair.of(TABiomes.FILTHY_ICE_HILLS, TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD)),
                Holder.direct(biomes));
        riverLayer = new SmoothLayer.Factory(7000L, Holder.direct(riverLayer));
        return riverLayer;
    }
}