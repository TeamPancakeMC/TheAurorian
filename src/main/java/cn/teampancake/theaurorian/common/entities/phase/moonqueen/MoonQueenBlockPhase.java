package cn.teampancake.theaurorian.common.entities.phase.moonqueen;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;

public class MoonQueenBlockPhase extends AttackPhase<MoonQueen> {

    public MoonQueenBlockPhase() {
        super(2, 1, 25, 10);
    }

    @Override
    public boolean canStart(MoonQueen entity, boolean coolDownOver) {
        return false;
    }

    @Override
    public void onStart(MoonQueen entity) {
        entity.triggerAnim("block_controller", "block_animation");
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