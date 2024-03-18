package cn.teampancake.theaurorian.common.entities.phase;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import net.minecraft.world.phys.Vec3;

public class MoonQueenMoonBefallPhase extends AttackPhase<MoonQueen> {

    public MoonQueenMoonBefallPhase() {
        super(3, 1, 26, 0);
    }

    @Override
    public boolean canStart(MoonQueen entity, boolean coolDownOver) {
        return false;
    }

    @Override
    public void onStart(MoonQueen entity) {}

    @Override
    public void tick(MoonQueen entity) {
        entity.setDeltaMovement(Vec3.ZERO);
        if (entity.getAttackTicks() == 14) {
            entity.pushAwaySurroundingEntities(5.0D, 2.0D);
            entity.level().broadcastEntityEvent(entity, (byte)77);
        }
    }

    @Override
    public boolean canContinue(MoonQueen entity) {
        return true;
    }

    @Override
    public void onStop(MoonQueen entity) {}

}