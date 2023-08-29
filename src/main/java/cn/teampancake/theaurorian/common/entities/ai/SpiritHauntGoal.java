package cn.teampancake.theaurorian.common.entities.ai;

import cn.teampancake.theaurorian.common.entities.monster.Spirit;
import cn.teampancake.theaurorian.utils.EntityHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;

import java.util.EnumSet;

public class SpiritHauntGoal extends Goal {

    private enum Direction {
        LEFT, RIGHT
    }

    private final Spirit spirit;
    private Direction strafeDirection;

    public SpiritHauntGoal(Spirit spirit) {
        this.spirit = spirit;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.spirit.getTarget();
        if (target != null && this.spirit.distanceTo(target) <= 8.0F) {
            if (this.spirit.hasLineOfSight(target)) {
                return !EntityHelper.isLookingAt(this.spirit, target, 0.1F);
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.spirit.getTarget();
        if (target != null && this.spirit.distanceTo(target) <= 8.0F) {
            if (this.strafeDirection != null && this.spirit.hasLineOfSight(target)) {
                return !EntityHelper.isLookingAt(this.spirit, target, 0.1F);
            }
        }
        return false;
    }

    @Override
    public void start() {
        this.spirit.getNavigation().stop();
        this.strafeDirection = this.getRandomDirection();
    }

    @Override
    public void stop() {
        this.spirit.getNavigation().stop();
        this.spirit.setXxa(0.0F);
    }

    @Override
    public void tick() {
        LivingEntity target = this.spirit.getTarget();
        if (target != null) {
            this.spirit.lookAt(target, 40.0F, 40.0F);
            this.spirit.setXxa(this.strafeDirection == Direction.RIGHT ? 0.3F : -0.3F);
            float distance = this.spirit.distanceTo(target);
            PathNavigation navigation = this.spirit.getNavigation();
            if (distance >= 2.0F) {
                navigation.moveTo(target, 1.75F);
            } else if (distance < 2.0F && navigation.getPath() != null) {
                this.spirit.getNavigation().stop();
            } else if (distance <= 1.75F) {
                this.spirit.doHurtTarget(target);
                this.strafeDirection = null;
            } else {
                target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60));
            }
        }
    }

    private Direction getRandomDirection() {
        return this.spirit.getRandom().nextBoolean() ? Direction.LEFT : Direction.RIGHT;
    }

}