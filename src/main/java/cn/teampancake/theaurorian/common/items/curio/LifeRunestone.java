package cn.teampancake.theaurorian.common.items.curio;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class LifeRunestone extends Runestone {

    public LifeRunestone(Properties properties, int rank, float lootChance) {
        super(properties, rank, lootChance);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> multimap = LinkedHashMultimap.create();
        float fixedHealthBoost = stack.getOrDefault(TADataComponents.FIX_HEALTH_BOOST, 0.0F);
        ResourceLocation key = TheAurorian.prefix("runestone_health_boost");
        AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_VALUE;
        multimap.put(Attributes.MAX_HEALTH, new AttributeModifier(key, fixedHealthBoost, operation));
        return multimap;
    }

}