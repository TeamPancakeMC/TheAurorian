package cn.teampancake.theaurorian.common.entities.projectile;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ThrownSlateBrick extends ThrowableItemProjectile {

    public ThrownSlateBrick(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    public ThrownSlateBrick(double x, double y, double z, Level level) {
        super(TAEntityTypes.THROWN_SLATE_BRICK.get(), x, y, z, level);
    }

    public ThrownSlateBrick(LivingEntity shooter, Level level) {
        super(TAEntityTypes.THROWN_SLATE_BRICK.get(), shooter, level);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        Level level = this.level();
        float pitch = 1.2F / (this.random.nextFloat() * 0.2F + 0.9F);
        this.playSound(SoundEvents.STONE_BREAK, 1.0F, pitch);
        if (!level.isClientSide) {
            level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            for (int i = 0; i < 16; ++i) {
                ItemParticleOption particleOption = new ItemParticleOption(ParticleTypes.ITEM, this.getItem());
                double xSpeed = ((double)this.random.nextFloat() - 0.5D) * 0.08D;
                double ySpeed = ((double)this.random.nextFloat() - 0.5D) * 0.08D;
                double zSpeed = ((double)this.random.nextFloat() - 0.5D) * 0.08D;
                this.level().addParticle(particleOption, this.getX(), this.getY(), this.getZ(), xSpeed, ySpeed, zSpeed);
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return TAItems.AURORIAN_SLATE_BRICK.get();
    }

}