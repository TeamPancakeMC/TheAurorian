package cn.teampancake.theaurorian.common.entities.phase.moonqueen;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;
import cn.teampancake.theaurorian.common.entities.projectile.blade_waves.BladeWave;
import cn.teampancake.theaurorian.common.utils.TAEntityUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class MoonQueenRangedPhase extends AttackPhase<MoonQueen> {

    public MoonQueenRangedPhase() {
        super(3, 2, 30, 0);
    }

    @Override
    public boolean canStart(MoonQueen entity, boolean coolDownOver) {
        return entity.preparationTime <= 0 && entity.isAlive() && TAEntityUtils.canReachTarget(entity, 12.0D);
    }

    @Override
    public void onStart(MoonQueen entity) {
        entity.triggerAnim(("blade_wave_controller"), ("blade_wave_animation"));
        LivingEntity target = entity.getTarget();
        entity.setSprinting(false);
        if (target != null) {
            entity.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
            entity.setAttackYRot(entity.getYRot());
        }
    }

    @Override
    public void tick(MoonQueen entity) {
        Level level = entity.level();
        LivingEntity target = entity.getTarget();
        if (!level.isClientSide && target != null && entity.getAttackTicks() == 3) {
            BladeWave bladeWave = new BladeWave(entity, entity.getViewVector(1.0F), level);
            level.addFreshEntity(bladeWave);
        }
    }

    @Override
    public boolean canContinue(MoonQueen entity) {
        return true;
    }

    @Override
    public void onStop(MoonQueen entity) {

    }

}