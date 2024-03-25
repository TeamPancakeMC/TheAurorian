package cn.teampancake.theaurorian.common.entities.projectile;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FallingFireball extends ThrowableItemProjectile {

    public float hitDamage;
    public int explosionPower = 1;

    public FallingFireball(EntityType<? extends FallingFireball> type, Level level) {
        super(type, level);
    }

    public FallingFireball(Level level, LivingEntity livingEntity) {
        super(TAEntityTypes.FALLING_FIREBALL.get(), livingEntity, level);
    }

    public FallingFireball(double x, double y, double z, Level level) {
        super(TAEntityTypes.FALLING_FIREBALL.get(), x, y, z, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            this.makeTrail(ParticleTypes.FLAME);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("HitDamage", this.hitDamage);
        compound.putByte("ExplosionPower", (byte)this.explosionPower);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.hitDamage = compound.getFloat("HitDamage");
        if (compound.contains("ExplosionPower", 99)) {
            this.explosionPower = compound.getByte("ExplosionPower");
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level().isClientSide) {
            this.makeFlameParticles();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!this.level().isClientSide) {
            Entity entity = result.getEntity();
            if (!entity.fireImmune()) {
                entity.setSecondsOnFire(5);
                entity.hurt(this.damageSources().inFire(), this.hitDamage);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        Level level = this.level();
        if (!level.isClientSide) {
            level.explode(this, this.getX(), this.getY(), this.getZ(),
                    this.explosionPower, Boolean.TRUE, Level.ExplosionInteraction.NONE);
            this.discard();
        }
    }

    private void makeFlameParticles() {
        for (int i = 0; i < 30; ++i) {
            double dx = this.getRandomX(1.0D);
            double dy = this.getRandomY() + 1.0D;
            double dz = this.getRandomZ(1.0D);
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(ParticleTypes.LAVA, dx, dy, dz, d0, d1, d2);
        }
    }

    public void makeTrail(ParticleOptions particle) {
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.level().addParticle(particle, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.FIRE_CHARGE;
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