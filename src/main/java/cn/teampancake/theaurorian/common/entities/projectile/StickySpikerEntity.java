package cn.teampancake.theaurorian.common.entities.projectile;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TAParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class StickySpikerEntity extends ThrowableItemProjectile {

    public StickySpikerEntity(EntityType<? extends StickySpikerEntity> type, Level level) {
        super(type, level);
    }

    public StickySpikerEntity(double x, double y, double z, Level level) {
        super(TAEntityTypes.STICKY_SPIKER.get(), x, y, z, level);
    }

    public StickySpikerEntity(LivingEntity shooter, Level level) {
        super(TAEntityTypes.STICKY_SPIKER.get(), shooter, level);
    }

    @Override
    protected Item getDefaultItem() {
        return TAItems.STICKY_SPIKER.get();
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3 && this.level().isClientSide) {
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(TAParticleTypes.STICK_SPIKER.get(),this.getX(),this.getY(),this.getZ(),0.0D,0.0D,0.0D);

            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!this.level().isClientSide) {
            if (result.getEntity() instanceof LivingEntity livingEntity && livingEntity != this.getOwner() && livingEntity.isAlive())  {
                livingEntity.hurt(this.damageSources().thrown(this, this.getOwner()), 0.5F);
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 200));
            }
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

}