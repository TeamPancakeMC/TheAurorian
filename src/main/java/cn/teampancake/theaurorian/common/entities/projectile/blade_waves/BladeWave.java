package cn.teampancake.theaurorian.common.entities.projectile.blade_waves;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
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
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        Level level = this.level();
        if (!level.isClientSide()) {
            EntityDimensions dimensions = this.getType().getDimensions();
            int x = Mth.ceil(dimensions.width() / 2.0F) * 0;
            int y = Mth.ceil(dimensions.height() / 2.0F) * 0;
            BlockPos.withinManhattan(this.blockPosition(), x, y, x).forEach(
                    tempPos -> level.destroyBlock(tempPos, Boolean.FALSE));
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (this.getOwner() instanceof Mob mob) {
            Entity entity = result.getEntity();
            double value = mob.getAttributeValue(Attributes.ATTACK_DAMAGE);
            entity.hurt(this.damageSources().mobAttack(mob), (float) value);
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.invulnerableTime = 0;
            }
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