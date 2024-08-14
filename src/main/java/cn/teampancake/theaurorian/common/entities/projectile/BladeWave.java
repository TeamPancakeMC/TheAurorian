package cn.teampancake.theaurorian.common.entities.projectile;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BladeWave extends AbstractHurtingProjectile {

    public BladeWave(EntityType<? extends BladeWave> type, Level level) {
        super(type, level);
    }

    public BladeWave(EntityType<? extends BladeWave> type, LivingEntity shooter, Vec3 movement, Level level) {
        super(type, shooter, movement, level);
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