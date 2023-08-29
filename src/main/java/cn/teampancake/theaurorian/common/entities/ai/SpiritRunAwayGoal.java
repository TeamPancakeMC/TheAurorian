package cn.teampancake.theaurorian.common.entities.ai;

import cn.teampancake.theaurorian.common.entities.monster.Spirit;
import cn.teampancake.theaurorian.utils.EntityHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class SpiritRunAwayGoal extends Goal {

    private final Spirit spirit;
    private Path pathAway;

    public SpiritRunAwayGoal(Spirit spirit) {
        this.spirit = spirit;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.spirit.getTarget();
        if (target != null) {
            if (EntityHelper.isLookingAt(this.spirit, target, 0.1D) && this.spirit.hasLineOfSight(target)) {
                Vec3 vec3 = DefaultRandomPos.getPosAway(this.spirit, 16, 10, target.position());
                if (vec3 != null && target.distanceToSqr(vec3) >= target.distanceToSqr(this.spirit)) {
                    this.pathAway = this.spirit.getNavigation().createPath(vec3.x, vec3.y, vec3.z, 0);
                    return this.pathAway != null;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.spirit.getTarget() != null && this.spirit.getNavigation().isInProgress();
    }

    @Override
    public void stop() {
        this.spirit.getNavigation().stop();
        this.spirit.getNavigation().moveTo(this.pathAway, 2.0D);
    }

}