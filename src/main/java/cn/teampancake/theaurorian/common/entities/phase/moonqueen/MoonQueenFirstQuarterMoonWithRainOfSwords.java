package cn.teampancake.theaurorian.common.entities.phase.moonqueen;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;
import cn.teampancake.theaurorian.common.entities.projectile.MoonQueenSword;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class MoonQueenFirstQuarterMoonWithRainOfSwords extends AttackPhase<MoonQueen> {

    private int countingSwordNum;
    private boolean isNormalSwordPosList;
    private List<Vec3> swordPosListInFixedAngle = new ArrayList<>();
    private List<Vec3[]> swordPosListInRandomDownVectors = new ArrayList<>();

    public MoonQueenFirstQuarterMoonWithRainOfSwords() {
        super(6, 2, 200, 100);
    }

    @Override
    public int getDuration(MoonQueen entity) {
        return entity.fqmPySwordNum * 5;
    }

    @Override
    public boolean canStart(MoonQueen entity, boolean coolDownOver) {
        return entity.isAlive() && entity.onGround() && TAEntityUtils.canReachTarget(entity, 20.0D);
    }

    @Override
    public void onStart(MoonQueen entity) {
        LivingEntity target = entity.getTarget();
        entity.setNoGravity(true);
        if (target != null) {
            this.doOnStart(entity, target);
        }
    }

    @Override
    public void tick(MoonQueen entity) {
        LivingEntity target = entity.getTarget();
        Level level = entity.level();
        if (target == null) return;
        this.doOnStart(entity, target);
        int i = entity.getAttackTicks();
        int count = entity.fqmPySwordNum;
        int floatTime = 30;
        if (i < floatTime) {
            Vec3 vec3 = entity.getDeltaMovement();
            entity.setDeltaMovement(vec3.add(0.0D, 0.04D, 0.0D));
        } else if (i == floatTime) {
            entity.setDeltaMovement(Vec3.ZERO);
            double dx = entity.getX() - target.getX();
            double dz = entity.getZ() - target.getZ();
            this.isNormalSwordPosList = dx * dx + dz * dz > 25.0D;
            if (this.isNormalSwordPosList) {
                Vec3 direction = entity.getLookAngle();
                this.swordPosListInFixedAngle = getCirclePointsWhenShootInFixedAngle(entity.position(), direction, 4.0D, count);
            } else {
                this.swordPosListInRandomDownVectors = getCirclePointsWhenShootInRandomDownVectors(entity.position(), 2.0D, 30.0D, count);
            }

        } else if (i < floatTime + this.getDuration(entity)) {
            if (!level.isClientSide() && this.countingSwordNum < count) {
                MoonQueenSword sword = new MoonQueenSword(level, entity);
                if (this.isNormalSwordPosList) {
                    sword.setPos(this.swordPosListInFixedAngle.get(this.countingSwordNum));
                    sword.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 0.01F, 1.0F);
                    sword.setTimeUntilShoot(level.random.nextInt(20) + 20);
                    sword.fixedXRot = entity.getXRot();
                    sword.fixedYRot = entity.getYRot();
                    sword.setSwordType(0);
                } else {
                    Vec3[] vec3s = this.swordPosListInRandomDownVectors.get(this.countingSwordNum);
                    sword.setTimeUntilShoot(level.random.nextInt(20) + 30);
                    sword.type1Vec3X = vec3s[1].x();
                    sword.type1Vec3Y = vec3s[1].y();
                    sword.type1Vec3Z = vec3s[1].z();
                    sword.setPos(vec3s[0]);
                    sword.setSwordType(1);
                }

                sword.setOwner(entity);
                level.addFreshEntity(sword);
                this.countingSwordNum++;
            }
        } else {
            this.countingSwordNum = 0;
            this.swordPosListInFixedAngle.clear();
            this.swordPosListInRandomDownVectors.clear();
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
        this.swordPosListInFixedAngle.clear();
        this.swordPosListInRandomDownVectors.clear();
    }

    private void doOnStart(MoonQueen entity, LivingEntity target) {
        entity.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
        entity.setAttackYRot(entity.getYRot());
        entity.setSprinting(false);
        entity.getNavigation().stop();
    }

    public static List<Vec3> getCirclePointsWhenShootInFixedAngle(Vec3 entityPos, Vec3 direction, double radius, int numPoints) {
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
            points.add(entityPos.add(pointOnCircle));
        }

        return points;
    }

    public static List<Vec3[]> getCirclePointsWhenShootInRandomDownVectors(Vec3 entityPos, double radius, double maxAngle, int numPoints) {
        List<Vec3[]> pointsWithVectors = new ArrayList<>();
        RandomSource random = RandomSource.create();
        for (int i = 0; i < numPoints; i++) {
            double theta = Mth.TWO_PI * i / numPoints;
            double xOffset = radius * Math.cos(theta);
            double zOffset = radius * Math.sin(theta);
            double x = entityPos.x + xOffset;
            double z = entityPos.z + zOffset;
            Vec3 circlePoint = new Vec3(x, entityPos.y + 2.0D, z);
            double angle = Math.toRadians(random.nextDouble() * maxAngle);
            double cosAngle = Math.cos(angle);
            double sinAngle = Math.sin(angle);
            double azimuth = Math.toRadians(random.nextDouble() * 360.0D);
            double cosAzimuth = Math.cos(azimuth);
            double sinAzimuth = Math.sin(azimuth);
            double vectorX = cosAzimuth * sinAngle;
            double vectorZ = sinAzimuth * sinAngle;
            Vec3 downVector = new Vec3(vectorX, -cosAngle, vectorZ).normalize();
            pointsWithVectors.add(new Vec3[]{circlePoint, downVector});
        }

        return pointsWithVectors;
    }

}