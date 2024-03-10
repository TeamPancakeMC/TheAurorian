package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.monster.SnowTundraGiantCrab;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;

public class SnowTundraGiantCrabMeleePhase extends AttackPhase<SnowTundraGiantCrab> {

    public static final int ID = 1;

    public SnowTundraGiantCrabMeleePhase() {
        super(ID, 1, 30, 10);
    }

    @Override
    public boolean canStart(SnowTundraGiantCrab entity, boolean coolDownOver) {
        return coolDownOver && TAEntityUtils.canReachTarget(entity, 3);
    }

    @Override
    public void onStart(SnowTundraGiantCrab entity) {
        String name = entity.getRandom().nextBoolean() ? "swing" : "smash";
        entity.triggerAnim(name + "_controller", name + "_animation");
    }

    @Override
    public void tick(SnowTundraGiantCrab entity) {
        if (entity.getAttackTicks() == 20f * 0.75f) {
            TAEntityUtils.performMeleeAttack(entity, 3);
        }
    }

    @Override
    public boolean canContinue(SnowTundraGiantCrab entity) {
        return true;
    }

    @Override
    public void onStop(SnowTundraGiantCrab entity) {

    }

}