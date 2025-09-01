package cn.teampancake.theaurorian.common.items.shield;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CeruleanShield extends ShieldItem {

    public CeruleanShield() {
        super(new Item.Properties().durability(512)
                .component(TADataComponents.ITEM_TAGS, List.of(Tags.Items.TOOLS_SHIELD, ItemTags.DURABILITY_ENCHANTABLE))
                .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC)
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE));
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack ToRepair, ItemStack repair) {
        return repair.getItem() == TAItems.CERULEAN_INGOT.get();
    }

}