package cn.teampancake.theaurorian.common.entities.monster;

public class SnowTundraGiantCrabStopHidePhase extends AttackPhase<SnowTundraGiantCrab> {
    public static final int ID = 4;

    public SnowTundraGiantCrabStopHidePhase() {
        super(ID, 1, 60, 0);
    }

    @Override
    public boolean canStart(SnowTundraGiantCrab entity, boolean coolDownOver) {
        return false;
    }

    @Override
    public void onStart(SnowTundraGiantCrab entity) {

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
