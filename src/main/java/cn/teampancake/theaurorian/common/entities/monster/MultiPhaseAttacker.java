package cn.teampancake.theaurorian.common.entities.monster;

public interface MultiPhaseAttacker {
    void setAttackState(int state);
    int getAttackState();
    void setAttackTicks(int ticks);
    int getAttackTicks();
}
