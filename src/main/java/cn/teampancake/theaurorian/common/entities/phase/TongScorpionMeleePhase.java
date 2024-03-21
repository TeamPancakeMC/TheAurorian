package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.monster.TongScorpion;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;

public class TongScorpionMeleePhase extends AttackPhase<TongScorpion> {

    public TongScorpionMeleePhase() {
        super(1, 1, 20, 10);
    }

    @Override
    public boolean canStart(TongScorpion entity, boolean coolDownOver) {
        return coolDownOver && TAEntityUtils.canReachTarget(entity, 2.0D);
    }

    @Override
    public void onStart(TongScorpion entity) {
        entity.triggerAnim("swing_controller", "swing_animation");
    }

    @Override
    public void tick(TongScorpion entity) {
        if (entity.getAttackTicks() == 6) {
            TAEntityUtils.performMeleeAttack(entity, 2.0D);
        }
    }

    @Override
    public boolean canContinue(TongScorpion entity) {
        return true;
    }

    @Override
    public void onStop(TongScorpion entity) {

    }

}