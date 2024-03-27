package cn.teampancake.theaurorian.common.data.datagen;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.data.datagen.provider.*;
import cn.teampancake.theaurorian.common.data.datagen.provider.tag.*;
import cn.teampancake.theaurorian.common.level.placement.AurorianVillagePools;
import cn.teampancake.theaurorian.common.registry.TAProcessorList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TADataGenerator {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        TABlockTagsProvider blockTagsProvider = new TABlockTagsProvider(output, provider, existingFileHelper);
        DatapackBuiltinEntriesProvider dataPackProvider = new RegistryDataGenerator(output, provider);
        CompletableFuture<HolderLookup.Provider> registryProvider = dataPackProvider.getRegistryProvider();
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new TAItemTagsProvider(
                output, provider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), dataPackProvider);
        generator.addProvider(event.includeServer(), new TAPaintingVariantTagsProvider(output, provider, existingFileHelper));
        generator.addProvider(event.includeServer(), new TAEntityTagsProvider(output, provider, existingFileHelper));
        generator.addProvider(event.includeServer(), new TAFluidTagsProvider(output, provider, existingFileHelper));
        generator.addProvider(event.includeServer(), new TABiomeTagsProvider(output, registryProvider, existingFileHelper));
        generator.addProvider(event.includeClient(), new TABlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new TAItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new TASoundProvider(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new TALanguageProvider(output, "en_us"));
        generator.addProvider(event.includeServer(), new TALanguageProvider(output, "zh_cn"));
        generator.addProvider(event.includeServer(), new TALootTableProvider(output));
        generator.addProvider(event.includeServer(), new TARecipeProvider(output));

        CompletableFuture<HolderLookup.Provider> lookupProvider = CompletableFuture.supplyAsync(TADataGenerator::getProvider);
    }

    private static HolderLookup.Provider getProvider() {
        final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
        registryBuilder.add(Registries.CONFIGURED_FEATURE, context -> {
        });
        registryBuilder.add(Registries.PLACED_FEATURE, context -> {
        });
        registryBuilder.add(Registries.PROCESSOR_LIST, TAProcessorList::bootstrap);
        registryBuilder.add(Registries.STRUCTURE, context -> {
        });
        registryBuilder.add(Registries.STRUCTURE_SET, context -> {
        });
        registryBuilder.add(Registries.TEMPLATE_POOL, AurorianVillagePools::bootstrap);
        registryBuilder.add(Registries.BIOME, context -> {
        });
        RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup());
    }
}