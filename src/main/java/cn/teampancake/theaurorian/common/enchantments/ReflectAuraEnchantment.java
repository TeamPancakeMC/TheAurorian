package cn.teampancake.theaurorian.common.enchantments;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class ReflectAuraEnchantment extends Enchantment {

    public ReflectAuraEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR, Enchantments.ARMOR_SLOTS);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getMinCost(int level) {
        return 10 + 20 * (level - 1);
    }

    @Override
    public int getMaxCost(int level) {
        return super.getMinCost(level) + 50;
    }

    @Override
    protected boolean checkCompatibility(Enchantment other) {
        return !(other instanceof VirtualizationEnchantment) && super.checkCompatibility(other);
    }

}