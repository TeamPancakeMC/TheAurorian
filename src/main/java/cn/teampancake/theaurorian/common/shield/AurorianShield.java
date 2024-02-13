package cn.teampancake.theaurorian.common.shield;

import cn.teampancake.theaurorian.api.IShield;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;

public class AurorianShield extends CommonShield {

    public AurorianShield(int priority,float shield, float maxShield,int color) {
        super(priority,shield, maxShield,color);
        this.rate = 0.25f;
    }

    @Override
    public IShield copy() {
        return new AurorianShield(getPriority(),getShield(), getMaxShield(),getColor());
    }

    @Override
    public float naturalRecovery(LivingEntity entity) {
        return 0.5f;
    }

    @Override
    public float applyDamageModifiers(LivingEntity entity, DamageSource source, float damage) {
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        if (key != null && key.getNamespace().equals("theaurorian")) {
            return damage * (1 - rate);
        }
        return damage;
    }

}