package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.renderer.level.TASkyRenderer;
import cn.teampancake.theaurorian.common.event.TAEventFactory;
import cn.teampancake.theaurorian.common.network.TAMessages;
import cn.teampancake.theaurorian.common.network.message.NightSyncMessage;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID)
public class LevelEventSubscriber {

    private static int dayCount;
    public static int phaseCode = 0;

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.level instanceof ServerLevel serverLevel) {
            List<ServerPlayer> playerList = serverLevel.players();
            if (serverLevel.dimension() == TADimensions.AURORIAN_DIMENSION) {
                long dayCounter = (serverLevel.dayTime() - 6000L) / 24000;
                if (dayCounter != dayCount) {
                    dayCount = (int) Math.floor(dayCounter);
                    phaseCode = (int) (Math.random() * TASkyRenderer.getDaySkyColors().size());
                    for (ServerPlayer serverPlayer : playerList) {
                        TAMessages.sendToPlayer(new NightSyncMessage(phaseCode), serverPlayer);
                    }
                }
            }

            long dayTime = serverLevel.dayTime();
            if (dayTime % 200 == 0) {
                for (ServerPlayer serverPlayer : playerList) {
                    if (serverLevel.dimension() != TADimensions.AURORIAN_DIMENSION) {
                        return;
                    }

                    if (dayTime > 12000 && dayTime <= 24000) {
                        serverPlayer.addEffect(new MobEffectInstance(TAMobEffects.PRESSURE.get(),320,0, false, false));
                    } else {
                        if (phaseCode == 2) {
                            serverPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 320, 0, false, false));
                        } else if (phaseCode == 0) {
                            serverPlayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 320, 0, false, false));
                            serverPlayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 320, 0, false, false));
                        } else if (phaseCode == 3) {
                            serverPlayer.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 320, 0, false, false));
                        } else if (phaseCode == 4) {
                            //TODO: VEGETABLES GROW FASTER
                        } else if (phaseCode == 1) {
                            //TODO: STOP ARMOR FROM BREAKING
                        } else {
                            TAEventFactory.onRegisterAurorianSkyBless(serverPlayer, serverLevel, phaseCode);
                        }
                    }
                }
            }
        }
    }

}