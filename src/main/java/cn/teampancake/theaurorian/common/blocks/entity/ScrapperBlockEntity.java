package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.client.inventory.ScrapperMenu;
import cn.teampancake.theaurorian.common.items.crafting.ScrapperRecipe;
import cn.teampancake.theaurorian.registry.ModBlockEntityTypes;
import cn.teampancake.theaurorian.registry.ModItems;
import cn.teampancake.theaurorian.registry.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

@SuppressWarnings("NotNullFieldNotInitialized")
public class ScrapperBlockEntity extends SimpleContainerBlockEntity {

    public int scrapTime;
    private Item ingredient;
    private final RecipeManager.CachedCheck<Container, ? extends ScrapperRecipe> quickCheck;

    public ScrapperBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.SCRAPPER.get(), pos, blockState);
        this.quickCheck = RecipeManager.createCheck(ModRecipes.SCRAPPER_RECIPE.get());
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ScrapperBlockEntity blockEntity) {
        ItemStack ingredient = blockEntity.handler.getStackInSlot(0);
        ItemStack consumables = blockEntity.handler.getStackInSlot(1);
        if (consumables.is(ModItems.CRYSTAL.get())) {
            consumables.shrink(1);
            setChanged(level, pos, state);
        }
        NonNullList<ItemStack> inventory = blockEntity.handler.getStacks();
        ScrapperRecipe recipe = !ingredient.isEmpty() ? blockEntity.quickCheck.getRecipeFor(blockEntity, level).orElse(null) : null;
        boolean flag1 = blockEntity.canScrap(level.registryAccess(), recipe, inventory, blockEntity.getMaxStackSize());
        if (blockEntity.scrapTime > 0) {
            if (--blockEntity.scrapTime == 0 && flag1) {
                blockEntity.doScrap(level.registryAccess(), recipe, inventory, blockEntity.getMaxStackSize());
                setChanged(level, pos, state);
            } else if (!flag1 || !ingredient.is(blockEntity.ingredient)) {
                blockEntity.scrapTime = 0;
                setChanged(level, pos, state);
            }
        } else if (flag1 && consumables.getCount() > 0) {
            blockEntity.scrapTime = 100;
            blockEntity.ingredient = ingredient.getItem();
            setChanged(level, pos, state);
        }
    }

    private boolean canScrap(RegistryAccess registryAccess, @Nullable ScrapperRecipe recipe, NonNullList<ItemStack> inventory, int maxStackSize) {
        if (!inventory.get(0).isEmpty() && recipe != null) {
            ItemStack stack1 = recipe.assemble(this, registryAccess);
            if (stack1.isEmpty()) {
                return false;
            } else {
                ItemStack stack2 = inventory.get(2);
                int stackCount = stack2.getCount() + stack1.getCount();
                if (stack2.isEmpty()) {
                    return true;
                } else if (ItemStack.isSameItem(stack2, stack1)) {
                    return false;
                } else if (stackCount < maxStackSize && stackCount <= stack2.getMaxStackSize()) {
                    return true;
                } else {
                    return stackCount <= stack1.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    private void doScrap(RegistryAccess registryAccess, @Nullable ScrapperRecipe recipe, NonNullList<ItemStack> inventory, int maxStackSize) {
        if (recipe != null && this.canScrap(registryAccess, recipe, inventory, maxStackSize)) {
            ItemStack stack2 = recipe.assemble(this, registryAccess);
            ItemStack stack1 = inventory.get(0), stack3 = inventory.get(2);
            if (stack3.isEmpty()) {
                inventory.set(2, stack2.copy());
            } else if (stack3.is(stack2.getItem())) {
                stack3.grow(stack2.getCount());
            }
            stack1.shrink(1);
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
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ScrapperMenu(containerId, inventory, this);
    }

}