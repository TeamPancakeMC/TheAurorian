package cn.teampancake.theaurorian.common.entities.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class SpiritMoveControl extends MoveControl {

    public SpiritMoveControl(Mob mob) {
        super(mob);
    }

    @Override
    public void tick() {
        if (this.operation == Operation.MOVE_TO) {
            Vec3 vec3 = new Vec3(this.wantedX - mob.getX(), this.wantedY - mob.getY(), this.wantedZ - mob.getZ());
            double d0 = vec3.length();
            if (d0 < this.mob.getBoundingBox().getSize()) {
                this.operation = Operation.WAIT;
                mob.setDeltaMovement(mob.getDeltaMovement().scale(0.5D));
            } else {
                mob.setDeltaMovement(mob.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05D / d0)));
                if (mob.getTarget() == null) {
                    Vec3 vec31 = mob.getDeltaMovement();
                    mob.setYRot(-((float) Mth.atan2(vec31.x, vec31.z)) * (180F / (float)Math.PI));
                } else {
                    double d2 = mob.getTarget().getX() - mob.getX();
                    double d1 = mob.getTarget().getZ() - mob.getZ();
                    mob.setYRot(-((float)Mth.atan2(d2, d1)) * (180F / (float)Math.PI));
                }
                mob.yBodyRot = mob.getYRot();
            }
        }
    }

}