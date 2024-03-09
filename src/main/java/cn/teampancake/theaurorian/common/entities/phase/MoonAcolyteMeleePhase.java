package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.monster.AttackPhase;
import cn.teampancake.theaurorian.common.entities.monster.MoonAcolyte;

public class MoonAcolyteMeleePhase extends AttackPhase<MoonAcolyte> {

    public MoonAcolyteMeleePhase() {
        super(1, 1, 30, 10);
    }

    @Override
    public boolean canStart(MoonAcolyte entity, boolean coolDownOver) {
        return coolDownOver && entity.canReachTarget(2);
    }

    @Override
    public void onStart(MoonAcolyte entity) {
        entity.triggerAnim("swing_controller", "swing_animation");
    }

    @Override
    public void tick(MoonAcolyte entity) {
        if (entity.getAttackTicks() == 9) {
            entity.performMeleeAttack(2);
        }
    }

    @Override
    public boolean canContinue(MoonAcolyte entity) {
        return true;
    }

    @Override
    public void onStop(MoonAcolyte entity) {

    }

}