package cn.teampancake.theaurorian.common.shields;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import cn.teampancake.theaurorian.common.registry.TAShields;
import cn.teampancake.theaurorian.common.utils.TAByteBufCodecs;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.AttachmentHolder;

import java.util.List;

public class BaseShield extends AttachmentHolder {

    private static final List<TagKey<DamageType>> DAMAGE_TYPES = List.of(DamageTypeTags.IS_FIRE, DamageTypeTags.IS_PROJECTILE,
            DamageTypeTags.IS_EXPLOSION, DamageTypeTags.IS_FIRE, DamageTypeTags.WITCH_RESISTANT_TO);
    public static final Codec<Holder<BaseShield>> CODEC = TAShields.REGISTRY.holderByNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<BaseShield>> STREAM_CODEC =
            TAByteBufCodecs.registry(TAShields.KEY, Registry::asHolderIdMap);
    private final DataComponentMap components;
    public int priority;
    public float rate;
    public int color;

    public BaseShield(Properties properties) {
        this.components = Properties.COMPONENT_INTERNER.intern(properties.components.build());
        this.priority = properties.priority;
        this.rate = properties.rate;
        this.color = properties.color;
    }

    public DataComponentMap components() {
        return this.components;
    }

    public void onBroken(LivingEntity livingEntity) {
        livingEntity.addEffect(new MobEffectInstance(TAMobEffects.BROKEN, 200, 0));
    }

    public boolean isNaturalRecovery(LivingEntity entity) {
        boolean isCombat = entity.getLastDamageSource() == null;
        return !entity.hasEffect(TAMobEffects.BROKEN) && isCombat;
    }

    public float naturalRecovery(LivingEntity entity) {
        return 1.0F;
    }

    public float applyDamageModifiers(LivingEntity entity, DamageSource source, float damage) {
        if (source.is(DamageTypes.MOB_ATTACK)) {
            return 0.0F;
        }

        for (TagKey<DamageType> damageTypeTag : DAMAGE_TYPES) {
            if (source.is(damageTypeTag)) {
                return damage * (1.0F - this.rate);
            }
        }

        return damage;
    }

    public float damage(LivingEntity entity, float damage) {
        return damage;
    }

    public boolean isDamageNegated(LivingEntity entity, DamageSource source, float damage) {
        return false;
    }

    public static class Properties {

        private static final Interner<DataComponentMap> COMPONENT_INTERNER = Interners.newStrongInterner();
        private final DataComponentMap.Builder components = DataComponentMap.builder();
        private int priority;
        private float rate = 0.0F;
        private int color;

        public <T> Properties component(DataComponentType<T> component, T value) {
            this.components.set(TADataComponents.SHIELD.get(), 0.0F);
            this.components.set(component, value);
            return this;
        }

        public Properties priority(int priority) {
            this.priority = priority;
            return this;
        }

        public Properties rate(float rate) {
            this.rate = rate;
            return this;
        }

        public Properties color(int color) {
            this.color = color;
            return this;
        }

    }

}