package cn.teampancake.theaurorian.common.items.shield;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.TAItemProperties;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;
import net.neoforged.neoforge.common.Tags;

public class CrystallineShield extends ShieldItem {

    public CrystallineShield() {
        super(TAItemProperties.get().rarity(Rarity.EPIC).durability(512).addItemTag(Tags.Items.TOOLS_SHIELD, ItemTags.DURABILITY_ENCHANTABLE, TAItemTags.IS_EPIC).hasTooltips());
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 100;
    }

}