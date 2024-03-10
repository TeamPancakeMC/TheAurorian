package cn.teampancake.theaurorian.common.entities.ai.goal;

import cn.teampancake.theaurorian.common.entities.animal.BlueTailWolf;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class BlueTailWolfDoNothingGoal extends Goal {

    private final BlueTailWolf mob;

    public BlueTailWolfDoNothingGoal(BlueTailWolf mob) {
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
        this.mob = mob;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public boolean canUse() {
        return this.mob.getAttackState() == 2;
    }

    public boolean canContinueToUse() {
        return this.mob.getAttackState() == 2;
    }

    public void tick() {
        this.mob.getNavigation().stop();
    }

}