package cn.teampancake.theaurorian.common.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.concurrent.ThreadLocalRandom;

public record RunestoneWater(float minChance, float maxChance, float healValue) {

    public static final Codec<RunestoneWater> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("min_chance").forGetter(RunestoneWater::minChance),
            Codec.FLOAT.fieldOf("max_chance").forGetter(RunestoneWater::maxChance),
            Codec.FLOAT.fieldOf("heal_value").forGetter(RunestoneWater::healValue)).apply(instance, RunestoneWater::new));
    public static final StreamCodec<FriendlyByteBuf, RunestoneWater> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, RunestoneWater::minChance, ByteBufCodecs.FLOAT, RunestoneWater::maxChance,
            ByteBufCodecs.FLOAT, RunestoneWater::healValue, RunestoneWater::new);

    public float getHealValue() {
        return ThreadLocalRandom.current().nextFloat() < this.generateActualChance() ? this.healValue : 0.0F;
    }

    private float generateActualChance() {
        return ThreadLocalRandom.current().nextFloat() * (this.maxChance - this.minChance) + this.minChance;
    }

}