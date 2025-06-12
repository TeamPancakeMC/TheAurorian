package cn.teampancake.theaurorian.common.entities.ai.goal;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.monster.MultiPhaseAttacker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * 专门为皎月女王设计的强制随机游走目标
 * 不受其他状态影响，定期执行，确保皎月女王能够持续游荡
 */
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
        // 每隔一段时间强制检查游走
        if (this.cooldownTicks > 0) {
            --this.cooldownTicks;
            return false;
        }
        
        // 不在攻击状态、决斗模式、准备状态时才能游走
        if (((MultiPhaseAttacker)moonQueen).getAttackState() != 0 || moonQueen.duelingMoment || moonQueen.preparationTime > 0) {
            this.cooldownTicks = 60; // 如果不能游走，冷却一小段时间
            return false;
        }
        
        // 如果在官方的随机游走行为执行后一段时间内，不再强制游走
        if (moonQueen.getNavigation().isInProgress()) {
            this.cooldownTicks = 100;
            return false;
        }
        
        // 强制定期游走
        if (this.forceMove || moonQueen.getRandom().nextInt(120) == 0) {
            Vec3 vec3 = this.findPosition();
            if (vec3 != null) {
                this.wantedX = vec3.x;
                this.wantedY = vec3.y;
                this.wantedZ = vec3.z;
                this.forceMove = false;
                return true;
            }
        }
        
        // 如果已经在原地不动一段时间，强制下次游走
        if (moonQueen.getDeltaMovement().lengthSqr() < 0.0001D) {
            this.forceMove = true;
            this.cooldownTicks = 20;
        }
        
        return false;
    }
    
    private Vec3 findPosition() {
        Vec3 vec3 = DefaultRandomPos.getPos(this.moonQueen, 10, 7);
        
        // 如果找不到随机位置，至少尝试移动一小段距离
        if (vec3 == null) {
            double angle = this.moonQueen.getRandom().nextDouble() * Math.PI * 2;
            vec3 = new Vec3(
                this.moonQueen.getX() + Math.cos(angle) * 5.0, 
                this.moonQueen.getY(),
                this.moonQueen.getZ() + Math.sin(angle) * 5.0
            );
            
            // 确保位置有效且在地面上
            BlockPos blockPos = new BlockPos((int)vec3.x, (int)vec3.y, (int)vec3.z);
            if (!this.moonQueen.level().getBlockState(blockPos.below()).isSolid()) {
                return null;
            }
        }
        
        return vec3;
    }
    
    @Override
    public boolean canContinueToUse() {
        return !this.moonQueen.getNavigation().isDone();
    }
    
    @Override
    public void start() {
        this.moonQueen.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
        // 确保攻击状态被重置
        ((MultiPhaseAttacker)this.moonQueen).setAttackState(0);
        ((MultiPhaseAttacker)this.moonQueen).setAttackTicks(0);
    }
    
    @Override
    public void stop() {
        this.cooldownTicks = moonQueen.getRandom().nextInt(40) + 20;
    }
    
    @Override
    public void tick() {
        // 如果导航系统卡住了，强制重新寻路
        if (this.moonQueen.getNavigation().isStuck()) {
            this.moonQueen.getNavigation().stop();
            this.forceMove = true;
            this.cooldownTicks = 10;
        }
    }
} 