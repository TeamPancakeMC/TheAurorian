package cn.teampancake.theaurorian.common.entities.phase.queen;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;

public class MoonQueenShrugPhase extends AttackPhase<MoonQueen> {

    public MoonQueenShrugPhase() {
        super(3, 1, 15, 10);
    }

    @Override
    public boolean canStart(MoonQueen entity, boolean coolDownOver) {
        return false;
    }

    @Override
    public void onStart(MoonQueen entity) {
        entity.triggerAnim("shrug_controller", "shrug_animation");
    }

    @Override
    public void tick(MoonQueen entity) {

    }

    @Override
    public boolean canContinue(MoonQueen entity) {
        return true;
    }

    @Override
    public void onStop(MoonQueen entity) {

    }

}