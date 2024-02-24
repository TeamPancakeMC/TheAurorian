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
import net.minecraft.world.effect.MobEffect;
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
                        serverPlayer.addEffect(blessEffect(TAMobEffects.PRESSURE.get()));
                    } else {
                        if (phaseCode == 2) {
                            serverPlayer.addEffect(blessEffect(MobEffects.MOVEMENT_SPEED));
                        } else if (phaseCode == 0) {
                            serverPlayer.addEffect(blessEffect(MobEffects.DAMAGE_BOOST));
                            serverPlayer.addEffect(blessEffect(MobEffects.DAMAGE_RESISTANCE));
                        } else if (phaseCode == 3) {
                            serverPlayer.addEffect(blessEffect(MobEffects.DIG_SPEED));
                        } else if (phaseCode == 4) {
                            //TODO: VEGETABLES GROW FASTER
                        } else if (phaseCode == 1) {
                            serverPlayer.addEffect(blessEffect(TAMobEffects.TOUGH.get()));
                        } else {
                            TAEventFactory.onRegisterAurorianSkyBless(serverPlayer, serverLevel, phaseCode);
                        }
                    }
                }
            }
        }
    }
    
    private static MobEffectInstance blessEffect(MobEffect effect) {
        return new MobEffectInstance(effect, 320, 0, false, false);
    }

}