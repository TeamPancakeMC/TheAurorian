package cn.teampancake.theaurorian.common.entities.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class LookAtTargetGoal extends Goal {
    private final Mob mob;

    public LookAtTargetGoal(Mob mob) {
        setFlags(EnumSet.of(Flag.LOOK));
        this.mob = mob;
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
        }
    }
}
