package cn.teampancake.theaurorian.common.entities.projectile;

import cn.teampancake.theaurorian.registry.ModEntityTypes;
import cn.teampancake.theaurorian.registry.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class WebbingEntity extends ThrowableItemProjectile {

    public WebbingEntity(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    public WebbingEntity(double x, double y, double z, Level level) {
        super(ModEntityTypes.WEBBING.get(), x, y, z, level);
    }

    public WebbingEntity(LivingEntity shooter, Level level) {
        super(ModEntityTypes.WEBBING.get(), shooter, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.WEBBING.get();
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3 && this.level().isClientSide) {
            for (int i = 0; i < 8; ++i) {
                //Todo: Should add a new particle.
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!this.level().isClientSide) {
            if (result.getEntity() instanceof LivingEntity livingEntity && livingEntity != this.getOwner()) {
                livingEntity.hurt(this.damageSources().thrown(this, this.getOwner()), 0.5F);
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
            }
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

}