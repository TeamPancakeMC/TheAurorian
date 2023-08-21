package cn.teampancake.theaurorian;

import cn.teampancake.theaurorian.common.blocks.ModBlocks;
import cn.teampancake.theaurorian.common.items.ModItems;
import cn.teampancake.theaurorian.config.AurorianConfig;
import cn.teampancake.theaurorian.enchantment.ModEnchantments;
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
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModEnchantments.ENCHANTMENTS.register(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AurorianConfig.SPEC, "theaurorian/TheAurorian.toml");
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

}
