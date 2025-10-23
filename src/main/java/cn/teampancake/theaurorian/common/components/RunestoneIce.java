package cn.teampancake.theaurorian.common.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.concurrent.ThreadLocalRandom;

public record RunestoneIce(float minChance, float maxChance, float baseReduction) {

    public static final Codec<RunestoneIce> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("min_chance").forGetter(RunestoneIce::minChance),
            Codec.FLOAT.fieldOf("max_chance").forGetter(RunestoneIce::maxChance),
            Codec.FLOAT.fieldOf("base_reduction").forGetter(RunestoneIce::baseReduction)).apply(instance, RunestoneIce::new));
    public static final StreamCodec<FriendlyByteBuf, RunestoneIce> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, RunestoneIce::minChance, ByteBufCodecs.FLOAT, RunestoneIce::maxChance,
            ByteBufCodecs.FLOAT, RunestoneIce::baseReduction, RunestoneIce::new);

    public float calculateDamageReduction(float originalDamage) {
        if (ThreadLocalRandom.current().nextFloat() >= this.generateActualChance()) return 0.0f;
        float effectiveness = this.calculateDamageEffectiveness(originalDamage);
        return Math.min(this.baseReduction * effectiveness, originalDamage);
    }

    private float generateActualChance() {
        return ThreadLocalRandom.current().nextFloat() * (this.maxChance - this.minChance) + this.minChance;
    }

    private float calculateDamageEffectiveness(float originalDamage) {
        if (originalDamage <= 1.0f) return 0.0f;
        double effectiveness = Math.log10(originalDamage) / Math.log10(5.0F);
        return Math.min(1.0f, (float) effectiveness);
    }

}