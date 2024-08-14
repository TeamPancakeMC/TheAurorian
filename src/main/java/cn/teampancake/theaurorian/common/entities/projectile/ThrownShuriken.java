package cn.teampancake.theaurorian.common.entities.projectile;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownShuriken extends LinearMotionProjectile {

    public ThrownShuriken(EntityType<? extends ThrownShuriken> type, Level level) {
        super(type, level);
    }

    public ThrownShuriken(LivingEntity shooter, Level level) {
        super(TAEntityTypes.THROWN_SHURIKEN.get(), shooter, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!this.level().isClientSide && result.getEntity() instanceof LivingEntity entity) {
            entity.hurt(this.damageSources().thrown(this, this.getOwner()), 6.0F);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return TAItems.MOON_SHURIKEN.get();
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

}