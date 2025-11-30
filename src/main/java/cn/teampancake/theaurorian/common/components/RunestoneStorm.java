package cn.teampancake.theaurorian.common.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record RunestoneStorm(float minSpeedBoost, float maxSpeedBoost, float fallDamageReduce, float jumpBoost) {

    public static final Codec<RunestoneStorm> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("min_speed_boost").forGetter(RunestoneStorm::minSpeedBoost),
            Codec.FLOAT.fieldOf("max_speed_boost").forGetter(RunestoneStorm::maxSpeedBoost),
            Codec.FLOAT.fieldOf("fall_damage_reduce").forGetter(RunestoneStorm::fallDamageReduce),
            Codec.FLOAT.fieldOf("jump_boost").forGetter(RunestoneStorm::jumpBoost)).apply(instance, RunestoneStorm::new));
    public static final StreamCodec<FriendlyByteBuf, RunestoneStorm> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, RunestoneStorm::minSpeedBoost, ByteBufCodecs.FLOAT, RunestoneStorm::maxSpeedBoost,
            ByteBufCodecs.FLOAT, RunestoneStorm::fallDamageReduce, ByteBufCodecs.FLOAT, RunestoneStorm::jumpBoost, RunestoneStorm::new);

}