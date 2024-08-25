package cn.teampancake.theaurorian.common.entities.phase.moonqueen;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;

public class MoonQueenRangedPhase extends AttackPhase<MoonQueen> {

    public MoonQueenRangedPhase() {
        super(3, 2, 30, 0);
    }

    @Override
    public boolean canStart(MoonQueen entity, boolean coolDownOver) {
        return entity.preparationTime <= 0 && entity.isAlive() && TAEntityUtils.canReachTarget(entity, 12.0D);
    }

    @Override
    public void onStart(MoonQueen entity) {

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