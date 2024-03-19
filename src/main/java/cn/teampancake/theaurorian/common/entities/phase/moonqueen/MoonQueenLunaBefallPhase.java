package cn.teampancake.theaurorian.common.entities.phase.moonqueen;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class MoonQueenLunaBefallPhase extends AttackPhase<MoonQueen> {

    public MoonQueenLunaBefallPhase() {
        super(4, 1, 205, 400, 5);
    }

    @Override
    public boolean canStart(MoonQueen entity, boolean coolDownOver) {
        return coolDownOver && !entity.duelingMoment && entity.getTarget() != null && entity.getHealth() < entity.getMaxHealth() / 2.0D;
    }

    @Override
    public void onStart(MoonQueen entity) {
        String controller = "luna_befall_controller";
        String animation = "luna_befall_animation";
        LivingEntity target = entity.getTarget();
        entity.triggerAnim(controller, animation);
        entity.setInvulnerable(true);
        entity.setNoGravity(true);
        if (target != null) {
            entity.getLookControl().setLookAt(target);
            entity.setAttackYRot(entity.getYRot());
        }
    }

    @Override
    public void tick(MoonQueen entity) {
        int ticks = entity.getAttackTicks();
        Vec3 vec3 = entity.getDeltaMovement();
        if (ticks > 30 && ticks <= 110) {
            entity.setDeltaMovement(vec3.x, 0.15F, vec3.z);
        } else if (ticks > 110 && ticks <= 180) {
            entity.setDeltaMovement(Vec3.ZERO);
        } else if (ticks > 180 && ticks <= this.getDuration()) {
            entity.setDeltaMovement(vec3.x, -0.5F, vec3.z);
        }
    }

    @Override
    public boolean canContinue(MoonQueen entity) {
        return true;
    }

    @Override
    public void onStop(MoonQueen entity) {
        entity.setNoGravity(false);
    }

}