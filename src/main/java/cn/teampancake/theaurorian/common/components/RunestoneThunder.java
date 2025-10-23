package cn.teampancake.theaurorian.common.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.concurrent.ThreadLocalRandom;

public record RunestoneThunder(float minChance, float maxChance) {

    public static final Codec<RunestoneThunder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("min_chance").forGetter(RunestoneThunder::minChance),
            Codec.FLOAT.fieldOf("max_chance").forGetter(RunestoneThunder::maxChance)).apply(instance, RunestoneThunder::new));
    public static final StreamCodec<FriendlyByteBuf, RunestoneThunder> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, RunestoneThunder::minChance, ByteBufCodecs.FLOAT, RunestoneThunder::maxChance, RunestoneThunder::new);

    public boolean canTriggerCriticalHit() {
        return ThreadLocalRandom.current().nextFloat() < this.generateActualChance();
    }

    private float generateActualChance() {
        return ThreadLocalRandom.current().nextFloat() * (this.maxChance - this.minChance) + this.minChance;
    }

}