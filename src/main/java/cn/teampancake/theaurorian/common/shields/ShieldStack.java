package cn.teampancake.theaurorian.common.shields;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAShields;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.*;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import javax.annotation.Nullable;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

public class ShieldStack implements DataComponentHolder, MutableDataComponentHolder {

    private static final DataComponentType<Float> SHIELD_COMPONENT = TADataComponents.SHIELD.get();
    private static final DataComponentType<Float> MAX_SHIELD_COMPONENT = TADataComponents.MAX_SHIELD.get();
    public static final Codec<ShieldStack> CODEC = Codec.lazyInitialized(() -> RecordCodecBuilder.create(
            instance -> instance.group(BaseShield.CODEC.fieldOf("id").forGetter(ShieldStack::getShield),
                    DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY)
                            .forGetter(stack -> stack.components.asPatch())).apply(instance, ShieldStack::new)));
    public static final StreamCodec<RegistryFriendlyByteBuf, ShieldStack> STREAM_CODEC = StreamCodec.composite(
            BaseShield.STREAM_CODEC, ShieldStack::getShield,
            DataComponentPatch.STREAM_CODEC, stack -> stack.components.asPatch(), ShieldStack::new);
    public static final ShieldStack EMPTY = new ShieldStack();
    @Nullable
    private final Holder<BaseShield> shield;
    private final PatchedDataComponentMap components;

    public ShieldStack(Holder<BaseShield> shield, PatchedDataComponentMap components) {
        this.shield = shield;
        this.components = components;
    }

    public ShieldStack(Holder<BaseShield> tag, DataComponentPatch components) {
        this(tag, PatchedDataComponentMap.fromPatch(tag.value().components(), components));
    }

    public ShieldStack(Holder<BaseShield> shield) {
        this(shield, new PatchedDataComponentMap(shield.value().components()));
    }

    private ShieldStack() {
        this.shield = null;
        this.components = new PatchedDataComponentMap(DataComponentMap.EMPTY);
    }

    public Holder<BaseShield> getShield() {
        return this.shield == null ? TAShields.COMMON : this.shield;
    }

    public float getShieldValue() {
        return this.getOrDefault(SHIELD_COMPONENT, 0.0F);
    }

    public float getMaxShieldValue() {
        return this.getOrDefault(MAX_SHIELD_COMPONENT, 0.0F);
    }

    public void consumeShield(float shield) {
        this.set(SHIELD_COMPONENT, Math.max(this.getShieldValue() - shield, 0.0F));
    }

    public void increaseShield(float shield) {
        this.set(SHIELD_COMPONENT, Math.min(this.getShieldValue() + shield, this.getMaxShieldValue()));
    }

    public void consumeMaxShield(float maxShield) {
        this.set(MAX_SHIELD_COMPONENT, Math.max(this.getMaxShieldValue() - maxShield, 0.0F));
    }

    public void increaseMaxShield(float maxShield) {
        this.set(MAX_SHIELD_COMPONENT, Math.max(this.getMaxShieldValue() + maxShield, 0.0F));
    }

    public float applyShields(LivingEntity entity, DamageSource source, float damage) {
        if (damage <= 0.0F) {
            return damage;
        } else {
            if (this.isBroken() && entity instanceof Player player) {
                this.getShield().value().onBroken(player);
                return damage;
            }

            float remainingDamage = this.getShield().value().applyDamageModifiers(entity, source, damage);
            this.consumeShield(damage - remainingDamage);
            return remainingDamage;
        }
    }

    public boolean isBroken() {
        return this.getShieldValue() <= 0.0f && this.getMaxShieldValue() != 0.0f;
    }

    public ShieldStack copy() {
        if (this.shield == null) {
            return EMPTY;
        } else {
            return new ShieldStack(this.getShield(), this.components.copy());
        }
    }

    @Nullable
    public <T, U> T update(DataComponentType<T> component, T defaultValue, U updateValue, BiFunction<T, U, T> updater) {
        return this.set(component, updater.apply(this.getOrDefault(component, defaultValue), updateValue));
    }

    @Nullable
    public <T> T update(DataComponentType<T> component, T defaultValue, UnaryOperator<T> updater) {
        T t = this.getOrDefault(component, defaultValue);
        return this.set(component, updater.apply(t));
    }

    @Override
    public <T> @Nullable T set(DataComponentType<? super T> component, @Nullable T value) {
        return this.components.set(component, value);
    }

    @Override
    public <T> @Nullable T remove(DataComponentType<? extends T> component) {
        return this.components.remove(component);
    }

    @Override
    public void applyComponents(DataComponentPatch components) {
        this.components.applyPatch(components);
    }

    @Override
    public void applyComponents(DataComponentMap components) {
        this.components.setAll(components);
    }

    @Override
    public DataComponentMap getComponents() {
        return this != EMPTY ? this.components : DataComponentMap.EMPTY;
    }

}