package cn.teampancake.theaurorian.common.items.curio.runestone;

import cn.teampancake.theaurorian.common.components.RunestoneWater;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.text.NumberFormat;
import java.util.List;

public class LightRunestone extends Runestone {

    public LightRunestone(Properties properties, int rank) {
        super(properties, rank);
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        RunestoneWater runestoneWater = stack.get(TADataComponents.RUNESTONE_LIGHT);
        Integer rank = stack.get(TADataComponents.RANK);
        if (runestoneWater != null && rank != null) {
            String key = "attribute.theaurorian.name.player.light_runestone";
            NumberFormat instance = NumberFormat.getPercentInstance();
            instance.setMaximumFractionDigits(0);
            String minChance = instance.format(runestoneWater.minChance());
            String maxChance = instance.format(runestoneWater.maxChance());
            String healValue = instance.format(runestoneWater.healValue());
            String chance = String.format("%s~%s", minChance, maxChance);
            if (rank == 5) chance = maxChance;
            MutableComponent component = Component.translatable(key, chance, healValue);
            tooltips.add(component.withStyle(ChatFormatting.BLUE));
        }

        return tooltips;
    }

}