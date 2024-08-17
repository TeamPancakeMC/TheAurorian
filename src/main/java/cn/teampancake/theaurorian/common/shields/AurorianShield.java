package cn.teampancake.theaurorian.common.shields;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.api.IShield;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class AurorianShield extends CommonShield {

    public AurorianShield(int priority,float shield, float maxShield,int color) {
        super(priority, shield, maxShield, color);
        this.rate = 0.25f;
    }

    @Override
    public IShield copy() {
        return new AurorianShield(this.getPriority(), this.getShield(), this.getMaxShield(), this.getColor());
    }

    @Override
    public float naturalRecovery(LivingEntity entity) {
        return 0.5f;
    }

    @Override
    public float applyDamageModifiers(LivingEntity entity, DamageSource source, float damage) {
        ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        if (key.getNamespace().equals(TheAurorian.MOD_ID)) {
            return damage * (1 - this.rate);
        }

        return damage;
    }

}