package cn.teampancake.theaurorian.common.entities.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class MeleeNoAttackGoal extends MeleeAttackGoal {

    public MeleeNoAttackGoal(PathfinderMob mob, boolean followingTargetEvenIfNotSeen) {
        super(mob, 1.0D, followingTargetEvenIfNotSeen);
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        // we use our custom attack manager
    }

}