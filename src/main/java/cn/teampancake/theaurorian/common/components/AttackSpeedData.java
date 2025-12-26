package cn.teampancake.theaurorian.common.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record AttackSpeedData(float currentSpeed, long lastAttackTime) {

    public static final Codec<AttackSpeedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("current_speed").forGetter(AttackSpeedData::currentSpeed),
            Codec.LONG.fieldOf("last_attack_time").forGetter(AttackSpeedData::lastAttackTime)
    ).apply(instance, AttackSpeedData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AttackSpeedData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, AttackSpeedData::currentSpeed, ByteBufCodecs.VAR_LONG, AttackSpeedData::lastAttackTime, AttackSpeedData::new);

    public AttackSpeedData withNewAttack(long time) {
        float newSpeed = Math.min(3.0f, this.currentSpeed + 0.2f);
        return new AttackSpeedData(newSpeed, time);
    }

    public AttackSpeedData withDecayedSpeed() {
        float newSpeed = Math.max(0.5f, this.currentSpeed - 0.5f);
        return new AttackSpeedData(newSpeed, this.lastAttackTime);
    }

    public static AttackSpeedData createInitial() {
        return new AttackSpeedData(0.5f, 0L);
    }

}