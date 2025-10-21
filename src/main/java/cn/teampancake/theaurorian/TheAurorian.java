package cn.teampancake.theaurorian;

import cn.teampancake.theaurorian.client.gui.hud.*;
import cn.teampancake.theaurorian.common.config.TAClientConfig;
import cn.teampancake.theaurorian.common.config.TACommonConfig;
import cn.teampancake.theaurorian.common.level.biome.TABiomeSource;
import cn.teampancake.theaurorian.common.level.chunk.TAChunkGenerator;
import cn.teampancake.theaurorian.common.level.data.world_event.ConfiguredEvent;
import cn.teampancake.theaurorian.common.registry.*;
import cn.teampancake.theaurorian.compat.mui.ModernUICompatibility;
import cn.teampancake.theaurorian.compat.thirst.ThirstWasTakenCompatibility;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Objects;

@Mod(TheAurorian.MOD_ID)
public class TheAurorian {

    public static final String MOD_ID = "theaurorian";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public TheAurorian(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, TACommonConfig.SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, TAClientConfig.SPEC);
        TACreativeModeTabs.TABS.register(modEventBus);
        TAItems.ITEMS.register(modEventBus);
        TABlocks.BLOCKS.register(modEventBus);
        TAFluids.FLUIDS.register(modEventBus);
        TAMenus.MENUS.register(modEventBus);
        TAFeatures.FEATURES.register(modEventBus);
        TAStats.CUSTOM_STATS.register(modEventBus);
        TARecipes.RECIPE_TYPES.register(modEventBus);
        TARecipes.RECIPE_SERIALIZERS.register(modEventBus);
        TAConfiguredCarvers.CARVERS.register(modEventBus);
        TASoundEvents.SOUND_EVENTS.register(modEventBus);
        TAAttachmentTypes.ATTACHMENT_TYPES.register(modEventBus);
        TADataComponents.DATA_COMPONENT_TYPE.register(modEventBus);
        TAArmorMaterials.ARMOR_MATERIALS.register(modEventBus);
        TAItemTooltips.ITEM_TOOLTIPS.register(modEventBus);
        TAWorldEvents.WORLD_EVENTS.register(modEventBus);
        TASkyColors.SKY_COLORS.register(modEventBus);
        TAShields.SHIELDS.register(modEventBus);
        TARunes.RUNES.register(modEventBus);
        TAAttributes.ATTRIBUTES.register(modEventBus);
        TAFluidTypes.FLUID_TYPES.register(modEventBus);
        TAEntityTypes.ENTITY_TYPES.register(modEventBus);
        TAParticleTypes.PARTICLE_TYPES.register(modEventBus);
        TAStructureTypes.STRUCTURE_TYPES.register(modEventBus);
        TABlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        TATrunkPlacerTypes.TRUNK_PLACER_TYPES.register(modEventBus);
        TAFoliagePlacerTypes.FOLIAGE_PLACER_TYPES.register(modEventBus);
        TATreeDecoratorTypes.TREE_DECORATOR_TYPES.register(modEventBus);
        TAStructureProcessors.STRUCTURE_PROCESSORS.register(modEventBus);
        TAStructurePieceTypes.STRUCTURE_PIECE_TYPES.register(modEventBus);
        TAStructurePlacementTypes.STRUCTURE_PLACEMENT_TYPES.register(modEventBus);
        TAEnchantmentEffectTypes.ENCHANTMENT_VALUE_EFFECT_TYPES.register(modEventBus);
        TAEnchantmentEffectTypes.ENCHANTMENT_ENTITY_EFFECT_TYPES.register(modEventBus);
        TAEnchantmentEffectComponents.ENCHANTMENT_EFFECT_COMPONENT_TYPES.register(modEventBus);
        TALootItemConditions.LOOT_CONDITION_TYPES.register(modEventBus);
        TABiomeLayers.BIOME_LAYER_TYPES.register(modEventBus);
        TAMobEffects.MOB_EFFECTS.register(modEventBus);
        TAPotions.POTIONS.register(modEventBus);
        TAVillagerProfession.register(modEventBus);
        modEventBus.addListener(this::init);
        modEventBus.addListener(this::createNewRegistries);
        modEventBus.addListener(this::registerExtraStuff);
        modEventBus.addListener(this::setRegistriesForDatapack);
        TAGameRules.register();
        if (ModList.get().isLoaded("thirst")) {
            ThirstWasTakenCompatibility.init();
        }

        if (ModList.get().isLoaded("modernui")){
            ModernUICompatibility.LOADED = true;
        }

        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(NightBarRender::registerNightOverlay);
            modEventBus.addListener(ShieldHudRenderer::registerShieldOverlay);
            modEventBus.addListener(ProgressBarRenderer::registerProgressBarOverlay);
            modEventBus.addListener(FrostbiteOutlineRender::registerFrostbiteOverlay);
            modEventBus.addListener(ActivationAnimationRender::registerAnimationOverlay);
        }
    }

    public void init(FMLCommonSetupEvent event) {
        event.enqueueWork(TAStats::init);
    }

    public void createNewRegistries(NewRegistryEvent event) {
        event.register(TAItemTooltips.REGISTRY);
        event.register(TABiomeLayers.REGISTRY);
        event.register(TAWorldEvents.REGISTRY);
        event.register(TASkyColors.REGISTRY);
        event.register(TAShields.REGISTRY);
        event.register(TARunes.REGISTRY);
    }

    public void registerExtraStuff(RegisterEvent event) {
        if (Objects.equals(event.getRegistryKey(), Registries.BIOME_SOURCE)) {
            Registry.register(BuiltInRegistries.BIOME_SOURCE, prefix("aurorian_biomes"), TABiomeSource.TA_CODEC);
        } else if (Objects.equals(event.getRegistryKey(), Registries.CHUNK_GENERATOR)) {
            Registry.register(BuiltInRegistries.CHUNK_GENERATOR, prefix("structure_locating_wrapper"), TAChunkGenerator.CODEC);
        }
    }

    public void setRegistriesForDatapack(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(TABiomeLayerStack.KEY, TABiomeLayerStack.DISPATCH_CODEC);
        event.dataPackRegistry(TAEventConfigurations.KEY, ConfiguredEvent.DIRECT_CODEC);
    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    public static ResourceLocation namedRegistry(String name) {
        return ResourceLocation.fromNamespaceAndPath("aurorian", name.toLowerCase(Locale.ROOT));
    }

}