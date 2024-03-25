package cn.teampancake.theaurorian.common.entities.phase.keeper;

import cn.teampancake.theaurorian.common.entities.boss.RunestoneKeeper;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;

public class UndeadMagicPhase extends AttackPhase<RunestoneKeeper> {

    public UndeadMagicPhase(int duration, int coolDown) {
        super(4, 1, duration, coolDown);
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
        return false;
    }

    @Override
    public void onStop(RunestoneKeeper entity) {

    }
}
