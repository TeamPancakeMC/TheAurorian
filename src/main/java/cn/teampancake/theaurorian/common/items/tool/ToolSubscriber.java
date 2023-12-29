package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.items.TAToolTiers;
import cn.teampancake.theaurorian.common.utils.InventoryUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;


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
                return tier == TAToolTiers.AURORIANITE;
            }
            return false;
        });

        inventoryItems.forEach(itemStack -> {
            if (itemStack.getItem() instanceof TieredItem tieredItem && tieredItem.getTier() instanceof TAToolTiers taToolTiers) {
                if (!itemStack.getOrCreateTag().contains("aurorianite_specialty_ticks")){
                    itemStack.getOrCreateTag().putInt("aurorianite_specialty_ticks", 0);
                }
                taToolTiers.doSpecialty(itemStack);
            }
        });

        inventoryItems.forEach(itemStack -> {
                    CompoundTag tag = itemStack.getOrCreateTag();
                    int ticks = tag.getInt("aurorianite_specialty_ticks");
                    if (ticks >= 20) {
                        tag.putBoolean("aurorianite_specialty", true);
                    } else {
                        tag.putInt("aurorianite_specialty_ticks", ticks + 1);
                    }
                });
    }
}
