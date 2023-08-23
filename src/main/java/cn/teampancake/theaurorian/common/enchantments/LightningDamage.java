package cn.teampancake.theaurorian.common.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class LightningDamage extends Enchantment {

	public static final String NAME = "lightning";
	private static final int LEVEL_COST_MIN = 5;
	private static final int LEVEL_COST = 15;
	private static final int LEVEL_MAX = 20;
	private static final int TIERS_MAX = 4;

	public LightningDamage() {
		super(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
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
		return !(enchantment instanceof LightningDamage);
	}

	public boolean canEnchant(ItemStack stack) {
		return stack.getItem() instanceof AxeItem || super.canApplyAtEnchantingTable(stack);
	}
}