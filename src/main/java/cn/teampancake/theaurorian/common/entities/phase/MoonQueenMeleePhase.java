package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.world.entity.LivingEntity;

public class MoonQueenMeleePhase extends AttackPhase<MoonQueen> {

    public MoonQueenMeleePhase() {
        super(1, 1, 42, 10);
    }

    @Override
    public boolean canStart(MoonQueen entity, boolean coolDownOver) {
        return coolDownOver && TAEntityUtils.canReachTarget(entity, 2);
    }

    @Override
    public void onStart(MoonQueen entity) {
        entity.triggerAnim("swing_controller", "swing_animation");
        LivingEntity target = entity.getTarget();
        if (target != null) {
            entity.getLookControl().setLookAt(target);
            entity.setAttackYRot(entity.getYRot());
        }
    }

    @Override
    public void tick(MoonQueen entity) {
        if (entity.getAttackTicks() == 13 || entity.getAttackTicks() == 25) {
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