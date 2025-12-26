package cn.teampancake.theaurorian.common.items.curio.runestone;

import cn.teampancake.theaurorian.common.components.RunestoneMountain;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;

import java.text.NumberFormat;
import java.util.List;

public class MountainRunestone extends Runestone {

    public MountainRunestone(Properties properties, int rank) {
        super(properties.component(TADataComponents.ADVANCED_RUNESTONE, Unit.INSTANCE), rank);
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        RunestoneMountain runestoneMountain = stack.get(TADataComponents.RUNESTONE_MOUNTAIN);
        Float fixedMiningBoost = stack.get(TADataComponents.FIXED_MINING_BOOST);
        Integer rank = stack.get(TADataComponents.RANK);
        if (runestoneMountain != null && fixedMiningBoost != null && rank != null) {
            String keyPrefix = "attribute.theaurorian.name.player.mountain_runestone.%s";
            NumberFormat instance = NumberFormat.getPercentInstance();
            instance.setMaximumFractionDigits(0);
            String minMiningXP = instance.format(runestoneMountain.minXpBoost());
            String maxMiningXP = instance.format(runestoneMountain.maxXpBoost());
            String miningXP = String.format("%s~%s", minMiningXP, maxMiningXP);
            if (rank == 5) miningXP = maxMiningXP;
            tooltips.add(Component.translatable(String.format(keyPrefix, "mining_xp"), miningXP).withStyle(ChatFormatting.BLUE));
            tooltips.add(Component.translatable(String.format(keyPrefix, "mining_boost"), fixedMiningBoost).withStyle(ChatFormatting.BLUE));
        }

        return tooltips;
    }

}