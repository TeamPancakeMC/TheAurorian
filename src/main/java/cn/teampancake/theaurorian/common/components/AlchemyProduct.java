package cn.teampancake.theaurorian.common.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public class AlchemyProduct {

    public static final AlchemyProduct EMPTY = new AlchemyProduct(-1, 0L);
    public static final Codec<AlchemyProduct> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("age").forGetter(o -> o.age),
            Codec.LONG.fieldOf("last_decay").forGetter(o -> o.lastDecay)).apply(instance, AlchemyProduct::new));
    public static final StreamCodec<FriendlyByteBuf, AlchemyProduct> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, alchemyProduct -> alchemyProduct.age,
            ByteBufCodecs.VAR_LONG, alchemyProduct -> alchemyProduct.lastDecay,
            AlchemyProduct::new);
    public int age;
    public long lastDecay;

    public AlchemyProduct(int age, long lastDecay) {
        this.age = age;
        this.lastDecay = lastDecay;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AlchemyProduct that = (AlchemyProduct) object;
        return this.age == that.age &&  this.lastDecay == that.lastDecay;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.age,  this.lastDecay);
    }

}