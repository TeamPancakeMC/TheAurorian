package cn.teampancake.theaurorian.common.shield;

import cn.teampancake.theaurorian.api.IShield;
import net.minecraft.world.entity.LivingEntity;

public class AurorianShield extends CommonShield{
    public AurorianShield(int priority,float shield, float maxShield,int color) {
        super(priority,shield, maxShield,color);
    }

    @Override
    public IShield copy() {
        return new AurorianShield(getPriority(),getShield(), getMaxShield(),getColor());
    }

    @Override
    public float naturalRecovery(LivingEntity entity) {
        return 0.5f;
    }
}
