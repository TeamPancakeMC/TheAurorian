package cn.teampancake.theaurorian.common.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class LightningDamageEnchantment extends Enchantment {

	public LightningDamageEnchantment() {
		super(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
	}

	@Override
	public int getMaxLevel() {
		return 4;
	}

	@Override
	public int getMinCost(int level) {
		return 5 + (level - 1) * 15;
	}

	@Override
	public int getMaxCost(int level) {
		return this.getMinCost(level) + 20;
	}

	@Override
	protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
		return !(enchantment instanceof LightningDamageEnchantment);
	}

	public boolean canEnchant(ItemStack stack) {
		return stack.getItem() instanceof AxeItem || super.canApplyAtEnchantingTable(stack);
	}

}