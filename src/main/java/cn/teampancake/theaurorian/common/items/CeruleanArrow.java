package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.entities.projectile.CeruleanArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class CeruleanArrow extends ArrowItem {

    public CeruleanArrow(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter, @Nullable ItemStack weapon) {
        CeruleanArrowEntity ceruleanArrow = new CeruleanArrowEntity(shooter, level, stack, weapon);
        Vec3 vec31 = ceruleanArrow.getDeltaMovement();
        Vec3 vec32 = new Vec3(vec31.x, vec31.y * 1.5F, vec31.z);
        ceruleanArrow.setBaseDamage(ceruleanArrow.getBaseDamage() + 1.0D);
        ceruleanArrow.setDeltaMovement(vec32);
        return ceruleanArrow;
    }

}