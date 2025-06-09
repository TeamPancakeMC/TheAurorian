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

public class BladeWave extends AbstractHurtingProjectile {
    
    public BladeWave(EntityType<? extends BladeWave> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.noCulling = true;
    }

    public BladeWave(LivingEntity owner, Vec3 movement, Level level) {
        super(TAEntityTypes.BLADE_WAVE.get(), owner, movement, level);
    }

    @Override
    protected @Nullable ParticleOptions getTrailParticle() {
        return null;
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(new Vec3(vec3.x, 0.0D, vec3.z));
        if (!this.level().isClientSide() && this.getOwner() instanceof Mob mob) {
            this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox(), entity -> 
                entity != this && entity != this.getOwner() && entity.isAlive()
            ).forEach(entity -> {
                double value = mob.getAttributeValue(Attributes.ATTACK_DAMAGE);
                entity.hurt(this.damageSources().mobAttack(mob), (float) value);
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.invulnerableTime = 0;
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