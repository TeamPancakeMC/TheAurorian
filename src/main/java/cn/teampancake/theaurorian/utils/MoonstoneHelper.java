package cn.teampancake.theaurorian.utils;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MoonstoneHelper {
    public static boolean handleMoonstoneDurability(ItemStack pStack, LivingEntity pAttacker) {
        Level level = pAttacker.level();
        if (level.isNight()) {
            if (AurorianUtil.randomChanceOf(0.5D)) {
                pStack.setDamageValue(pStack.getDamageValue() - 1);
                return true;
            }
        }
        pStack.hurtAndBreak(2, pAttacker, (p_43296_) -> {
            p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }
}
