package cn.teampancake.theaurorian.common.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class AuroraEnchantment extends Enchantment {

    public AuroraEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[] {EquipmentSlot.HEAD});
    }

    @Override
    public int getMinCost(int level) {
        return 30;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) * 2;
    }

    @Override
    protected boolean checkCompatibility(Enchantment other) {
        return !(other instanceof MoonlightEnchantment) && super.checkCompatibility(other);
    }

}