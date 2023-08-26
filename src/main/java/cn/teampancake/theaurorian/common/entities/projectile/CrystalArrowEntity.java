package cn.teampancake.theaurorian.common.entities.projectile;

import cn.teampancake.theaurorian.registry.ModEntityTypes;
import cn.teampancake.theaurorian.registry.ModItems;
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
        super(ModEntityTypes.CRYSTAL_ARROW.get(), x, y, z, level);
    }

    public CrystalArrowEntity(LivingEntity shooter, Level level) {
        super(ModEntityTypes.CRYSTAL_ARROW.get(), shooter, level);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.CRYSTAL_ARROW.get());
    }

}