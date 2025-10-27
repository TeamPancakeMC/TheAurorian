package cn.teampancake.theaurorian.common.level.data.world_event;

import cn.teampancake.theaurorian.common.level.data.world_event.BaseWorldEvent.EventState;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WorldEventData {

    public static final Codec<WorldEventData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(ResourceLocation.CODEC, Codec.LONG).fieldOf("last_activation_days").forGetter(data -> data.lastActivationDays),
            Codec.unboundedMap(ResourceLocation.CODEC, Codec.LONG).fieldOf("current_ticks").forGetter(data -> data.currentTicks),
            Codec.unboundedMap(ResourceLocation.CODEC, Codec.LONG).fieldOf("remaining_ticks").forGetter(data -> data.remainingTicks),
            Codec.unboundedMap(ResourceLocation.CODEC, EventState.CODEC).fieldOf("event_states").forGetter(data -> data.eventStates),
            Codec.unboundedMap(UUIDUtil.CODEC, Codec.INT).fieldOf("kill_count_in_blood_moon").forGetter(data -> data.killCountInBloodMoons)
    ).apply(instance, WorldEventData::new));
    public static final StreamCodec<ByteBuf, WorldEventData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(HashMap::new, ResourceLocation.STREAM_CODEC, ByteBufCodecs.VAR_LONG), data -> data.lastActivationDays,
            ByteBufCodecs.map(HashMap::new, ResourceLocation.STREAM_CODEC, ByteBufCodecs.VAR_LONG), data -> data.currentTicks,
            ByteBufCodecs.map(HashMap::new, ResourceLocation.STREAM_CODEC, ByteBufCodecs.VAR_LONG), data -> data.remainingTicks,
            ByteBufCodecs.map(HashMap::new, ResourceLocation.STREAM_CODEC, EventState.STREAM_CODEC), data -> data.eventStates,
            ByteBufCodecs.map(HashMap::new, UUIDUtil.STREAM_CODEC, ByteBufCodecs.INT), data -> data.killCountInBloodMoons, WorldEventData::new);
    public Map<ResourceLocation, Long> lastActivationDays;
    public Map<ResourceLocation, Long> currentTicks;
    public Map<ResourceLocation, Long> remainingTicks;
    public Map<ResourceLocation, EventState> eventStates;
    public Map<UUID, Integer> killCountInBloodMoons;

    public WorldEventData(
            Map<ResourceLocation, Long> lastActivationDays,
            Map<ResourceLocation, Long> currentTicks,
            Map<ResourceLocation, Long> remainingTicks,
            Map<ResourceLocation, EventState> eventStates,
            Map<UUID, Integer> killCountInBloodMoons) {
        this.lastActivationDays = new HashMap<>(lastActivationDays);
        this.currentTicks = new HashMap<>(currentTicks);
        this.remainingTicks = new HashMap<>(remainingTicks);
        this.eventStates = new HashMap<>(eventStates);
        this.killCountInBloodMoons = new HashMap<>(killCountInBloodMoons);
    }

}