package cn.teampancake.theaurorian.common.enchantments;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class VirtualizationEnchantment extends Enchantment {

    public VirtualizationEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, Enchantments.ARMOR_SLOTS);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    protected boolean checkCompatibility(Enchantment other) {
        return !(other instanceof ReflectAuraEnchantment) && super.checkCompatibility(other);
    }

}