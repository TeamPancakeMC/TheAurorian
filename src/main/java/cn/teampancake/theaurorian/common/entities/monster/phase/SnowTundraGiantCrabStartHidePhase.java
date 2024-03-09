package cn.teampancake.theaurorian.common.entities.monster.phase;

import cn.teampancake.theaurorian.common.entities.monster.AttackPhase;
import cn.teampancake.theaurorian.common.entities.monster.SnowTundraGiantCrab;

public class SnowTundraGiantCrabStartHidePhase extends AttackPhase<SnowTundraGiantCrab> {

    public static final int ID = 2;

    public SnowTundraGiantCrabStartHidePhase() {
        super(ID, 1, 35, 500, SnowTundraGiantCrabHidePhase.ID);
    }

    @Override
    public boolean canStart(SnowTundraGiantCrab entity, boolean coolDownOver) {
        return coolDownOver && entity.getHealth() < entity.getMaxHealth() * 0.3;
    }

    @Override
    public void onStart(SnowTundraGiantCrab entity) {
        entity.triggerAnim("start_hide_controller", "start_hide_animation");
    }

    @Override
    public void tick(SnowTundraGiantCrab entity) {

    }

    @Override
    public boolean canContinue(SnowTundraGiantCrab entity) {
        return true;
    }

    @Override
    public void onStop(SnowTundraGiantCrab entity) {

    }

}