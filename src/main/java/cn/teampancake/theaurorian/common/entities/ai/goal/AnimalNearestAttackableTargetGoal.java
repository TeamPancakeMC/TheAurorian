package cn.teampancake.theaurorian.common.entities.ai.goal;

import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class AnimalNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    public AnimalNearestAttackableTargetGoal(Mob mob, Class<T> targetType) {
        super(mob, targetType, Boolean.TRUE);
    }

    @Override
    public boolean canUse() {
        return TAWorldEvents.BLOOD_MOON.get().isActive(this.mob.level()) && super.canUse();
    }

}