package cn.teampancake.theaurorian.common.shield;

import cn.teampancake.theaurorian.api.IShield;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import cn.teampancake.theaurorian.common.registry.TAShields;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public abstract class BaseShield implements IShield {

    private final int priority;
    private final MaxShieldData maxShieldData;
    private float shield;
    private final int color;
    public float rate = 0.0f;

    public BaseShield(int priority, float shield, float maxShield, int color) {
        this.priority = priority;
        this.shield = shield;
        this.maxShieldData = new MaxShieldData(this,maxShield);
        this.color = color;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return TAShields.getKey(this);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public float getShield() {
        return shield;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void consumeShield(float shield) {
        this.shield = Math.max(this.shield - shield, 0);
    }

    @Override
    public void increaseShield(float shield) {
        this.shield = Math.min(this.shield + shield, this.maxShieldData.getMaxShield());
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Override
    public void increaseMaxShield(float maxShield) {
        this.maxShieldData.increaseMaxShield(maxShield);
    }

    @Override
    public void consumeMaxShield(float maxShield) {
        this.maxShieldData.consumeMaxShield(maxShield);
    }

    @Override
    public float getMaxShield() {
        return this.maxShieldData.getMaxShield();
    }

    @Override
    public MaxShieldData getMaxShieldData() {
        return maxShieldData;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putFloat("shield", this.shield);
        compoundTag.put("maxShieldData", this.maxShieldData.serializeNBT());
        compoundTag.putFloat("rate",rate);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        this.shield = compoundTag.getFloat("shield");
        this.maxShieldData.deserializeNBT(compoundTag.getCompound("maxShieldData"));
        this.rate = compoundTag.getFloat("rate");
    }

    @Override
    public float naturalRecovery(LivingEntity entity) {
        return 0.0f;
    }

    @Override
    public boolean isNaturalRecovery(LivingEntity entity) {
        boolean effect = entity.getEffect(TAMobEffects.BROKEN.get()) == null;
        boolean isCombat = entity.getLastDamageSource() == null;
        return effect && isCombat;
    }

    @Override
    public float applyDamageModifiers(LivingEntity entity, DamageSource source, float damage) {
        return damage;
    }

    @Override
    public float damage(LivingEntity entity, float damage) {
        return damage;
    }

    @Override
    public void onBroken(LivingEntity livingEntity) {
        livingEntity.addEffect(new MobEffectInstance(TAMobEffects.BROKEN.get(), 200, 0));
    }

    @Override
    public boolean isDamageNegated(LivingEntity entity, DamageSource source, float damage) {
        return false;
    }

    @Override
    public boolean isBroken() {
        return this.shield <= 0.0f && this.getMaxShield() != 0.0f;
    }

}