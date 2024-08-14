package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.entities.projectile.CrystalArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class CrystalArrow extends ArrowItem {

    public CrystalArrow(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter, @Nullable ItemStack weapon) {
        CrystalArrowEntity crystalArrow = new CrystalArrowEntity(shooter, level, stack, weapon);
        Vec3 vec31 = crystalArrow.getDeltaMovement();
        Vec3 vec32 = new Vec3(vec31.x, vec31.y * 0.45F, vec31.z);
        crystalArrow.setBaseDamage(crystalArrow.getBaseDamage() + 2.0D);
        crystalArrow.setDeltaMovement(vec32);
//        crystalArrow.setKnockback(2);
        return crystalArrow;
    }

}