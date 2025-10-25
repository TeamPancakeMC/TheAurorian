package cn.teampancake.theaurorian.common.items.curio.runestone;

import cn.teampancake.theaurorian.common.components.RunestoneIce;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.text.NumberFormat;
import java.util.List;

public class IceRunestone extends Runestone {

    public IceRunestone(Properties properties, int rank) {
        super(properties, rank);
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        RunestoneIce runestoneIce = stack.get(TADataComponents.RUNESTONE_ICE);
        Integer rank = stack.get(TADataComponents.RANK);
        if (runestoneIce != null && rank != null) {
            String key = "attribute.theaurorian.name.player.ice_runestone";
            NumberFormat instance = NumberFormat.getPercentInstance();
            instance.setMaximumFractionDigits(0);
            String minChance = instance.format(runestoneIce.minChance());
            String maxChance = instance.format(runestoneIce.maxChance());
            String reduction = instance.format(runestoneIce.baseReduction());
            String chance = String.format("%s~%s", minChance, maxChance);
            if (rank == 5) chance = maxChance;
            MutableComponent component = Component.translatable(key, chance, reduction);
            tooltips.add(component.withStyle(ChatFormatting.BLUE));
        }

        return tooltips;
    }

}