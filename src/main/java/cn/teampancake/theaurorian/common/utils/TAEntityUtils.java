package cn.teampancake.theaurorian.common.utils;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class TAEntityUtils {
    public static boolean canReachTarget(Mob entity, double range) {
        LivingEntity target = entity.getTarget();
        if (target == null) {
            return false;
        }
        for (LivingEntity livingEntity : entity.level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, entity, entity.getBoundingBox().inflate(range))) {
            if (livingEntity.getUUID().equals(target.getUUID())) {
                return true;
            }
        }
        return false;
    }

    public static void performMeleeAttack(Mob entity, double range) {
        LivingEntity target = entity.getTarget();
        if (target != null) {
            for (LivingEntity livingEntity : entity.level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, entity, entity.getBoundingBox().inflate(range))) {
                if (livingEntity.getUUID().equals(target.getUUID())) {
                    livingEntity.invulnerableTime = 0;
                    entity.doHurtTarget(livingEntity);
                }
            }
        }
    }
}
