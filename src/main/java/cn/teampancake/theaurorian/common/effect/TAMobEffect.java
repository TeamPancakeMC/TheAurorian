package cn.teampancake.theaurorian.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class TAMobEffect extends MobEffect {

    public TAMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public void onEffectRemoved(LivingEntity livingEntity) {}

    public void onEffectExpired(LivingEntity livingEntity, int amplifier) {}

}