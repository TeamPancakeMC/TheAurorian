package cn.teampancake.theaurorian.common.entities.ai.goal;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class MoonQueenForceStrollGoal extends Goal {

    private final MoonQueen moonQueen;
    private final double speedModifier;
    private int cooldownTicks;
    private boolean forceMove;
    private double wantedX;
    private double wantedY; 
    private double wantedZ;
    
    public MoonQueenForceStrollGoal(MoonQueen moonQueen, double speedModifier) {
        this.moonQueen = moonQueen;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.cooldownTicks = 0;
    }
    
    @Override
    public boolean canUse() {
        // 冷却时间检查
        if (this.cooldownTicks > 0) {
            --this.cooldownTicks;
            return false;
        }

        // 战斗状态检查 - 在这些状态下不应该随机移动
        if (this.moonQueen.getAttackState() != 0 ||
            this.moonQueen.duelingMoment ||
            this.moonQueen.preparationTime > 0) {
            this.cooldownTicks = 40; // 减少冷却时间，提高响应性
            return false;
        }

        // 如果有目标且距离较近，不进行随机移动
        if (this.moonQueen.getTarget() != null) {
            double distanceToTarget = this.moonQueen.distanceToSqr(this.moonQueen.getTarget());
            if (distanceToTarget < 64.0D) { // 8格内不随机移动
                this.cooldownTicks = 30;
                return false;
            }
        }

        // 如果正在导航中，等待完成
        if (this.moonQueen.getNavigation().isInProgress()) {
            this.cooldownTicks = 60; // 减少等待时间
            return false;
        }

        // 检查是否需要强制移动或随机触发
        boolean shouldMove = this.forceMove ||
                           this.moonQueen.getRandom().nextInt(80) == 0; // 提高触发频率

        if (shouldMove) {
            Vec3 vec3 = this.findPosition();
            if (vec3 != null) {
                this.wantedX = vec3.x;
                this.wantedY = vec3.y;
                this.wantedZ = vec3.z;
                this.forceMove = false;
                return true;
            } else {
                // 如果找不到合适位置，短暂冷却后重试
                this.cooldownTicks = 10;
            }
        }

        // 检查是否卡住不动
        if (this.moonQueen.getDeltaMovement().lengthSqr() < 0.0001D &&
            this.moonQueen.getTarget() == null) {
            this.forceMove = true;
            this.cooldownTicks = 15; // 快速重试
        }

        return false;
    }

    @Nullable
    private Vec3 findPosition() {
        // 首先尝试使用默认的随机位置生成器，范围更大
        Vec3 vec3 = DefaultRandomPos.getPos(this.moonQueen, 15, 10);

        if (vec3 == null) {
            // 如果默认方法失败，使用改进的圆形搜索算法
            vec3 = this.findPositionInCircle();
        }

        // 验证位置的可达性和安全性
        if (vec3 != null && this.isPositionValid(vec3)) {
            return vec3;
        }

        return null;
    }

    @Nullable
    private Vec3 findPositionInCircle() {
        // 尝试多个角度和距离组合
        for (int attempts = 0; attempts < 8; attempts++) {
            double angle = this.moonQueen.getRandom().nextDouble() * Math.PI * 2;
            double distance = 3.0 + this.moonQueen.getRandom().nextDouble() * 12.0; // 3-15格距离

            double x = this.moonQueen.getX() + Math.cos(angle) * distance;
            double z = this.moonQueen.getZ() + Math.sin(angle) * distance;

            // 寻找合适的Y坐标（支持水陆两栖）
            BlockPos targetPos = new BlockPos((int)x, (int)this.moonQueen.getY(), (int)z);
            double y = this.findSuitableY(targetPos);

            if (y != -1) {
                return new Vec3(x, y, z);
            }
        }
        return null;
    }

    private double findSuitableY(BlockPos basePos) {
        // 向上和向下搜索合适的Y坐标
        for (int yOffset = -3; yOffset <= 5; yOffset++) {
            BlockPos checkPos = basePos.offset(0, yOffset, 0);
            BlockPos belowPos = checkPos.below();
            BlockPos abovePos = checkPos.above();

            // 检查是否是合适的位置（可以是陆地或水中）
            boolean canStandHere = this.moonQueen.level().getBlockState(belowPos).isSolid() ||
                                  this.moonQueen.level().getFluidState(checkPos).isSource();
            boolean hasSpace = !this.moonQueen.level().getBlockState(checkPos).isSolid() &&
                              !this.moonQueen.level().getBlockState(abovePos).isSolid();

            if (canStandHere && hasSpace) {
                return checkPos.getY();
            }
        }
        return -1;
    }

    private boolean isPositionValid(Vec3 pos) {
        BlockPos blockPos = new BlockPos((int)pos.x, (int)pos.y, (int)pos.z);

        // 检查位置是否在世界边界内
        if (!this.moonQueen.level().getWorldBorder().isWithinBounds(blockPos)) {
            return false;
        }

        // 检查是否可以到达该位置
        return this.moonQueen.getNavigation().createPath(pos.x, pos.y, pos.z, 1) != null;
    }
    
    @Override
    public boolean canContinueToUse() {
        return !this.moonQueen.getNavigation().isDone();
    }
    
    @Override
    public void start() {
        // 使用更高的移动速度，确保能够有效移动
        double actualSpeed = this.speedModifier;

        // 如果在水中，保持正常速度（因为已有水中移动不减速特性）
        if (this.moonQueen.isInWater()) {
            actualSpeed = this.speedModifier * 1.1; // 水中稍微快一点
        }

        this.moonQueen.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, actualSpeed);

        // 确保攻击状态重置
        this.moonQueen.setAttackState(0);
        this.moonQueen.setAttackTicks(0);
    }

    @Override
    public void stop() {
        // 随机冷却时间，避免过于规律的移动
        this.cooldownTicks = this.moonQueen.getRandom().nextInt(30) + 15;
        this.moonQueen.getNavigation().stop();
    }

    private int stuckCheckTicks = 0;
    private double lastX, lastZ;

    @Override
    public void tick() {
        // 改进的卡住检测
        double currentX = this.moonQueen.getX();
        double currentZ = this.moonQueen.getZ();

        // 检查是否在移动
        double movementDistance = Math.sqrt(Math.pow(currentX - this.lastX, 2) + Math.pow(currentZ - this.lastZ, 2));

        if (movementDistance < 0.1) { // 移动距离很小
            this.stuckCheckTicks++;
            if (this.stuckCheckTicks > 40) { // 2秒没有明显移动
                this.handleStuckSituation();
            }
        } else {
            this.stuckCheckTicks = 0;
        }

        this.lastX = currentX;
        this.lastZ = currentZ;

        // 原有的导航卡住检测
        if (this.moonQueen.getNavigation().isStuck()) {
            this.handleStuckSituation();
        }
    }

    private void handleStuckSituation() {
        this.moonQueen.getNavigation().stop();
        this.forceMove = true;
        this.cooldownTicks = 5; // 快速重试
        this.stuckCheckTicks = 0;

        // 如果在水中卡住，尝试向上游
        if (this.moonQueen.isInWater()) {
            this.moonQueen.setDeltaMovement(
                this.moonQueen.getDeltaMovement().add(0, 0.1, 0)
            );
        }
    }

} 