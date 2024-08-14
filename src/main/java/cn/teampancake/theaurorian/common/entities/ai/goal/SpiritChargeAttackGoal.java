package cn.teampancake.theaurorian.common.entities.ai.goal;

import cn.teampancake.theaurorian.common.entities.monster.Spirit;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class SpiritChargeAttackGoal extends Goal {
    
    private final Spirit spirit;

    public SpiritChargeAttackGoal(Spirit spirit) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.spirit = spirit;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.spirit.getTarget();
        boolean flag = this.spirit.getRandom().nextInt(reducedTickDelay(7)) == 0;
        if (target != null && target.isAlive() && !this.spirit.getMoveControl().hasWanted() && flag) {
            return this.spirit.distanceToSqr(target) > 4.0D;
        } else {
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.spirit.getMoveControl().hasWanted() && this.spirit.getFlag(1) && this.spirit.getTarget() != null && this.spirit.getTarget().isAlive();
    }

    @Override
    public void start() {
        LivingEntity target = this.spirit.getTarget();
        if (target != null) {
            Vec3 vec3 = target.getEyePosition();
            this.spirit.getMoveControl().setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
        }

        this.spirit.setFlag(1, true);
        this.spirit.playSound(SoundEvents.VEX_CHARGE, 1.0F, 1.0F);
    }

    @Override
    public void stop() {
        this.spirit.setFlag(1, false);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = this.spirit.getTarget();
        if (target != null) {
            if (this.spirit.getBoundingBox().intersects(target.getBoundingBox())) {
                this.spirit.doHurtTarget(target);
                this.spirit.setFlag(1, false);
            } else {
                if (this.spirit.distanceToSqr(target) < 9.0D) {
                    Vec3 vec3 = target.getEyePosition();
                    this.spirit.getMoveControl().setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
                }
            }
        }
    }
    
}