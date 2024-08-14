package cn.teampancake.theaurorian.common.entities.projectile;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EyeOfDisturbedEntity extends AbstractHurtingProjectile {

    private int time;

    public EyeOfDisturbedEntity(EntityType<? extends EyeOfDisturbedEntity> type, Level level) {
        super(type, level);
    }

    public EyeOfDisturbedEntity(Level level, LivingEntity shooter, Vec3 movement) {
        super(TAEntityTypes.EYE_OF_DISTURBED.get(), shooter, movement, level);
    }

    private void makeParticleTrail() {
        for (int i = 0; i < 5; i++) {
            double x = this.getX() + 0.5D * (this.random.nextDouble() - this.random.nextDouble());
            double y = this.getY() + 0.5D * (this.random.nextDouble() - this.random.nextDouble());
            double z = this.getZ() + 0.5D * (this.random.nextDouble() - this.random.nextDouble());
            float xs = (float)((0x374577 >> 16 & 255) / 255.0D);
            float ys = (float)((0x374577 >> 8 & 255) / 255.0D);
            float zs = (float)((0x374577 & 255) / 255.0D);
            ColorParticleOption particleOption = ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, xs, ys, zs);
            this.level().addParticle(particleOption, x, y, z, 0.0D, 0.0D, 0.0D);
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
        if (!this.level().isClientSide && this.getOwner() instanceof Monster monster) {
            if (result.getEntity() instanceof LivingEntity livingEntity && livingEntity == monster.getTarget()) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200));
                livingEntity.hurt(this.damageSources().magic(), 2.0F);
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