package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.boss.MoonlightKnight;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.util.RandomSource;

public class MoonlightKnightBeastMeleePhase extends AttackPhase<MoonlightKnight> {

    private final String[] parts = new String[] {"swing_beast", "burst_beast", "claw"};
    private final int[] durations = new int[] {28, 30, 35};
    private final int[] hurtTargetTime = new int[] {9, 13, 13};
    private int randomIndex = -1;

    public MoonlightKnightBeastMeleePhase() {
        super(2, 1, 30, 0);
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
        return coolDownOver && entity.isCrystal() && TAEntityUtils.canReachTarget(entity, 2.5D);
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
        if (i >= 0 && entity.getAttackTicks() < this.hurtTargetTime[i]) {
            TAEntityUtils.performMeleeAttack(entity, 3.0D);
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