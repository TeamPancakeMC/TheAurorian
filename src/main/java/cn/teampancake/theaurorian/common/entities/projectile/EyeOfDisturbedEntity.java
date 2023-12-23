package cn.teampancake.theaurorian.common.entities.projectile;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class EyeOfDisturbedEntity extends AbstractHurtingProjectile {

    private int time;

    public EyeOfDisturbedEntity(EntityType<? extends EyeOfDisturbedEntity> type, Level level) {
        super(type, level);
    }

    public EyeOfDisturbedEntity(Level level, LivingEntity shooter, double offsetX, double offsetY, double offsetZ) {
        super(TAEntityTypes.EYE_OF_DISTURBED.get(), shooter, offsetX, offsetY, offsetZ, level);
    }

    private void makeParticleTrail() {
        for (int i = 0; i < 5; i++) {
            double x = this.getX() + 0.5D * (this.random.nextDouble() - this.random.nextDouble());
            double y = this.getY() + 0.5D * (this.random.nextDouble() - this.random.nextDouble());
            double z = this.getZ() + 0.5D * (this.random.nextDouble() - this.random.nextDouble());
            double xs = (double)(0x374577 >> 16 & 255) / 255.0D;
            double ys = (double)(0x374577 >> 8 & 255) / 255.0D;
            double zs = (double)(0x374577 & 255) / 255.0D;
            this.level().addParticle(ParticleTypes.ENTITY_EFFECT, x, y, z, xs, ys, zs);
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.makeParticleTrail();
        if (++this.time > 200) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!this.level().isClientSide) {
            Entity entity = result.getEntity();
            if (this.getOwner() instanceof Monster monster && entity == monster.getTarget()) {
                entity.hurt(this.damageSources().magic(), 2.0F);
            }
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.level().levelEvent(2007, this.blockPosition(), 0x2b4e95);
            this.discard();
        }
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