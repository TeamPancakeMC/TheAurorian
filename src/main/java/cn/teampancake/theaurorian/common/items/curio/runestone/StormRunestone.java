package cn.teampancake.theaurorian.common.items.curio.runestone;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.components.RunestoneStorm;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class StormRunestone extends Runestone {

    public StormRunestone(Properties properties, int rank) {
        super(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE), rank);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> multimap = LinkedHashMultimap.create();
        float fixedSpeed = stack.getOrDefault(TADataComponents.FIXED_SPEED_BOOST, 0.0F);
        ResourceLocation key = TheAurorian.prefix("runestone_speed");
        AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL;
        multimap.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(key, fixedSpeed, operation));
        return multimap;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        RunestoneStorm runestoneStorm = stack.get(TADataComponents.RUNESTONE_STORM);
        Integer rank = stack.get(TADataComponents.RANK);
        if (runestoneStorm != null && rank != null) {
            String key = "attribute.theaurorian.name.player.storm_runestone";
            float fallDamageReduce = runestoneStorm.fallDamageReduce();
            MutableComponent component = Component.translatable(key, fallDamageReduce);
            tooltips.add(component.withStyle(ChatFormatting.BLUE));
        }

        return tooltips;
    }

}