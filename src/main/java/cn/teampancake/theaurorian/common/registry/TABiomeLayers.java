package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.biome.layer.FilteredBiomeLayer;
import cn.teampancake.theaurorian.common.level.biome.layer.RandomBiomeLayer;
import cn.teampancake.theaurorian.common.level.biome.layer.SeamLayer;
import cn.teampancake.theaurorian.common.level.legacy.layer.BiomeLayerType;
import cn.teampancake.theaurorian.common.level.legacy.layer.SmoothLayer;
import cn.teampancake.theaurorian.common.level.legacy.layer.ZoomLayer;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class TABiomeLayers {

    public static final ResourceKey<Registry<BiomeLayerType>> BIOME_LAYER_TYPE_KEY = ResourceKey.createRegistryKey(AurorianMod.namedRegistry("biome_layer_type"));
    public static final DeferredRegister<BiomeLayerType> BIOME_LAYER_TYPES = DeferredRegister.create(BIOME_LAYER_TYPE_KEY, AurorianMod.MOD_ID);
    public static final Registry<BiomeLayerType> REGISTRY = new RegistryBuilder<>(BIOME_LAYER_TYPE_KEY).create();
    public static final Codec<BiomeLayerType> CODEC = Codec.lazyInitialized(REGISTRY::byNameCodec);

    public static final DeferredHolder<BiomeLayerType, BiomeLayerType> ZOOM = registerType("zoom", () -> () -> ZoomLayer.Factory.CODEC);
    public static final DeferredHolder<BiomeLayerType, BiomeLayerType> RIVER = registerType("river", () -> () -> SeamLayer.Factory.CODEC);
    public static final DeferredHolder<BiomeLayerType, BiomeLayerType> SMOOTH = registerType("smooth", () -> () -> SmoothLayer.Factory.CODEC);
    public static final DeferredHolder<BiomeLayerType, BiomeLayerType> FILTERED = registerType("filtered", () -> () -> FilteredBiomeLayer.Factory.CODEC);
    public static final DeferredHolder<BiomeLayerType, BiomeLayerType> RANDOM_BIOMES = registerType("random_biomes", () -> () -> RandomBiomeLayer.Factory.CODEC);

    private static DeferredHolder<BiomeLayerType, BiomeLayerType> registerType(String name, Supplier<BiomeLayerType> factory) {
        return BIOME_LAYER_TYPES.register(name, factory);
    }

}