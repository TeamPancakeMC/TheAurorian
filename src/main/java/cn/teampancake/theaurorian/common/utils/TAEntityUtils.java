package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.common.registry.TAEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
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

    public static boolean canTriggerEnchantmentEffect(LivingEntity entity, ResourceKey<Enchantment> key) {
        Holder<Enchantment> holder = TAEnchantments.get(entity.level(), key);
        double totalProbability = 1.0D;
        for (ItemStack stack : entity.getArmorSlots()) {
            if (!stack.isEmpty()) {
                int level = stack.getEnchantmentLevel(holder);
                double p1 = level * 0.01D;
                double p2 = 1.0D - p1;
                totalProbability *= p2;
            }
        }

        return Math.random() < 1.0D - totalProbability;
    }

}