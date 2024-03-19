package cn.teampancake.theaurorian.common.entities.phase.spidermother;

import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;

public class SpiderMotherHatchBeginPhase extends AttackPhase<SpiderMother> {

    public SpiderMotherHatchBeginPhase() {
        super(5, 1, 6, 0, 6);
    }

    @Override
    public boolean canStart(SpiderMother entity, boolean coolDownOver) {
        return entity.getHealth() < entity.getMaxHealth() * 0.5F;
    }

    @Override
    public void onStart(SpiderMother entity) {
        entity.triggerAnim("hatch_begin_controller", "hatch_begin_animation");
    }

    @Override
    public void tick(SpiderMother entity) {

    }

    @Override
    public boolean canContinue(SpiderMother entity) {
        return false;
    }

    @Override
    public void onStop(SpiderMother entity) {

    }

}