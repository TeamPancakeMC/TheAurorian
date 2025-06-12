package cn.teampancake.theaurorian.common.entities.ai.goal;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.monster.MultiPhaseAttacker;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

/**
 * 专门用于重置皎月女王没有目标时的攻击状态
 * 确保她能正常进行随机游走
 */
public class MoonQueenResetAttackStateGoal extends Goal {

    private final MoonQueen moonQueen;
    private int cooldown;
    
    public MoonQueenResetAttackStateGoal(MoonQueen moonQueen) {
        this.moonQueen = moonQueen;
        this.setFlags(EnumSet.noneOf(Flag.class)); // 不与任何行为冲突
    }
    
    @Override
    public boolean canUse() {
        // 只有当没有目标且不处于特殊状态时才尝试重置
        return moonQueen.getTarget() == null && 
               !moonQueen.duelingMoment && 
               moonQueen.preparationTime <= 0 &&
               ((MultiPhaseAttacker)moonQueen).getAttackState() != 0;
    }
    
    @Override
    public void start() {
        this.cooldown = 20; // 等待20刻再重置
    }
    
    @Override
    public void tick() {
        if (--this.cooldown <= 0) {
            ((MultiPhaseAttacker)moonQueen).setAttackState(0);
            ((MultiPhaseAttacker)moonQueen).setAttackTicks(0);
        }
    }
    
    @Override
    public boolean canContinueToUse() {
        return this.cooldown > 0 && moonQueen.getTarget() == null;
    }
} 