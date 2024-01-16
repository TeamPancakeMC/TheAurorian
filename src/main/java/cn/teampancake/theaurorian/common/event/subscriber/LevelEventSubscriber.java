package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.AurorianMod;
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

import static cn.teampancake.theaurorian.client.renderer.level.TASkyRenderer.getDaySkyColors;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
public class LevelEventSubscriber {

    private static int DayCount;

    public static int PhaseCode=0;

    @SubscribeEvent
    public static void registerAurorianSkyColor(RegisterAurorianSkyColorEvent event) {
    }

    @SubscribeEvent
    public static void OnLevelTick(TickEvent.LevelTickEvent event) {
        if (event.level instanceof ServerLevel serverLevel) {
            if (serverLevel.dimension == TADimensions.AURORIAN_DIMENSION) {
                long DayCounter = (serverLevel.dayTime() - 6000L) / 24000;

            // t = t - 12000;

                if (DayCounter != DayCount) {
                    List<ServerPlayer> playerList = serverLevel.players();

                    DayCount = (int) Math.floor(DayCounter);
                    PhaseCode = (int) (Math.random() * getDaySkyColors().size());
                    for (ServerPlayer serverPlayer : playerList) {
                        TAMessages.sendToPlayer(new NightSyncMessage(PhaseCode), serverPlayer);
                    }

                }
            }
        }
    }
}