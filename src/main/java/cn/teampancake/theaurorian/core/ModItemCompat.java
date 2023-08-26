package cn.teampancake.theaurorian.core;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.items.CeruleanArrow;
import cn.teampancake.theaurorian.common.items.CrystalArrow;
import cn.teampancake.theaurorian.registry.ModItems;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItemCompat {

    @SubscribeEvent
    public static void registerCompostables(FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public static void registerDispenser(FMLCommonSetupEvent event) {
        DispenserBlock.registerBehavior(ModItems.CERULEAN_ARROW.get(), new CeruleanArrow.Dispense());
        DispenserBlock.registerBehavior(ModItems.CRYSTAL_ARROW.get(), new CrystalArrow.Dispense());
    }

}