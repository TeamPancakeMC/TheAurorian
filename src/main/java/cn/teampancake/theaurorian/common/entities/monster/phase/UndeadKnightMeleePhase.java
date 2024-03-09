package cn.teampancake.theaurorian.common.entities.monster.phase;

import cn.teampancake.theaurorian.common.entities.monster.AttackPhase;
import cn.teampancake.theaurorian.common.entities.monster.UndeadKnight;

public class UndeadKnightMeleePhase extends AttackPhase<UndeadKnight> {

    public static final int ID = 1;

    public UndeadKnightMeleePhase() {
        super(ID, 1, 25, 10);
    }

    @Override
    public boolean canStart(UndeadKnight entity, boolean coolDownOver) {
        return coolDownOver && entity.canReachTarget(3);
    }

    @Override
    public void onStart(UndeadKnight entity) {

    }

    @Override
    public void tick(UndeadKnight entity) {
        if (entity.getAttackTicks() == 12) {
            entity.performMeleeAttack(3);
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
