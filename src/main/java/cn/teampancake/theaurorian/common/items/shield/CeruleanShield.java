package cn.teampancake.theaurorian.common.items.shield;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.jetbrains.annotations.NotNull;

public class CeruleanShield extends ShieldItem {

    public CeruleanShield(Item.Properties properties) {
        super(properties.durability(512).component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC));
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack ToRepair, ItemStack repair) {
        return repair.getItem() == TAItems.CERULEAN_INGOT.get();
    }

}