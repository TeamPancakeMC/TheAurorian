package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.inventory.ScrapperMenu;
import cn.teampancake.theaurorian.common.items.crafting.ScrapperRecipe;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TARecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class ScrapperBlockEntity extends SimpleContainerBlockEntity implements WorldlyContainer {

    public int scrapTime;
    private final ContainerData containerData = new Data();
    private final RecipeManager.CachedCheck<Container, ? extends ScrapperRecipe> quickCheck;
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};

    public ScrapperBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.SCRAPPER.get(), pos, blockState);
        this.quickCheck = RecipeManager.createCheck(TARecipes.SCRAPPER_RECIPE.get());
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ScrapperBlockEntity blockEntity) {
        if (!level.isClientSide()) {
            ItemStack ingredient = blockEntity.handler.getStackInSlot(0);
            ItemStack consumables = blockEntity.handler.getStackInSlot(1);
            NonNullList<ItemStack> inventory = blockEntity.handler.getStacks();
            ScrapperRecipe recipe = !ingredient.isEmpty() ? blockEntity.quickCheck.getRecipeFor(blockEntity, level).orElse(null) : null;
            if (blockEntity.canWork(level.registryAccess(), recipe, inventory, blockEntity.getMaxStackSize()) && consumables.getCount() > 0) {
                blockEntity.scrapTime++;
                setChanged(level, pos, state);
                if (blockEntity.scrapTime > 100) {
                    blockEntity.startWork(level.registryAccess(), recipe, inventory, blockEntity.getMaxStackSize());
                    blockEntity.scrapTime = 0;
                    consumables.shrink(1);
                    setChanged(level, pos, state);
                }
            } else {
                blockEntity.scrapTime = 0;
                setChanged(level, pos, state);
            }
        }
    }

    protected void startWork(RegistryAccess registryAccess, @Nullable Recipe<Container> recipe, NonNullList<ItemStack> inventory, int maxStackSize) {
        if (recipe != null && this.canWork(registryAccess, recipe, inventory, maxStackSize)) {
            ItemStack copyOfResultStack = recipe.assemble(this, registryAccess);
            ItemStack itemStack = inventory.get(0), resultStack = inventory.get(2);
            if (resultStack.isEmpty()) {
                inventory.set(2, copyOfResultStack.copy());
            } else if (resultStack.is(copyOfResultStack.getItem())) {
                resultStack.grow(copyOfResultStack.getCount());
            }
            itemStack.shrink(1);
        }
    }

    protected boolean canWork(RegistryAccess registryAccess, @Nullable Recipe<Container> recipe, NonNullList<ItemStack> inventory, int maxStackSize) {
        if (!inventory.get(0).isEmpty() && recipe != null) {
            ItemStack copyOfResultStack = recipe.assemble(this, registryAccess);
            if (copyOfResultStack.isEmpty()) {
                return false;
            } else {
                ItemStack resultStack = inventory.get(2);
                int stackCount = resultStack.getCount() + copyOfResultStack.getCount();
                if (resultStack.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItem(resultStack, copyOfResultStack)) {
                    return false;
                } else if (stackCount < maxStackSize && stackCount <= resultStack.getMaxStackSize()) {
                    return true;
                } else {
                    return stackCount <= copyOfResultStack.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.scrapTime = tag.getInt("ScrapTime");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("ScrapTime", this.scrapTime);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index == 1) {
            return stack.is(TAItems.CRYSTAL.get());
        } else if (index == 0) {
            return stack.isDamageableItem();
        } else {
            return false;
        }
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return side == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return this.canPlaceItem(index, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index == 2;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ScrapperMenu(containerId, inventory, this, this.containerData);
    }

    private class Data implements ContainerData {

        @Override
        public int get(int index) {
            return ScrapperBlockEntity.this.scrapTime;
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                ScrapperBlockEntity.this.scrapTime = value;
            }
        }

        @Override
        public int getCount() {
            return 1;
        }

    }

}