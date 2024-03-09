package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.items.ITooltipsItem;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class UmbraPickaxe extends PickaxeItem implements ITooltipsItem {

    public UmbraPickaxe() {
        super(TAToolTiers.UMBRA, 5, 1.2f, new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos clickedPos = context.getClickedPos();
        ItemStack itemInHand = context.getItemInHand();
        CompoundTag compoundTag = itemInHand.getOrCreateTag();
        CompoundTag selectedBlockTag = compoundTag.getCompound("SelectedBlock");
        HolderLookup<Block> blockGetter = level.holderLookup(Registries.BLOCK);
        BlockState selectedState = NbtUtils.readBlockState(blockGetter, selectedBlockTag);
        BlockState clickedState = level.getBlockState(clickedPos);
        boolean isSameState = clickedState.is(selectedState.getBlock());
        boolean canMining = clickedState.getDestroySpeed(level, clickedPos) != 0.0F;
        if (clickedState.getFluidState().isEmpty() && !isSameState && canMining) {
            boolean durabilityEnough = itemInHand.getMaxDamage() - itemInHand.getDamageValue() >= 60;
            if (player != null && player.isSteppingCarefully()) {
                if (durabilityEnough) {
                    compoundTag.put("SelectedBlock", NbtUtils.writeBlockState(clickedState));
                    Consumer<LivingEntity> onBreak = e -> e.broadcastBreakEvent(context.getHand());
                    itemInHand.hurtAndBreak(50, player, onBreak);
                }
                if (player instanceof ServerPlayer serverPlayer) {
                    String prefix = "messages." + this.getDescriptionId();
                    String message = prefix + (durabilityEnough ? ".selected" : ".fail");
                    MutableComponent name = clickedState.getBlock().getName().withStyle(ChatFormatting.GREEN);
                    serverPlayer.sendSystemMessage(Component.translatable(message, name));
                }
            }

            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        if (level != null) {
            CompoundTag compoundTag = stack.getOrCreateTag().getCompound("SelectedBlock");
            HolderLookup<Block> blockGetter = level.holderLookup(Registries.BLOCK);
            BlockState selected = NbtUtils.readBlockState(blockGetter, compoundTag);
            String message = "messages." + this.getDescriptionId() + ".selected";
            MutableComponent name = selected.getBlock().getName().withStyle(ChatFormatting.GREEN);
            tooltipComponents.add(Component.translatable(message, name));
        }
    }

}