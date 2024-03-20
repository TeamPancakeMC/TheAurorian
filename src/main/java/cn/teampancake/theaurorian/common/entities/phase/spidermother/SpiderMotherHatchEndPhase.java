package cn.teampancake.theaurorian.common.entities.phase.spidermother;

import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;

public class SpiderMotherHatchEndPhase extends AttackPhase<SpiderMother> {

    public SpiderMotherHatchEndPhase() {
        super(7, 1, 17, 0);
    }

    @Override
    public boolean canStart(SpiderMother entity, boolean coolDownOver) {
        return false;
    }

    @Override
    public void onStart(SpiderMother entity) {
        entity.triggerAnim("hatch_end_controller", "hatch_end_animation");
    }

    @Override
    public void tick(SpiderMother entity) {

    }

    @Override
    public boolean canContinue(SpiderMother entity) {
        return true;
    }

    @Override
    public void onStop(SpiderMother entity) {

    }

}