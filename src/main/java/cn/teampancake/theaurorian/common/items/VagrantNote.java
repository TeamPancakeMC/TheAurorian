package cn.teampancake.theaurorian.common.items;

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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        if (player instanceof ServerPlayer serverPlayer) {
            List<Integer> chapters = itemInHand.getOrDefault(TADataComponents.CHAPTERS, new ArrayList<>());
            PacketDistributor.sendToPlayer(serverPlayer, new ShowVagrantNoteScreenS2CPacket(chapters));
            return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
        }

        return InteractionResultHolder.pass(itemInHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        List<Integer> chapters = stack.getOrDefault(TADataComponents.CHAPTERS, new ArrayList<>());
        String key = "tooltips.item.theaurorian.vagrant_note.chapter_numbers";
        tooltipComponents.add(Component.translatable(key, chapters.size()).withStyle(ChatFormatting.YELLOW));
    }

}