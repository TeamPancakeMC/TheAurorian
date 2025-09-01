package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class BookOfSin extends Item {

    public BookOfSin() {
        super(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)
                .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.RARE)
                .component(TADataComponents.SIMPLE_MODEL, Unit.INSTANCE)
                .component(TADataComponents.ABSORBED_EXPERIENCE, 0));
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        DataComponentType<Integer> component = TADataComponents.ABSORBED_EXPERIENCE.get();
        ItemStack itemInHand = player.getItemInHand(usedHand);
        Integer i = itemInHand.get(component);
        if (i != null && i > 0) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (level.isClientSide) {
                RandomSource random = level.getRandom();
                float pitch = (random.nextFloat() - random.nextFloat()) * 0.35F + 0.9F;
                level.playLocalSound(player.getX(), player.getY(), player.getZ(),
                        SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS,
                        0.1F, pitch, Boolean.FALSE);
            } else {
                player.giveExperiencePoints(i);
                itemInHand.set(component, 0);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Integer i = stack.getOrDefault(TADataComponents.ABSORBED_EXPERIENCE, 0);
        String key = "tooltips.item.theaurorian.book_of_sin.absorbed_experiences";
        tooltipComponents.add(Component.translatable(key, i).withStyle(ChatFormatting.YELLOW));
    }

}