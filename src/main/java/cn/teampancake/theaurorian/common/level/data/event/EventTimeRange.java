package cn.teampancake.theaurorian.common.level.data.event;

public class EventTimeRange {

    private final int startTime;
    private final int duration;
    private final TimeUnit unit;

    public EventTimeRange(int startTime, int duration, TimeUnit unit) {
        this.startTime = startTime;
        this.duration = duration;
        this.unit = unit;
    }

    public long getStartTicks() {
        return this.convertToTicks(this.startTime, this.unit);
    }

    public long getDurationTicks() {
        return this.convertToTicks(this.duration, this.unit);
    }

    public long getEndTicks() {
        return this.getStartTicks() + this.getDurationTicks();
    }

    private long convertToTicks(int value, TimeUnit unit) {
        return switch (unit) {
            case SECONDS -> value * 20L;
            case MINUTES -> value * 1200L;
            case HOURS -> value * 72000L;
            default -> value;
        };
    }

    public boolean isInTimeRange(long worldTime) {
        long currentTimeOfDay = worldTime % 24000;
        long startTicks = this.getStartTicks();
        long endTicks = this.getEndTicks();
        if (endTicks > 24000) {
            return currentTimeOfDay >= startTicks || currentTimeOfDay <= (endTicks % 24000);
        } else {
            return currentTimeOfDay >= startTicks && currentTimeOfDay <= endTicks;
        }
    }

    public static EventTimeRange daily(int startHour, int durationHours) {
        return new EventTimeRange(startHour, durationHours, TimeUnit.HOURS);
    }

    public static EventTimeRange hourly(int startMinute, int durationMinutes) {
        return new EventTimeRange(startMinute, durationMinutes, TimeUnit.MINUTES);
    }

    public static EventTimeRange byTicks(int startTicks, int durationTicks) {
        return new EventTimeRange(startTicks, durationTicks, TimeUnit.TICKS);
    }

    public enum TimeUnit {
        TICKS, SECONDS, MINUTES, HOURS
    }

}