package cn.teampancake.theaurorian.common.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class LacerationEffect extends TAMobEffect {

    public LacerationEffect() {
        super(MobEffectCategory.HARMFUL, 0x710817);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Vec3 vec3 = livingEntity.getDeltaMovement();
        DamageSource source = livingEntity.damageSources().magic();
        if (vec3.x > 0.0D || vec3.y > 0.0D || vec3.z > 0.0D) {
            livingEntity.hurt(source, amplifier + 1);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}