package cn.teampancake.theaurorian.common.enchantments;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;

public class LightningResistance extends Enchantment {

	public LightningResistance() {
		super(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR, Enchantments.ARMOR_SLOTS);
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return 10 + (enchantmentLevel - 1) * 15;
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return this.getMinCost(enchantmentLevel) + 30;
	}

	@Override
	protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
		return !(enchantment instanceof LightningResistance);
	}

	public boolean canEnchant(ItemStack stack) {
		return stack.getItem() instanceof ArmorItem || super.canApplyAtEnchantingTable(stack);
	}

}