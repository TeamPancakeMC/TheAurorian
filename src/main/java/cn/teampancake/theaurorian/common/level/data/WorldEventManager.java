package cn.teampancake.theaurorian.common.level.data;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.data.event.BaseWorldEvent;
import cn.teampancake.theaurorian.common.level.data.event.EventTimeRange;
import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WorldEventManager {

    private static final Map<ResourceLocation, WorldEventData> WORLD_EVENT_DATA = new ConcurrentHashMap<>();

    public static WorldEventData getWorldEventData(ServerLevel level) {
        return WORLD_EVENT_DATA.computeIfAbsent(level.dimension().location(),
                location -> new WorldEventData(level.getDayTime()));
    }

    public static void updateWorldEvents(ServerLevel level) {
        boolean dataChanged = false;
        long currentTime = level.getDayTime();
        WorldEventData eventData = getWorldEventData(level);
        for (BaseWorldEvent event : TAWorldEvents.REGISTRY) {
            ResourceLocation eventId = event.getEventId();
            if (eventId == null) continue;
            boolean isInCooldown = isEventInCooldown(level, eventId, currentTime);
            if (!isInCooldown) {
                Optional<Long> nextTriggerTime = calculateNextTriggerTime(level, event, currentTime);
                if (nextTriggerTime.isPresent()) {
                    long eventStartTime = nextTriggerTime.get();
                    Long lastEventEndTime = eventData.getLastEventEndTime(eventId);
                    boolean hasConflict = lastEventEndTime != null && event.isOmenAftermathConflict(eventStartTime, lastEventEndTime);
                    boolean omenAlreadyExecuted = eventData.isOmenExecuted(eventId);
                    if (!hasConflict && !omenAlreadyExecuted) {
                        if (event.shouldExecuteOmen(currentTime, eventStartTime)) {
                            TheAurorian.LOGGER.info("执行预兆: {} 在 {} 刻后开始", event.getDisplayName(), eventStartTime - currentTime);
                            event.executeOmen(level);
                            eventData.markOmenExecuted(eventId);
                            dataChanged = true;
                        }
                    }
                }
            }

            Long lastEventEndTime = eventData.getLastEventEndTime(eventId);
            if (lastEventEndTime != null) {
                boolean aftermathAlreadyExecuted = eventData.isAftermathExecuted(eventId);
                if (!aftermathAlreadyExecuted && event.shouldExecuteAftermath(currentTime, lastEventEndTime)) {
                    TheAurorian.LOGGER.info("执行余波: {} 在事件结束 {} 刻后", event.getDisplayName(), currentTime - lastEventEndTime);
                    event.executeAftermath(level);
                    eventData.markAftermathExecuted(eventId);
                    dataChanged = true;
                }
            }

            boolean stateChanged = checkEventStateChange(level, event, currentTime, eventData);
            if (stateChanged) {
                dataChanged = true;
                if (!event.shouldBeActive(currentTime) && eventData.wasActiveLastTick.getOrDefault(eventId, false)) {
                    eventData.setLastEventEndTime(eventId, currentTime);
                    TheAurorian.LOGGER.info("记录事件结束时间: {} 在 {}", event.getDisplayName(), currentTime);
                }
            }
        }

        if (dataChanged) {
            WorldEventDataStorage.get(level).setDirty();
        }
    }

    private static boolean isEventInCooldown(ServerLevel level, ResourceLocation eventId, long currentTime) {
        Optional<BaseWorldEvent> eventOpt = TAWorldEvents.REGISTRY.getOptional(eventId);
        if (eventOpt.isPresent()) {
            BaseWorldEvent event = eventOpt.get();
            WorldEventData eventData = getWorldEventData(level);
            Long lastTriggerDay = eventData.lastTriggerDays.get(eventId);
            if (lastTriggerDay != null && event.getCooldownDays() > 0) {
                long currentWorldDay = eventData.getWorldTotalDays(currentTime);
                long daysSinceLastTrigger = currentWorldDay - lastTriggerDay;
                return daysSinceLastTrigger < event.getCooldownDays();
            }
        }

        return false;
    }

    private static boolean checkEventStateChange(ServerLevel level, BaseWorldEvent event, long currentTime, WorldEventData eventData) {
        ResourceLocation eventId = event.getEventId();
        if (eventId == null) return false;
        boolean shouldBeActiveNow = event.shouldBeActive(currentTime);
        boolean wasActiveLastTick = eventData.wasActiveLastTick.getOrDefault(eventId, false);
        eventData.wasActiveLastTick.put(eventId, shouldBeActiveNow);
        if (shouldBeActiveNow != wasActiveLastTick) {
            if (shouldBeActiveNow) {
                if (eventData.shouldActivateEvent(event, currentTime) && event.meetsTriggerConditions(level, currentTime)) {
                    TheAurorian.LOGGER.info("事件满足条件，开始执行: {}", event.getDisplayName());
                    eventData.recordEventActivation(eventId, currentTime);
                    event.onEventStart(level);
                    Long lastTrigger = eventData.lastTriggerDays.get(eventId);
                    TheAurorian.LOGGER.info("上次触发: {}, 当前世界天数: {}", lastTrigger, eventData.getWorldTotalDays(currentTime));
                    return true;
                } else {
                    TheAurorian.LOGGER.info("事件状态变为激活但不满足触发条件: {}", event.getDisplayName());
                    return false;
                }
            } else {
                TheAurorian.LOGGER.info("事件结束: {}", event.getDisplayName());
                event.onEventEnd(level);
                return true;
            }
        }

        if (shouldBeActiveNow) {
            event.onEventTick(level, currentTime);
        }

        return false;
    }

    private static Optional<Long> calculateNextTriggerTime(ServerLevel level, BaseWorldEvent event, long currentTime) {
        WorldEventData eventData = getWorldEventData(level);
        ResourceLocation eventId = event.getEventId();
        if (eventId == null || WorldEventManager.isEventActive(level, eventId)) return Optional.empty();
        Long lastTriggerDay = eventData.lastTriggerDays.get(eventId);
        if (lastTriggerDay != null) {
            long currentWorldDay = eventData.getWorldTotalDays(currentTime);
            long daysSinceLastTrigger = currentWorldDay - lastTriggerDay;
            if (daysSinceLastTrigger < event.getCooldownDays()) {
                TheAurorian.LOGGER.info("预兆计算跳过: {} 仍在冷却中 ({}/{} 天)", eventId, daysSinceLastTrigger, event.getCooldownDays());
                return Optional.empty();
            }
        }

        return calculateNextTimeInRange(currentTime, event.getActiveTimeRange());
    }

    private static Optional<Long> calculateNextTimeInRange(long currentTime, EventTimeRange timeRange) {
        long currentTimeOfDay = currentTime % 24000;
        long startTicks = timeRange.getStartTicks();
        long endTicks = timeRange.getEndTicks();
        if (endTicks < startTicks) endTicks += 24000;
        if (currentTimeOfDay < startTicks) {
            return Optional.of(currentTime - currentTimeOfDay + startTicks);
        } else if (currentTimeOfDay < endTicks) {
            return Optional.of(currentTime);
        } else {
            return Optional.of(currentTime - currentTimeOfDay + 24000 + startTicks);
        }
    }

    public static boolean isEventActive(ServerLevel level, ResourceLocation eventId) {
        BaseWorldEvent event = TAWorldEvents.REGISTRY.get(eventId);
        return event != null && event.shouldBeActive(level.getDayTime());
    }

    public static void initializeWorldStartTime(ServerLevel level) {
        WorldEventData eventData = getWorldEventData(level);
        if (eventData.worldStartTime == 0) {
            eventData.worldStartTime = level.getGameTime();
            WorldEventDataStorage.get(level).setDirty();
        }
    }

}