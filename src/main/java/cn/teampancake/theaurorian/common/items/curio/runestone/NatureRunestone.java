package cn.teampancake.theaurorian.common.items.curio.runestone;

import cn.teampancake.theaurorian.common.components.RunestoneNature;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;

import java.text.NumberFormat;
import java.util.List;

public class NatureRunestone extends Runestone {

    public NatureRunestone(Properties properties, int rank) {
        super(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE), rank);
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        RunestoneNature runestoneNature = stack.get(TADataComponents.RUNESTONE_NATURE);
        Float fixedChopBoost = stack.get(TADataComponents.FIXED_CHOP_BOOST);
        Integer rank = stack.get(TADataComponents.RANK);
        if (runestoneNature != null && fixedChopBoost != null && rank != null) {
            String keyPrefix = "attribute.theaurorian.name.player.nature_runestone.%s";
            NumberFormat instance = NumberFormat.getPercentInstance();
            instance.setMaximumFractionDigits(0);
            String minPassiveXP = instance.format(runestoneNature.minXpBoost());
            String maxPassiveXP = instance.format(runestoneNature.maxXpBoost());
            String passiveXP = String.format("%s~%s", minPassiveXP, maxPassiveXP);
            if (rank == 5) passiveXP = maxPassiveXP;
            tooltips.add(Component.translatable(String.format(keyPrefix, "passive_xp"), passiveXP).withStyle(ChatFormatting.BLUE));
            tooltips.add(Component.translatable(String.format(keyPrefix, "chop_boost"), fixedChopBoost).withStyle(ChatFormatting.BLUE));
        }

        return tooltips;
    }

}