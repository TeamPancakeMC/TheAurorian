package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;

public class MoonQueenMeleePhase extends AttackPhase<MoonQueen> {

    private final String[] parts = new String[] {"swing", "swing_2", "burst"};
    private final int[] hurtTargetTime = new int[] {9, 6, 8};
    private int randomIndex = -1;

    public MoonQueenMeleePhase() {
        super(1, 1, 22, 0);
    }

    @Override
    public boolean canStart(MoonQueen entity, boolean coolDownOver) {
        return entity.getPreparationTime() <= 0 && entity.isAlive() && TAEntityUtils.canReachTarget(entity, 2);
    }

    @Override
    public void onStart(MoonQueen entity) {
        this.randomIndex = RandomSource.create().nextInt(3);
        String part = this.parts[this.randomIndex];
        String controller = part + "_controller";
        String animation = part + "_animation";
        entity.triggerAnim(controller, animation);
        LivingEntity target = entity.getTarget();
        if (target != null) {
            entity.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
            entity.setAttackYRot(entity.getYRot());
        }
    }

    @Override
    public void tick(MoonQueen entity) {
        if (this.randomIndex >= 0 && entity.getAttackTicks() == this.hurtTargetTime[this.randomIndex]) {
            TAEntityUtils.performMeleeAttack(entity, 2);
        }
    }

    @Override
    public boolean canContinue(MoonQueen entity) {
        return true;
    }

    @Override
    public void onStop(MoonQueen entity) {

    }

}