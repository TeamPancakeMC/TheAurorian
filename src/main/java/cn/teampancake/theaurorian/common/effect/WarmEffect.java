package cn.teampancake.theaurorian.common.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class WarmEffect extends TAMobEffect{

    public WarmEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xfdefab);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.getHealth() < livingEntity.getMaxHealth()) {
            livingEntity.heal(1.0F);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int k = 100 >> amplifier;
        if (k > 0) {
            return duration % k == 0;
        } else {
            return true;
        }
    }

}