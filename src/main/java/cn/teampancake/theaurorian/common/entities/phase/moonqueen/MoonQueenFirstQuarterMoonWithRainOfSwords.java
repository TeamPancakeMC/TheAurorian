package cn.teampancake.theaurorian.common.entities.phase.moonqueen;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;
import cn.teampancake.theaurorian.common.entities.projectile.MoonQueenSword;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class MoonQueenFirstQuarterMoonWithRainOfSwords extends AttackPhase<MoonQueen> {

    private int countingSwordNum;
    private List<Vec3> swordPosList = new ArrayList<>();

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
        Vec3 direction = entity.getLookAngle();
        if (i < floatTime) {
            Vec3 vec3 = entity.getDeltaMovement();
            entity.setDeltaMovement(vec3.add(0.0D, 0.01D, 0.0D));
        } else if (i == floatTime) {
            entity.setDeltaMovement(Vec3.ZERO);
            this.swordPosList = getCirclePoints(entity.position(), direction, 5.0D, count);
        } else if (i < floatTime + this.getDuration(entity)) {
            if (!level.isClientSide() && this.countingSwordNum < count) {
                MoonQueenSword sword = new MoonQueenSword(TAEntityTypes.MOON_QUEEN_SWORD.get(), level);
                sword.triggerAnim("spawn_controller", "spawn_animation");
                sword.setPos(this.swordPosList.get(this.countingSwordNum));
                sword.setModelXRot((float) direction.x());
                sword.setModelYRot((float) direction.y());
                sword.setModelZRot((float) direction.z());
                level.addFreshEntity(sword);
                this.countingSwordNum++;
            }
        } else {
            this.countingSwordNum = 0;
            this.swordPosList.clear();
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
        this.swordPosList.clear();
    }

    public static List<Vec3> getCirclePoints(Vec3 playerPos, Vec3 direction, double radius, int numPoints) {
        List<Vec3> points = new ArrayList<>();
        direction = direction.normalize();
        Vec3 up = new Vec3(0.0D, 1.0D, 0.0D);
        if (Math.abs(direction.dot(up)) > 0.99D) {
            up = new Vec3(1.0D, 0.0D, 0.0D);
        }

        Vec3 u = up.cross(direction).normalize();
        Vec3 v = direction.cross(u).normalize();
        for (int i = 0; i < numPoints; i++) {
            double theta = 2.0D * Math.PI * i / numPoints;
            double x = radius * Math.cos(theta);
            double y = radius * Math.sin(theta);
            Vec3 pointOnCircle = u.scale(x).add(v.scale(y));
            points.add(playerPos.add(pointOnCircle));
        }

        return points;
    }

}