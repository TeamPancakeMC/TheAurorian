package cn.teampancake.theaurorian.common.entities.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;

import java.util.EnumSet;

public class ModRangedAttackGoal extends Goal {

    protected final Monster monster;
    private final float minDistance;
    private final float maxDistance;
    protected int attackCooldown = 0;
    protected int attackWindUp = 0;
    protected double targetX;
    protected double targetY;
    protected double targetZ;

    public ModRangedAttackGoal(Monster monster, float minDistance, float maxDistance) {
        this.monster = monster;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.monster.getTarget();
        if (target != null) {
            float distance = this.monster.distanceTo(target);
            boolean flag = this.monster.getSensing().hasLineOfSight(target);
            if (distance >= this.minDistance && distance <= this.maxDistance && flag) {
                if (this.attackCooldown > 0) {
                    this.attackCooldown--;
                } else {
                    return this.attackCooldown == 0;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.monster.getTarget();
        if (target != null && this.monster.getSensing().hasLineOfSight(target) && this.attackCooldown == 0 || this.attackCooldown == 0) {
            return true;
        } else {
            this.setAttack(false);
            return false;
        }
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void start() {
        this.monster.getNavigation().stop();
        this.setAttack(true);
        this.startAttack();
    }

    @Override
    public void stop() {
        this.monster.getNavigation().stop();
    }

    public void setAttack(boolean bool) {}

    public void startAttack() {}

    protected void setAttackLocation(LivingEntity target) {
        this.targetX = target.getX();
        this.targetY = target.getY();
        this.targetZ = target.getZ();
    }

}