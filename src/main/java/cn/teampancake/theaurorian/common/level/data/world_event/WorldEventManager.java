package cn.teampancake.theaurorian.common.level.data.world_event;

import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

public class WorldEventManager {

    public static WorldEventData getWorldEventData(ServerLevel level) {
        return WorldEventDataStorage.get(level).getEventData();
    }

    public static void updateWorldEvents(ServerLevel level) {
        for (BaseWorldEvent<?> worldEvent : TAWorldEvents.REGISTRY) {
            if (worldEvent.getEventId() == null) continue;
            worldEvent.checkActivation(level);
            worldEvent.updateEvent(level);
        }
    }

    public static void initialize(ServerLevel level) {
        WorldEventDataStorage storage = WorldEventDataStorage.get(level);
        WorldEventData eventData = storage.getEventData();
        for (BaseWorldEvent<?> event : TAWorldEvents.REGISTRY) {
            ResourceLocation eventId = event.getEventId();
            if (eventId == null) continue;
            if (eventData.isFirstActivation.isEmpty()) {
                eventData.isFirstActivation.put(eventId, true);
            }

            if (eventData.lastActivationDays.isEmpty()) {
                eventData.lastActivationDays.put(eventId, -1L);
            }

            if (eventData.currentTicks.isEmpty()) {
                eventData.currentTicks.put(eventId, 0L);
            }

            if (eventData.remainingTicks.isEmpty()) {
                eventData.remainingTicks.put(eventId, 0L);
            }

            if (eventData.eventStates.isEmpty()) {
                eventData.eventStates.put(eventId, BaseWorldEvent.EventState.INACTIVE.name());
            }
        }

        storage.setDirty();
    }

}