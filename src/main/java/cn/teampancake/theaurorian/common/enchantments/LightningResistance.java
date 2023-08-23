package cn.teampancake.theaurorian.common.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class LightningResistance extends Enchantment {

	public static final String NAME = "lightning_resistance";
	private static final int LEVEL_COST_MIN = 10;
	private static final int LEVEL_COST = 15;
	private static final int LEVEL_MAX = 30;
	private static final int TIERS_MAX = 1;

	public LightningResistance() {
		super(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
	}

	@Override
	public int getMaxLevel() {
		return TIERS_MAX;
	}

	@Override
	public int getMinCost(int enchantmentLevel) {
		return LEVEL_COST_MIN + (enchantmentLevel - 1) * LEVEL_COST;
	}

	@Override
	public int getMaxCost(int enchantmentLevel) {
		return this.getMinCost(enchantmentLevel) + LEVEL_MAX;
	}

	@Override
	protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
		return !(enchantment instanceof LightningResistance);
	}

	public boolean canEnchant(ItemStack stack) {
		return stack.getItem() instanceof ArmorItem || super.canApplyAtEnchantingTable(stack);
	}
}