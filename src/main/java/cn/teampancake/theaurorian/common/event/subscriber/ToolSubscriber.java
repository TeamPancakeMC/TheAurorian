package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.common.items.TAToolTiers;
import cn.teampancake.theaurorian.common.utils.InventoryUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Map;

public class ToolSubscriber {

    @SubscribeEvent
    public static void onTickPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level level = player.level();
        if (level.isClientSide()) return;
        Inventory inventory = player.getInventory();
        List<ItemStack> inventoryItems = InventoryUtils.getInventoryItems(inventory, stack -> {
            if (stack.getItem() instanceof TieredItem tieredItem) {
                Tier tier = tieredItem.getTier();
                return tier == TAToolTiers.AURORIAN_STEEL;
            }
            return false;
        });

        inventoryItems.forEach(itemStack -> {
            if (itemStack.getItem() instanceof TieredItem tieredItem && tieredItem.getTier() instanceof TAToolTiers taToolTiers) {
                if (!itemStack.getOrCreateTag().contains("aurorian_steel_specialty_ticks")) {
                    itemStack.getOrCreateTag().putInt("aurorian_steel_specialty_ticks", 0);
                }
                taToolTiers.doSpecialty(itemStack);
            }
        });

        inventoryItems.forEach(itemStack -> {
            CompoundTag tag = itemStack.getOrCreateTag();
            int ticks = tag.getInt("aurorian_steel_specialty_ticks");
            if (ticks >= 20) {
                tag.putBoolean("aurorian_steel_specialty", true);
            } else {
                tag.putInt("aurorian_steel_specialty_ticks", ticks + 1);
            }
        });
    }

   public static void aurorianSteelSpecialty(ItemStack stack) {
        if (stack.getOrCreateTag().getBoolean("aurorian_steel_specialty")) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
            Enchantment enchantment = enchantments.keySet().stream()
                    .filter(enchantment1 -> enchantments.get(enchantment1) < enchantment1.getMaxLevel())
                    .skip((int) (enchantments.size() * Math.random())).findFirst().orElse(null);
            if (enchantment != null) {
                enchantments.computeIfPresent(enchantment, (k, level) -> level + 1);
                EnchantmentHelper.setEnchantments(enchantments, stack);
            }
        }
    }

}