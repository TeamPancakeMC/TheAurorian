package cn.teampancake.theaurorian.common.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class TempShieldEffect extends TAMobEffect {

    public TempShieldEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF800000);
    }

    @Override
    public void onEffectExpired(LivingEntity livingEntity, int amplifier) {

    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

}