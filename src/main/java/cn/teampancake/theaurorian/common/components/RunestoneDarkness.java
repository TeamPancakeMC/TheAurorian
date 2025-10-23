package cn.teampancake.theaurorian.common.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.concurrent.ThreadLocalRandom;

public record RunestoneDarkness(float minChance, float maxChance, float minIgnore, float maxIgnore) {

    public static final Codec<RunestoneDarkness> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("min_chance").forGetter(RunestoneDarkness::minChance),
            Codec.FLOAT.fieldOf("max_chance").forGetter(RunestoneDarkness::maxChance),
            Codec.FLOAT.fieldOf("min_ignore").forGetter(RunestoneDarkness::minIgnore),
            Codec.FLOAT.fieldOf("max_ignore").forGetter(RunestoneDarkness::maxIgnore)).apply(instance, RunestoneDarkness::new));
    public static final StreamCodec<FriendlyByteBuf, RunestoneDarkness> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, RunestoneDarkness::minChance, ByteBufCodecs.FLOAT, RunestoneDarkness::maxChance,
            ByteBufCodecs.FLOAT, RunestoneDarkness::minIgnore, ByteBufCodecs.FLOAT, RunestoneDarkness::maxIgnore, RunestoneDarkness::new);

    public float getIgnoreArmorValue() {
        if (ThreadLocalRandom.current().nextFloat() >= this.generateActualChance()) return 0.0f;
        return this.generateActualIgnoreArmorValue();
    }

    private float generateActualChance() {
        return ThreadLocalRandom.current().nextFloat() * (this.maxChance - this.minChance) + this.minChance;
    }

    private float generateActualIgnoreArmorValue() {
        return ThreadLocalRandom.current().nextFloat() * (this.maxIgnore - this.minIgnore) + this.minIgnore;
    }

}