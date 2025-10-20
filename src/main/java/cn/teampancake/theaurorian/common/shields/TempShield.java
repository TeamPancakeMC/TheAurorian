package cn.teampancake.theaurorian.common.shields;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public class TempShield extends BaseShield {

    @Nullable
    private BaseShield shieldType;

    public TempShield(Properties properties) {
        super(properties);
    }

    @Override
    public float naturalRecovery(LivingEntity entity) {
        if (this.shieldType != null) {
            return this.shieldType.naturalRecovery(entity);
        }

        return super.naturalRecovery(entity);
    }

    @Override
    public boolean isNaturalRecovery(LivingEntity entity) {
        if (this.shieldType != null) {
            return this.shieldType.isNaturalRecovery(entity);
        }

        return super.isNaturalRecovery(entity);
    }

    @Override
    public float applyDamageModifiers(LivingEntity entity, DamageSource source, float damage) {
        if (this.shieldType != null) {
            return this.shieldType.applyDamageModifiers(entity, source, damage);
        }
        return super.applyDamageModifiers(entity, source, damage);
    }

    @Override
    public float damage(LivingEntity entity, float damage) {
        if (this.shieldType != null) {
            return this.shieldType.damage(entity, damage);
        }
        return super.damage(entity, damage);
    }

    @Override
    public boolean isDamageNegated(LivingEntity entity, DamageSource source, float damage) {
        if (this.shieldType != null) {
            return this.shieldType.isDamageNegated(entity, source, damage);
        }
        return super.isDamageNegated(entity, source, damage);
    }

}