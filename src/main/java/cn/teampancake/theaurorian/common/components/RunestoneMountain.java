package cn.teampancake.theaurorian.common.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record RunestoneMountain(float minMiningBoost, float maxMiningBoost, float minXpBoost, float maxXpBoost) {
    
    public static final Codec<RunestoneMountain> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("min_mining_boost").forGetter(RunestoneMountain::minMiningBoost),
            Codec.FLOAT.fieldOf("max_mining_boost").forGetter(RunestoneMountain::maxMiningBoost),
            Codec.FLOAT.fieldOf("min_xp_boost").forGetter(RunestoneMountain::minXpBoost),
            Codec.FLOAT.fieldOf("max_xp_boost").forGetter(RunestoneMountain::maxXpBoost)).apply(instance, RunestoneMountain::new));
    public static final StreamCodec<FriendlyByteBuf, RunestoneMountain> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, RunestoneMountain::minMiningBoost, ByteBufCodecs.FLOAT, RunestoneMountain::maxMiningBoost,
            ByteBufCodecs.FLOAT, RunestoneMountain::minXpBoost, ByteBufCodecs.FLOAT, RunestoneMountain::maxXpBoost, RunestoneMountain::new);
    
}