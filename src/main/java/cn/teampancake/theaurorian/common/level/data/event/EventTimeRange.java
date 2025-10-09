package cn.teampancake.theaurorian.common.level.data.event;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;

public class EventTimeRange {

    public static final Codec<EventTimeRange> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("start_time").forGetter(e -> e.startTime),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("duration").forGetter(e -> e.duration),
            TimeUnit.CODEC.fieldOf("unit").forGetter(e -> e.unit)).apply(instance, EventTimeRange::new));
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

    public long adjustToTimeRange(long worldTime) {
        long timeOfDay = worldTime % 24000;
        long dayBase = worldTime - timeOfDay;
        if (timeOfDay < this.getStartTicks()) {
            return dayBase + this.getStartTicks();
        } else if (timeOfDay > this.getEndTicks()) {
            return dayBase + 24000 + this.getStartTicks();
        }

        return worldTime;
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

    public enum TimeUnit implements StringRepresentable {

        TICKS("ticks"),
        SECONDS("seconds"),
        MINUTES("minutes"),
        HOURS("hours");

        public static final Codec<TimeUnit> CODEC = StringRepresentable.fromEnum(TimeUnit::values);
        public static final StreamCodec<ByteBuf, TimeUnit> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);
        private final String name;

        TimeUnit(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

    }

}