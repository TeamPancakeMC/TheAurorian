package cn.teampancake.theaurorian.common.shields;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class ShieldInstance {

    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Codec<ShieldInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BaseShield.CODEC.fieldOf("id").forGetter(ShieldInstance::getShieldHolder),
            Codec.FLOAT.fieldOf("shield").forGetter(ShieldInstance::getShield),
            Codec.FLOAT.fieldOf("max_shield").forGetter(ShieldInstance::getMaxShield)
    ).apply(instance, ShieldInstance::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ShieldInstance> STREAM_CODEC = StreamCodec.composite(
            BaseShield.STREAM_CODEC, ShieldInstance::getShieldHolder, ByteBufCodecs.FLOAT, ShieldInstance::getShield,
            ByteBufCodecs.FLOAT, ShieldInstance::getMaxShield, ShieldInstance::new);
    private final Holder<BaseShield> shieldHolder;
    private float shield;
    private float maxShield;

    public ShieldInstance(Holder<BaseShield> shieldHolder, float shield, float maxShield) {
        this.shieldHolder = shieldHolder;
        this.shield = shield;
        this.maxShield = maxShield;
    }

    public Holder<BaseShield> getShieldHolder() {
        return this.shieldHolder;
    }

    public float getShield() {
        return this.shield;
    }

    public float getMaxShield() {
        return this.maxShield;
    }

    public void consumeShield(float shield) {
        this.shield = Math.max(this.shield - shield, 0);
    }

    public void increaseShield(float shield) {
        this.shield = Math.min(this.shield + shield, this.maxShield);
    }

    public void consumeMaxShield(float maxShield) {
        this.maxShield = Math.max(this.maxShield - maxShield, 0);
    }

    public void increaseMaxShield(float maxShield) {
        this.maxShield = Math.max(this.maxShield + maxShield, 0);
    }

    public Tag save() {
        return CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow();
    }

    @Nullable
    public static ShieldInstance load(CompoundTag nbt) {
        return CODEC.parse(NbtOps.INSTANCE, nbt).resultOrPartial(LOGGER::error).orElse(null);
    }

}