package cn.teampancake.theaurorian.common.level.data.world_event;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

public record BaseEventConfig(
        int startTick, int duration, int intervalDays, boolean hasPrecursor, boolean hasAftermath,
        int precursorOffset, int aftermathOffset, boolean allowTimeSkipTrigger) {

    public static final Codec<BaseEventConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("start_tick").forGetter(config -> config.startTick),
            ExtraCodecs.POSITIVE_INT.fieldOf("duration").forGetter(config -> config.duration),
            ExtraCodecs.POSITIVE_INT.fieldOf("interval_days").forGetter(config -> config.intervalDays),
            Codec.BOOL.fieldOf("has_precursor").forGetter(config -> config.hasPrecursor),
            Codec.BOOL.fieldOf("has_aftermath").forGetter(config -> config.hasAftermath),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("precursor_offset").forGetter(config -> config.precursorOffset),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("aftermath_offset").forGetter(config -> config.aftermathOffset),
            Codec.BOOL.fieldOf("allowTime_skip_trigger").forGetter(config -> config.allowTimeSkipTrigger)
    ).apply(instance, BaseEventConfig::new));

}