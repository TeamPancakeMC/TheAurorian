package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.monster.AttackPhase;
import cn.teampancake.theaurorian.common.entities.monster.Spirit;

public class SpiritMeleePhase extends AttackPhase<Spirit> {

    public SpiritMeleePhase() {
        super(1, 1, 30, 10);
    }

    @Override
    public boolean canStart(Spirit entity, boolean coolDownOver) {
        return coolDownOver && entity.canReachTarget(2);
    }

    @Override
    public void onStart(Spirit entity) {
        entity.triggerAnim("swing_controller", "swing_animation");
    }

    @Override
    public void tick(Spirit entity) {
        if (entity.getAttackTicks() == 9) {
            entity.performMeleeAttack(2);
        }
    }

    @Override
    public boolean canContinue(Spirit entity) {
        return true;
    }

    @Override
    public void onStop(Spirit entity) {

    }

}