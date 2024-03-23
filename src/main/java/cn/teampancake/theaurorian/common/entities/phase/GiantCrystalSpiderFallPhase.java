package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.monster.GiantCrystalSpider;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class GiantCrystalSpiderFallPhase extends AttackPhase<GiantCrystalSpider> {

    public GiantCrystalSpiderFallPhase() {
        super(1, 1, 60, 10);
    }

    @Override
    public boolean canStart(GiantCrystalSpider entity, boolean coolDownOver) {
        return coolDownOver && TAEntityUtils.canReachTarget(entity, 4.0D);
    }

    @Override
    public void onStart(GiantCrystalSpider entity) {
        entity.triggerAnim("fall_controller", "fall_animation");
        Vec3 selfVec3 = entity.getDeltaMovement().scale(0.2D);
        LivingEntity target = entity.getTarget();
        if (target != null) {
            double x = target.getX() - entity.getX();
            double z = target.getZ() - entity.getZ();
            Vec3 vec3 = new Vec3(x, 0.0D, z);
            if (vec3.lengthSqr() > 1.0E-7D) {
                vec3 = vec3.normalize().scale(0.4D).add(selfVec3);
            }

            entity.setDeltaMovement(vec3.x, 0.5D, vec3.z);
        }
    }

    @Override
    public void tick(GiantCrystalSpider entity) {
        if (entity.getAttackTicks() == 12) {
            TAEntityUtils.performMeleeAttack(entity, 1.0D);
        }
    }

    @Override
    public boolean canContinue(GiantCrystalSpider entity) {
        return true;
    }

    @Override
    public void onStop(GiantCrystalSpider entity) {

    }

}