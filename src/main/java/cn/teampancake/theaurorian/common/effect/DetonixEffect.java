package cn.teampancake.theaurorian.common.effect;

import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class DetonixEffect extends TAMobEffect{

    public DetonixEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF4500);
    }

    @Override
    public void onEffectExpired(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.isInvulnerableTo(livingEntity.level().damageSources().source(DamageTypes.EXPLOSION))) {
            livingEntity.level().explode(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), amplifier + 1, Level.ExplosionInteraction.MOB);
        }
    }

}