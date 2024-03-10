package cn.teampancake.theaurorian.common.entities.ai.goal;

import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Monster;

public class ZombieLikeAttackGoal extends MeleeAttackGoal {

    private final Monster monster;
    private int raiseArmTicks;

    public ZombieLikeAttackGoal(Monster monster) {
        super(monster, 1.0D, Boolean.FALSE);
        this.monster = monster;
    }

    public void start() {
        super.start();
        this.raiseArmTicks = 0;
    }

    public void stop() {
        super.stop();
        this.monster.setAggressive(false);
    }

    public void tick() {
        super.tick();
        ++this.raiseArmTicks;
        this.monster.setAggressive(this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2);
    }

}