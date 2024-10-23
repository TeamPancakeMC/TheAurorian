package cn.teampancake.theaurorian.common.items.shield;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.TAItemProperties;
import cn.teampancake.theaurorian.common.registry.TAItems;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

public class CeruleanShield extends ShieldItem {

    public CeruleanShield() {
        super(TAItemProperties.get().durability(512).addItemTag(Tags.Items.TOOLS_SHIELD, ItemTags.DURABILITY_ENCHANTABLE, TAItemTags.IS_EPIC).hasTooltips());
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack ToRepair, ItemStack repair) {
        return repair.getItem() == TAItems.CERULEAN_INGOT.get();
    }

}