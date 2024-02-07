package cn.teampancake.theaurorian.common.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class HolinessEffect extends TAMobEffect {

    public HolinessEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xffffeb);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        livingEntity.getActiveEffectsMap().values().stream()
                .map(MobEffectInstance::getEffect)
                .filter(effect -> !effect.isBeneficial())
                .forEach(livingEntity::removeEffect);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}