package cn.teampancake.theaurorian.common.utils;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

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

}