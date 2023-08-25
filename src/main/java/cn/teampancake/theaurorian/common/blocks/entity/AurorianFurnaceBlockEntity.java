package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.common.blocks.AurorianFurnace;
import cn.teampancake.theaurorian.config.AurorianConfig;
import cn.teampancake.theaurorian.mixin.InvokeAbstractFurnaceBlockEntity;
import cn.teampancake.theaurorian.registry.ModBlockEntityTypes;
import cn.teampancake.theaurorian.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AurorianFurnaceBlockEntity extends AbstractFurnaceBlockEntity {

    public AurorianFurnaceBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.AURORIAN_FURNACE.get(), pos, blockState, RecipeType.SMELTING);
    }

    public float getChimneySpeedMultiplier() {
        int y = 0;
        int chimneyCount = 0;
        while (this.level != null && !(this.level.isEmptyBlock(this.worldPosition.above(y)))
                && chimneyCount < AurorianConfig.CONFIG_MAXIMUM_CHIMNEYS.get()) {
            y++;
            if (this.level.getBlockState(this.worldPosition.above(y)).getBlock() == ModBlocks.AURORIAN_FURNACE_CHIMNEY.get()) {
                chimneyCount++;
            }
        }
        return (float) ((chimneyCount / AurorianConfig.CONFIG_MAXIMUM_CHIMNEYS.get()) * AurorianConfig.CONFIG_CHIMNEY_SPEED_MULTIPLIER.get());
    }

    private int getSmeltTime() {
        return (int) (200 - 200 * this.getChimneySpeedMultiplier());
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AurorianFurnaceBlockEntity blockEntity) {
        boolean flag = blockEntity.isLit();
        boolean flag1 = false;
        if (blockEntity.isLit()) {
            --blockEntity.litTime;
        }

        ItemStack itemStack = blockEntity.items.get(1);
        boolean flag2 = !blockEntity.items.get(0).isEmpty();
        boolean flag3 = !itemStack.isEmpty();
        if (blockEntity.isLit() || flag3 && flag2) {
            int i = blockEntity.getMaxStackSize();
            Recipe<?> recipe = flag2 ? blockEntity.quickCheck.getRecipeFor(blockEntity, level).orElse(null) : null;
            if (!blockEntity.isLit() && ((InvokeAbstractFurnaceBlockEntity)blockEntity).canBurn(level.registryAccess(), recipe, blockEntity.items, i)) {
                int burnDuration = blockEntity.getBurnDuration(itemStack);
                blockEntity.litTime = (int) (burnDuration - burnDuration * blockEntity.getChimneySpeedMultiplier());
                blockEntity.litDuration = blockEntity.litTime;
                if (blockEntity.isLit()) {
                    flag1 = true;
                    if (itemStack.hasCraftingRemainingItem()) {
                        blockEntity.items.set(1, itemStack.getCraftingRemainingItem());
                    } else if (flag3) {
                        itemStack.shrink(1);
                        if (itemStack.isEmpty()) {
                            blockEntity.items.set(1, itemStack.getCraftingRemainingItem());
                        }
                    }
                }
            }

            if (blockEntity.isLit() && ((InvokeAbstractFurnaceBlockEntity)blockEntity).canBurn(level.registryAccess(), recipe, blockEntity.items, i)) {
                ++blockEntity.cookingProgress;
                if (blockEntity.cookingProgress == blockEntity.cookingTotalTime) {
                    blockEntity.cookingProgress = 0;
                    blockEntity.cookingTotalTime = blockEntity.getSmeltTime();
                    if (((InvokeAbstractFurnaceBlockEntity)blockEntity).burn(level.registryAccess(), recipe, blockEntity.items, i)) {
                        blockEntity.setRecipeUsed(recipe);
                    }

                    flag1 = true;
                }
            } else {
                blockEntity.cookingProgress = 0;
            }
        } else if (!blockEntity.isLit() && blockEntity.cookingProgress > 0) {
            blockEntity.cookingProgress = Mth.clamp(blockEntity.cookingProgress - 2, 0, blockEntity.cookingTotalTime);
        }

        if (flag != blockEntity.isLit()) {
            flag1 = true;
            state = state.setValue(AurorianFurnace.LIT, blockEntity.isLit());
            level.setBlock(pos, state, 3);
        }

        if (flag1) {
            setChanged(level, pos, state);
        }
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameTags(itemstack, stack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if (index == 0 && !flag) {
            this.cookingTotalTime = this.getSmeltTime();
            this.cookingProgress = 0;
            this.setChanged();
        }
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("theaurorian.container.aurorian_furnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new FurnaceMenu(containerId, inventory, this, this.dataAccess);
    }

}