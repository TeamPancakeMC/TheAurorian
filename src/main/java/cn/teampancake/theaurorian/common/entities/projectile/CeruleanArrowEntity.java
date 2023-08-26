package cn.teampancake.theaurorian.common.entities.projectile;

import cn.teampancake.theaurorian.registry.ModEntityTypes;
import cn.teampancake.theaurorian.registry.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CeruleanArrowEntity extends AbstractArrow {

    public CeruleanArrowEntity(EntityType<? extends CeruleanArrowEntity> type, Level level) {
        super(type, level);
    }

    public CeruleanArrowEntity(double x, double y, double z, Level level) {
        super(ModEntityTypes.CERULEAN_ARROW.get(), x, y, z, level);
    }

    public CeruleanArrowEntity(LivingEntity shooter, Level level) {
        super(ModEntityTypes.CERULEAN_ARROW.get(), shooter, level);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.CERULEAN_ARROW.get());
    }

}