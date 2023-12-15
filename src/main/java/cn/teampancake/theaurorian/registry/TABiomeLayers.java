package cn.teampancake.theaurorian.registry;

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
import net.minecraft.util.ExtraCodecs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class TABiomeLayers {

    public static final ResourceKey<Registry<BiomeLayerType>> BIOME_LAYER_TYPE_KEY = ResourceKey.createRegistryKey(AurorianMod.namedRegistry("biome_layer_type"));
    public static final DeferredRegister<BiomeLayerType> BIOME_LAYER_TYPES = DeferredRegister.create(BIOME_LAYER_TYPE_KEY, AurorianMod.MOD_ID);
    public static final Supplier<IForgeRegistry<BiomeLayerType>> REGISTRY = BIOME_LAYER_TYPES.makeRegistry(() -> new RegistryBuilder<BiomeLayerType>().allowModification().disableSync());
    public static final Codec<BiomeLayerType> CODEC = ExtraCodecs.lazyInitializedCodec(() -> REGISTRY.get().getCodec());

    public static final RegistryObject<BiomeLayerType> ZOOM = registerType("zoom", () -> () -> ZoomLayer.Factory.CODEC);
    public static final RegistryObject<BiomeLayerType> RIVER = registerType("river", () -> () -> SeamLayer.Factory.CODEC);
    public static final RegistryObject<BiomeLayerType> SMOOTH = registerType("smooth", () -> () -> SmoothLayer.Factory.CODEC);
    public static final RegistryObject<BiomeLayerType> FILTERED = registerType("filtered", () -> () -> FilteredBiomeLayer.Factory.CODEC);
    public static final RegistryObject<BiomeLayerType> RANDOM_BIOMES = registerType("random_biomes", () -> () -> RandomBiomeLayer.Factory.CODEC);

    private static RegistryObject<BiomeLayerType> registerType(String name, Supplier<BiomeLayerType> factory) {
        return BIOME_LAYER_TYPES.register(name, factory);
    }

}