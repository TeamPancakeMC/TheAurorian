package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.monster.UndeadKnight;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;

public class UndeadKnightMeleePhase extends AttackPhase<UndeadKnight> {

    public static final int ID = 1;

    public UndeadKnightMeleePhase() {
        super(ID, 1, 25, 10);
    }

    @Override
    public boolean canStart(UndeadKnight entity, boolean coolDownOver) {
        return coolDownOver && TAEntityUtils.canReachTarget(entity, 3);
    }

    @Override
    public void onStart(UndeadKnight entity) {

    }

    @Override
    public void tick(UndeadKnight entity) {
        if (entity.getAttackTicks() == 12) {
            TAEntityUtils.performMeleeAttack(entity, 3);
        }
    }

    @Override
    public boolean canContinue(UndeadKnight entity) {
        return true;
    }

    @Override
    public void onStop(UndeadKnight entity) {

    }
}
