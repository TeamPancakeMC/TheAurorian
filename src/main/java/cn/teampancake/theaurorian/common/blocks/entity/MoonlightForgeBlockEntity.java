package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MoonlightForgeBlockEntity extends BaseContainerBlockEntity {

    private LazyOptional<IItemHandler> lazyOptional = LazyOptional.empty();
    private final MoonlightForgeBlockEntity.Handler handler = new Handler();
    private boolean hasMoonLight;
    private boolean isCrafting;
    private boolean isPowered;
    private int craftProgress;

    public MoonlightForgeBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.MOONLIGHT_FORGE.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MoonlightForgeBlockEntity blockEntity) {
        if (!level.isClientSide) {
            if (!level.hasNeighborSignal(pos)) {
                blockEntity.hasMoonLight = level.canSeeSky(pos) && !level.isDay();
                blockEntity.isPowered = false;
                if (blockEntity.isCrafting) {
                    float heightPercent = (float) pos.getY() / (float) level.getHeight();
                    int tickInterval = 2;
                    if (heightPercent <= 0.25) {
                        tickInterval += 16;
                    } else if (heightPercent > 0.25 && heightPercent <= 0.5) {
                        tickInterval += 8;
                    } else if (heightPercent > 0.5 && heightPercent <= 0.75) {
                        tickInterval += 4;
                    }
                    if (level.getGameTime() % tickInterval == 0) {
                        int newVal = blockEntity.craftProgress + 1;
                        if (newVal >= 100) {
                            blockEntity.stopCrafting();
                            blockEntity.doCraft();
                        } else {
                            blockEntity.craftProgress = newVal;
                        }
                    }
                }

                ItemStack inputStack1 = blockEntity.handler.getStackInSlot(0);
                ItemStack inputStack2 = blockEntity.handler.getStackInSlot(1);
                ItemStack outputStack = blockEntity.handler.getStackInSlot(2);
                ItemStack recipeOutput = blockEntity.getRecipeOutput(inputStack1, inputStack2);
                if (blockEntity.hasMoonLight) {
                    boolean flag = outputStack.getCount() + recipeOutput.getCount() <= outputStack.getMaxStackSize();
                    if (!outputStack.isEmpty() || (outputStack.isStackable() && recipeOutput.is(outputStack.getItem()) && flag)) {
                        if (!blockEntity.isCrafting) {
                            blockEntity.isCrafting = true;
                            blockEntity.updateClient();
                        }
                    } else {
                        blockEntity.stopCrafting();
                    }
                } else if (blockEntity.isCrafting) {
                    blockEntity.stopCrafting();
                }
            } else {
                blockEntity.isPowered = true;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isCrafting() {
        return !this.isPowered && this.hasMoonLight && this.craftProgress > 0;
    }

    public ItemStack getRecipeOutput(ItemStack input1, ItemStack input2) {
        //Todo: Should finish the migration of recipes.
        return ItemStack.EMPTY;
    }

    private void doCraft() {
        ItemStack output = null;
        ItemStack input1 = this.handler.getStackInSlot(0);
        ItemStack input2 = this.handler.getStackInSlot(1);
        ItemStack req1 = null;
        ItemStack req2 = null;
        //Todo: Should finish the migration of recipes.
    }

    private void stopCrafting() {
        this.isCrafting = false;
        this.craftProgress = 0;
        this.updateClient();
    }

    private void updateClient() {
        this.setChanged();
        if (this.level != null) {
            BlockState state = this.level.getBlockState(this.worldPosition);
            this.level.sendBlockUpdated(this.worldPosition, state, state, 3);
        }
    }

    @NotNull @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (side != null && cap == ForgeCapabilities.ITEM_HANDLER) {
            return this.lazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyOptional = LazyOptional.of(() -> this.handler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.lazyOptional.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", this.handler.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.handler.deserializeNBT(tag);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("theaurorian.container.moonlight_forge");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return this.handler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.handler.getStacks()) {
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.handler.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        return ContainerHelper.removeItem(this.handler.getStacks(), pIndex, pCount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.handler.getStacks(), slot);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {

    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.handler.getStacks().clear();
    }

    private class Handler extends ItemStackHandler {

        public Handler() {
            super(3);
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        public NonNullList<ItemStack> getStacks() {
            return this.stacks;
        }

    }

}