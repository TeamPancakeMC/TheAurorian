package cn.teampancake.theaurorian.common.entities.ai.goal;

import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class AnimalHurtByTargetGoal extends HurtByTargetGoal {

    public AnimalHurtByTargetGoal(PathfinderMob mob, Class<?>... toIgnoreDamage) {
        super(mob, toIgnoreDamage);
    }

    @Override
    public boolean canUse() {
        return TAWorldEvents.BLOOD_MOON.get().isActive(this.mob.level()) && super.canUse();
    }

}