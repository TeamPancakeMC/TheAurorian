package cn.teampancake.theaurorian.common.items.curio;

import cn.teampancake.theaurorian.client.gui.tooltips.ItemTooltip;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Runestone extends Item implements ICurioItem {

    private static final Map<Integer, Holder<ItemTooltip>> RANK_MAP = Map.of(
            1, TAItemTooltips.UNCOMMON, 2, TAItemTooltips.RARE, 3, TAItemTooltips.EPIC,
            4, TAItemTooltips.LEGENDARY, 5, TAItemTooltips.MYTHICAL);

    public Runestone(Properties properties, int rank) {
        super(properties.component(TADataComponents.RANK, rank)
                .component(TADataComponents.ITEM_TOOLTIP, RANK_MAP.get(rank))
                .component(TADataComponents.ITEM_TAGS, List.of(TAItemTags.RUNESTONE)));
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(slotContext.entity());
        Optional<ICurioStacksHandler> stacksHandler = curiosInventory.flatMap(o -> o.getStacksHandler("runestone"));
        IDynamicStackHandler stacks = stacksHandler.orElseThrow().getStacks();
        Integer rankOfCurrent = stack.get(TADataComponents.RANK);
        for (int i = 0; i < stacks.getSlots(); i++) {
            ItemStack stackInSlot = stacks.getStackInSlot(i);
            if (stackInSlot.is(stack.getItem())) {
                Integer rankInSlot = stackInSlot.get(TADataComponents.RANK);
                if (rankInSlot == null || rankOfCurrent == null) continue;
                if (Objects.equals(rankInSlot, rankOfCurrent)) return false;
            }
        }

        return slotContext.identifier().equals("runestone");
    }

}