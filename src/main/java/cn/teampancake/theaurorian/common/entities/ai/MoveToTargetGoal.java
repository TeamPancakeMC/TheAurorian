package cn.teampancake.theaurorian.common.entities.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class MoveToTargetGoal extends Goal {
    private final Mob mob;
    private final double speedModifier;
    private final int recalculateInterval;
    private Vec3 lastTargetPos = Vec3.ZERO;
    private int lastRecalculate = 0;

    public MoveToTargetGoal(Mob mob, double speedModifier) {
        this(mob, speedModifier, 60);
    }

    public MoveToTargetGoal(Mob mob, double speedModifier, int recalculateInterval) {
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.recalculateInterval = recalculateInterval;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public boolean canUse() {
        return mob.getTarget() != null;
    }

    public boolean canContinueToUse() {
        return mob.getTarget() != null;
    }

    public void tick() {
        if (mob.getTarget() != null) {
            mob.getLookControl().setLookAt(mob.getTarget(), 500, 500);
            if (lastTargetPos != mob.getTarget().position() && mob.tickCount - lastRecalculate > recalculateInterval) {
                lastTargetPos = mob.getTarget().position();
                lastRecalculate = mob.tickCount;
                mob.getNavigation().moveTo(mob.getTarget().getX(), mob.getTarget().getY(), mob.getTarget().getZ(), speedModifier);
            }
        }
    }
}
