package cn.teampancake.theaurorian.common.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class HolinessEffect extends TAMobEffect {

    public HolinessEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xffffeb);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        livingEntity.getActiveEffects().stream().filter(instance -> getCategory() == MobEffectCategory.HARMFUL)
                .forEach(instance -> livingEntity.removeEffect(instance.getEffect()));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}