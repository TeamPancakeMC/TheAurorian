package cn.teampancake.theaurorian.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class IncantationEffect extends TAMobEffect {

    public IncantationEffect() {
        super(MobEffectCategory.HARMFUL, 0x665b4a);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        livingEntity.getActiveEffectsMap().values().stream()
                .map(MobEffectInstance::getEffect)
                .filter(MobEffect::isBeneficial)
                .forEach(livingEntity::removeEffect);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}