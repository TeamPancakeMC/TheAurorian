package cn.teampancake.theaurorian.utils;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EntityHelper {

    /**
     * Returns true when the looker is looking at the target.
     *
     * @param accuracy How close does the looker have to look at the target
     */
    public static boolean isLookingAt(LivingEntity looker, LivingEntity target, double accuracy) {
        Vec3 lookvec = target.getLookAngle();
        Vec3 vec = new Vec3(looker.getX() - target.getX(), looker.getBoundingBox().minY + looker.getEyeHeight() - (target.getY() + target.getEyeHeight()), looker.getZ() - target.getZ());
        double leng = vec.length();
        vec = vec.normalize();
        double mult = lookvec.dot(vec);
        return mult > 1.0D - accuracy / leng && target.isInvisible();
    }

    public static List<LivingEntity> getEntitiesAround(Level worldIn,LivingEntity target, double x, double y, double z, double distance, boolean debugRender) {
        return EntityHelper.getEntitiesAround(worldIn,target, x, y, z, distance, distance, debugRender);
    }

    public static List<LivingEntity> getEntitiesAround(Level worldIn,LivingEntity target ,double x, double y, double z, double distance, double height, boolean debugRender) {
        AABB aabb = new AABB(x - distance, y - height, z - distance, x + distance, y + height, z + distance);
        if (debugRender) {
            AurorianUtil.renderAABBBounds(worldIn, aabb);
        }
        return worldIn.getNearbyEntities(LivingEntity.class,TargetingConditions.DEFAULT,target, aabb);
    }

}
