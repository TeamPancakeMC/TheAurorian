package cn.teampancake.theaurorian.common.entities.phase.queen;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;

public class MoonQueenLunaBefallEndPhase extends AttackPhase<MoonQueen> {

    public MoonQueenLunaBefallEndPhase() {
        super(5, 1, 20, 0);
    }

    @Override
    public boolean canStart(MoonQueen entity, boolean coolDownOver) {
        return false;
    }

    @Override
    public void onStart(MoonQueen entity) {
        String controller = "luna_befall_end_controller";
        String animation = "luna_befall_end_animation";
        entity.triggerAnim(controller, animation);
    }

    @Override
    public void tick(MoonQueen entity) {

    }

    @Override
    public boolean canContinue(MoonQueen entity) {
        return false;
    }

    @Override
    public void onStop(MoonQueen entity) {
        entity.setInvulnerable(false);
    }

}