package cn.teampancake.theaurorian.common.level.data.event;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

public class WorldEventManager {

    private static final IEventScheduler eventScheduler = new AbsoluteTimeEventScheduler();

    public static WorldEventData getWorldEventData(ServerLevel level) {
        return WorldEventDataStorage.get(level).getEventData();
    }

    public static void updateWorldEvents(ServerLevel level) {
        long currentTime = level.getDayTime();
        WorldEventData eventData = getWorldEventData(level);
        long timeJump = eventData.detectTimeJump(currentTime);
        if (timeJump != 0) handleTimeJump(level, eventData, timeJump, currentTime);
        for (BaseWorldEvent<?> event : TAWorldEvents.REGISTRY) {
            processEvent(level, event, eventData, currentTime);
        }

        WorldEventDataStorage.get(level).setDirty();
    }

    private static void handleTimeJump(ServerLevel level, WorldEventData eventData, long timeJump, long currentTime) {
        TheAurorian.LOGGER.info("处理时间跳跃: {} 游戏刻", timeJump);
        eventScheduler.rescheduleEventsAfterTimeJump(level, eventData, timeJump, currentTime);
        handleEventsDuringTimeJump(level, eventData, timeJump, currentTime);
    }

    private static void handleEventsDuringTimeJump(ServerLevel level, WorldEventData eventData, long timeJump, long currentTime) {
        long jumpStartTime = currentTime - timeJump;
        for (BaseWorldEvent<?> event : TAWorldEvents.REGISTRY) {
            ResourceLocation eventId = event.getEventId();
            if (eventId == null) continue;
            long nextEventTime = eventData.getScheduledEventTime(eventId, -1);
            if (nextEventTime != -1 && nextEventTime >= jumpStartTime && nextEventTime <= currentTime) {
                triggerEvent(event, level, nextEventTime, eventData);
            }

            handleOmenDuringJump(event, level, eventData, jumpStartTime, currentTime);
            handleAftermathDuringJump(event, level, eventData, jumpStartTime, currentTime);
        }
    }

    private static void handleOmenDuringJump(
            BaseWorldEvent<?> event, ServerLevel level,
            WorldEventData eventData, long jumpStartTime, long currentTime) {
        ResourceLocation eventId = event.getEventId();
        long warningTime = event.getOmenWarningTime(level);
        if (eventId == null || warningTime <= 0) return;
        Long lastActivationTime = eventData.lastAbsoluteActivationTime.get(eventId);
        if (lastActivationTime != null) {
            long omenTime = lastActivationTime - warningTime;
            if (omenTime >= jumpStartTime && omenTime <= currentTime && !Boolean.TRUE.equals(eventData.omenExecuted.get(eventId))) {
                TheAurorian.LOGGER.debug("Executing omen for event {} that occurred during time jump", eventId);
                event.executeOmen(level);
                eventData.omenExecuted.put(eventId, true);
            }
        }

        Long scheduledTime = eventData.scheduledEventTimes.get(eventId);
        if (scheduledTime != null) {
            long scheduledOmenTime = scheduledTime - warningTime;
            if (scheduledOmenTime >= jumpStartTime && scheduledOmenTime <= currentTime && !Boolean.TRUE.equals(eventData.omenExecuted.get(eventId))) {
                TheAurorian.LOGGER.debug("Executing scheduled omen for event {} that occurred during time jump", eventId);
                event.executeOmen(level);
                eventData.omenExecuted.put(eventId, true);
            }
        }
    }

    private static void handleAftermathDuringJump(
            BaseWorldEvent<?> event, ServerLevel level,
            WorldEventData eventData, long jumpStartTime, long currentTime) {
        ResourceLocation eventId = event.getEventId();
        long aftermathDelay = event.getAftermathDelay(level);
        if (eventId == null || aftermathDelay <= 0) return;
        Long lastEventEndTime = eventData.lastEventEndTimes.get(eventId);
        if (lastEventEndTime != null) {
            long aftermathTime = lastEventEndTime + aftermathDelay;
            if (aftermathTime >= jumpStartTime && aftermathTime <= currentTime &&
                    !Boolean.TRUE.equals(eventData.aftermathExecuted.get(eventId))) {
                TheAurorian.LOGGER.debug("Executing aftermath for event {} that occurred during time jump", eventId);
                event.executeAftermath(level);
                eventData.aftermathExecuted.put(eventId, true);
            }
        }

        if (eventData.isEventActive(eventId)) {
            Long eventEndTime = eventData.eventEndTimes.get(eventId);
            if (eventEndTime != null) {
                long potentialAftermathTime = eventEndTime + aftermathDelay;
                if (potentialAftermathTime >= jumpStartTime && potentialAftermathTime <= currentTime &&
                        !Boolean.TRUE.equals(eventData.aftermathExecuted.get(eventId))) {
                    TheAurorian.LOGGER.debug("Executing aftermath for active event {} that occurred during time jump", eventId);
                    event.executeAftermath(level);
                    eventData.aftermathExecuted.put(eventId, true);
                }
            }
        }
    }

    private static void processEvent(ServerLevel level, BaseWorldEvent<?> event, WorldEventData eventData, long currentTime) {
        if (eventScheduler.shouldTriggerEvent(level, event, eventData, currentTime)) {
            triggerEvent(event, level, currentTime, eventData);
        }

        ResourceLocation eventId = event.getEventId();
        if (eventId != null && eventData.isEventActive(eventId)) {
            handleActiveEvent(event, level, eventData, currentTime);
        }

        handleOmenAndAftermath(event, level, eventData, currentTime);
    }

    private static void triggerEvent(BaseWorldEvent<?> event, ServerLevel level, long triggerTime, WorldEventData eventData) {
        ResourceLocation eventId = event.getEventId();
        if (eventId == null) return;
        eventData.recordEventActivation(eventId, triggerTime, event.getDurationTicks(level));
        event.onEventStart(level);
        long nextEventTime = eventScheduler.getNextEventTime(level, event, eventData);
        eventData.scheduledEventTimes.put(eventId, nextEventTime);
    }

    private static void handleActiveEvent(BaseWorldEvent<?> event, ServerLevel level, WorldEventData eventData, long currentTime) {
        ResourceLocation eventId = event.getEventId();
        Long eventEndTime = eventData.eventEndTimes.get(eventId);
        if (eventId != null && eventEndTime != null) {
            if (currentTime >= eventEndTime) {
                eventData.recordEventEnd(eventId, currentTime);
                event.onEventEnd(level);
            } else {
                float progress = eventScheduler.getEventProgress(level, event, eventData, currentTime);
                event.onEventTick(level, currentTime, progress);
            }
        }
    }

    private static void handleOmenAndAftermath(BaseWorldEvent<?> event, ServerLevel level, WorldEventData eventData, long currentTime) {
        ResourceLocation eventId = event.getEventId();
        if (eventId == null) return;
        Long activationTime = eventData.lastAbsoluteActivationTime.get(eventId);
        if (activationTime != null && event.shouldExecuteOmen(level, currentTime, activationTime, eventData)) {
            event.executeOmen(level);
            TheAurorian.LOGGER.debug("预兆触发时间：{}", currentTime);
            eventData.omenExecuted.put(eventId, true);
        }

        Long lastEndTime = eventData.lastEventEndTimes.get(eventId);
        if (lastEndTime != null && event.shouldExecuteAftermath(level, currentTime, lastEndTime, eventData)) {
            event.executeAftermath(level);
            TheAurorian.LOGGER.debug("余波触发时间：{}", currentTime);
            eventData.aftermathExecuted.put(eventId, true);
        }
    }

    public static void initializeWorldStartTime(ServerLevel level) {
        WorldEventDataStorage storage = WorldEventDataStorage.get(level);
        WorldEventData eventData = storage.getEventData();
        long currentTime = level.getDayTime();
        if (eventData.worldStartTime == 0) {
            eventData.worldStartTime = currentTime;
            eventData.lastProcessedTime = currentTime;
            storage.setDirty();
        }
    }

}