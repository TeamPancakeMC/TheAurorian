package cn.teampancake.theaurorian.common.level.data.event;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAEventConfigurations;
import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public abstract class BaseWorldEvent<WC extends BaseEventConfig> {

    private final MapCodec<ConfiguredEvent<WC, BaseWorldEvent<WC>>> configuredCodec;

    public BaseWorldEvent(Codec<WC> codec) {
        this.configuredCodec = codec.fieldOf("config").xmap(config -> new ConfiguredEvent<>(this, config), ConfiguredEvent::config);
    }

    protected abstract ResourceKey<ConfiguredEvent<?, ?>> getConfigKey();

    @Nullable
    public ResourceLocation getEventId() {
        return TAWorldEvents.REGISTRY.getKey(this);
    }

    public MapCodec<ConfiguredEvent<WC, BaseWorldEvent<WC>>> configuredCodec() {
        return this.configuredCodec;
    }

    @SuppressWarnings("unchecked")
    public WC getConfig(Level level) {
        HolderLookup.RegistryLookup<ConfiguredEvent<?, ?>> registryLookup =
                level.registryAccess().lookupOrThrow(TAEventConfigurations.KEY);
        return (WC) registryLookup.getOrThrow(this.getConfigKey()).value().config();
    }

    public EventTimeRange getActiveTimeRange(Level level) {
        return this.getConfig(level).activeTimeRange;
    }

    public long getCooldownDays(Level level) {
        return this.getConfig(level).cooldownDays;
    }

    public long getDurationTicks(Level level) {
        return this.getActiveTimeRange(level).getDurationTicks();
    }

    public long getOmenWarningTime(Level level) {
        return this.getConfig(level).omenWarningTime;
    }

    public long getAftermathDelay(Level level) {
        return this.getConfig(level).aftermathDelay;
    }

    public void onEventStart(ServerLevel level) {}

    public void onEventEnd(ServerLevel level) {}

    public void onEventTick(ServerLevel level, long currentTime, float progress) {}

    public void executeOmen(ServerLevel level) {}

    public void executeAftermath(ServerLevel level) {}

    public boolean shouldBeActive(Level level) {
        return this.getActiveTimeRange(level).isInTimeRange(level.dayTime());
    }

    public boolean shouldExecuteOmen(Level level, long currentTime, long eventStartTime, WorldEventData eventData) {
        long warningTime = this.getOmenWarningTime(level);
        if (this.getEventId() == null || warningTime <= 0) return false;
        long omenTime = eventStartTime - warningTime;
        return currentTime >= omenTime && omenTime >= eventData.worldStartTime &&
                !Boolean.TRUE.equals(eventData.omenExecuted.get(this.getEventId()));
    }

    public boolean shouldExecuteAftermath(Level level, long currentTime, long eventEndTime, WorldEventData eventData) {
        long aftermathDelay = this.getAftermathDelay(level);
        if (this.getEventId() == null || aftermathDelay <= 0) return false;
        long aftermathTime = eventEndTime + aftermathDelay;
        return currentTime >= aftermathTime && aftermathTime >= eventData.worldStartTime &&
                !Boolean.TRUE.equals(eventData.aftermathExecuted.get(this.getEventId()));
    }

    private boolean hasOmenAftermathConflict(Level level, WorldEventData eventData, long checkTime) {
        ResourceLocation eventId = this.getEventId();
        if (eventId == null) return false;
        Long lastEventEndTime = eventData.getLastEventEndTime(eventId);
        if (lastEventEndTime != null) {
            long lastAftermathTime = lastEventEndTime + this.getAftermathDelay(level);
            if (Math.abs(checkTime - lastAftermathTime) < 24000) return true;
        }

        long estimatedNextStartTime = this.estimateNextEventTime(level, eventData, eventId);
        if (estimatedNextStartTime > 0) {
            long nextOmenTime = estimatedNextStartTime - this.getOmenWarningTime(level);
            return Math.abs(checkTime - nextOmenTime) < 24000;
        }

        return false;
    }

    private long estimateNextEventTime(Level level, WorldEventData eventData, ResourceLocation eventId) {
        Long lastActivationTime = eventData.lastActivationTime.get(eventId);
        if (lastActivationTime == null) return -1;
        long coolDownTicks = this.getCooldownDays(level) * 24000L;
        long estimatedTime = lastActivationTime + coolDownTicks;
        return this.getActiveTimeRange(level).adjustToTimeRange(estimatedTime);
    }

    public boolean canTriggerInDimension(ResourceLocation dimension) {
        return dimension.getNamespace().equals(TheAurorian.MOD_ID);
    }

    public boolean meetsTriggerConditions(ServerLevel level, long currentTime, WorldEventData eventData) {
        return level.getRandom().nextFloat() < this.getConfig(level).triggerChance;
    }

}