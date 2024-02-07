package cn.teampancake.theaurorian.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class SanctificationEffect extends MobEffect {
    public SanctificationEffect() {
        super(MobEffectCategory.BENEFICIAL,0xbdc3c7);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        livingEntity.getActiveEffectsMap().values().stream()
                .map(MobEffectInstance::getEffect)
                .filter(effect -> !effect.isBeneficial())
                .forEach(livingEntity::removeEffect);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
