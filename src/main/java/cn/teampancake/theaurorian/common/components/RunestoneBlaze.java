package cn.teampancake.theaurorian.common.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.concurrent.ThreadLocalRandom;

public record RunestoneBlaze(float minChance, float maxChance, float minBoost, float maxBoost) {

    public static final Codec<RunestoneBlaze> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("min_chance").forGetter(RunestoneBlaze::minChance),
            Codec.FLOAT.fieldOf("max_chance").forGetter(RunestoneBlaze::maxChance),
            Codec.FLOAT.fieldOf("min_boost").forGetter(RunestoneBlaze::minBoost),
            Codec.FLOAT.fieldOf("max_boost").forGetter(RunestoneBlaze::minBoost)).apply(instance, RunestoneBlaze::new));
    public static final StreamCodec<FriendlyByteBuf, RunestoneBlaze> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, RunestoneBlaze::minChance, ByteBufCodecs.FLOAT, RunestoneBlaze::maxChance,
            ByteBufCodecs.FLOAT, RunestoneBlaze::minBoost, ByteBufCodecs.FLOAT, RunestoneBlaze::maxBoost, RunestoneBlaze::new);

    public float getCriticalMultiplierBoost() {
        if (ThreadLocalRandom.current().nextFloat() >= this.generateActualChance()) return 0.0f;
        return this.generateActualMultiplier();
    }

    private float generateActualChance() {
        return ThreadLocalRandom.current().nextFloat() * (this.maxChance - this.minChance) + this.minChance;
    }

    private float generateActualMultiplier() {
        return ThreadLocalRandom.current().nextFloat() * (this.maxBoost - this.minBoost) + this.minBoost;
    }

}