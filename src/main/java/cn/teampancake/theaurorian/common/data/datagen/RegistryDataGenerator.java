package cn.teampancake.theaurorian.common.data.datagen;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, TABiomes::bootstrap)
            .add(Registries.ENCHANTMENT, TAEnchantments::bootstrap)
            .add(Registries.PAINTING_VARIANT, TAPaintingVariants::bootstrap)
            .add(Registries.NOISE_SETTINGS, TADimensions::bootstrapNoise)
            .add(Registries.DIMENSION_TYPE, TADimensions::bootstrapType)
            .add(Registries.LEVEL_STEM, TADimensions::bootstrapStem)
            .add(Registries.CONFIGURED_CARVER, TAConfiguredCarvers::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, TAConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, TAPlacedFeatures::bootstrap)
            .add(Registries.DAMAGE_TYPE, TADamageTypes::bootstrap)
            .add(Registries.STRUCTURE, TAStructures::bootstrap)
            .add(Registries.STRUCTURE_SET, TAStructureSets::bootstrap)
            .add(Registries.TEMPLATE_POOL, TATemplatePools::bootstrap)
            .add(TABiomeLayerStack.BIOME_STACK_KEY, TABiomeLayerStack::bootstrap);

    public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(TheAurorian.MOD_ID));
    }

}