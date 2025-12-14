package cn.teampancake.theaurorian.common.items.curio.runestone;

import cn.teampancake.theaurorian.client.gui.tooltips.ItemTooltip;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Map;
import java.util.Optional;

public class Runestone extends Item implements ICurioItem {

    private static final Map<Integer, Pair<Float, Holder<ItemTooltip>>> RANK_MAP = Map.of(
            1, Pair.of(0.6F, TAItemTooltips.UNCOMMON),
            2, Pair.of(0.25F, TAItemTooltips.RARE),
            3, Pair.of(0.1F, TAItemTooltips.EPIC),
            4, Pair.of(0.05F, TAItemTooltips.LEGENDARY),
            5, Pair.of(0.0F, TAItemTooltips.MYTHICAL));
    private final float lootChance;

    public Runestone(Properties properties, int rank) {
        super(properties.component(TADataComponents.RANK, rank)
                .component(TADataComponents.ITEM_TOOLTIP, RANK_MAP.get(rank).getSecond()));
        this.lootChance = RANK_MAP.get(rank).getFirst();
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(slotContext.entity());
        Optional<ICurioStacksHandler> stacksHandler = curiosInventory.flatMap(o -> o.getStacksHandler("runestone"));
        IDynamicStackHandler stacks = stacksHandler.orElseThrow().getStacks();
        for (int i = 0; i < stacks.getSlots(); i++) {
            ItemStack stackInSlot = stacks.getStackInSlot(i);
            if (!stackInSlot.isEmpty()) {
                Item itemInSlot = stackInSlot.getItem();
                String key1 = BuiltInRegistries.ITEM.getKey(itemInSlot).toString();
                String key2 = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
                String s1 = key1.substring(0, key1.lastIndexOf('_'));
                String s2 = key2.substring(0, key2.lastIndexOf('_'));
                if (s1.equals(s2)) return false;
            }
        }

        return slotContext.identifier().equals("runestone");
    }

    public float getLootChance() {
        return this.lootChance;
    }

}