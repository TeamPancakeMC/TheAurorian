package cn.teampancake.theaurorian;

import cn.teampancake.theaurorian.registry.*;
import cn.teampancake.theaurorian.config.AurorianConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Locale;

@Mod(AurorianMod.MOD_ID)
public class AurorianMod {

    public static final String MOD_ID = "theaurorian";

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
        TAFluidTypes.FLUID_TYPES.register(modEventBus);
        TAEntityTypes.ENTITY_TYPES.register(modEventBus);
        TAEnchantments.ENCHANTMENTS.register(modEventBus);
        TAParticleTypes.PARTICLE_TYPES.register(modEventBus);
        TABlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

}