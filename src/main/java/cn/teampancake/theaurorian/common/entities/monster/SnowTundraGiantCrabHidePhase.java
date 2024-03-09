package cn.teampancake.theaurorian.common.entities.monster;

public class SnowTundraGiantCrabHidePhase extends AttackPhase<SnowTundraGiantCrab> {
    public static final int ID = 3;

    public SnowTundraGiantCrabHidePhase() {
        super(ID, 1, 200, 0, SnowTundraGiantCrabStopHidePhase.ID);
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
        entity.heal(0.04f);
        entity.getNavigation().stop();
        entity.getLookControl().setLookAt(entity.getX() + 1, entity.getY(), entity.getZ(), 360, 360);
    }

    @Override
    public boolean canContinue(SnowTundraGiantCrab entity) {
        return true;
    }

    @Override
    public void onStop(SnowTundraGiantCrab entity) {
        entity.triggerAnim("stop_hide_controller", "stop_hide_animation");
    }
}
