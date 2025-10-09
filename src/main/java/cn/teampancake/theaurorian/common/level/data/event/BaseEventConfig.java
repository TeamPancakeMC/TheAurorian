package cn.teampancake.theaurorian.common.level.data.event;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

public class BaseEventConfig {

    public static final Codec<BaseEventConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("cooldown_day").forGetter(config -> config.cooldownDays),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("omen_warning_time").forGetter(config -> config.omenWarningTime),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("aftermath_delay").forGetter(config -> config.aftermathDelay),
            ExtraCodecs.POSITIVE_FLOAT.fieldOf("trigger_chance").forGetter(config -> config.triggerChance),
            EventTimeRange.CODEC.fieldOf("active_time_range").forGetter(config -> config.activeTimeRange)
    ).apply(instance, BaseEventConfig::new));
    public final int cooldownDays;
    public final int omenWarningTime;
    public final int aftermathDelay;
    public final float triggerChance;
    public final EventTimeRange activeTimeRange;

    public BaseEventConfig(
            int cooldownDays, int omenWarningTime, int aftermathDelay,
            float triggerChance, EventTimeRange activeTimeRange) {
        this.cooldownDays = cooldownDays;
        this.omenWarningTime = omenWarningTime;
        this.aftermathDelay = aftermathDelay;
        this.triggerChance = triggerChance;
        this.activeTimeRange = activeTimeRange;
    }

    public BaseEventConfig(int cooldownDays, int omenWarningTime, int aftermathDelay, EventTimeRange activeTimeRange) {
        this(cooldownDays, omenWarningTime, aftermathDelay, 1.0F, activeTimeRange);
    }
    
}