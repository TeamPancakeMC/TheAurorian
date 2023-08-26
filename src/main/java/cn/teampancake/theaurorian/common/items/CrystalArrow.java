package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.entities.projectile.CrystalArrowEntity;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CrystalArrow extends ArrowItem {

    public CrystalArrow(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        CrystalArrowEntity crystalArrow = new CrystalArrowEntity(shooter, level);
        Vec3 vec31 = crystalArrow.getDeltaMovement();
        Vec3 vec32 = new Vec3(vec31.x, vec31.y * 0.45F, vec31.z);
        crystalArrow.setDeltaMovement(vec32);
        crystalArrow.setKnockback(2);
        return crystalArrow;
    }

    public static class Dispense extends AbstractProjectileDispenseBehavior {

        @Override
        protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
            CrystalArrowEntity crystalArrow = new CrystalArrowEntity(position.x(), position.y(), position.z(), level);
            Vec3 vec31 = crystalArrow.getDeltaMovement();
            Vec3 vec32 = new Vec3(vec31.x, vec31.y * 0.45F, vec31.z);
            crystalArrow.setDeltaMovement(vec32);
            crystalArrow.setKnockback(2);
            return crystalArrow;
        }

    }

}