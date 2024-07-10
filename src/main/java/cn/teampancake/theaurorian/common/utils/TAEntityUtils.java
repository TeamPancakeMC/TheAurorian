package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.entities.animal.IOverworldAurorianAnimal;
import cn.teampancake.theaurorian.common.registry.TACapability;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;

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

    @SuppressWarnings("deprecation")
    public static<T extends Mob> void convertWithExtraData(EntityType<T> entityType, Mob mob) {
        if (!BuiltInRegistries.ENTITY_TYPE.getKey(mob.getType()).getNamespace().equals(AurorianMod.MOD_ID)) {
            mob.getCapability(TACapability.MISC_CAP).ifPresent(miscNBT -> miscNBT.completelyInvisible = true);
            if (convertTo(entityType, mob) instanceof IOverworldAurorianAnimal animal) {
                animal.setSpawnInOverworld(true);
            }
        }
    }

    @Nullable
    private static <T extends Mob> T convertTo(EntityType<T> entityType, Mob mob) {
        if (mob.isRemoved()) {
            return null;
        } else {
            T t = entityType.create(mob.level());
            if (t == null) {
                return null;
            } else {
                t.copyPosition(mob);
                t.setBaby(mob.isBaby());
                if (mob.hasCustomName()) {
                    t.setCustomName(mob.getCustomName());
                    t.setCustomNameVisible(mob.isCustomNameVisible());
                }

                if (mob.isPersistenceRequired()) {
                    t.setPersistenceRequired();
                }

                mob.level().addFreshEntity(t);
                if (mob.isPassenger()) {
                    Entity entity = mob.getVehicle();
                    mob.stopRiding();
                    if (entity != null) {
                        t.startRiding(entity, true);
                    }
                }

                mob.setInvisible(true);
                mob.discard();
                return t;
            }
        }
    }

}