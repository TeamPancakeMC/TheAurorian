package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.animal.BlueTailWolf;

public class BlueTailWolfHowlPhase extends AttackPhase<BlueTailWolf> {

    public BlueTailWolfHowlPhase() {
        super(2, 1, 70, 200);
    }

    @Override
    public boolean canStart(BlueTailWolf entity, boolean coolDownOver) {
        return coolDownOver && entity.getTarget() != null && entity.hasNearbyFriends();
    }

    @Override
    public void onStart(BlueTailWolf entity) {
        entity.triggerAnim("howl_controller", "howl_animation");
    }

    @Override
    public void tick(BlueTailWolf entity) {
        if (entity.getAttackTicks() == 40) {
            entity.alertNearbyFriends();
        }
    }

    @Override
    public boolean canContinue(BlueTailWolf entity) {
        return true;
    }

    @Override
    public void onStop(BlueTailWolf entity) {

    }

}