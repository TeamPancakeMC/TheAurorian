package cn.teampancake.theaurorian.common.entities.projectile;

import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CrystalArrowEntity extends AbstractArrow {

    public CrystalArrowEntity(EntityType<? extends CrystalArrowEntity> type, Level level) {
        super(type, level);
    }

    public CrystalArrowEntity(double x, double y, double z, Level level) {
        super(TAEntityTypes.CRYSTAL_ARROW.get(), x, y, z, level);
    }

    public CrystalArrowEntity(LivingEntity shooter, Level level) {
        super(TAEntityTypes.CRYSTAL_ARROW.get(), shooter, level);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(TAItems.CRYSTAL_ARROW.get());
    }

}