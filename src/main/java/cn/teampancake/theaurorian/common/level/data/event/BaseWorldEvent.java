package cn.teampancake.theaurorian.common.level.data.event;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;

public class BaseWorldEvent {

    public static final Codec<Holder<BaseWorldEvent>> CODEC = TAWorldEvents.REGISTRY.holderByNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<BaseWorldEvent>> STREAM_CODEC = ByteBufCodecs.holderRegistry(TAWorldEvents.KEY);
    protected final int cooldownDays;
    protected final float triggerChance;
    protected final EventTimeRange activeTimeRange;

    public BaseWorldEvent(int cooldownDays, float triggerChance, EventTimeRange activeTimeRange) {
        this.cooldownDays = cooldownDays;
        this.triggerChance = triggerChance;
        this.activeTimeRange = activeTimeRange;
    }

    @Nullable
    public ResourceLocation getEventId() {
        return TAWorldEvents.REGISTRY.getKey(this);
    }

    public Component getDisplayName() {
        return Component.translatable(Util.makeDescriptionId("event", this.getEventId()));
    }

    public EventTimeRange getActiveTimeRange() {
        return this.activeTimeRange;
    }

    public int getCooldownDays() {
        return this.cooldownDays;
    }

    public float getTriggerChance() {
        return this.triggerChance;
    }

    public int getOmenWarningTime() {
        return 0;
    }

    public int getAftermathDelay() {
        return 0;
    }

    public void executeOmen(ServerLevel level) {}

    public void executeAftermath(ServerLevel level) {}

    public boolean shouldBeActive(long worldTime) {
        return this.activeTimeRange.isInTimeRange(worldTime);
    }

    public boolean shouldExecuteOmen(long currentTime, long eventStartTime) {
        int warningTime = this.getOmenWarningTime();
        return warningTime > 0 && (eventStartTime - currentTime) == warningTime;
    }

    public boolean shouldExecuteAftermath(long currentTime, long eventEndTime) {
        int aftermathDelay = this.getAftermathDelay();
        return aftermathDelay > 0 && (currentTime - eventEndTime) == aftermathDelay;
    }

    public boolean isOmenAftermathConflict(long nextEventStartTime, long lastEventEndTime) {
        int warningTime = this.getOmenWarningTime();
        int aftermathDelay = this.getAftermathDelay();
        if (warningTime == 0 || aftermathDelay == 0) return false;
        long nextOmenTime = nextEventStartTime - warningTime;
        long lastAftermathTime = lastEventEndTime + aftermathDelay;
        return Math.abs(nextOmenTime - lastAftermathTime) < 24000;
    }

    public void onEventStart(ServerLevel level) {}

    public void onEventEnd(ServerLevel level) {}

    public void onEventTick(ServerLevel level, long currentTime) {}

    public boolean canTriggerInDimension(ResourceLocation dimension) {
        return dimension.getNamespace().equals(TheAurorian.MOD_ID);
    }

    public boolean meetsTriggerConditions(ServerLevel level, long currentTime) {
        return true;
    }

}