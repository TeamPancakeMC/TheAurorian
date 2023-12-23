package cn.teampancake.theaurorian;

import cn.teampancake.theaurorian.common.level.biome.TABiomeSource;
import cn.teampancake.theaurorian.common.level.chunk.TAChunkGenerator;
import cn.teampancake.theaurorian.compat.ThirstWasTakenCompat;
import cn.teampancake.theaurorian.common.config.AurorianConfig;
import cn.teampancake.theaurorian.common.registry.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Objects;

@Mod(AurorianMod.MOD_ID)
public class AurorianMod {

    public static final String MOD_ID = "theaurorian";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public AurorianMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,
                AurorianConfig.SPEC, "theaurorian/TheAurorian.toml");
        TATabs.TABS.register(modEventBus);
        TAItems.ITEMS.register(modEventBus);
        TABlocks.BLOCKS.register(modEventBus);
        TAFluids.FLUIDS.register(modEventBus);
        TAFeatures.FEATURES.register(modEventBus);
        TAMenuTypes.MENU_TYPES.register(modEventBus);
        TARecipes.RECIPE_TYPES.register(modEventBus);
        TARecipes.RECIPE_SERIALIZERS.register(modEventBus);
        TASoundEvents.SOUND_EVENTS.register(modEventBus);
        TAFluidTypes.FLUID_TYPES.register(modEventBus);
        TAEntityTypes.ENTITY_TYPES.register(modEventBus);
        TAEnchantments.ENCHANTMENTS.register(modEventBus);
        TAParticleTypes.PARTICLE_TYPES.register(modEventBus);
        TABlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        TAPaintingVariants.PAINTING_VARIANTS.register(modEventBus);
        TABiomeLayerStack.BIOME_LAYER_STACKS.register(modEventBus);
        TABiomeLayers.BIOME_LAYER_TYPES.register(modEventBus);
        TAMobEffects.MOB_EFFECTS.register(modEventBus);
        modEventBus.addListener(this::registerExtraStuff);
        modEventBus.addListener(this::setRegistriesForDatapack);
        MinecraftForge.EVENT_BUS.register(this);
        if (ModList.get().isLoaded("thirst")) {
            ThirstWasTakenCompat.init();
        }
    }

    public void registerExtraStuff(RegisterEvent event) {
        if (Objects.equals(event.getRegistryKey(), Registries.BIOME_SOURCE)) {
            Registry.register(BuiltInRegistries.BIOME_SOURCE, prefix("aurorian_biomes"), TABiomeSource.TA_CODEC);
        } else if (Objects.equals(event.getRegistryKey(), Registries.CHUNK_GENERATOR)) {
            Registry.register(BuiltInRegistries.CHUNK_GENERATOR, prefix("structure_locating_wrapper"), TAChunkGenerator.CODEC);
        }
    }

    public void setRegistriesForDatapack(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(TABiomeLayerStack.BIOME_STACK_KEY, TABiomeLayerStack.DISPATCH_CODEC);
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    public static ResourceLocation namedRegistry(String name) {
        return new ResourceLocation("aurorian", name.toLowerCase(Locale.ROOT));
    }

}