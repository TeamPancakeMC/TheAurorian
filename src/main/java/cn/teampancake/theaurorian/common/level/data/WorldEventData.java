package cn.teampancake.theaurorian.common.level.data;

import cn.teampancake.theaurorian.common.level.data.event.BaseWorldEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WorldEventData {

    public long worldStartTime;
    public final Map<ResourceLocation, Long> lastTriggerDays = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Long> lastActivationTime = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Long> eventEndTimes = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Long> lastEventEndTimes = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Boolean> wasActiveLastTick = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Boolean> omenExecuted = new ConcurrentHashMap<>();
    public final Map<ResourceLocation, Boolean> aftermathExecuted = new ConcurrentHashMap<>();
    public final Map<UUID, BloodMoonPlayerData> bloodMoonPlayerData = new ConcurrentHashMap<>();

    public WorldEventData(long worldStartGameTime) {
        this.worldStartTime = worldStartGameTime;
    }

    public boolean shouldActivateEvent(BaseWorldEvent event, long currentTime) {
        ResourceLocation eventId = event.getEventId();
        if (eventId == null) return false;
        if (event.getCooldownDays() > 0) {
            Long lastTriggerDay = lastTriggerDays.get(eventId);
            if (lastTriggerDay != null) {
                long currentWorldDay = getWorldTotalDays(currentTime);
                long daysSinceLastTrigger = currentWorldDay - lastTriggerDay;
                if (daysSinceLastTrigger < event.getCooldownDays()) {
                    return false;
                }
            }
        }

        if (event.getTriggerChance() < 1.0f) {
            RandomSource random = RandomSource.create(currentTime + eventId.hashCode());
            return !(random.nextFloat() > event.getTriggerChance());
        }

        return true;
    }

    public void recordEventActivation(ResourceLocation eventId, long currentTime) {
        this.lastTriggerDays.put(eventId, this.getWorldTotalDays(currentTime));
        this.lastActivationTime.put(eventId, currentTime);
    }

    public boolean checkEventStateChange(ResourceLocation eventId, boolean isActiveNow) {
        boolean wasActive = this.wasActiveLastTick.getOrDefault(eventId, false);
        this.wasActiveLastTick.put(eventId, isActiveNow);
        return wasActive != isActiveNow;
    }

    @Nullable
    public Long getLastEventEndTime(ResourceLocation eventId) {
        return this.lastEventEndTimes.get(eventId);
    }

    public void setLastEventEndTime(ResourceLocation eventId, long endTime) {
        this.lastEventEndTimes.put(eventId, endTime);
        this.omenExecuted.remove(eventId);
        this.aftermathExecuted.remove(eventId);
    }

    public long getEventRemainingTime(ResourceLocation eventId, long currentTime) {
        Long endTime = this.eventEndTimes.get(eventId);
        if (endTime != null && currentTime < endTime) {
            return endTime - currentTime;
        }

        return 0;
    }

    public boolean isOmenExecuted(ResourceLocation eventId) {
        return this.omenExecuted.getOrDefault(eventId, false);
    }

    public void markOmenExecuted(ResourceLocation eventId) {
        this.omenExecuted.put(eventId, true);
    }

    public boolean isAftermathExecuted(ResourceLocation eventId) {
        return this.aftermathExecuted.getOrDefault(eventId, false);
    }

    public void markAftermathExecuted(ResourceLocation eventId) {
        this.aftermathExecuted.put(eventId, true);
    }

    public float getEventProgress(ResourceLocation eventId, long currentTime, long startTime, long durationTicks) {
        Long endTime = this.eventEndTimes.get(eventId);
        if (endTime != null && currentTime < endTime) {
            long elapsed = currentTime - startTime;
            return (float) elapsed / durationTicks;
        }

        return 1.0f;
    }

    public long getWorldTotalDays(long currentTime) {
        return (currentTime - this.worldStartTime) / 24000L;
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