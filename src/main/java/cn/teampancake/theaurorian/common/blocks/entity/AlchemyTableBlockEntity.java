package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.client.inventory.AlchemyTableMenu;
import cn.teampancake.theaurorian.common.items.crafting.AlchemyTableRecipe;
import cn.teampancake.theaurorian.common.items.crafting.AlchemyTableRecipeInput;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import cn.teampancake.theaurorian.common.registry.TARecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class AlchemyTableBlockEntity extends SimpleContainerBlockEntity {

    private int alchemyTime;
    private int maxAlchemyTime;
    private final ContainerData containerData = new Data();
    private final RecipeManager.CachedCheck<AlchemyTableRecipeInput, ? extends AlchemyTableRecipe> quickCheck;

    public AlchemyTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.ALCHEMY_TABLE.get(), pos, blockState);
        this.quickCheck = RecipeManager.createCheck(TARecipes.ALCHEMY_TABLE_RECIPE.get());
        this.handler = new Handler(5);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AlchemyTableBlockEntity blockEntity) {
        if (!level.isClientSide()) {
            AlchemyTableRecipe recipe = blockEntity.checkBrewRecipe();
            if (recipe != null) {
                blockEntity.brew(recipe, pos, state);
            } else {
                blockEntity.maxAlchemyTime = 0;
                blockEntity.alchemyTime = 0;
                setChanged(level, pos, state);
            }
        }
    }

    protected void brew(AlchemyTableRecipe recipe, BlockPos pos, BlockState state) {
        if (this.level != null) {
            this.maxAlchemyTime = recipe.alchemyTime();
            this.alchemyTime++;
            if (this.alchemyTime > recipe.alchemyTime()) {
                this.handler.insertItem(4, recipe.assemble(this.getRecipeInput(), level.registryAccess()), false);
                recipe.consumeIngredients(this);
                this.maxAlchemyTime = 0;
                this.alchemyTime = 0;
            }

            setChanged(this.level, pos, state);
        }
    }

    @Nullable
    protected AlchemyTableRecipe checkBrewRecipe() {
        if (this.level != null) {
            RecipeHolder<? extends AlchemyTableRecipe> holder = this.quickCheck.getRecipeFor(this.getRecipeInput(), this.level).orElse(null);
            return holder != null ? holder.value() : null;
        }

        return null;
    }

    private AlchemyTableRecipeInput getRecipeInput() {
        ItemStack input1 = this.getItem(0);
        ItemStack input2 = this.getItem(1);
        ItemStack input3 = this.getItem(2);
        ItemStack material = this.getItem(3);
        return new AlchemyTableRecipeInput(input1, input2, input3, material);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.alchemyTime = tag.getInt("AlchemyTime");
        this.maxAlchemyTime = tag.getInt("MaxAlchemyTime");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("AlchemyTime", this.alchemyTime);
        tag.putInt("MaxAlchemyTime", this.maxAlchemyTime);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        ContainerLevelAccess access = ContainerLevelAccess.create(Objects.requireNonNull(this.level), this.worldPosition);
        return new AlchemyTableMenu(containerId, inventory, access, this.handler, this.containerData);
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