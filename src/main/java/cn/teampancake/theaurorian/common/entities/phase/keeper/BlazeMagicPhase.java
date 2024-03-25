package cn.teampancake.theaurorian.common.entities.phase.keeper;

import cn.teampancake.theaurorian.common.entities.boss.RunestoneKeeper;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;

public class BlazeMagicPhase extends AttackPhase<RunestoneKeeper> {

    public BlazeMagicPhase() {
        super(1, 1, 20, 400);
    }

    @Override
    public boolean canStart(RunestoneKeeper entity, boolean coolDownOver) {
        return false;
    }

    @Override
    public void onStart(RunestoneKeeper entity) {

    }

    @Override
    public void tick(RunestoneKeeper entity) {

    }

    @Override
    public boolean canContinue(RunestoneKeeper entity) {
        return true;
    }

    @Override
    public void onStop(RunestoneKeeper entity) {

    }

}