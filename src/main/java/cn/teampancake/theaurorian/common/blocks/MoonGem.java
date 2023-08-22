package cn.teampancake.theaurorian.common.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MoonGem extends Block {

    public MoonGem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        if (!Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("string.theaurorian.tooltip.shiftinfo").withStyle(ChatFormatting.ITALIC));
        } else {
            tooltip.add(Component.translatable("string.theaurorian.tooltip.moongem"));
        }
    }

}