package cn.teampancake.theaurorian.common.level.data.event;

import net.minecraft.server.level.ServerLevel;

public interface IEventScheduler {

    long getNextEventTime(ServerLevel level, BaseWorldEvent<?> event, WorldEventData eventData);

    boolean shouldTriggerEvent(ServerLevel level, BaseWorldEvent<?> event, WorldEventData eventData, long currentTime);

    void rescheduleEventsAfterTimeJump(ServerLevel level, WorldEventData eventData, long timeJump, long currentTime);

    float getEventProgress(ServerLevel level, BaseWorldEvent<?> event, WorldEventData eventData, long currentTime);

}