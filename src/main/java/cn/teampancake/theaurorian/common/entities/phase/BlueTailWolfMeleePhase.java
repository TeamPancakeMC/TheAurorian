package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.animal.BlueTailWolf;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;

public class BlueTailWolfMeleePhase extends AttackPhase<BlueTailWolf> {

    public BlueTailWolfMeleePhase() {
        super(1, 1, 10, 20);
    }

    @Override
    public boolean canStart(BlueTailWolf entity, boolean coolDownOver) {
        return coolDownOver && TAEntityUtils.canReachTarget(entity, 1);
    }

    @Override
    public void onStart(BlueTailWolf entity) {
        entity.triggerAnim("bite_controller", "bite_animation");
    }

    @Override
    public void tick(BlueTailWolf entity) {
        if (entity.getAttackTicks() == 4) {
            TAEntityUtils.performMeleeAttack(entity, 1);
        }
    }

    @Override
    public boolean canContinue(BlueTailWolf entity) {
        return true;
    }

    @Override
    public void onStop(BlueTailWolf entity) {

    }

}