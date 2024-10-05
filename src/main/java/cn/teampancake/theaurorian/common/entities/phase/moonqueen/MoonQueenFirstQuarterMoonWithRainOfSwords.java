package cn.teampancake.theaurorian.common.entities.phase.moonqueen;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;
import cn.teampancake.theaurorian.common.entities.projectile.MoonQueenSword;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MoonQueenFirstQuarterMoonWithRainOfSwords extends AttackPhase<MoonQueen> {

    private int countingSwordNum;

    public MoonQueenFirstQuarterMoonWithRainOfSwords() {
        super(6, 2, 200, 100);
    }

    @Override
    public int getDuration(MoonQueen entity) {
        return entity.fqmPySwordNum * 5 + 10;
    }

    @Override
    public boolean canStart(MoonQueen entity, boolean coolDownOver) {
        return entity.isAlive() && entity.onGround() && TAEntityUtils.canReachTarget(entity, 10.0D);
    }

    @Override
    public void onStart(MoonQueen entity) {
        LivingEntity target = entity.getTarget();
        entity.setSprinting(false);
        entity.setNoGravity(true);
        if (target != null) {
            entity.setAttackYRot(entity.getYRot());
        }
    }

    @Override
    public void tick(MoonQueen entity) {
        LivingEntity target = entity.getTarget();
        Level level = entity.level();
        if (target != null) {
            entity.setAttackYRot(entity.getYRot());
            entity.setSprinting(false);
            entity.getNavigation().stop();
        }

        int i = entity.getAttackTicks();
        int count = entity.fqmPySwordNum;
        int floatTime = 60;
        if (i < floatTime) {
            Vec3 vec3 = entity.getDeltaMovement();
            entity.setDeltaMovement(vec3.add(0.0D, 0.01D, 0.0D));
        } else if (i < floatTime + this.getDuration(entity)) {
            entity.setDeltaMovement(Vec3.ZERO);
            Vec3 position = entity.position();
            double centerX = position.x();
            double centerY = position.y();
            double centerZ = position.z();
            double radius = 5.0D;
            double phi = 45.0D;
            double angleStep = 2.0F * Math.PI / count;
            if (this.countingSwordNum < count) {
                double theta = this.countingSwordNum * angleStep;
                double x = centerX + radius * Math.sin(phi) * Math.cos(theta);
                double y = centerY + radius * Math.sin(phi) * Math.sin(theta);
                double z = centerZ + radius * Math.sin(phi) * Math.sin(theta);
                MoonQueenSword sword = new MoonQueenSword(level, x, y, z);
                sword.triggerAnim(("spawn_controller"), ("spawn_animation"));
                level.addFreshEntity(sword);
                this.countingSwordNum++;
            }
        } else {
            this.countingSwordNum = 0;
        }
    }

    @Override
    public boolean canContinue(MoonQueen entity) {
        return true;
    }

    @Override
    public void onStop(MoonQueen entity) {
        entity.resetFallDistance();
        entity.setNoGravity(false);
        this.countingSwordNum = 0;
    }

}