package cn.teampancake.theaurorian.api;

import cn.teampancake.theaurorian.common.shields.MaxShieldData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface IShield extends INBTSerializable<CompoundTag> {

    ResourceLocation getRegistryName();

    int getPriority();

    float getShield();

    int getColor();

    void consumeShield(float shield);

    void increaseShield(float shield);

    float getMaxShield();

    MaxShieldData getMaxShieldData();

    float naturalRecovery(LivingEntity entity);

    boolean isNaturalRecovery(LivingEntity entity);

    IShield copy();

    void increaseMaxShield(float maxShield);

    void consumeMaxShield(float maxShield);

    float damage(LivingEntity entity, float damage);

    float applyDamageModifiers(LivingEntity entity, DamageSource source, float damage);

    void onBroken(LivingEntity livingEntity);

    boolean isDamageNegated(LivingEntity entity, DamageSource source, float damage);

    boolean isBroken();

}