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
        if (this.cooldownTicks > 0) {
            --this.cooldownTicks;
            return false;
        }

        if (this.moonQueen.getAttackState() != 0 || this.moonQueen.duelingMoment || this.moonQueen.preparationTime > 0) {
            this.cooldownTicks = 60;
            return false;
        }

        if (this.moonQueen.getNavigation().isInProgress()) {
            this.cooldownTicks = 100;
            return false;
        }

        if (this.forceMove || this.moonQueen.getRandom().nextInt(120) == 0) {
            Vec3 vec3 = this.findPosition();
            if (vec3 != null) {
                this.wantedX = vec3.x;
                this.wantedY = vec3.y;
                this.wantedZ = vec3.z;
                this.forceMove = false;
                return true;
            }
        }

        if (this.moonQueen.getDeltaMovement().lengthSqr() < 0.0001D) {
            this.forceMove = true;
            this.cooldownTicks = 20;
        }
        
        return false;
    }

    @Nullable
    private Vec3 findPosition() {
        Vec3 vec3 = DefaultRandomPos.getPos(this.moonQueen, 10, 7);
        if (vec3 == null) {
            double angle = this.moonQueen.getRandom().nextDouble() * Math.PI * 2;
            vec3 = new Vec3(
                this.moonQueen.getX() + Math.cos(angle) * 5.0, 
                this.moonQueen.getY(),
                this.moonQueen.getZ() + Math.sin(angle) * 5.0);

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
        this.moonQueen.setAttackState(0);
        this.moonQueen.setAttackTicks(0);
    }
    
    @Override
    public void stop() {
        this.cooldownTicks = moonQueen.getRandom().nextInt(40) + 20;
    }
    
    @Override
    public void tick() {
        if (this.moonQueen.getNavigation().isStuck()) {
            this.moonQueen.getNavigation().stop();
            this.forceMove = true;
            this.cooldownTicks = 10;
        }
    }

} 