package cn.teampancake.theaurorian.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

import java.util.Map;

public class TAEventFactory {

    public static void onRegisterAurorianSkyColor(Map<ResourceLocation, Integer> daySkyColors) {
        MinecraftForge.EVENT_BUS.post(new RegisterAurorianSkyColorEvent(daySkyColors));
    }

}