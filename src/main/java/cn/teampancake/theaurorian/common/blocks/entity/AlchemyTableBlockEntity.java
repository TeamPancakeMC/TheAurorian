package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.client.inventory.AlchemyTableMenu;
import cn.teampancake.theaurorian.common.blocks.AlchemyTable;
import cn.teampancake.theaurorian.common.blocks.state.properties.AlchemyTablePart;
import cn.teampancake.theaurorian.common.items.crafting.AlchemyTableRecipe;
import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import cn.teampancake.theaurorian.common.registry.TARecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AlchemyTableBlockEntity extends SimpleContainerBlockEntity {

    private int alchemyTime;
    private int maxAlchemyTime;
    private final ContainerData containerData = new Data();
    private final RecipeManager.CachedCheck<AlchemyTableBlockEntity, ? extends AlchemyTableRecipe> quickCheck;

    public AlchemyTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.ALCHEMY_TABLE.get(), pos, blockState);
        this.quickCheck = RecipeManager.createCheck(TARecipes.ALCHEMY_TABLE_RECIPE.get());
        this.handler = new SimpleContainerBlockEntity.Handler(5);
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
        this.maxAlchemyTime = recipe.alchemyTime;
        this.alchemyTime++;
        if (this.alchemyTime > recipe.alchemyTime) {
            this.handler.insertItem(4, recipe.assemble(this, level.registryAccess()),false);
            recipe.consumeIngredients(this);
            this.maxAlchemyTime = 0;
            this.alchemyTime = 0;
        }
        setChanged(level, pos, state);
    }

    @Nullable
    protected AlchemyTableRecipe checkBrewRecipe() {
        return this.quickCheck.getRecipeFor(this, this.level).orElse(null);
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

    @NotNull @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        BlockState state = level.getBlockState(getBlockPos());
        AlchemyTablePart part = state.getValue(AlchemyTable.PART);
        if (part == AlchemyTablePart.LEFT) {
            BlockPos relative = getBlockPos().relative(AlchemyTable.getNeighbourDirection(part, state.getValue(AlchemyTable.FACING)));
            BlockState relativeState = level.getBlockState(relative);
            if (relativeState.is(state.getBlock()) && relativeState.getValue(AlchemyTable.PART) == AlchemyTablePart.RIGHT && level.getBlockEntity(relative) instanceof AlchemyTableBlockEntity blockEntity2) {
                return blockEntity2.getCapability(cap,side);
            }
        }
        if (side != null && cap == ForgeCapabilities.ITEM_HANDLER) {
            return this.lazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

}