package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.entities.projectile.CeruleanArrowEntity;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CeruleanArrow extends ArrowItem {

    public CeruleanArrow(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        CeruleanArrowEntity ceruleanArrow = new CeruleanArrowEntity(shooter, level);
        Vec3 vec31 = ceruleanArrow.getDeltaMovement();
        Vec3 vec32 = new Vec3(vec31.x, vec31.y * 1.5F, vec31.z);
        ceruleanArrow.setBaseDamage(ceruleanArrow.getBaseDamage() + 1.0D);
        ceruleanArrow.setDeltaMovement(vec32);
        return ceruleanArrow;
    }

    public static class Dispense extends AbstractProjectileDispenseBehavior {

        @Override
        protected Projectile getProjectile(Level level, Position position, ItemStack stack) {
            CeruleanArrowEntity ceruleanArrow = new CeruleanArrowEntity(position.x(), position.y(), position.z(), level);
            Vec3 vec31 = ceruleanArrow.getDeltaMovement();
            Vec3 vec32 = new Vec3(vec31.x, vec31.y * 1.5F, vec31.z);
            ceruleanArrow.setBaseDamage(ceruleanArrow.getBaseDamage() + 1.0D);
            ceruleanArrow.setDeltaMovement(vec32);
            return ceruleanArrow;
        }

    }

}