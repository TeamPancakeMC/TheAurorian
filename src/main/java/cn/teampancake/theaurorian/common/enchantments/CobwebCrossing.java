package cn.teampancake.theaurorian.common.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class CobwebCrossing extends Enchantment {

    public CobwebCrossing() {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[] {EquipmentSlot.FEET});
    }

    public int getMinCost(int enchantmentLevel) {
        return 20;
    }

    public int getMaxCost(int enchantmentLevel) {
        return 50;
    }

    public int getMaxLevel() {
        return 1;
    }

}