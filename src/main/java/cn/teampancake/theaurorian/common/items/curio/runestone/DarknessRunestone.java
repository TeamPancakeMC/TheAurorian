package cn.teampancake.theaurorian.common.items.curio.runestone;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.components.RunestoneDarkness;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.text.NumberFormat;
import java.util.List;

public class DarknessRunestone extends Runestone {

    private final double armorReduction;

    public DarknessRunestone(Properties properties, int rank, double armorReduction) {
        super(properties, rank);
        this.armorReduction = armorReduction;
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> multimap = LinkedHashMultimap.create();
        ResourceLocation key = TheAurorian.prefix("runestone_armor_reduction");
        AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_VALUE;
        multimap.put(Attributes.ARMOR, new AttributeModifier(key, -this.armorReduction, operation));
        return multimap;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        RunestoneDarkness runestoneDarkness = stack.get(TADataComponents.RUNESTONE_DARKNESS);
        Integer rank = stack.get(TADataComponents.RANK);
        if (runestoneDarkness != null && rank != null) {
            String key = "attribute.theaurorian.name.player.darkness_runestone";
            NumberFormat instance = NumberFormat.getPercentInstance();
            instance.setMaximumFractionDigits(0);
            String minChance = instance.format(runestoneDarkness.minChance());
            String maxChance = instance.format(runestoneDarkness.maxChance());
            String minIgnore = instance.format(runestoneDarkness.minIgnore());
            String maxIgnore = instance.format(runestoneDarkness.maxIgnore());
            String chance = String.format("%s~%s", minChance, maxChance);
            String ignore = String.format("%s~%s", minIgnore, maxIgnore);
            if (rank == 5) chance = maxChance;
            MutableComponent component = Component.translatable(key, chance, ignore);
            tooltips.add(component.withStyle(ChatFormatting.BLUE));
        }

        return tooltips;
    }

}