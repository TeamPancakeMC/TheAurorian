package cn.teampancake.theaurorian.common.shields;

import net.minecraft.world.entity.LivingEntity;

public class BloodMoonShield extends BaseShield {

    public BloodMoonShield(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isNaturalRecovery(LivingEntity entity) {
        return false;
    }

}