package cn.teampancake.theaurorian.common.utils;

import cn.teampancake.theaurorian.common.config.AurorianConfig;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class AurorianSteelHelper {

    public static int maxLevelBase = AurorianConfig.Config_AurorianSteel_BaseMaxLevel.get();
    public static double maxLevelMultiplier = AurorianConfig.Config_AurorianSteel_BaseMaxLevelMultiplier.get();
    private static final DataComponentType<CustomData> CUSTOM_DATA = DataComponents.CUSTOM_DATA;

    /**
     * Tooltip for all Aurorian Steel tools.
     */
    @OnlyIn(Dist.CLIENT)
    public static Component getAurorianSteelTooltip() {
        return Component.translatable("string.theaurorian.tooltip.aurorian_steel_tools");
    }

    @OnlyIn(Dist.CLIENT)
    public static void getAurorianSteelInfo(ItemStack stack, List < Component > tooltip) {
        boolean canLevelUp = hasEnchantToLevelUp(stack);
        if (Screen.hasShiftDown()) {
            if (!canLevelUp) {
                tooltip.add(Component.translatable("string.theaurorian.tooltip.aurorian_steel_tools.no_enchants").withStyle(ChatFormatting.LIGHT_PURPLE));
            } else {
                int round = Math.round(AurorianSteelHelper.maxLevelBase * AurorianSteelHelper.getMultiplier(stack));
                tooltip.add(Component.translatable("string.theaurorian.tooltip.silent_wood_pickaxe1").append(" [" + AurorianSteelHelper.getLevel(stack) + "/" + round + "]"));
            }
            tooltip.add(Component.translatable("string.theaurorian.tooltip.shift_info").withStyle(ChatFormatting.ITALIC));
        } else {
            if (canLevelUp) {
                Component nextEnchant = getNextEnchantment(stack);
                if (nextEnchant != null) {
                    tooltip.add(Component.translatable("string.theaurorian.tooltip.aurorian_steel_tools.next_enchant").append(nextEnchant).withStyle(ChatFormatting.LIGHT_PURPLE));
                }
            }

            tooltip.add(getAurorianSteelTooltip());
        }
    }

    /**
     * Returns the display name of the next enchantment it will level up.
     */
    public static Component getNextEnchantment(ItemStack stack) {
        if (stack.isEnchanted()) {
            ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);
            for (Object2IntMap.Entry<Holder<Enchantment>> e : enchantments.entrySet()) {
                Enchantment enchantment = e.getKey().value();
                if (enchantment.getMaxLevel() > 1 && e.getIntValue() < enchantment.getMaxLevel()) {
                    switch (AurorianConfig.Config_AurorianSteel_Enchants_WhitelistBlacklist.get()) {
                        case 1 -> {
                            for (String enchant : AurorianConfig.Config_AurorianSteel_Enchants.get()) {
                                ResourceKey<Enchantment> key = e.getKey().getKey();
                                if (key != null && enchant.equals(key.toString())) {
                                    return Enchantment.getFullname(e.getKey(), e.getIntValue() + 1);
                                }
                            }
                        }

                        case 2 -> {
                            for (String enchant : AurorianConfig.Config_AurorianSteel_Enchants.get()) {
                                ResourceKey<Enchantment> key = e.getKey().getKey();
                                if (key != null && enchant.equals(key.toString())) {
                                    return null;
                                }
                            }

                            return Enchantment.getFullname(e.getKey(), e.getIntValue() + 1);
                        }

                        default -> {
                            return Enchantment.getFullname(e.getKey(), e.getIntValue() + 1);
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Checks the itemstack and config to see if the item has enchantments to
     * level up.
     */
    public static boolean hasEnchantToLevelUp(ItemStack stack) {
        if (stack.isEnchanted()) {
            ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);
            for (Object2IntMap.Entry<Holder<Enchantment>> e : enchantments.entrySet()) {
                Enchantment enchantment = e.getKey().value();
                if (enchantment.getMaxLevel() > 1 && e.getIntValue() < enchantment.getMaxLevel()) {
                    switch (AurorianConfig.Config_AurorianSteel_Enchants_WhitelistBlacklist.get()) {
                        case 1 -> {
                            for (String enchant : AurorianConfig.Config_AurorianSteel_Enchants.get()) {
                                ResourceKey<Enchantment> key = e.getKey().getKey();
                                if (key != null && enchant.equals(key.toString())) {
                                    return true;
                                }
                            }
                        }

                        case 2 -> {
                            for (String enchant : AurorianConfig.Config_AurorianSteel_Enchants.get()) {
                                ResourceKey<Enchantment> key = e.getKey().getKey();
                                if (key != null && enchant.equals(key.toString())) {
                                    return false;
                                }
                            }

                            return true;
                        }

                        default -> {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Called whenever Aurorian Steel tools take damage.
     */
    public static void handleAurorianSteelDurability(ItemStack stack) {
        int itemLevel = getLevel(stack);
        float levelMultiplier = getMultiplier(stack);
        if (itemLevel >= Math.round(maxLevelBase * levelMultiplier) - 1) {
            if (stack.isEnchanted()) {
                ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);
                for (Object2IntMap.Entry<Holder<Enchantment>> e : enchantments.entrySet()) {
                    Enchantment enchantment = e.getKey().value();
                    if (enchantment.getMaxLevel() > 1 && e.getIntValue() < enchantment.getMaxLevel()) {
                        switch (AurorianConfig.Config_AurorianSteel_Enchants_WhitelistBlacklist.get()) {
                            case 1 -> {
                                for (String enchant : AurorianConfig.Config_AurorianSteel_Enchants.get()) {
                                    ResourceKey<Enchantment> key = e.getKey().getKey();
                                    if (key != null && enchant.equals(key.toString())) {
                                        e.setValue(e.getIntValue() + 1);
                                        enchantments.keySet().add(e.getKey());
                                        EnchantmentHelper.setEnchantments(stack, enchantments);
                                        setMultiplier(stack, (float) (levelMultiplier * maxLevelMultiplier));
                                        setLevel(stack, 0);
                                        return;
                                    }
                                }
                            }

                            case 2 -> {
                                for (String enchant : AurorianConfig.Config_AurorianSteel_Enchants.get()) {
                                    ResourceKey<Enchantment> key = e.getKey().getKey();
                                    if (key != null && enchant.equals(key.toString())) {
                                        return;
                                    }
                                }

                                e.setValue(e.getIntValue() + 1);
                                enchantments.keySet().add(e.getKey());
                                EnchantmentHelper.setEnchantments(stack, enchantments);
                                setMultiplier(stack, (float) (levelMultiplier * maxLevelMultiplier));
                                setLevel(stack, 0);
                                return;
                            }

                            default -> {
                                e.setValue(e.getIntValue() + 1);
                                enchantments.keySet().add(e.getKey());
                                EnchantmentHelper.setEnchantments(stack, enchantments);
                                setMultiplier(stack, (float) (levelMultiplier * maxLevelMultiplier));
                                setLevel(stack, 0);
                                return;
                            }
                        }
                    }
                }
            }
        } else {
            setLevel(stack, itemLevel + 1);
        }
    }

    public static float getMultiplier(ItemStack stack) {
        checkNbt(stack);
        return getStackTag(stack).getFloat("upgrade_multiplier");
    }

    public static void setMultiplier(ItemStack stack, float amt) {
        CompoundTag nbt = checkNbt(stack);
        nbt.putFloat("upgrade_multiplier", amt);
    }

    public static int getLevel(ItemStack stack) {
        checkNbt(stack);
        return getStackTag(stack).getInt("current_upgrade_level");
    }

    public static void setLevel(ItemStack stack, int amt) {
        CompoundTag nbt = checkNbt(stack);
        nbt.putInt("current_upgrade_level", amt);
    }

    public static CompoundTag checkNbt(ItemStack stack) {
        CompoundTag nbt = new CompoundTag();
        if (stack.has(CUSTOM_DATA)) {
            nbt = getStackTag(stack);
        }

        if (!nbt.contains("current_upgrade_level")) {
            nbt.putInt("current_upgrade_level", 0);
            stack.set(CUSTOM_DATA, CustomData.of(nbt));
        }

        if (!nbt.contains("upgrade_multiplier")) {
            nbt.putFloat("upgrade_multiplier", 1F);
            stack.set(CUSTOM_DATA, CustomData.of(nbt));
        }

        return nbt;
    }

    private static CompoundTag getStackTag(ItemStack stack) {
        return stack.getOrDefault(CUSTOM_DATA, CustomData.EMPTY).copyTag();
    }

}