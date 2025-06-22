package cn.teampancake.theaurorian.common.event.subscriber;

import cn.teampancake.theaurorian.common.registry.TAToolTiers;
import cn.teampancake.theaurorian.common.utils.TAInventoryUtils;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ToolEventSubscriber {

    private static final DataComponentType<CustomData> CUSTOM_DATA = DataComponents.CUSTOM_DATA;

    @SubscribeEvent
    public static void onTickPlayerTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        List<ItemStack> inventoryItems = TAInventoryUtils.getInventoryItems(player.getInventory(), stack -> {
            if (stack.getItem() instanceof TieredItem tieredItem) {
                Tier tier = tieredItem.getTier();
                return tier == TAToolTiers.AURORIAN_STEEL;
            }

            return false;
        });

        inventoryItems.forEach(itemStack -> {
            if (itemStack.getItem() instanceof TieredItem tieredItem) {
                CompoundTag compoundTag = getStackTag(itemStack);
                if (!compoundTag.contains("aurorian_steel_specialty_ticks")) {
                    compoundTag.putInt("aurorian_steel_specialty_ticks", 0);
                    itemStack.set(CUSTOM_DATA, CustomData.of(compoundTag));
                }

                Map<Tier, Consumer<ItemStack>> specialties = TAToolTiers.getTierSpecialties();
                if (specialties.containsKey(tieredItem.getTier())) {
                    specialties.get(tieredItem.getTier()).accept(itemStack);
                }
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

    private static CompoundTag getStackTag(ItemStack stack) {
        return stack.getOrDefault(CUSTOM_DATA, CustomData.EMPTY).copyTag();
    }

}