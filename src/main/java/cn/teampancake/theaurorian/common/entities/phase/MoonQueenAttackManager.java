package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.monster.MultiPhaseAttacker;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 专门为皎月女王设计的攻击管理器
 * 与默认攻击管理器的区别在于，这个管理器只会在有目标时尝试选择攻击阶段
 */
public class MoonQueenAttackManager extends AttackManager<MoonQueen> {

    public MoonQueenAttackManager(MoonQueen entity, List<AttackPhase<MoonQueen>> phaseList) {
        super(entity, phaseList);
    }
    
    @Override
    public void tick() {
        // 只有在皎月女王有目标时才执行攻击逻辑
        if (this.getEntity().getTarget() != null) {
            // 委托给父类方法执行攻击逻辑
            super.tick();
        } else if (this.getEntity().getAttackState() != 0) {
            // 如果没有目标且当前在攻击状态，则重置攻击状态
            this.getEntity().setAttackState(0);
            this.getEntity().setAttackTicks(0);
        }
    }
} 