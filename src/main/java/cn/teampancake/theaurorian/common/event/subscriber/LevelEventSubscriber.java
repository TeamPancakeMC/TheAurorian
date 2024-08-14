package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.renderer.level.TASkyRenderer;
import cn.teampancake.theaurorian.common.event.TAEventFactory;
import cn.teampancake.theaurorian.common.network.FutureNightS2CPacket;
import cn.teampancake.theaurorian.common.network.NightTypeS2CPacket;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import cn.teampancake.theaurorian.common.registry.TAGameRules;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;

@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class LevelEventSubscriber {

    private static int dayCount;
    public static int phaseCode = 0;
    public static Queue<Integer> futurePhase = new LinkedList<>();

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        MinecraftServer server = event.getLevel().getServer();
        if (server != null) {
            TADimensions.seed = server.getWorldData().worldGenOptions().seed();
        }
    }

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Pre event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            List<ServerPlayer> playerList = serverLevel.players();
            if (serverLevel.dimension() == TADimensions.AURORIAN_DIMENSION) {
                long dayCounter = (serverLevel.dayTime() + 6000L) / 24000;
                if (dayCounter != dayCount) {
                    dayCount = (int) Math.floor(dayCounter);
                    if (futurePhase.size() < 4) {
                        Random random = new Random();
                        futurePhase.add(random.nextInt(TASkyRenderer.DaySkyColors.size()));
                    }

                    phaseCode = futurePhase.remove();
                    Integer[] list = futurePhase.toArray(Integer[]::new);
                    for (ServerPlayer serverPlayer : playerList) {
                        PacketDistributor.sendToPlayer(serverPlayer, new NightTypeS2CPacket(phaseCode));
                        PacketDistributor.sendToPlayer(serverPlayer, new FutureNightS2CPacket(Arrays.stream(list).mapToInt(Integer::valueOf).toArray()));
                    }
                }
            }

            if (serverLevel.dimension() != TADimensions.AURORIAN_DIMENSION) {
                return;
            }

            long dayTime = (serverLevel.dayTime() + 6000L) % 24000;
            if (dayTime % 200 == 0) {
                for (ServerPlayer serverPlayer : playerList) {
                    if (serverPlayer.level().dimension() != TADimensions.AURORIAN_DIMENSION) {
                        continue;
                    }

                    if (dayTime > 6000 && dayTime <= 18000) {
                        if (!serverPlayer.getData(TAAttachmentTypes.IMMUNE_TO_PRESSURE)) {
                            serverPlayer.addEffect(blessEffect(TAMobEffects.PRESSURE));
                        }
                    } else {
                        if (serverLevel.getGameRules().getBoolean(TAGameRules.RULE_ENABLE_AURORIAN_BLESS)) {
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
                                serverPlayer.addEffect(blessEffect(TAMobEffects.TOUGH));
                            } else {
                                TAEventFactory.onRegisterAurorianSkyBless(serverPlayer, serverLevel, phaseCode);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static MobEffectInstance blessEffect(Holder<MobEffect> effect) {
        return new MobEffectInstance(effect, 320, 0, false, false);
    }

}