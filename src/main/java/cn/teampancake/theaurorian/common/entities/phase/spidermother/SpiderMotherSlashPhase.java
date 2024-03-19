package cn.teampancake.theaurorian.common.entities.phase.spidermother;

import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.world.entity.LivingEntity;

public class SpiderMotherSlashPhase extends AttackPhase<SpiderMother> {

    public SpiderMotherSlashPhase() {
        super(1, 1, 20, 10);
    }

    @Override
    public boolean canStart(SpiderMother entity, boolean coolDownOver) {
        return coolDownOver && entity.getTarget() != null && TAEntityUtils.canReachTarget(entity, 2);
    }

    @Override
    public void onStart(SpiderMother entity) {
        entity.triggerAnim("slash_controller", "slash_animation");
    }

    @Override
    public void tick(SpiderMother entity) {
        LivingEntity target = entity.getTarget();
        if (entity.getAttackTicks() == 6 && target != null) {
            entity.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
            TAEntityUtils.performMeleeAttack(entity, 2.0D);
        }
    }

    @Override
    public boolean canContinue(SpiderMother entity) {
        return true;
    }

    @Override
    public void onStop(SpiderMother entity) {

    }

}