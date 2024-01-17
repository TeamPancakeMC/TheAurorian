package cn.teampancake.theaurorian.common.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;

import java.util.Map;

public class TAEventFactory {

    public static void onRegisterAurorianSkyColor(Map<ResourceLocation, Integer> daySkyColors) {
        MinecraftForge.EVENT_BUS.post(new RegisterAurorianSkyColorEvent(daySkyColors));
    }

    public static void onRegisterAurorianSkyBless(ServerPlayer serverPlayer, ServerLevel serverLevel, String colorName) {
        MinecraftForge.EVENT_BUS.post(new RegisterAurorianSkyBlessEvent(serverPlayer, serverLevel, colorName));
    }

}