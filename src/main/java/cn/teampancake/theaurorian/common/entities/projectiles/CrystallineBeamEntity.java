package cn.teampancake.theaurorian.common.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class CrystallineBeamEntity extends Arrow {
    public CrystallineBeamEntity(EntityType<? extends Arrow> pEntityType, Level level) {
        super(pEntityType, level);
    }

    public CrystallineBeamEntity(Level pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
    }

    public CrystallineBeamEntity(Level pLevel, LivingEntity pShooter) {
        super(pLevel, pShooter);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        //TODO onHit
    }
}
