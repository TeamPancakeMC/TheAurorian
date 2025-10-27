package cn.teampancake.theaurorian.common.level.data.world_event;

import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

public class WorldEventManager {

    public static void updateWorldEvents(ServerLevel level) {
        for (BaseWorldEvent<?> worldEvent : TAWorldEvents.REGISTRY) {
            if (worldEvent.getEventId() == null) continue;
            worldEvent.checkActivation(level);
            worldEvent.updateEvent(level);
        }
    }

    public static void initialize(ServerLevel level) {
        WorldEventDataStorage storage = WorldEventDataStorage.get(level);
        String status = BaseWorldEvent.EventState.INACTIVE.name();
        for (BaseWorldEvent<?> event : TAWorldEvents.REGISTRY) {
            ResourceLocation eventId = event.getEventId();
            storage.lastActivationDays.putIfAbsent(eventId, -1L);
            storage.currentTicks.putIfAbsent(eventId, 0L);
            storage.remainingTicks.putIfAbsent(eventId, 0L);
            storage.eventStates.putIfAbsent(eventId, status);
        }
    }

}