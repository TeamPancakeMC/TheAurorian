package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAAttachmentTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;

public class TAEntityUtils {

    public static boolean canReachTarget(Mob entity, double range) {
        LivingEntity target = entity.getTarget();
        AABB aabb = entity.getBoundingBox().inflate(range);
        if (target == null) {
            return false;
        }

        for (LivingEntity livingEntity : entity.level().getNearbyEntities(
                LivingEntity.class, TargetingConditions.DEFAULT, entity, aabb)) {
            if (livingEntity.getUUID().equals(target.getUUID())) {
                return true;
            }
        }

        return false;
    }

    public static void performMeleeAttack(Mob entity, double range) {
        LivingEntity target = entity.getTarget();
        if (target != null) {
            AABB aabb = entity.getBoundingBox().inflate(range);
            for (LivingEntity livingEntity : entity.level().getNearbyEntities(
                    LivingEntity.class, TargetingConditions.DEFAULT, entity, aabb)) {
                if (livingEntity.getUUID().equals(target.getUUID())) {
                    livingEntity.invulnerableTime = 0;
                    entity.doHurtTarget(livingEntity);
                }
            }
        }
    }

    public static<T extends Mob> void convertWithExtraData(EntityType<T> entityType, Mob mob) {
        if (!BuiltInRegistries.ENTITY_TYPE.getKey(mob.getType()).getNamespace().equals(TheAurorian.MOD_ID)) {
            mob.setData(TAAttachmentTypes.COMPLETELY_INVISIBLE, true);
            mob.setData(TAAttachmentTypes.SPAWN_IN_OVERWORLD, true);
            mob.convertTo(entityType, Boolean.FALSE);
        }
    }

}