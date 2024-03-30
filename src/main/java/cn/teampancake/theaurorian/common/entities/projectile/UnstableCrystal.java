package cn.teampancake.theaurorian.common.entities.projectile;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class UnstableCrystal extends ThrowableItemProjectile {

    public UnstableCrystal(EntityType<? extends UnstableCrystal> type, Level level) {
        super(type, level);
    }

    public UnstableCrystal(double x, double y, double z, Level level) {
        super(TAEntityTypes.UNSTABLE_CRYSTAL.get(), x, y, z, level);
    }

    public UnstableCrystal(LivingEntity shooter, Level level) {
        super(TAEntityTypes.UNSTABLE_CRYSTAL.get(), shooter, level);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        Level level = this.level();
        if (!level.isClientSide) {
            level.explode(this, this.getX(), this.getY(), this.getZ(), 3.0F, Boolean.FALSE, Level.ExplosionInteraction.MOB);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return TAItems.UNSTABLE_CRYSTAL.get();
    }

}