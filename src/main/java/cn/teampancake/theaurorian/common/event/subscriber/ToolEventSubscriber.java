package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.common.items.TAToolTiers;
import cn.teampancake.theaurorian.common.utils.InventoryUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;
import java.util.Set;

public class ToolEventSubscriber {

    private static final DataComponentType<CustomData> CUSTOM_DATA = DataComponents.CUSTOM_DATA;

    @SubscribeEvent
    public static void onTickPlayerTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        List<ItemStack> inventoryItems = InventoryUtils.getInventoryItems(player.getInventory(), stack -> {
            if (stack.getItem() instanceof TieredItem tieredItem) {
                Tier tier = tieredItem.getTier();
                return tier == TAToolTiers.AURORIAN_STEEL;
            }

            return false;
        });

        inventoryItems.forEach(itemStack -> {
            CompoundTag compoundTag = getStackTag(itemStack);
            if (itemStack.getItem() instanceof TieredItem tieredItem && tieredItem.getTier() instanceof TAToolTiers taToolTiers) {
                if (!compoundTag.contains("aurorian_steel_specialty_ticks")) {
                    compoundTag.putInt("aurorian_steel_specialty_ticks", 0);
                    itemStack.set(CUSTOM_DATA, CustomData.of(compoundTag));
                }

                taToolTiers.doSpecialty(itemStack);
            }
        });

        inventoryItems.forEach(itemStack -> {
            CompoundTag compoundTag = getStackTag(itemStack);
            int ticks = compoundTag.getInt("aurorian_steel_specialty_ticks");
            if (ticks >= 20) {
                compoundTag.putBoolean("aurorian_steel_specialty", true);
            } else {
                compoundTag.putInt("aurorian_steel_specialty_ticks", ticks + 1);
            }

            itemStack.set(CUSTOM_DATA, CustomData.of(compoundTag));
        });
    }

    public static void aurorianSteelSpecialty(ItemStack stack) {
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
        return stack.getOrDefault(CUSTOM_DATA, CustomData.EMPTY).copyTag();
    }

}