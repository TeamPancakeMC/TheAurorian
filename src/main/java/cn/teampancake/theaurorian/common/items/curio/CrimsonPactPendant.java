package cn.teampancake.theaurorian.common.items.curio;

import cn.teampancake.theaurorian.TheAurorian;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CrimsonPactPendant extends Item implements ICurioItem {

    public static final ItemAttributeModifiers ATTRIBUTES = ItemAttributeModifiers.builder().add(
            Attributes.MAX_HEALTH, new AttributeModifier(TheAurorian.prefix("base_max_heath"), -0.5D,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE), EquipmentSlotGroup.OFFHAND).build();

    public CrimsonPactPendant(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return slotContext.identifier().equals("necklace");
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> multimap = LinkedHashMultimap.create();
        ATTRIBUTES.modifiers().forEach(entry -> multimap.put(entry.attribute(), entry.modifier()));
        return multimap;
    }

}