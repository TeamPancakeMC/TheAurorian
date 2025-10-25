package cn.teampancake.theaurorian.common.items.curio.runestone;

import cn.teampancake.theaurorian.common.components.RunestoneBlaze;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.text.NumberFormat;
import java.util.List;

public class BlazeRunestone extends Runestone {

    public BlazeRunestone(Properties properties, int rank) {
        super(properties, rank);
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        RunestoneBlaze runestoneBlaze = stack.get(TADataComponents.RUNESTONE_BLAZE);
        Integer rank = stack.get(TADataComponents.RANK);
        if (runestoneBlaze != null && rank != null) {
            String key = "attribute.theaurorian.name.player.blaze_runestone";
            NumberFormat instance = NumberFormat.getPercentInstance();
            instance.setMaximumFractionDigits(0);
            String minChance = instance.format(runestoneBlaze.minChance());
            String maxChance = instance.format(runestoneBlaze.maxChance());
            String minBoost = instance.format(runestoneBlaze.minBoost());
            String maxBoost = instance.format(runestoneBlaze.maxBoost());
            String chance = String.format("%s~%s", minChance, maxChance);
            String boost = String.format("%s~%s", minBoost, maxBoost);
            if (rank == 5) chance = maxChance;
            MutableComponent component = Component.translatable(key, chance, boost);
            tooltips.add(component.withStyle(ChatFormatting.BLUE));
        }

        return tooltips;
    }

}