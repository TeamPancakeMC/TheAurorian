package cn.teampancake.theaurorian.common.items.shield;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;
import net.neoforged.neoforge.common.Tags;

import java.util.List;

public class CrystallineShield extends ShieldItem {

    public CrystallineShield() {
        super(new Item.Properties().rarity(Rarity.EPIC).durability(512)
                .component(TADataComponents.ITEM_TAGS, List.of(Tags.Items.TOOLS_SHIELD, ItemTags.DURABILITY_ENCHANTABLE))
                .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC)
                .component(TADataComponents.EXTRA_TOOLTIP, Unit.INSTANCE));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 100;
    }

}