package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.monster.DisturbedHollow;
import cn.teampancake.theaurorian.common.entities.projectile.EyeOfDisturbedEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DisturbedHollowShootPhase extends AttackPhase<DisturbedHollow> {

    public DisturbedHollowShootPhase() {
        super(1, 1, 60, 10);
    }

    @Override
    public boolean canStart(DisturbedHollow entity, boolean coolDownOver) {
        LivingEntity target = entity.getTarget();
        return coolDownOver && target != null && entity.distanceToSqr(target) > this.getAttackReachSqr(entity, target);
    }

    @Override
    public void onStart(DisturbedHollow entity) {
        entity.triggerAnim("shoot_controller", "shoot_animation");
        LivingEntity target = entity.getTarget();
        if (target != null) {
            entity.getLookControl().setLookAt(target);
            entity.setDeltaMovement(Vec3.ZERO);
        }
    }

    @Override
    public void tick(DisturbedHollow entity) {
        LivingEntity target = entity.getTarget();
        if (entity.getAttackTicks() == 20 && target != null) {
            Level level = entity.level();
            if (target.distanceToSqr(entity) < 4096.0F && entity.hasLineOfSight(target)) {
                Vec3 vec3 = entity.getViewVector(1.0F);
                double ox = target.getX() - (entity.getX() + vec3.x * 4.0D);
                double oy = target.getY(0.5D) - (0.5D + entity.getY(0.5D));
                double oz = target.getZ() - (entity.getZ() + vec3.z * 4.0D);
                EyeOfDisturbedEntity eyeOfDisturbed = new EyeOfDisturbedEntity(level, entity, ox, oy, oz);
                level.levelEvent(1002, entity.blockPosition(), 0);
                double x = entity.getX() + vec3.x * 5.0D;
                double y = entity.getY(0.5D) + 0.5D;
                double z = eyeOfDisturbed.getZ() + vec3.z * 5.0D;
                eyeOfDisturbed.setOwner(entity);
                eyeOfDisturbed.setPos(x, y, z);
                level.addFreshEntity(eyeOfDisturbed);
            }
        }
    }

    @Override
    public boolean canContinue(DisturbedHollow entity) {
        return true;
    }

    @Override
    public void onStop(DisturbedHollow entity) {

    }

    private double getAttackReachSqr(DisturbedHollow entity, LivingEntity attackTarget) {
        return entity.getBbWidth() * 2.0F * entity.getBbWidth() * 2.0F + attackTarget.getBbWidth();
    }

}