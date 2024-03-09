package cn.teampancake.theaurorian.common.entities.ai;

import cn.teampancake.theaurorian.common.entities.monster.SnowTundraGiantCrab;
import cn.teampancake.theaurorian.common.entities.monster.SnowTundraGiantCrabHidePhase;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class GiantCrabDoNothingGoal extends Goal {
    private final SnowTundraGiantCrab mob;

    public GiantCrabDoNothingGoal(SnowTundraGiantCrab mob) {
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
        this.mob = mob;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public boolean canUse() {
        return mob.getAttackState() == SnowTundraGiantCrabHidePhase.ID;
    }

    public boolean canContinueToUse() {
        return mob.getAttackState() == SnowTundraGiantCrabHidePhase.ID;
    }

    public void tick() {
        mob.getNavigation().stop();
    }
}
