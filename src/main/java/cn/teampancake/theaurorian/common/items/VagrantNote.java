package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.components.ChapterContent;
import cn.teampancake.theaurorian.common.network.ShowVagrantNoteScreenS2CPacket;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.registry.TAItemTooltips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class VagrantNote extends Item {

    public VagrantNote() {
        super(new Properties().rarity(Rarity.EPIC).stacksTo(1)
                .component(TADataComponents.CHAPTERS, new ArrayList<>())
                .component(TADataComponents.ITEM_TOOLTIP, TAItemTooltips.EPIC)
                .component(TADataComponents.SIMPLE_MODEL, Unit.INSTANCE));
    }

    @Override
    public Component getName(ItemStack stack) {
        if (stack.has(TADataComponents.NOTE_PASSPORT)) {
            super.getName(stack).getStyle().withColor(ChatFormatting.GOLD);
        }
        
        return super.getName(stack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        List<ChapterContent> chapters = itemInHand.get(TADataComponents.CHAPTERS);
        if (player instanceof ServerPlayer serverPlayer && chapters != null) {
            boolean hasPassport = itemInHand.has(TADataComponents.NOTE_PASSPORT);
            PacketDistributor.sendToPlayer(serverPlayer, new ShowVagrantNoteScreenS2CPacket(chapters, hasPassport, level.dimension()));
            return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
        }

        return InteractionResultHolder.pass(itemInHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        List<ChapterContent> chapters = stack.getOrDefault(TADataComponents.CHAPTERS, new ArrayList<>());
        String nKey = "tooltips.item.theaurorian.vagrant_note.chapter_numbers";
        tooltipComponents.add(Component.translatable(nKey, chapters.size()).withStyle(ChatFormatting.YELLOW));
        if (stack.has(TADataComponents.NOTE_PASSPORT)) {
            String pKey = "tooltips.item.theaurorian.vagrant_note.pass";
            tooltipComponents.add(Component.translatable(pKey).withStyle(ChatFormatting.GREEN));
        }
    }

}