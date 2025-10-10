package cn.teampancake.theaurorian.common.level.data.event;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WorldEventData {

    public long worldStartTime;
    public long lastProcessedTime;
    public final Map<ResourceLocation, Long> lastTriggerDays = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Long> lastActivationTime = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Long> eventEndTimes = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Long> lastEventEndTimes = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Long> lastAbsoluteActivationTime = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Long> scheduledEventTimes = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Boolean> wasActiveLastTick = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Boolean> omenExecuted = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Boolean> aftermathExecuted = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Boolean> currentlyActive = new ConcurrentHashMap<>();
    public final Map<UUID, BloodMoonPlayerData> bloodMoonPlayerData = new ConcurrentHashMap<>();

    public WorldEventData(long worldStartTime) {
        this.worldStartTime = worldStartTime;
        this.lastProcessedTime = worldStartTime;
    }

    public long detectTimeJump(long currentTime) {
        long expectedNextTime = this.lastProcessedTime + 1;
        long timeJump = currentTime - expectedNextTime;
        this.lastProcessedTime = currentTime;
        return Math.abs(timeJump) > 100 ? timeJump : 0;
    }

    public void recordEventActivation(ResourceLocation eventId, long absoluteTime, long durationTicks) {
        this.lastAbsoluteActivationTime.put(eventId, absoluteTime);
        this.eventEndTimes.put(eventId, absoluteTime + durationTicks);
        this.currentlyActive.put(eventId, true);
        this.wasActiveLastTick.put(eventId, true);
        this.omenExecuted.remove(eventId);
        this.aftermathExecuted.remove(eventId);
    }

    public void recordEventEnd(ResourceLocation eventId, long endTime) {
        this.lastEventEndTimes.put(eventId, endTime);
        this.eventEndTimes.remove(eventId);
        this.currentlyActive.put(eventId, false);
        this.wasActiveLastTick.put(eventId, false);
    }

    public long getScheduledEventTime(ResourceLocation eventId, long defaultTime) {
        return this.scheduledEventTimes.getOrDefault(eventId, defaultTime);
    }

    @Nullable
    public Long getLastEventEndTime(ResourceLocation eventId) {
        return this.lastEventEndTimes.get(eventId);
    }

    public float getEventProgress(ResourceLocation eventId, long currentTime) {
        Long startTime = this.lastActivationTime.get(eventId);
        Long endTime = this.eventEndTimes.get(eventId);
        if (startTime != null && endTime != null && currentTime < endTime) {
            long elapsed = currentTime - startTime;
            long duration = endTime - startTime;
            return (float) elapsed / duration;
        }

        return 1.0f;
    }

    public long getWorldTotalDays(long currentTime) {
        return (currentTime - this.worldStartTime) / 24000L;
    }

    public boolean isEventActive(ResourceLocation eventId) {
        return Boolean.TRUE.equals(this.currentlyActive.get(eventId));
    }

    public static class BloodMoonPlayerData {

        public int kills;
        public boolean rewardActive;
        public boolean penaltyActive;
        public long rewardUntil;

        public BloodMoonPlayerData() {
            this.kills = 0;
            this.rewardActive = false;
            this.penaltyActive = false;
            this.rewardUntil = 0;
        }

    }

}