package cn.teampancake.theaurorian.common.entities.projectile.blade_waves;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class BladeWave extends AbstractHurtingProjectile {

    // 距离衰减与射程
    private static final float BASE_MIN_DAMAGE = 6.0F;
    private static final float BASE_MAX_DAMAGE = 16.0F;
    private static final double MAX_RANGE = 30.0D; // 最远30格

    private Vec3 startPos;

    public BladeWave(EntityType<? extends BladeWave> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.noCulling = true;
    }

    public BladeWave(LivingEntity owner, Vec3 movement, Level level) {
        super(TAEntityTypes.BLADE_WAVE.get(), owner, movement, level);
        this.startPos = owner.position();
    }

    @Override
    protected @Nullable ParticleOptions getTrailParticle() {
        return null;
    }

    @Override
    public void tick() {
        super.tick();

        // 初始化起点（若通过无参构造反序列化）
        if (this.startPos == null) {
            this.startPos = this.position();
        }

        // 计算飞行距离，超过最大射程则移除
        double traveled = this.position().distanceTo(this.startPos);
        if (traveled >= MAX_RANGE) {
            this.discard();
            return;
        }

        if (!this.level().isClientSide() && this.getOwner() instanceof Mob mob) {
            this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox(), entity ->
                entity != this && entity != this.getOwner() && entity.isAlive()
            ).forEach(entity -> {
                // 线性距离衰减伤害：min + (max-min) * (1 - traveled/MAX_RANGE)
                float t = (float) min(1.0, max(0.0, traveled / MAX_RANGE));
                float damage = BASE_MIN_DAMAGE + (BASE_MAX_DAMAGE - BASE_MIN_DAMAGE) * (1.0F - t);

                entity.hurt(this.damageSources().mobAttack(mob), damage);
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.invulnerableTime = 0; // 确保伤害能叠加生效
                }
            });
        }
    }

    @Override
    protected float getInertia() {
        return 1.0F;
    }

    @Override
    protected float getLiquidInertia() {
        return this.getInertia();
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

}