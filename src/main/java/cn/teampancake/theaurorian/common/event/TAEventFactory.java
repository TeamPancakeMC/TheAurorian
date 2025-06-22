package cn.teampancake.theaurorian.common.event;

import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.common.NeoForge;

import java.util.Map;

public class TAEventFactory {

    public static void onRegisterAurorianSkyColor(Map<ResourceLocation, Integer> daySkyColors) {
        NeoForge.EVENT_BUS.post(new RegisterAurorianSkyColorEvent(daySkyColors));
    }

    public static void onRegisterAurorianSkyBless(ServerPlayer serverPlayer, ServerLevel serverLevel, int phaseCode) {
        NeoForge.EVENT_BUS.post(new RegisterAurorianSkyBlessEvent(serverPlayer, serverLevel, phaseCode));
    }

    public static void onRegisterConflictingEffect(Multimap<Holder<MobEffect>, Holder<MobEffect>> conflictMap) {
        NeoForge.EVENT_BUS.post(new RegisterConflictingEffectEvent(conflictMap));
    }

}