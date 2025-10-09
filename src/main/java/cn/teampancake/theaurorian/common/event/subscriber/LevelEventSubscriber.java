package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.data.*;
import cn.teampancake.theaurorian.common.level.data.event.WorldEventDataStorage;
import cn.teampancake.theaurorian.common.level.data.event.WorldEventManager;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.*;
import java.util.function.Consumer;

@EventBusSubscriber(modid = TheAurorian.MOD_ID)
public class LevelEventSubscriber {

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            WorldEventManager.initializeWorldStartTime(serverLevel);
            WorldSkyDataStorage.get(serverLevel);
            WorldEventDataStorage.get(serverLevel);
        }
    }

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel() instanceof Level level && level.isClientSide()) {
            ClientSkyColorData.clearClientData(level);
        }
    }

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Pre event) {
        if (event.getLevel() instanceof ServerLevel level) {
            if (TACommonUtils.isAurorianDimension(level)) {
                WorldSkyManager.updateSkyColors(level);
                WorldEventManager.updateWorldEvents(level);
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

        public static String[] getAllNames() {
            return NAMES;
        }

        public static String getDisplayName(int code) {
            if (code >= 0 && code < NAMES.length) {
                return Component.translatable("night_phase.theaurorian." + NAMES[code]).getString();
            }

            return "Unknown Night";
        }

    }

}