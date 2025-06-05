package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.common.components.RuneGame;
import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.data.pack.RuneGameLoader;
import cn.teampancake.theaurorian.common.network.RuneGameStartS2CPacket;
import cn.teampancake.theaurorian.common.registry.TADataComponents;
import cn.teampancake.theaurorian.common.utils.AlgorithmUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class RuneKnowledgeFragment extends Item {

    public RuneKnowledgeFragment() {
        super(new Item.Properties().stacksTo(1)
                .component(TADataComponents.ITEM_TAGS, List.of(TAItemTags.IS_LEGENDARY))
                .component(TADataComponents.SIMPLE_MODEL, Unit.INSTANCE)
                .component(TADataComponents.RUNE_GAME, RuneGame.EMPTY)
                .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, Boolean.FALSE));
    }

    @Override
    public Component getName(ItemStack stack) {
        RuneGame runeGame = stack.get(TADataComponents.RUNE_GAME.get());
        MutableComponent description = Component.translatable(this.getDescriptionId(stack));
        if (runeGame != null && runeGame.isDone()) {
            return description.withStyle(ChatFormatting.LIGHT_PURPLE);
        } else {
            return description.withStyle(ChatFormatting.YELLOW);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        if (player instanceof ServerPlayer serverPlayer && !level.isClientSide && !RuneGameLoader.RUNE_GAME.isEmpty()) {
            String randomLevel = AlgorithmUtils.convert3DtoString(RuneGameLoader.getRandomLevel());
            PacketDistributor.sendToPlayer(serverPlayer, new RuneGameStartS2CPacket(randomLevel));
            return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
        }

        return InteractionResultHolder.pass(itemInHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        RuneGame runeGame = stack.get(TADataComponents.RUNE_GAME);
        if (runeGame != null) {
            runeGame.addToTooltip(context, tooltipComponents::add, tooltipFlag);
        }
    }

}