package cn.teampancake.theaurorian.common.entities.phase.moonqueen;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.entities.phase.AttackPhase;
import cn.teampancake.theaurorian.common.registry.TAParticleTypes;
import cn.teampancake.theaurorian.common.registry.TASoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class MoonQueenMoonBefallPhase extends AttackPhase<MoonQueen> {

    private static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new SimpleExplosionDamageCalculator(
            Boolean.FALSE, Boolean.FALSE, Optional.of(1.0F), Optional.empty());

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
        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            int tick = entity.getAttackTicks();
            if (tick < 15) {
                this.createGatheringEffect(entity, serverLevel, tick);
            } else if (tick == 15) {
                level.explode(entity, Explosion.getDefaultDamageSource(level, entity), EXPLOSION_DAMAGE_CALCULATOR,
                        entity.getX(), entity.getY(), entity.getZ(), 2.0F, Boolean.FALSE, Level.ExplosionInteraction.NONE,
                        Boolean.FALSE, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, TASoundEvents.EMPTY);
            } else {
                this.createBurstEffect(entity, serverLevel, tick - 15);
            }
        }
    }

    @Override
    public boolean canContinue(MoonQueen entity) {
        return true;
    }

    @Override
    public void onStop(MoonQueen entity) {}

    private void createGatheringEffect(LivingEntity entity, ServerLevel serverLevel, int tick) {
        double centerX = entity.getX();
        double centerY = entity.getY() + 1.0D;
        double centerZ = entity.getZ();
        // 创建能量聚集效果
        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(i * 45 + tick * 10);
            double distance = 2.0D * (1.0D - tick / 15.0D);
            double x = Math.cos(angle) * distance;
            double z = Math.sin(angle) * distance;
            serverLevel.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(),
                    centerX + x, centerY, centerZ + z,
                    2, 0, 0.1D, 0, 0.05D);
        }

        // 创建能量漩涡效果
        for (int i = 0; i < 360; i += 10) {
            double angle = Math.toRadians(i + tick * 5);
            double radius = (1.0D - tick / 15.0D);
            double x = Math.cos(angle) * radius;
            double z = Math.sin(angle) * radius;
            serverLevel.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(),
                    centerX + x, centerY, centerZ + z,
                    1, 0, 0.05D, 0, 0.02D);
        }
    }

    private void createBurstEffect(LivingEntity entity, ServerLevel serverLevel, int progress) {
        double centerX = entity.getX();
        double centerY = entity.getY() + 1.0D;
        double centerZ = entity.getZ();
        double expansion = progress / 11.0D;
        // 创建多层冲击波
        for (int layer = 0; layer < 3; layer++) {
            double layerExpansion = expansion * (1.0D + layer * 0.5D);
            // 为每一层创建不同大小和速度的粒子环
            for (int i = 0; i < 360; i += 5) {
                double angle = Math.toRadians(i);
                double radius = 0.5D + layerExpansion * 2.0D;
                double speed = 0.2D + layer * 0.1D;
                double x = Math.cos(angle) * radius;
                double z = Math.sin(angle) * radius;
                // 创建向外扩散的粒子
                serverLevel.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(),
                        centerX + x, centerY, centerZ + z,
                        1, x * speed, 0.1D, z * speed, 0.05D);
            }
        }

        // 创建垂直能量柱
        if (progress < 5) {
            for (int i = 0; i < 20; i++) {
                double offsetX = (entity.getRandom().nextDouble() - 0.5D) * 2.0D;
                double offsetZ = (entity.getRandom().nextDouble() - 0.5D) * 2.0D;
                serverLevel.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(),
                        centerX + offsetX, centerY, centerZ + offsetZ,
                        1, 0, 0.2D, 0, 0.05D);
            }
        }

        // 创建能量波纹
        if (progress < 8) {
            for (int i = 0; i < 8; i++) {
                double angle = Math.toRadians(i * 45);
                double distance = 2.0D + expansion * 3.0D;
                double x = Math.cos(angle) * distance;
                double z = Math.sin(angle) * distance;
                serverLevel.sendParticles(TAParticleTypes.MAGIC_PURPLE.get(),
                        centerX + x, centerY, centerZ + z,
                        3, 0, 0.1D, 0, 0.1D);
            }
        }
    }

}