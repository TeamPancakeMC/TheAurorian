package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.boss.MoonlightKnight;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.util.RandomSource;

public class MoonlightKnightNormalMeleePhase extends AttackPhase<MoonlightKnight> {

    private final String[] parts = new String[] {"swing", "swing_sting", "swing_stomp", "stomp"};
    private final int[] durations = new int[] {40, 46, 54, 48};
    private int randomIndex = -1;

    public MoonlightKnightNormalMeleePhase() {
        super(1, 1, 40, 0);
    }

    @Override
    public int getDuration(MoonlightKnight entity) {
        if (this.randomIndex >= 0) {
            return this.durations[this.randomIndex];
        } else {
            return super.getDuration(entity);
        }
    }

    @Override
    public boolean canStart(MoonlightKnight entity, boolean coolDownOver) {
        return coolDownOver && !entity.isCrystal() && TAEntityUtils.canReachTarget(entity, 2.5D);
    }

    @Override
    public void onStart(MoonlightKnight entity) {
        this.randomIndex = RandomSource.create().nextInt(3);
        String part = this.parts[this.randomIndex];
        String controller = part + "_controller";
        String animation = part + "_animation";
        entity.triggerAnim(controller, animation);
    }

    @Override
    public void tick(MoonlightKnight entity) {
        int i = this.randomIndex;
        int ticks = entity.getAttackTicks();
        if (i >= 0 && (i == 3 && ticks == 22 || i != 3 && ticks == 10)) {
            TAEntityUtils.performMeleeAttack(entity, 2.5D);
        }
    }

    @Override
    public boolean canContinue(MoonlightKnight entity) {
        return true;
    }

    @Override
    public void onStop(MoonlightKnight entity) {

    }

}