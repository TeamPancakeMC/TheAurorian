package cn.teampancake.theaurorian.common.items.tool;

import cn.teampancake.theaurorian.common.data.datagen.tags.TAItemTags;
import cn.teampancake.theaurorian.common.items.TAItemProperties;
import cn.teampancake.theaurorian.common.items.TAToolTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class UmbraPickaxe extends PickaxeItem {

    public UmbraPickaxe() {
        super(TAToolTiers.UMBRA, TAItemProperties.get().rarity(Rarity.EPIC)
                .component(DataComponents.CUSTOM_DATA, getDefaultSelectedBlock())
                .attributes(createAttributes(TAToolTiers.UMBRA, (5), (1.2F)))
                .addItemTag(ItemTags.PICKAXES, TAItemTags.IS_EPIC).hasTooltips());
    }

    private static CustomData getDefaultSelectedBlock() {
        CompoundTag selectedBlock = CustomData.EMPTY.copyTag();
        BlockState state = Blocks.AIR.defaultBlockState();
        CompoundTag compoundTag = NbtUtils.writeBlockState(state);
        selectedBlock.put("selected_block", compoundTag);
        return CustomData.of(selectedBlock);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos clickedPos = context.getClickedPos();
        ItemStack itemInHand = context.getItemInHand();
        DataComponentType<CustomData> type = DataComponents.CUSTOM_DATA;
        CompoundTag compoundTag = itemInHand.getOrDefault(type, CustomData.EMPTY).copyTag();
        CompoundTag selectedBlockTag = compoundTag.getCompound("selected_block");
        HolderLookup<Block> blockGetter = level.holderLookup(Registries.BLOCK);
        BlockState selectedState = NbtUtils.readBlockState(blockGetter, selectedBlockTag);
        BlockState clickedState = level.getBlockState(clickedPos);
        boolean isSameState = clickedState.is(selectedState.getBlock());
        boolean canMining = clickedState.getDestroySpeed(level, clickedPos) != 0.0F;
        if (clickedState.getFluidState().isEmpty() && !isSameState && canMining) {
            boolean durabilityEnough = itemInHand.getMaxDamage() - itemInHand.getDamageValue() >= 60;
            if (player != null && player.isSteppingCarefully()) {
                if (durabilityEnough) {
                    compoundTag.put("selected_block", NbtUtils.writeBlockState(clickedState));
                    itemInHand.hurtAndBreak(50, player, LivingEntity.getSlotForHand(context.getHand()));
                    itemInHand.set(type, CustomData.of(compoundTag));
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel != null) {
            CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            CompoundTag compoundTag = customData.copyTag().getCompound("selected_block");
            HolderLookup<Block> blockGetter = clientLevel.holderLookup(Registries.BLOCK);
            BlockState selected = NbtUtils.readBlockState(blockGetter, compoundTag);
            String message = "messages." + this.getDescriptionId() + ".selected";
            MutableComponent name = selected.getBlock().getName().withStyle(ChatFormatting.GREEN);
            tooltipComponents.add(Component.translatable(message, name));
        }
    }

}