package cn.teampancake.theaurorian.common.entities.projectile;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class CrystalArrowEntity extends AbstractArrow {

    public CrystalArrowEntity(EntityType<? extends CrystalArrowEntity> type, Level level) {
        super(type, level);
    }

    public CrystalArrowEntity(double x, double y, double z, Level level, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(TAEntityTypes.CRYSTAL_ARROW.get(), x, y, z, level, pickupItemStack, firedFromWeapon);
    }

    public CrystalArrowEntity(LivingEntity shooter, Level level, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(TAEntityTypes.CRYSTAL_ARROW.get(), shooter, level, pickupItemStack, firedFromWeapon);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(TAItems.CRYSTAL_ARROW.get());
    }

}