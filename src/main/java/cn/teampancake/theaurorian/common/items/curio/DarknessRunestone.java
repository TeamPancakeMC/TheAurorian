package cn.teampancake.theaurorian.common.items.curio;

import cn.teampancake.theaurorian.TheAurorian;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class DarknessRunestone extends Runestone {

    private final double armorReduction;

    public DarknessRunestone(Properties properties, int rank, float lootChance, double armorReduction) {
        super(properties, rank, lootChance);
        this.armorReduction = armorReduction;
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> multimap = LinkedHashMultimap.create();
        if (slotContext.entity().getAttributeValue(Attributes.ARMOR) > this.armorReduction) {
            ResourceLocation key = TheAurorian.prefix("runestone_armor_reduction");
            AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_VALUE;
            multimap.put(Attributes.ARMOR, new AttributeModifier(key, -this.armorReduction, operation));
        }

        return multimap;
    }

}