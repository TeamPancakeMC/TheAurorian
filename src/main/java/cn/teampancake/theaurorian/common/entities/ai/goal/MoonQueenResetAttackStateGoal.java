package cn.teampancake.theaurorian.common.entities.ai.goal;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class MoonQueenResetAttackStateGoal extends Goal {

    private final MoonQueen moonQueen;
    private int cooldown;
    
    public MoonQueenResetAttackStateGoal(MoonQueen moonQueen) {
        this.moonQueen = moonQueen;
        this.setFlags(EnumSet.noneOf(Flag.class));
    }
    
    @Override
    public boolean canUse() {
        return this.moonQueen.getTarget() == null &&
               !this.moonQueen.duelingMoment &&
                this.moonQueen.preparationTime <= 0 &&
                this.moonQueen.getAttackState() != 0;
    }
    
    @Override
    public void start() {
        this.cooldown = 20;
    }
    
    @Override
    public void tick() {
        if (--this.cooldown <= 0) {
            this.moonQueen.setAttackState(0);
            this.moonQueen.setAttackTicks(0);
        }
    }
    
    @Override
    public boolean canContinueToUse() {
        return this.cooldown > 0 && this.moonQueen.getTarget() == null;
    }

} 