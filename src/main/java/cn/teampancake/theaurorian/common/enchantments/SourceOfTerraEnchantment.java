package cn.teampancake.theaurorian.common.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SourceOfTerraEnchantment extends Enchantment {

    public SourceOfTerraEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMinCost(int level) {
        return 40;
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) * 2;
    }

}