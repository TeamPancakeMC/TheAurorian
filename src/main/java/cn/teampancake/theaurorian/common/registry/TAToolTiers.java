package cn.teampancake.theaurorian.common.registry;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.common.SimpleTier;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class TAToolTiers {

    public static final Tier SILENT_WOOD = new SimpleTier(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 59, 3.0F, 0.0F, 20, () -> Ingredient.of(TABlocks.SILENT_TREE_PLANKS.get()));
    public static final Tier AURORIAN_STONE = new SimpleTier(BlockTags.INCORRECT_FOR_STONE_TOOL, 131, 4.5F, 1.5F, 14, () -> Ingredient.of(TABlocks.AURORIAN_STONE.get()));
    public static final Tier MOONSTONE = new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 250, 7.0F, 2.5F, 14, () -> Ingredient.of(TABlocks.MOONSTONE_BLOCK.get()));
    public static final Tier AURORIANITE = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1000, 8.0F, 3.0F, 20, () -> Ingredient.of(TAItems.AURORIANITE_INGOT.get()));
    public static final Tier UMBRA = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1000, 8.0F, 3.0F, 20, () -> Ingredient.of(TAItems.UMBRA_INGOT.get()));
    public static final Tier CRYSTALLINE = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1000, 8.0F, 3.0F, 20, () -> Ingredient.of(TAItems.CRYSTALLINE_INGOT.get()));
    public static final Tier AURORIAN_STEEL = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1500, 8.5F, 3.5F, 10, () -> Ingredient.of(TAItems.AURORIAN_STEEL.get()));
    public static final Tier AURORIAN_ALLOY_STEEL = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2100, 9.0F, 0.0F, 15, () -> Ingredient.of(Items.NETHERITE_INGOT));
    public static final Tier TSLAT = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2000, 1.9F, 7.0F, 15, Ingredient::of);

    public static Map<Tier, Consumer<ItemStack>> getTierSpecialties() {
        Map<Tier, Consumer<ItemStack>> actionMap = new HashMap<>();
        actionMap.put(AURORIAN_STEEL, TAToolTiers::aurorianSteelSpecialty);
        return actionMap;
    }

    private static void aurorianSteelSpecialty(ItemStack stack) {
        if (getStackTag(stack).getBoolean("aurorian_steel_specialty")) {
            ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);
            Set<Object2IntMap.Entry<Holder<Enchantment>>> entrySet = enchantments.entrySet();
            Object2IntMap.Entry<Holder<Enchantment>> enchantment = entrySet.stream()
                    .filter(entry -> entry.getIntValue() < entry.getKey().value().getMaxLevel())
                    .skip((int) (enchantments.size() * Math.random())).findFirst().orElse(null);
            if (enchantment != null) {
                entrySet.remove(enchantment);
                enchantment.setValue(enchantment.getIntValue() + 1);
                entrySet.add(enchantment);
                EnchantmentHelper.setEnchantments(stack, enchantments);
            }
        }
    }

    private static CompoundTag getStackTag(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
    }

}