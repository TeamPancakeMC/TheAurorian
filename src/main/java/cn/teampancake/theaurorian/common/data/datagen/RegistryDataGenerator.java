package cn.teampancake.theaurorian.common.data.datagen;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.feature.TAConfiguredFeatures;
import cn.teampancake.theaurorian.common.level.placement.TAPlacements;
import cn.teampancake.theaurorian.common.registry.TABiomeLayerStack;
import cn.teampancake.theaurorian.common.registry.TABiomes;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import cn.teampancake.theaurorian.common.registry.TATrimMaterials;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, TABiomes::bootstrap)
            .add(Registries.NOISE_SETTINGS, TADimensions::bootstrapNoise)
            .add(Registries.DIMENSION_TYPE, TADimensions::bootstrapType)
            .add(Registries.LEVEL_STEM, TADimensions::bootstrapStem)
            .add(Registries.CONFIGURED_FEATURE, TAConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, TAPlacements::bootstrap)
            .add(Registries.TRIM_MATERIAL, TATrimMaterials::bootstrap)
            .add(TABiomeLayerStack.BIOME_STACK_KEY, TABiomeLayerStack::bootstrap);

    public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(AurorianMod.MOD_ID));
    }

}