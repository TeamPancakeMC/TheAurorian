package cn.teampancake.theaurorian.common.entities.projectile;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;

public class BladeWave extends AbstractHurtingProjectile {

    public BladeWave(EntityType<? extends BladeWave> type, Level level) {
        super(type, level);
    }

    public BladeWave(EntityType<? extends BladeWave> type, LivingEntity shooter, double offsetX, double offsetY, double offsetZ, Level level) {
        super(type, shooter, offsetX, offsetY, offsetZ, level);
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