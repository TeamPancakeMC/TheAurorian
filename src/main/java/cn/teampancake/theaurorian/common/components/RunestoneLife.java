package cn.teampancake.theaurorian.common.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record RunestoneLife(int minHealthBoost, int maxHealthBoost) {

    public static final Codec<RunestoneLife> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("min_health_boost").forGetter(RunestoneLife::minHealthBoost),
            Codec.INT.fieldOf("max_health_boost").forGetter(RunestoneLife::maxHealthBoost)).apply(instance, RunestoneLife::new));
    public static final StreamCodec<FriendlyByteBuf, RunestoneLife> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, RunestoneLife::minHealthBoost, ByteBufCodecs.INT, RunestoneLife::maxHealthBoost, RunestoneLife::new);
    
}