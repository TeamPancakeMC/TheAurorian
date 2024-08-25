package cn.teampancake.theaurorian.common.entities.projectile.blade_waves;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;

public class BladeWave extends AbstractHurtingProjectile {

    private final BladeWavePart[] subEntities;
    private final BladeWavePart part1;
    private final BladeWavePart part2;

    public BladeWave(EntityType<? extends BladeWave> type, Level level) {
        super(type, level);
        this.part1 = new BladeWavePart(this, "part1", 0.5F, 0.5F);
        this.part2 = new BladeWavePart(this, "part2", 0.5F, 0.5F);
        this.subEntities = new BladeWavePart[] {this.part1, this.part2};
        this.setId(ENTITY_COUNTER.getAndAdd(this.subEntities.length + 1) + 1);
        this.tickPart(this.part1, 1.0D, 0.0D, 0.0D);
        this.tickPart(this.part1, -1.0D, 0.0D, 0.0D);
        this.noPhysics = true;
        this.noCulling = true;
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.subEntities.length; i++) {
            this.subEntities[i].setId(id + i + 1);
        }
    }

    @Override
    public @Nullable PartEntity<?>[] getParts() {
        return this.subEntities;
    }

    @Override
    protected @Nullable ParticleOptions getTrailParticle() {
        return null;
    }

    @Override
    protected float getInertia() {
        return 1.0F;
    }

    @Override
    protected float getLiquidInertia() {
        return this.getInertia();
    }

    private void tickPart(BladeWavePart part, double offsetX, double offsetY, double offsetZ) {
        part.setPos(this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ);
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