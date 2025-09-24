package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.event.TAEventFactory;
import cn.teampancake.theaurorian.common.network.NightTypeS2CPacket;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import cn.teampancake.theaurorian.common.registry.TAGameRules;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;
import java.util.function.Consumer;

@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class LevelEventSubscriber {

    private static int dayCount;
    public static int phaseCode = 0;
    private static boolean isDay = true;
    private static long lastDayTime = 0;
    private static final Random random = new Random();
    private static final float PHASE_CHANGE_CHANCE = 0.2f;
    private static final int FORECAST_DAYS = 3;
    private static final int[] futurePhases = new int[]{-1, -1, -1};
    private static boolean forecastInitialized = false;

    private static void ensureForecastInitialized() {
        if (forecastInitialized) return;
        int previous = Math.max(-1, phaseCode);
        for (int i = 0; i < FORECAST_DAYS; i++) {
            int p = NightPhase.getRandomPhase().getCode();
            int safety = 0;
            while (p == previous && safety++ < 8) {
                p = NightPhase.getRandomPhase().getCode();
            }
            futurePhases[i] = p;
            previous = p;
        }
        forecastInitialized = true;
    }

    private static void shiftForecastAndAppend() {
        int previousTail = futurePhases[FORECAST_DAYS - 1];
        // shift left
        for (int i = 0; i < FORECAST_DAYS - 1; i++) {
            futurePhases[i] = futurePhases[i + 1];
        }
        // append new not equal to last (and not equal to previous day by construction)
        int p = NightPhase.getRandomPhase().getCode();
        int safety = 0;
        while ((FORECAST_DAYS >= 2 && p == futurePhases[FORECAST_DAYS - 2]) || p == previousTail && safety++ < 8) {
            p = NightPhase.getRandomPhase().getCode();
        }
        futurePhases[FORECAST_DAYS - 1] = p;
    }

    public static int[] getFuturePhases() {
        ensureForecastInitialized();
        int[] copy = new int[FORECAST_DAYS];
        System.arraycopy(futurePhases, 0, copy, 0, FORECAST_DAYS);
        return copy;
    }

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        LevelAccessor levelAccessor = event.getLevel();
        MinecraftServer server = levelAccessor.getServer();
        if (server != null) {
            TADimensions.seed = server.getWorldData().worldGenOptions().seed();
        }
    }

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Pre event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            if (serverLevel.dimension() == TADimensions.AURORIAN_DIMENSION) {
                List<ServerPlayer> playerList = serverLevel.players();
                long dayTime = (serverLevel.dayTime() + 6000L) % 24000;
                boolean currentIsDay = dayTime > 6000 && dayTime <= 18000;
                if (currentIsDay != isDay) {
                    isDay = currentIsDay;
                    if (isDay) {
                        ensureForecastInitialized();
                        // Consume today's phase from forecast to guarantee prediction consistency
                        phaseCode = futurePhases[0];
                        for (ServerPlayer serverPlayer : playerList) {
                            PacketDistributor.sendToPlayer(serverPlayer, new NightTypeS2CPacket(phaseCode));
                            if (serverLevel.getGameRules().getBoolean(TAGameRules.RULE_ENABLE_AURORIAN_BLESS)) {
                                NightPhase.fromCode(phaseCode).applyBlessEffect(serverPlayer);
                            }

                            serverPlayer.sendSystemMessage(
                                    Component.translatable("commands.theaurorian.night_phase.changed",
                                            NightPhase.getDisplayName(phaseCode)));
                        }
                        // Advance forecast window for the next days
                        shiftForecastAndAppend();
                    }
                }

                if (dayTime % 200 == 0) {
                    for (ServerPlayer serverPlayer : playerList) {
                        if (serverPlayer.level().dimension() != TADimensions.AURORIAN_DIMENSION) {
                            continue;
                        }

                        if (isDay) {
                            applyNighttimeEffect(serverPlayer, serverLevel);
                        } else {
                            applyBrightMoonNightEffect(serverPlayer);
                        }
                    }
                }

                // Heartbeat: ensure clients stay in sync with server phase regardless of time commands or tick acceleration
                if (dayTime % 100 == 0) {
                    for (ServerPlayer serverPlayer : playerList) {
                        if (serverPlayer.level().dimension() != TADimensions.AURORIAN_DIMENSION) continue;
                        PacketDistributor.sendToPlayer(serverPlayer, new NightTypeS2CPacket(phaseCode));
                    }
                }

                lastDayTime = dayTime;
            }
        }
    }

    public static boolean setNightPhase(NightPhase phase, ServerLevel serverLevel) {
        if (serverLevel.dimension() != TADimensions.AURORIAN_DIMENSION) {
            return false;
        }
        
        phaseCode = phase.getCode();
        for (ServerPlayer serverPlayer : serverLevel.players()) {
            PacketDistributor.sendToPlayer(serverPlayer, new NightTypeS2CPacket(phaseCode));
            long dayTime = (serverLevel.dayTime() + 6000L) % 24000;
            boolean currentIsDay = dayTime > 6000 && dayTime <= 18000;
            if (currentIsDay && serverLevel.getGameRules().getBoolean(TAGameRules.RULE_ENABLE_AURORIAN_BLESS)) {
                phase.applyBlessEffect(serverPlayer);
            }

            serverPlayer.sendSystemMessage(Component.translatable("commands.theaurorian.night_phase.set", NightPhase.getDisplayName(phaseCode)));
        }
        
        return true;
    }

    private static void applyBrightMoonNightEffect(ServerPlayer serverPlayer) {
        if (!serverPlayer.getData(TAAttachmentTypes.IMMUNE_TO_PRESSURE)) {
            serverPlayer.addEffect(blessEffect(TAMobEffects.PRESSURE));
        }
    }

    private static void applyNighttimeEffect(ServerPlayer serverPlayer, ServerLevel serverLevel) {
        if (serverLevel.getGameRules().getBoolean(TAGameRules.RULE_ENABLE_AURORIAN_BLESS)) {
            NightPhase currentPhase = NightPhase.fromCode(phaseCode);
            if (currentPhase != NightPhase.CUSTOM) {
                currentPhase.applyBlessEffect(serverPlayer);
            } else {
                TAEventFactory.onRegisterAurorianSkyBless(serverPlayer, serverLevel, phaseCode);
            }
        }
    }

    private static MobEffectInstance blessEffect(Holder<MobEffect> effect) {
        return new MobEffectInstance(effect, 320, 0, false, false);
    }

    public enum NightPhase {
        COMBAT_NIGHT(0, player -> {
            player.addEffect(blessEffect(MobEffects.DAMAGE_BOOST));
            player.addEffect(blessEffect(MobEffects.DAMAGE_RESISTANCE));
        }),
        PROTECTION_NIGHT(1, player -> player.addEffect(blessEffect(TAMobEffects.TOUGH))),
        EXPLORATION_NIGHT(2, player -> player.addEffect(blessEffect(MobEffects.MOVEMENT_SPEED))),
        MINING_NIGHT(3, player -> player.addEffect(blessEffect(MobEffects.DIG_SPEED))),
        GROWTH_NIGHT(4, player -> {}),
        CUSTOM(-1, null);

        private final int code;
        private final Consumer<ServerPlayer> blessEffect;
        private static final String[] NAMES = {"combat", "protection", "exploration", "mining", "growth"};

        private static final Map<Integer, NightPhase> BY_CODE = new HashMap<>();
        private static final Map<String, NightPhase> BY_NAME = new HashMap<>();

        static {
            for (NightPhase phase : values()) {
                if (phase != CUSTOM) {
                    BY_CODE.put(phase.code, phase);
                    BY_NAME.put(NAMES[phase.code], phase);
                    BY_NAME.put(phase.name().toLowerCase(Locale.ROOT), phase);
                }
            }
        }

        NightPhase(int code, Consumer<ServerPlayer> blessEffect) {
            this.code = code;
            this.blessEffect = blessEffect;
        }

        public int getCode() {
            return code;
        }

        public void applyBlessEffect(ServerPlayer player) {
            if (this.blessEffect != null) {
                this.blessEffect.accept(player);
            }
        }

        public static NightPhase fromCode(int code) {
            return BY_CODE.getOrDefault(code, CUSTOM);
        }

        public static NightPhase fromName(String name) {
            return BY_NAME.getOrDefault(name.toLowerCase(Locale.ROOT), CUSTOM);
        }

        public static String getName(int code) {
            if (code >= 0 && code < NAMES.length) {
                return NAMES[code];
            }
            return "unknown";
        }

        public static String[] getAllNames() {
            return NAMES;
        }

        public static String getDisplayName(int code) {
            if (code >= 0 && code < NAMES.length) {
                return Component.translatable("night_phase.theaurorian." + NAMES[code]).getString();
            }
            return "Unknown Night";
        }

        public static NightPhase getRandomPhase() {
            int randomCode = random.nextInt(NAMES.length);
            return fromCode(randomCode);
        }
    }

}