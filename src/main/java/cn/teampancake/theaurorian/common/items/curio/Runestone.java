package cn.teampancake.theaurorian.common.items.curio;

import cn.teampancake.theaurorian.client.gui.tooltips.ItemTooltip;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Runestone extends Item implements ICurioItem {

    private static final Map<Integer, Holder<ItemTooltip>> RANK_MAP = Map.of(
            1, TAItemTooltips.UNCOMMON, 2, TAItemTooltips.RARE, 3, TAItemTooltips.EPIC,
            4, TAItemTooltips.LEGENDARY, 5, TAItemTooltips.MYTHICAL);
    private final float lootChance;

    public Runestone(Properties properties, int rank, float lootChance) {
        super(properties.component(TADataComponents.RANK, rank)
                .component(TADataComponents.ITEM_TOOLTIP, RANK_MAP.get(rank))
                .component(TADataComponents.ITEM_TAGS, List.of(TAItemTags.RUNESTONE)));
        this.lootChance = lootChance;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(slotContext.entity());
        Optional<ICurioStacksHandler> stacksHandler = curiosInventory.flatMap(o -> o.getStacksHandler("runestone"));
        IDynamicStackHandler stacks = stacksHandler.orElseThrow().getStacks();
        for (int i = 0; i < stacks.getSlots(); i++) {
            Item itemInSlot = stacks.getStackInSlot(i).getItem();
            String key1 = BuiltInRegistries.ITEM.getKey(itemInSlot).toString();
            String key2 = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
            String s1 = key1.substring(0, key1.lastIndexOf('_'));
            String s2 = key2.substring(0, key2.lastIndexOf('_'));
            if (s1.equals(s2)) return false;
        }

        return slotContext.identifier().equals("runestone");
    }

    public float getLootChance() {
        return this.lootChance;
    }

}