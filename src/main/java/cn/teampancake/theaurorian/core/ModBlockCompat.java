package cn.teampancake.theaurorian.core;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlockCompat {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerColors(FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    public static void registerFlammable(FMLCommonSetupEvent event) {

    }

}