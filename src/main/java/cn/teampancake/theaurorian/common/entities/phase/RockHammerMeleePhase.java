package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.monster.RockHammer;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;

public class RockHammerMeleePhase extends AttackPhase<RockHammer> {

    public RockHammerMeleePhase() {
        super(1, 1, 25, 10);
    }

    @Override
    public boolean canStart(RockHammer entity, boolean coolDownOver) {
        return coolDownOver && TAEntityUtils.canReachTarget(entity, 2);
    }

    @Override
    public void onStart(RockHammer entity) {
        entity.triggerAnim("smash_controller", "smash_animation");
    }

    @Override
    public void tick(RockHammer entity) {
        if (entity.getAttackTicks() == 10) {
            TAEntityUtils.performMeleeAttack(entity, 2);
        }
    }

    @Override
    public boolean canContinue(RockHammer entity) {
        return true;
    }

    @Override
    public void onStop(RockHammer entity) {

    }

}