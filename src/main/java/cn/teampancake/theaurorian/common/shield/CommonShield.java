package cn.teampancake.theaurorian.common.shield;


import cn.teampancake.theaurorian.api.IShield;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;

import static net.minecraft.tags.DamageTypeTags.*;

public class CommonShield extends BaseShield{
    public float rate = 0.25f;
    protected static final TagKey<DamageType>[] DAMAGE_TYPE_TAGS = new TagKey[]{
            IS_FIRE,IS_PROJECTILE,IS_EXPLOSION,IS_FALL,WITCH_RESISTANT_TO
    };
    public CommonShield(int priority,float shield, float maxShield,int color) {
        super(priority, shield, maxShield,color);
    }
    public CommonShield(int maxShield) {
        super(0, maxShield, maxShield,0xFF808080);
    }


    @Override
    public IShield copy() {
        return new CommonShield(getPriority(),getShield(), getMaxShield(),getColor());
    }

    @Override
    public float naturalRecovery(LivingEntity entity) {
        return 1.0f;
    }
    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = super.serializeNBT();
        compoundTag.putFloat("rate",rate);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        super.deserializeNBT(compoundTag);
        this.rate = compoundTag.getFloat("rate");
    }

    @Override
    public float applyDamageModifiers(LivingEntity entity, DamageSource source, float damage) {
        if (source.is(DamageTypes.MOB_ATTACK)) {
            return 0.0f;
        }
        for (TagKey<DamageType> damageTypeTag : DAMAGE_TYPE_TAGS) {
            if (source.is(damageTypeTag)) {
                return damage * (1 - rate);
            }
        }
        return damage;
    }
}