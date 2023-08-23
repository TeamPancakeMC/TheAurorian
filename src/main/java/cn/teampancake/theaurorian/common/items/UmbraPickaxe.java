package cn.teampancake.theaurorian.common.items;

import cn.teampancake.theaurorian.config.AurorianConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class UmbraPickaxe extends PickaxeItem implements ITooltipsItem {

    public UmbraPickaxe(Tier pTier) {
        super(pTier, 5, 1.2f, new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        if (getSelectedBlock(pStack) != null) {
            if (pState.getBlock() == getSelectedBlock(pStack)) {
                return (float) (super.getDestroySpeed(pStack, pState) * AurorianConfig.Config_UmbraPickaxeMiningSpeedMultiplier.get());
            }
        }
        return super.getDestroySpeed(pStack, pState);
    }

    private boolean setSelectedBlock(ItemStack stack, Block block) {
        String string = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).toString();
        stack.getOrCreateTag().putString("selectedBlock", string);
        return true;
    }
    private Block getSelectedBlock(ItemStack stack) {
        String string = stack.getOrCreateTag().getString("selectedBlock");
        if (string.isEmpty()) return null;
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(string));
    }
}