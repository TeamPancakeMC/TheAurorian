package cn.teampancake.theaurorian.common.shields;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class AurorianShield extends BaseShield {

    public AurorianShield(Properties properties) {
        super(properties);
    }

    @Override
    public float naturalRecovery(LivingEntity entity) {
        return 0.5F;
    }

    @Override
    public float applyDamageModifiers(LivingEntity entity, DamageSource source, float damage) {
        ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        if (key.getNamespace().equals(TheAurorian.MOD_ID)) {
            return damage * (1.0F - this.rate);
        } else {
            return damage;
        }
    }

}