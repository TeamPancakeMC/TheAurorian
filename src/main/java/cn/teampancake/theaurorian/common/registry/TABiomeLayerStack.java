package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.biome.layer.FilteredBiomeLayer;
import cn.teampancake.theaurorian.common.level.biome.layer.RandomBiomeLayer;
import cn.teampancake.theaurorian.common.level.biome.layer.SeamLayer;
import cn.teampancake.theaurorian.common.level.legacy.layer.*;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.DeferredRegister;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class TABiomeLayerStack {

    public static final ResourceKey<Registry<BiomeLayerFactory>> BIOME_STACK_KEY = ResourceKey.createRegistryKey(AurorianMod.namedRegistry("biome_layer_stack"));
    public static final DeferredRegister<BiomeLayerFactory> BIOME_LAYER_STACKS = DeferredRegister.create(BIOME_STACK_KEY, AurorianMod.MOD_ID);
    public static final Codec<BiomeLayerFactory> DISPATCH_CODEC = TABiomeLayers.CODEC.dispatch("layer_type", BiomeLayerFactory::getType, BiomeLayerType::getCodec);
    public static final Codec<Holder<BiomeLayerFactory>> HOLDER_CODEC = RegistryFileCodec.create(BIOME_STACK_KEY, DISPATCH_CODEC, true);
    public static final ResourceKey<BiomeLayerFactory> RANDOM_FOREST_BIOMES = registerKey("random_forest_biomes");
    public static final ResourceKey<BiomeLayerFactory> BIOMES_ALONG_STREAMS = registerKey("biomes_along_streams");

    private static ResourceKey<BiomeLayerFactory> registerKey(String name) {
        return ResourceKey.create(BIOME_STACK_KEY, AurorianMod.prefix(name));
    }

    public static void bootstrap(BootstapContext<BiomeLayerFactory> context) {
        Pair<BiomeLayerFactory,BiomeLayerFactory> biomeFactory = getRiverLayer();
        BiomeLayerFactory riverLayer = biomeFactory.getFirst();
        riverLayer = new SmoothLayer.Factory(7000L, Holder.direct(riverLayer));
        Holder.Reference<BiomeLayerFactory> randomBiomes = context.register(RANDOM_FOREST_BIOMES, biomeFactory.getSecond());
        context.register(BIOMES_ALONG_STREAMS, new FilteredBiomeLayer.Factory(100L,
                TABiomes.AURORIAN_RIVER, Holder.direct(riverLayer), randomBiomes));
    }

    public static Holder<BiomeLayerFactory> getDefaultLayer() {
        Pair<BiomeLayerFactory, BiomeLayerFactory> biomeFactory = getRiverLayer();
        BiomeLayerFactory riverLayer = biomeFactory.getFirst();
        return Holder.direct(new FilteredBiomeLayer.Factory(100L, TABiomes.AURORIAN_RIVER,
                Holder.direct(riverLayer), Holder.direct(biomeFactory.getSecond())));
    }

    public static Pair<BiomeLayerFactory, BiomeLayerFactory> getRiverLayer() {
        BiomeLayerFactory biomes = new RandomBiomeLayer.Factory(1L, 15,
                ImmutableList.of(TABiomes.AURORIAN_PLAINS, TABiomes.AURORIAN_FOREST,
                        TABiomes.AURORIAN_FOREST_HILL, TABiomes.LAVENDER_PLAINS,
                        TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD),
                ImmutableList.of(TABiomes.AURORIAN_LAKE, TABiomes.WEEPING_WILLOW_FOREST,
                        TABiomes.BRIGHT_MOON_DESERT, TABiomes.EQUINOX_FLOWER_PLAINS));
        biomes = new ZoomLayer.Factory(1000L, false, Holder.direct(biomes));
        biomes = new ZoomLayer.Factory(1001L, false, Holder.direct(biomes));
        biomes = new ZoomLayer.Factory(1002L, false, Holder.direct(biomes));
        biomes = new ZoomLayer.Factory(1003L, false, Holder.direct(biomes));
        biomes = new ZoomLayer.Factory(1004L, false, Holder.direct(biomes));
        biomes = new ZoomLayer.Factory(1005L, false, Holder.direct(biomes));
        BiomeLayerFactory riverLayer = new SeamLayer.Factory(1L, TABiomes.AURORIAN_RIVER,
                List.of(),
                List.of(Pair.of(TABiomes.AURORIAN_PLAINS,TABiomes.AURORIAN_PLAINS),
                        Pair.of(TABiomes.AURORIAN_FOREST_HILL,TABiomes.AURORIAN_FOREST_HILL),
                        Pair.of(TABiomes.EQUINOX_FLOWER_PLAINS,TABiomes.EQUINOX_FLOWER_PLAINS),
                        Pair.of(TABiomes.LAVENDER_PLAINS,TABiomes.LAVENDER_PLAINS)), Holder.direct(biomes));
        riverLayer = new SmoothLayer.Factory(7000L, Holder.direct(riverLayer));
        return new Pair<>(riverLayer, biomes);
    }

}