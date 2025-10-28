package cn.teampancake.theaurorian.common.entities.ai.goal;

import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class AnimalPanicGoal extends PanicGoal {

    public AnimalPanicGoal(PathfinderMob mob, double speedModifier) {
        super(mob, speedModifier);
    }

    @Override
    public boolean canUse() {
        return !TAWorldEvents.BLOOD_MOON.get().isActive(this.mob.level()) && super.canUse();
    }

}