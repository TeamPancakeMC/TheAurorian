package cn.teampancake.theaurorian.common.level.data.world_event;

import cn.teampancake.theaurorian.common.registry.TAEventConfigurations;
import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public abstract class BaseWorldEvent<WC extends BaseEventConfig> {

    private final MapCodec<ConfiguredEvent<WC, BaseWorldEvent<WC>>> configuredCodec;

    public BaseWorldEvent(Codec<WC> codec) {
        this.configuredCodec = codec.fieldOf("config").xmap(config -> new ConfiguredEvent<>(this, config), ConfiguredEvent::config);
    }

    protected abstract ResourceKey<ConfiguredEvent<?, ?>> getConfigKey();

    @Nullable
    public ResourceLocation getEventId() {
        return TAWorldEvents.REGISTRY.getKey(this);
    }

    public MapCodec<ConfiguredEvent<WC, BaseWorldEvent<WC>>> configuredCodec() {
        return this.configuredCodec;
    }

    @SuppressWarnings("unchecked")
    public WC getConfig(Level level) {
        HolderLookup.RegistryLookup<ConfiguredEvent<?, ?>> lookup =
                level.registryAccess().lookupOrThrow(TAEventConfigurations.KEY);
        return (WC) lookup.getOrThrow(this.getConfigKey()).value().config();
    }

    public boolean isActive(ServerLevel level) {
        String evenState = WorldEventManager.getWorldEventData(level).eventStates.get(this.getEventId());
        return evenState != null && EventState.valueOf(evenState.toUpperCase()) == EventState.ACTIVE;
    }

    public float getProgress(ServerLevel level) {
        ResourceLocation eventId = this.getEventId();
        if (eventId == null || !this.isActive(level)) return 0.0F;
        WorldEventData eventData = WorldEventManager.getWorldEventData(level);
        Long remainingTick = eventData.remainingTicks.get(eventId);
        long duration = this.getConfig(level).duration();
        return remainingTick != null ? ((float)remainingTick / duration) : 0.0F;
    }

    public void onEventStart(ServerLevel level) {}

    public void onEventEnd(ServerLevel level) {}

    public void onEventTick(ServerLevel level, long currentTick) {}

    public void onPrecursorStart(ServerLevel level) {}

    public void onAftermathStart(ServerLevel level) {}

    public void checkActivation(ServerLevel level) {
        ResourceLocation eventId = this.getEventId();
        long dayTime = level.getDayTime() % 24000L;
        long totalDays = level.getDayTime() / 24000L;
        WorldEventData eventData = WorldEventManager.getWorldEventData(level);
        Boolean isFirstActivation = eventData.isFirstActivation.get(eventId);
        Long lastActivationDay = eventData.lastActivationDays.get(eventId);
        Long remainingTick = eventData.remainingTicks.get(eventId);
        String evenState = eventData.eventStates.get(eventId);
        if (isFirstActivation != null && lastActivationDay != null &&
                remainingTick != null && evenState != null) {
            WC config = this.getConfig(level);
            int startTick = config.startTick();
            long duration = config.duration();
            int intervalDays = config.intervalDays();
            if (lastActivationDay == -1 || totalDays - lastActivationDay >= intervalDays) {
                boolean hasPrecursor = config.hasPrecursor();
                int precursorOffset = config.precursorOffset();
                if (hasPrecursor && dayTime == (startTick - precursorOffset + 24000) % 24000) {
                    eventData.eventStates.put(eventId, EventState.PRECURSOR.name);
                    this.onPrecursorStart(level);
                    WorldEventDataStorage.get(level).setDirty();
                    return;
                }

                if (dayTime == startTick) {
                    if (hasPrecursor && !config.allowTimeSkipTrigger()) return;
                    if (isFirstActivation) eventData.isFirstActivation.put(eventId, false);
                    eventData.lastActivationDays.put(eventId, totalDays);
                    eventData.remainingTicks.put(eventId, duration);
                    eventData.eventStates.put(eventId, EventState.ACTIVE.name);
                    this.onEventStart(level);
                    WorldEventDataStorage.get(level).setDirty();
                    return;
                }
            }

            if (config.hasAftermath() && dayTime == (startTick + duration + config.aftermathOffset()) % 24000) {
                if ((lastActivationDay == 0 && totalDays == 1) || (lastActivationDay - 1 == totalDays - intervalDays)) {
                    if (isFirstActivation) eventData.isFirstActivation.put(eventId, false);
                    eventData.eventStates.put(eventId, EventState.AFTERMATH.name);
                    this.onAftermathStart(level);
                    WorldEventDataStorage.get(level).setDirty();
                }
            }
        }
    }

    public void updateEvent(ServerLevel level) {
        ResourceLocation eventId = this.getEventId();
        WorldEventData eventData = WorldEventManager.getWorldEventData(level);
        Long currentTick = eventData.currentTicks.get(eventId);
        Long remainingTick = eventData.remainingTicks.get(eventId);
        String evenState = eventData.eventStates.get(eventId);
        if (currentTick != null && remainingTick != null && evenState != null) {
            EventState currentState = EventState.valueOf(evenState.toUpperCase());
            if (currentState == EventState.ACTIVE) {
                this.onEventTick(level, currentTick);
                eventData.remainingTicks.put(eventId, remainingTick - 1);
                eventData.currentTicks.put(eventId, currentTick + 1);
                WorldEventDataStorage.get(level).setDirty();
                if (eventData.remainingTicks.get(eventId) < 0) {
                    eventData.eventStates.put(eventId, EventState.INACTIVE.name);
                    eventData.currentTicks.put(eventId, 0L);
                    this.onEventEnd(level);
                    WorldEventDataStorage.get(level).setDirty();
                }
            } else if (currentState == EventState.PRECURSOR || currentState == EventState.AFTERMATH) {
                eventData.eventStates.put(eventId, EventState.INACTIVE.name);
                WorldEventDataStorage.get(level).setDirty();
            }
        }
    }

    public void handleTimeSkip(ServerLevel level, long skippedTime) {
        ResourceLocation eventId = this.getEventId();
        long skippedDays = skippedTime / 24000;
        long skippedDayTime = skippedTime % 24000;
        WorldEventData eventData = WorldEventManager.getWorldEventData(level);
        Long lastActivationDay = eventData.lastActivationDays.get(eventId);
        String evenState = eventData.eventStates.get(eventId);
        if (lastActivationDay != null && evenState != null) {
            WC config = this.getConfig(level);
            int startTick = config.startTick();
            long duration = config.duration();
            boolean hasPrecursor = config.hasPrecursor();
            if (hasPrecursor) {
                long precursorTime = (startTick - config.precursorOffset() + 24000) % 24000;
                if (skippedDayTime >= precursorTime) {
                    eventData.lastActivationDays.put(eventId, level.getDayTime() / 24000);
                    WorldEventDataStorage.get(level).setDirty();
                    return;
                }
            }

            Long remainingTick = eventData.remainingTicks.get(eventId);
            Long currentTick = eventData.currentTicks.get(eventId);
            EventState currentState = EventState.valueOf(evenState.toUpperCase());
            if (remainingTick == null || currentTick == null) return;
            if (currentState == EventState.ACTIVE) {
                remainingTick = Math.max(0, remainingTick - skippedTime);
                currentTick = Math.max(duration, currentTick + skippedTime);
                eventData.remainingTicks.put(eventId, remainingTick);
                eventData.currentTicks.put(eventId, currentTick);
                WorldEventDataStorage.get(level).setDirty();
                if (eventData.remainingTicks.get(eventId) < 0) {
                    eventData.eventStates.put(eventId, EventState.INACTIVE.name);
                    eventData.currentTicks.put(eventId, 0L);
                    this.onEventEnd(level);
                    WorldEventDataStorage.get(level).setDirty();
                }
            } else if (currentState == EventState.INACTIVE) {
                if (skippedDayTime >= startTick && (!hasPrecursor || config.allowTimeSkipTrigger())) {
                    long timeSinceStart = skippedDayTime - startTick;
                    remainingTick = Math.max(0, duration - timeSinceStart);
                    eventData.remainingTicks.put(eventId, remainingTick);
                    WorldEventDataStorage.get(level).setDirty();
                    if (eventData.remainingTicks.get(eventId) > 0) {
                        eventData.eventStates.put(eventId, EventState.ACTIVE.name);
                        eventData.lastActivationDays.put(eventId, level.getDayTime() / 24000 - skippedDays);
                        this.onEventStart(level);
                        WorldEventDataStorage.get(level).setDirty();
                    }
                }
            }
        }
    }

    public void handleTimeRewind(ServerLevel level, long rewoundTime) {
        ResourceLocation eventId = this.getEventId();
        long currentDayTime = level.getDayTime();
        long newDayTime = currentDayTime - rewoundTime;
        WorldEventData eventData = WorldEventManager.getWorldEventData(level);
        Long lastActivationDay = eventData.lastActivationDays.get(eventId);
        Long remainingTick = eventData.remainingTicks.get(eventId);
        Long currentTick = eventData.currentTicks.get(eventId);
        String evenState = eventData.eventStates.get(eventId);
        if (lastActivationDay == null || remainingTick == null ||
                currentTick == null || evenState == null) return;
        WC config = this.getConfig(level);
        int startTick = config.startTick();
        long duration = config.duration();
        EventState currentState = EventState.valueOf(evenState.toUpperCase());
        switch (currentState) {
            case ACTIVE:
                eventData.remainingTicks.put(eventId, remainingTick + rewoundTime);
                if (eventData.remainingTicks.get(eventId) > duration) {
                    eventData.eventStates.put(eventId, EventState.INACTIVE.name);
                    eventData.remainingTicks.put(eventId, duration);
                    eventData.lastActivationDays.put(eventId, -1L);
                }

                break;
            case PRECURSOR:
            case AFTERMATH:
                eventData.eventStates.put(eventId, EventState.INACTIVE.name);
                break;
            case INACTIVE:
                if (config.hasPrecursor()) {
                    long precursorTime = (startTick - config.precursorOffset() + 24000) % 24000;
                    if (newDayTime % 24000 <= precursorTime && currentDayTime % 24000 > precursorTime) {
                        eventData.lastActivationDays.put(eventId, -1L);
                    }
                }

                if (newDayTime % 24000 <= startTick && currentDayTime % 24000 > startTick) {
                    eventData.lastActivationDays.put(eventId, -1L);
                }

                break;
        }

        WorldEventDataStorage.get(level).setDirty();
    }

    public enum EventState implements StringRepresentable {

        INACTIVE("inactive"),
        PRECURSOR("precursor"),
        ACTIVE("active"),
        AFTERMATH("aftermath");

        public static final Codec<EventState> CODEC = StringRepresentable.fromEnum(EventState::values);
        public static final StreamCodec<ByteBuf, EventState> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);
        private final String name;

        EventState(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

    }

}