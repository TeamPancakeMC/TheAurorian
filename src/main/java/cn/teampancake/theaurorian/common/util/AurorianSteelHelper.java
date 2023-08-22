package cn.teampancake.theaurorian.common.util;

import cn.teampancake.theaurorian.config.AurorianConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Map;

public class AurorianSteelHelper {

    public static int maxlevelbase = AurorianConfig.Config_AurorianSteel_BaseMaxLevel.get();
    public static double maxlevelmultiplier = AurorianConfig.Config_AurorianSteel_BaseMaxLevelMultiplier.get();

    /**
     * Tooltip for all Aurorian Steel tools.
     */
    @OnlyIn(Dist.CLIENT)
    public static Component getAurorianSteelTooltip() {
        return Component.translatable("string.theaurorian.tooltip.auroriansteeltools");
    }

    @OnlyIn(Dist.CLIENT)
    public static void getAurorianSteelInfo(ItemStack stack, Level worldIn, List<Component> tooltip) {
        boolean canlevelup = hasEnchantToLevelUp(stack);
        if (Screen.hasShiftDown()) {
        if (!canlevelup) {
            tooltip.add(Component.translatable("string.theaurorian.tooltip.auroriansteeltools.noenchants").withStyle(ChatFormatting.LIGHT_PURPLE));
        } else {
            tooltip.add(Component.translatable("string.theaurorian.tooltip.silentwoodpickaxe1").append(" ["+ AurorianSteelHelper.getLevel(stack) + "/" + Math.round(AurorianSteelHelper.maxlevelbase * AurorianSteelHelper.getMultiplier(stack)) + "]" ) );
        }
            tooltip.add(Component.translatable("string.theaurorian.tooltip.shiftinfo").withStyle(ChatFormatting.ITALIC));
        } else {
            if (canlevelup) {
            Component nextenchant = getNextEnchantment(stack, worldIn);
            if (nextenchant != null) {
            tooltip.add(Component.translatable("string.theaurorian.tooltip.auroriansteeltools.nextenchant").append(nextenchant).withStyle(ChatFormatting.LIGHT_PURPLE) );
            }
            }
         tooltip.add(getAurorianSteelTooltip());
        }
    }

    /**
     * Returns the display name of the next enchantment it will level up.
     */
    public static Component getNextEnchantment(ItemStack stack, Level worldIn) {
        if (stack.isEnchanted()) {
            Map<Enchantment, Integer> enchs = EnchantmentHelper.getEnchantments(stack);
            for (Map.Entry<Enchantment, Integer> e : enchs.entrySet()) {
                if (e.getKey().getMaxLevel() > 1 && e.getValue() < e.getKey().getMaxLevel()) {
                    switch (AurorianConfig.Config_AurorianSteel_Enchants_WhitelistBlacklist.get()) {
                        default -> {
                            return e.getKey().getFullname(e.getValue() + 1);
                        }
                        case 1 -> {
                            for (String enchreg : AurorianConfig.Config_AurorianSteel_Enchants.get()) {
                                if (enchreg.equals(e.getKey().getDescriptionId())) {
                                    return e.getKey().getFullname(e.getValue() + 1);
                                }
                            }
                        }
                        case 2 -> {
                            for (String enchreg : AurorianConfig.Config_AurorianSteel_Enchants.get()) {
                                if (enchreg.equals(e.getKey().getDescriptionId())) {
                                    return null;
                                }
                            }
                            return e.getKey().getFullname(e.getValue() + 1);
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
            Map<Enchantment, Integer> enchs = EnchantmentHelper.getEnchantments(stack);
            for (Map.Entry<Enchantment, Integer> e : enchs.entrySet()) {
                if (e.getKey().getMaxLevel() > 1 && e.getValue() < e.getKey().getMaxLevel()) {
                    switch (AurorianConfig.Config_AurorianSteel_Enchants_WhitelistBlacklist.get()) {
                        default -> {
                            return true;
                        }
                        case 1 -> {
                            for (String enchreg : AurorianConfig.Config_AurorianSteel_Enchants.get()) {
                                if (enchreg.equals(e.getKey().getDescriptionId())) {
                                    return true;
                                }
                            }
                        }
                        case 2 -> {
                            for (String enchreg : AurorianConfig.Config_AurorianSteel_Enchants.get()) {
                                if (enchreg.equals(e.getKey().getDescriptionId())) {
                                    return false;
                                }
                            }
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
        int itemlevel = getLevel(stack);
        float levelmultiplier = getMultiplier(stack);

        if (itemlevel >= Math.round(maxlevelbase * levelmultiplier) - 1) {
            if (stack.isEnchanted()) {
                Map<Enchantment, Integer> enchs = EnchantmentHelper.getEnchantments(stack);
                for (Map.Entry<Enchantment, Integer> e : enchs.entrySet()) {
                    if (e.getKey().getMaxLevel() > 1 && e.getValue() < e.getKey().getMaxLevel()) {
                        switch (AurorianConfig.Config_AurorianSteel_Enchants_WhitelistBlacklist.get()) {
                            default -> {
                                enchs.put(e.getKey(), e.getValue() + 1);
                                EnchantmentHelper.setEnchantments(enchs, stack);
                                setMultiplier(stack, (float) (levelmultiplier * maxlevelmultiplier));
                                setLevel(stack, 0);
                                return;
                            }
                            case 1 -> {
                                for (String enchreg : AurorianConfig.Config_AurorianSteel_Enchants.get()) {
                                    if (enchreg.equals(e.getKey().getDescriptionId())) {
                                        enchs.put(e.getKey(), e.getValue() + 1);
                                        EnchantmentHelper.setEnchantments(enchs, stack);
                                        setMultiplier(stack, (float) (levelmultiplier * maxlevelmultiplier));
                                        setLevel(stack, 0);
                                        return;
                                    }
                                }
                            }
                            case 2 -> {
                                for (String enchreg : AurorianConfig.Config_AurorianSteel_Enchants.get()) {
                                    if (enchreg.equals(e.getKey().getDescriptionId())) {
                                        return;
                                    }
                                }
                                enchs.put(e.getKey(), e.getValue() + 1);
                                EnchantmentHelper.setEnchantments(enchs, stack);
                                setMultiplier(stack, (float) (levelmultiplier * maxlevelmultiplier));
                                setLevel(stack, 0);
                                return;
                            }
                        }
                    }
                }
            }
        }
        else {
            setLevel(stack, itemlevel + 1);
        }
    }

    public static float getMultiplier(ItemStack stack) {
        checkNbt(stack);
        return stack.getTag().getFloat("upgrademultiplier");
        }

    public static void setMultiplier(ItemStack stack, float amt) {
        CompoundTag nbt = checkNbt(stack);
        nbt.putFloat("upgrademultiplier", amt);
        }

    public static int getLevel(ItemStack stack) {
        checkNbt(stack);
        return stack.getTag().getInt("currentupgradelevel");
        }

    public static void setLevel(ItemStack stack, int amt) {
        CompoundTag nbt = checkNbt(stack);
        nbt.putInt("currentupgradelevel", amt);
    }

    public static CompoundTag checkNbt(ItemStack stack) {
        CompoundTag nbt=new CompoundTag();
        if (stack.hasTag()) {
            nbt = stack.getTag();
        }

        if (!nbt.contains("currentupgradelevel")) {
            nbt.putInt("currentupgradelevel", 0);
            stack.setTag(nbt);
        }
        if (!nbt.contains("upgrademultiplier")) {
            nbt.putFloat("upgrademultiplier", 1F);
            stack.setTag(nbt);
        }
            return nbt;
    }

}