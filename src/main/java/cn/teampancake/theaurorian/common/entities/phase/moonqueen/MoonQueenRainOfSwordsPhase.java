package cn.teampancake.theaurorian.common.entities.phase.moonqueen;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;
import cn.teampancake.theaurorian.common.entities.projectile.MoonQueenSword;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class MoonQueenRainOfSwordsPhase extends AttackPhase<MoonQueen> {

    private int countingSwordNum;
    private final int floatTime;
    private List<Vec3> swordPosListInFixedAngle = new ArrayList<>();

    public MoonQueenRainOfSwordsPhase() {
        super(6, 2, 200, 500);
        this.floatTime = 10;
    }

    @Override
    public int getDuration(MoonQueen entity) {
        return entity.fqmPySwordNum * 3;
    }

    @Override
    public boolean canStart(MoonQueen entity, boolean coolDownOver) {
        LivingEntity target = entity.getTarget();
        boolean flag = target != null && entity.distanceToSqr(target) >= 100.0D;
        return entity.isAlive() && entity.onGround() && flag && TAEntityUtils.canReachTarget(entity, 24.0D) && coolDownOver;
    }

    @Override
    public void onStart(MoonQueen entity) {
        LivingEntity target = entity.getTarget();
        entity.setNoGravity(true);
        if (target != null) {
            target.setGlowingTag(true);
            entity.setDeltaMovement(0.0D, 1.0D, 0.0D);
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
        int maxTime = this.floatTime + this.getDuration(entity);
        entity.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());
        if (i == this.floatTime) {
            entity.setDeltaMovement(Vec3.ZERO);
            Vec3 direction = entity.getLookAngle();
            this.swordPosListInFixedAngle = getCirclePointsWhenShootInFixedAngle(entity.position(), direction, 3.0D, count);
        } else if (i > this.floatTime && i < maxTime) {
            if (!level.isClientSide() && this.countingSwordNum < count / 2) {
                int index1 = this.countingSwordNum;
                int index2 = count - 1 - this.countingSwordNum;

                MoonQueenSword sword1 = new MoonQueenSword(level, entity);
                sword1.setPos(this.swordPosListInFixedAngle.get(index1));
                sword1.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), -1.5F, 0.001F, 1.0F);
                sword1.setTimeUntilShoot(level.random.nextInt(20) + 20);
                sword1.fixedXRot = entity.getXRot();
                sword1.fixedYRot = entity.getYRot();
                sword1.setSwordType(0);
                sword1.setOwner(entity);
                level.addFreshEntity(sword1);

                MoonQueenSword sword2 = new MoonQueenSword(level, entity);
                sword2.setPos(this.swordPosListInFixedAngle.get(index2));
                sword2.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), -1.5F, 0.001F, 1.0F);
                sword2.setTimeUntilShoot(level.random.nextInt(20) + 20);
                sword2.fixedXRot = entity.getXRot();
                sword2.fixedYRot = entity.getYRot();
                sword2.setSwordType(0);
                sword2.setOwner(entity);
                level.addFreshEntity(sword2);

                this.countingSwordNum++;
            }
        } else if (i > maxTime) {
            this.countingSwordNum = 0;
            this.swordPosListInFixedAngle.clear();
            entity.setDeltaMovement(0.0D, -2.0D, 0.0D);
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
        entity.setDeltaMovement(0.0D, -2.0D, 0.0D);
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

        // 创建一个垂直向上的向量作为参考
        Vec3 up = new Vec3(0.0D, 1.0D, 0.0D);

        // 如果视线方向与垂直方向几乎平行，使用一个不同的参考向量
        if (Math.abs(direction.dot(up)) > 0.99D) {
            up = new Vec3(1.0D, 0.0D, 0.0D);
        }

        // 计算垂直于视线方向的向量
        Vec3 right = direction.cross(up).normalize();
        Vec3 forward = direction.cross(right).normalize();

        // 在垂直于视线方向的平面上创建圆形
        for (int i = 0; i < numPoints; i++) {
            double theta = 2.0D * Math.PI * i / numPoints;
            double x = radius * Math.cos(theta);
            double y = radius * Math.sin(theta);

            // 使用right和forward向量来创建环绕Boss的圆形
            Vec3 pointOnCircle = right.scale(x).add(forward.scale(y));
            points.add(entityPos.add(pointOnCircle));
        }

        return points;
    }

}