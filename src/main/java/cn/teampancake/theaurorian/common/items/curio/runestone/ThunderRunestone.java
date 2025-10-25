package cn.teampancake.theaurorian.common.items.curio.runestone;

import cn.teampancake.theaurorian.common.components.RunestoneThunder;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.text.NumberFormat;
import java.util.List;

public class ThunderRunestone extends Runestone {

    public ThunderRunestone(Properties properties, int rank) {
        super(properties, rank);
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        RunestoneThunder runestoneThunder = stack.get(TADataComponents.RUNESTONE_THUNDER);
        Integer rank = stack.get(TADataComponents.RANK);
        if (runestoneThunder != null && rank != null) {
            String key = "attribute.theaurorian.name.player.thunder_runestone";
            NumberFormat instance = NumberFormat.getPercentInstance();
            instance.setMaximumFractionDigits(0);
            String minChance = instance.format(runestoneThunder.minChance());
            String maxChance = instance.format(runestoneThunder.maxChance());
            String chance = rank == 5 ? maxChance : String.format("%s~%s", minChance, maxChance);
            MutableComponent component = Component.translatable(key, chance);
            tooltips.add(component.withStyle(ChatFormatting.BLUE));
        }

        return tooltips;
    }

}