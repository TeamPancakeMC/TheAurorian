package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.common.blocks.AurorianFurnace;
import cn.teampancake.theaurorian.common.config.AurorianConfig;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

@SuppressWarnings("unchecked")
public class AurorianFurnaceBlockEntity extends AbstractFurnaceBlockEntity {

    public AurorianFurnaceBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.AURORIAN_FURNACE.get(), pos, blockState, RecipeType.SMELTING);
    }

    public float getChimneySpeedMultiplier() {
        int y = 0;
        int chimneyCount = 0;
        while (this.level != null && !(this.level.isEmptyBlock(this.worldPosition.above(y)))
                && chimneyCount < AurorianConfig.CONFIG_MAXIMUM_CHIMNEYS.get()) {
            y++;
            if (this.level.getBlockState(this.worldPosition.above(y)).getBlock() == TABlocks.AURORIAN_FURNACE_CHIMNEY.get()) {
                chimneyCount++;
            }
        }
        return (float) ((chimneyCount / AurorianConfig.CONFIG_MAXIMUM_CHIMNEYS.get()) * AurorianConfig.CONFIG_CHIMNEY_SPEED_MULTIPLIER.get());
    }

    private int getSmeltTime(float ChimneySpeedMultiplier) {
        return (int) (200 - 200 * ChimneySpeedMultiplier);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AurorianFurnaceBlockEntity blockEntity) {
        boolean isLit = blockEntity.isLit();
        boolean flag1 = false;
        if (isLit) {
            --blockEntity.litTime;
        }

        ItemStack itemStack = blockEntity.items.get(1);
        boolean hasInput = !blockEntity.items.get(0).isEmpty();
        boolean hasFuel = !itemStack.isEmpty();
        if (isLit || hasFuel && hasInput) {
            int i = blockEntity.getMaxStackSize();
            float chimneySpeedMultiplier = blockEntity.getChimneySpeedMultiplier();
            Recipe<?> recipe = hasInput ? blockEntity.quickCheck.getRecipeFor(blockEntity, level).orElse(null) : null;
            if (!blockEntity.isLit() && blockEntity.canBurn(level.registryAccess(), recipe, blockEntity.items, i)) {
                int burnDuration = blockEntity.getBurnDuration(itemStack);
                blockEntity.litTime = (int) (burnDuration - burnDuration * chimneySpeedMultiplier);
                blockEntity.litDuration = blockEntity.litTime;
                if (blockEntity.isLit()) {
                    flag1 = true;
                    if (itemStack.hasCraftingRemainingItem()) {
                        blockEntity.items.set(1, itemStack.getCraftingRemainingItem());
                    } else if (hasFuel) {
                        itemStack.shrink(1);
                        if (itemStack.isEmpty()) {
                            blockEntity.items.set(1, itemStack.getCraftingRemainingItem());
                        }
                    }
                }
            }

            if (blockEntity.isLit() && blockEntity.canBurn(level.registryAccess(), recipe, blockEntity.items, i)) {
                ++blockEntity.cookingProgress;
                if (blockEntity.cookingProgress == blockEntity.cookingTotalTime) {
                    blockEntity.cookingProgress = 0;
                    blockEntity.cookingTotalTime = blockEntity.getSmeltTime(chimneySpeedMultiplier);
                    if (blockEntity.burn(level.registryAccess(), recipe, blockEntity.items, i)) {
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

        if (isLit != blockEntity.isLit()) {
            flag1 = true;
            state = state.setValue(AurorianFurnace.LIT, blockEntity.isLit());
            level.setBlock(pos, state, 3);
        }

        if (flag1) {
            setChanged(level, pos, state);
        }
    }

    private boolean canBurn(RegistryAccess registryAccess, @Nullable Recipe<?> recipe, NonNullList<ItemStack> inventory, int maxStackSize) {
        if (!inventory.get(0).isEmpty() && recipe != null) {
            ItemStack itemStack = ((Recipe<WorldlyContainer>) recipe).assemble(this, registryAccess);
            if (itemStack.isEmpty()) {
                return false;
            } else {
                ItemStack itemStack1 = inventory.get(2);
                if (itemStack1.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItem(itemStack1, itemStack)) {
                    return false;
                } else if (itemStack1.getCount() + itemStack.getCount() <= maxStackSize && itemStack1.getCount() + itemStack.getCount() <= itemStack1.getMaxStackSize()) {
                    return true;
                } else {
                    return itemStack1.getCount() + itemStack.getCount() <= itemStack.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    private boolean burn(RegistryAccess registryAccess, @Nullable Recipe<?> recipe, NonNullList<ItemStack> inventory, int maxStackSize) {
        if (recipe != null && this.canBurn(registryAccess, recipe, inventory, maxStackSize)) {
            ItemStack itemStack = inventory.get(0);
            ItemStack itemStack1 = ((Recipe<WorldlyContainer>) recipe).assemble(this, registryAccess);
            ItemStack itemStack2 = inventory.get(2);
            if (itemStack2.isEmpty()) {
                inventory.set(2, itemStack1.copy());
            } else if (itemStack2.is(itemStack1.getItem())) {
                itemStack2.grow(itemStack1.getCount());
            }

            if (itemStack.is(Blocks.WET_SPONGE.asItem()) && !inventory.get(1).isEmpty() && inventory.get(1).is(Items.BUCKET)) {
                inventory.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemStack.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        ItemStack itemStack = this.items.get(index);
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameTags(itemStack, stack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if (index == 0 && !flag) {
            this.cookingTotalTime = this.getSmeltTime(this.getChimneySpeedMultiplier());
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