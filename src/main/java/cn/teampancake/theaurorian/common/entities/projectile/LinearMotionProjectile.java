package cn.teampancake.theaurorian.common.entities.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class LinearMotionProjectile extends ThrowableItemProjectile {

    public LinearMotionProjectile(EntityType<? extends LinearMotionProjectile> type, Level level) {
        super(type, level);
    }

    public LinearMotionProjectile(EntityType<? extends LinearMotionProjectile> type, double x, double y, double z, Level level) {
        super(type, x, y, z, level);
    }

    public LinearMotionProjectile(EntityType<? extends LinearMotionProjectile> type, LivingEntity shooter, Level level) {
        super(type, shooter, level);
    }

    @Override
    public void shootFromRotation(Entity shooter, float rotationPitch, float rotationYaw, float pitchOffset, float velocity, float inaccuracy) {
        float value = ((float) Math.PI / 180.0F);
        float f = -Mth.sin(rotationYaw * value) * Mth.cos(rotationPitch * value);
        float f1 = -Mth.sin((rotationPitch + pitchOffset) * value);
        float f2 = Mth.cos(rotationYaw * value) * Mth.cos(rotationPitch * value);
        this.shoot(f, f1, f2, velocity, inaccuracy);
        Vec3 vec3 = shooter.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, 0.0D, vec3.z));
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            for (int i = 0; i < 8; ++i) {
                double xSpeed = ((double)this.random.nextFloat() - 0.5D) * 0.08D;
                double ySpeed = ((double)this.random.nextFloat() - 0.5D) * 0.08D;
                double zSpeed = ((double)this.random.nextFloat() - 0.5D) * 0.08D;
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), xSpeed, ySpeed, zSpeed);
            }
        }
    }

}