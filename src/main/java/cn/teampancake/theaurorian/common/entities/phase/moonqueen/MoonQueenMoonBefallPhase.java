package cn.teampancake.theaurorian.common.entities.phase.moonqueen;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;
import cn.teampancake.theaurorian.common.registry.TASoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class MoonQueenMoonBefallPhase extends AttackPhase<MoonQueen> {

    private static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR =
            new SimpleExplosionDamageCalculator(Boolean.FALSE, Boolean.FALSE, (Optional.of(1.0F)), (Optional.empty()));

    public MoonQueenMoonBefallPhase() {
        super(4, 1, 26, 0);
    }

    @Override
    public boolean canStart(MoonQueen entity, boolean coolDownOver) {
        return false;
    }

    @Override
    public void onStart(MoonQueen entity) {}

    @Override
    public void tick(MoonQueen entity) {
        Level level = entity.level();
        entity.setDeltaMovement(Vec3.ZERO);
        if (!level.isClientSide && entity.getAttackTicks() == 15) {
            level.explode(entity, Explosion.getDefaultDamageSource(level, entity), EXPLOSION_DAMAGE_CALCULATOR,
                    entity.getX(), entity.getY(), entity.getZ(), (2.0F), Boolean.FALSE, Level.ExplosionInteraction.NONE,
                    Boolean.FALSE, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, TASoundEvents.EMPTY);
//            level.broadcastEntityEvent(entity, (byte) 77);
        }
    }

    @Override
    public boolean canContinue(MoonQueen entity) {
        return true;
    }

    @Override
    public void onStop(MoonQueen entity) {}

}