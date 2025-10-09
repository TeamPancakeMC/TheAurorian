package cn.teampancake.theaurorian.common.level.data.event;

import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

public class WorldEventManager {

    public static WorldEventData getWorldEventData(ServerLevel level) {
        return WorldEventDataStorage.get(level).getEventData();
    }

    public static void updateWorldEvents(ServerLevel level) {
        long currentTime = level.getDayTime();
        WorldEventData eventData = getWorldEventData(level);
        for (BaseWorldEvent<?> event : TAWorldEvents.REGISTRY) {
            ResourceLocation eventId = event.getEventId();
            if (eventId == null || currentTime < 24000L) continue;
            if (shouldTriggerEvent(event, level, currentTime, eventData)) {
                long durationTicks = event.getDurationTicks(level);
                eventData.recordEventActivation(eventId, currentTime, durationTicks);
                event.onEventStart(level);
            }

            if (eventData.isEventActive(eventId)) {
                Long endTime = eventData.eventEndTimes.get(eventId);
                if (endTime != null && currentTime >= endTime) {
                    eventData.recordEventEnd(eventId, currentTime);
                    event.onEventEnd(level);
                } else {
                    float progress = eventData.getEventProgress(eventId, currentTime);
                    event.onEventTick(level, currentTime, progress);
                }
            }

            handleOmenAndAftermath(event, level, currentTime, eventData);
            eventData.wasActiveLastTick.put(eventId, eventData.isEventActive(eventId));
        }

        WorldEventDataStorage.get(level).setDirty();
    }

    private static boolean shouldTriggerEvent(BaseWorldEvent<?> event, ServerLevel level, long currentTime, WorldEventData eventData) {
        ResourceLocation eventId = event.getEventId();
        if (eventId == null) return false;
        if (eventData.isEventActive(eventId)) return false;
        if (!event.canTriggerInDimension(level.dimension().location())) return false;
        if (!event.shouldBeActive(level)) return false;
        return event.meetsTriggerConditions(level, currentTime, eventData);
    }

    private static void handleOmenAndAftermath(BaseWorldEvent<?> event, ServerLevel level, long currentTime, WorldEventData eventData) {
        ResourceLocation eventId = event.getEventId();
        if (eventId == null) return;
        Long activationTime = eventData.lastActivationTime.get(eventId);
        if (activationTime != null && event.shouldExecuteOmen(level, currentTime, activationTime) &&
                !Boolean.TRUE.equals(eventData.omenExecuted.get(eventId))) {
            event.executeOmen(level);
            eventData.omenExecuted.put(eventId, true);
            eventData.aftermathExecuted.put(eventId, false);
        }

        Long lastEndTime = eventData.getLastEventEndTime(eventId);
        if (lastEndTime != null && activationTime != null &&
                event.shouldExecuteAftermath(level, currentTime, lastEndTime) &&
                !Boolean.TRUE.equals(eventData.aftermathExecuted.get(eventId))) {
            event.executeAftermath(level);
            eventData.aftermathExecuted.put(eventId, true);
            eventData.omenExecuted.put(eventId, false);
        }
    }

    public static void initializeWorldStartTime(ServerLevel level) {
        long dayTime = level.getDayTime();
        long time = Math.max(0L, dayTime - (dayTime % 24000L));
        WorldEventData eventData = getWorldEventData(level);
        for (BaseWorldEvent<?> event : TAWorldEvents.REGISTRY) {
            ResourceLocation eventId = event.getEventId();
            if (eventId == null) continue;
            eventData.lastEventEndTimes.put(eventId, time);
            eventData.currentlyActive.put(eventId, false);
            eventData.wasActiveLastTick.put(eventId, false);
            eventData.omenExecuted.put(eventId, false);
            eventData.aftermathExecuted.put(eventId, false);
        }

        eventData.worldStartTime = time;
        WorldEventDataStorage.get(level).setDirty();
    }

}