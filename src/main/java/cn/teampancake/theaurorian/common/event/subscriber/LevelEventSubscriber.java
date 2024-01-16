package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.renderer.level.TASkyRenderer;
import cn.teampancake.theaurorian.common.event.RegisterAurorianSkyColorEvent;
import cn.teampancake.theaurorian.common.network.TAMessages;
import cn.teampancake.theaurorian.common.network.message.NightSyncMessage;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
public class LevelEventSubscriber {

    private static int dayCount;

    public static int phaseCode = 0;

    @SubscribeEvent
    public static void registerAurorianSkyColor(RegisterAurorianSkyColorEvent event) {
    }

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.level instanceof ServerLevel serverLevel) {
            if (serverLevel.dimension == TADimensions.AURORIAN_DIMENSION) {
                long dayCounter = (serverLevel.dayTime() - 6000L) / 24000;
                if (dayCounter != dayCount) {
                    dayCount = (int) Math.floor(dayCounter);
                    phaseCode = (int) (Math.random() * TASkyRenderer.getDaySkyColors().size());
                    List<ServerPlayer> playerList = serverLevel.players();
                    for (ServerPlayer serverPlayer : playerList) {
                        TAMessages.sendToPlayer(new NightSyncMessage(phaseCode), serverPlayer);
                    }
                }
            }
        }
    }

}