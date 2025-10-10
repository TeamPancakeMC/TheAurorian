package cn.teampancake.theaurorian.common.level.data.event;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

public class AbsoluteTimeEventScheduler implements IEventScheduler {

    @Override
    public long getNextEventTime(ServerLevel level, BaseWorldEvent<?> event, WorldEventData eventData) {
        ResourceLocation eventId = event.getEventId();
        if (eventId == null) return -1;
        Long lastActivationTime = eventData.lastAbsoluteActivationTime.get(eventId);
        if (lastActivationTime == null) {
            return this.calculateFirstEventTime(level, event, eventData);
        }

        long coolDownTicks = event.getCooldownDays(level) * 24000L;
        long nextEventTime = lastActivationTime + coolDownTicks;
        return this.adjustToTimeRange(level, event, nextEventTime);
    }

    private long calculateFirstEventTime(ServerLevel level, BaseWorldEvent<?> event, WorldEventData eventData) {
        long minFirstEventTime = eventData.worldStartTime + (2 * 24000L);
        return this.adjustToTimeRange(level, event, minFirstEventTime);
    }

    private long adjustToTimeRange(ServerLevel level, BaseWorldEvent<?> event, long proposedTime) {
        return event.getActiveTimeRange(level).adjustToTimeRange(proposedTime);
    }

    @Override
    public boolean shouldTriggerEvent(ServerLevel level, BaseWorldEvent<?> event, WorldEventData eventData, long currentTime) {
        ResourceLocation eventId = event.getEventId();
        if (eventId == null || eventData.isEventActive(eventId)) return false;
        long nextEventTime = this.getNextEventTime(level, event, eventData);
        if (nextEventTime == -1) return false;
        return currentTime >= nextEventTime &&
                event.canTriggerInDimension(level.dimension().location()) &&
                event.meetsTriggerConditions(level, currentTime, eventData);
    }

    @Override
    public void rescheduleEventsAfterTimeJump(ServerLevel level, WorldEventData eventData, long timeJump, long currentTime) {
        TheAurorian.LOGGER.info("Rescheduling events after time jump of {} ticks", timeJump);
        for (BaseWorldEvent<?> event : TAWorldEvents.REGISTRY) {
            ResourceLocation eventId = event.getEventId();
            if (eventId == null) continue;
            if (eventData.isEventActive(eventId)) {
                this.handleActiveEventDuringJump(event, eventData, currentTime, level);
            }

            long newNextTime = this.getNextEventTime(level, event, eventData);
            if (newNextTime != -1) {
                eventData.scheduledEventTimes.put(eventId, newNextTime);
                TheAurorian.LOGGER.debug("Rescheduled event {} to time {}", eventId, newNextTime);
                TheAurorian.LOGGER.debug("当前时间：{}，下次预兆时间：{}，下次余波时间：{}", currentTime,
                        newNextTime - event.getOmenWarningTime(level), newNextTime + event.getAftermathDelay(level));
            }
        }
    }

    private void handleActiveEventDuringJump(BaseWorldEvent<?> event, WorldEventData eventData, long currentTime, ServerLevel level) {
        ResourceLocation eventId = event.getEventId();
        Long eventEndTime = eventData.eventEndTimes.get(eventId);
        if (eventId != null && eventEndTime != null) {
            if (currentTime >= eventEndTime) {
                TheAurorian.LOGGER.debug("Event {} ended during time jump", eventId);
                eventData.recordEventEnd(eventId, eventEndTime);
                event.onEventEnd(level);
                if (event.shouldExecuteAftermath(level, currentTime, eventEndTime, eventData)) {
                    event.executeAftermath(level);
                    TheAurorian.LOGGER.debug("余波触发时间：{}", currentTime);
                    eventData.aftermathExecuted.put(eventId, true);
                }
            } else {
                float progress = getEventProgress(level, event, eventData, currentTime);
                event.onEventTick(level, currentTime, progress);
            }
        }
    }

    @Override
    public float getEventProgress(ServerLevel level, BaseWorldEvent<?> event, WorldEventData eventData, long currentTime) {
        ResourceLocation eventId = event.getEventId();
        if (eventId == null) return 0.0f;
        Long startTime = eventData.lastAbsoluteActivationTime.get(eventId);
        Long endTime = eventData.eventEndTimes.get(eventId);
        if (endTime == null) return 0.0f;
        if (startTime != null && currentTime < endTime) {
            long elapsed = currentTime - startTime;
            long duration = endTime - startTime;
            return Math.min(1.0f, (float) elapsed / duration);
        }

        return currentTime >= endTime ? 1.0f : 0.0f;
    }

}