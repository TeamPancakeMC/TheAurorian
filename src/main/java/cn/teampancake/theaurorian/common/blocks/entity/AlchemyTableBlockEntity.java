package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.client.inventory.AlchemyTableMenu;
import cn.teampancake.theaurorian.common.items.crafting.AlchemyTableRecipe;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import cn.teampancake.theaurorian.common.registry.TARecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class AlchemyTableBlockEntity extends SimpleContainerBlockEntity {

    private int alchemyTime;
    private int maxAlchemyTime;
    private final ContainerData containerData = new Data();
    private final RecipeManager.CachedCheck<Container, ? extends AlchemyTableRecipe> quickCheck;

    public AlchemyTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.ALCHEMY_TABLE.get(), pos, blockState);
        this.quickCheck = RecipeManager.createCheck(TARecipes.ALCHEMY_TABLE_RECIPE.get());
        this.handler = new SimpleContainerBlockEntity.Handler(5);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AlchemyTableBlockEntity blockEntity) {
        if (!level.isClientSide()) {
            int t = 0;
            for (int i = 0; i < 4; i++) {
                t += blockEntity.handler.getStackInSlot(i).isEmpty() ? 0 : 1;
            }

            NonNullList<ItemStack> inventory = blockEntity.handler.getStacks();
            AlchemyTableRecipe recipe = t == 4 ? blockEntity.quickCheck.getRecipeFor(blockEntity, level).orElse(null) : null;
            if (blockEntity.canWork(level.registryAccess(), recipe, inventory, blockEntity.getMaxStackSize()) && recipe != null) {
                blockEntity.maxAlchemyTime = recipe.alchemyTime;
                blockEntity.alchemyTime++;
                setChanged(level, pos, state);
                if (blockEntity.alchemyTime > recipe.alchemyTime) {
                    blockEntity.startWork(level.registryAccess(), recipe,
                            inventory, blockEntity.getMaxStackSize());
                    blockEntity.maxAlchemyTime = 0;
                    blockEntity.alchemyTime = 0;
                    recipe.getInputAndAmount().forEach(ItemStack::shrink);
                    setChanged(level, pos, state);
                }
            } else {
                blockEntity.maxAlchemyTime = 0;
                blockEntity.alchemyTime = 0;
                setChanged(level, pos, state);
            }
        }
    }

    protected void startWork(RegistryAccess registryAccess, @Nullable Recipe<Container> recipe, NonNullList<ItemStack> inventory, int maxStackSize) {
        if (recipe instanceof AlchemyTableRecipe alchemyRecipe && this.canWork(registryAccess, alchemyRecipe, inventory, maxStackSize)) {
            ItemStack copyOfResultStack = alchemyRecipe.assemble(this, registryAccess);
            ItemStack resultStack = inventory.get(4);
            if (resultStack.isEmpty()) {
                inventory.set(4, copyOfResultStack.copy());
            } else if (resultStack.is(copyOfResultStack.getItem())) {
                resultStack.grow(copyOfResultStack.getCount());
            }

            alchemyRecipe.getInputAndAmount().forEach(ItemStack::shrink);
        }
    }

    protected boolean canWork(RegistryAccess registryAccess, @Nullable Recipe<Container> recipe, NonNullList<ItemStack> inventory, int maxStackSize) {
        int t = 0;
        for (int i = 0; i < 4; i++) {
            t += inventory.get(i).isEmpty() ? 0 : 1;
        }

        if (t == 4 && recipe != null) {
            ItemStack copyOfResultStack = recipe.assemble(this, registryAccess);
            if (copyOfResultStack.isEmpty()) {
                return false;
            } else {
                ItemStack resultStack = inventory.get(4);
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
    protected Component getDefaultName() {
        return Component.empty();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.alchemyTime = tag.getInt("AlchemyTime");
        this.maxAlchemyTime = tag.getInt("MaxAlchemyTime");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("AlchemyTime", this.alchemyTime);
        tag.putInt("MaxAlchemyTime", this.maxAlchemyTime);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new AlchemyTableMenu(containerId, inventory, this, this.containerData);
    }

    private class Data implements ContainerData {

        @Override
        public int get(int index) {
            if (index == 0) {
                return alchemyTime;
            } else if (index == 1) {
                return maxAlchemyTime;
            } else {
                return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                alchemyTime = value;
            } else if (index == 1) {
                maxAlchemyTime = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

    }

}