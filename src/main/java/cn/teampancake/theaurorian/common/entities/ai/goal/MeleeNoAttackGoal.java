package cn.teampancake.theaurorian.common.entities.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class MeleeNoAttackGoal extends MeleeAttackGoal {

    public MeleeNoAttackGoal(PathfinderMob mob, boolean followingTargetEvenIfNotSeen) {
        this(mob, 1.0D, followingTargetEvenIfNotSeen);
    }

    public MeleeNoAttackGoal(PathfinderMob mob, double speed, boolean followingTargetEvenIfNotSeen) {
        super(mob, speed, followingTargetEvenIfNotSeen);
    }

    public MeleeNoAttackGoal(PathfinderMob mob) {
        this(mob, 1.0D, Boolean.FALSE);
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.mob.getTarget();
        // 只有当实体有目标时才执行此行为
        return target != null && target.isAlive() && super.canUse();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy) {
        // we use our custom attack manager
    }

}