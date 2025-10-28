package cn.teampancake.theaurorian.common.entities.ai.goal;

import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class AnimalMeleeAttackGoal extends MeleeAttackGoal {

    public AnimalMeleeAttackGoal(PathfinderMob mob, double speedModifier) {
        super(mob, speedModifier, Boolean.FALSE);
    }

    @Override
    public boolean canUse() {
        return TAWorldEvents.BLOOD_MOON.get().isActive(this.mob.level()) && super.canUse();
    }

}