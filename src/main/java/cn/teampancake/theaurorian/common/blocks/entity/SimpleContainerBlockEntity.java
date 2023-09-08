package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class SimpleContainerBlockEntity extends BaseContainerBlockEntity {

    protected LazyOptional<IItemHandler> lazyOptional = LazyOptional.empty();
    protected final SimpleContainerBlockEntity.Handler handler = new SimpleContainerBlockEntity.Handler();

    protected SimpleContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
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
        this.lazyOptional = LazyOptional.of(() -> this.handler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.lazyOptional.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", this.handler.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.handler.deserializeNBT(tag);
    }

    @Override
    protected Component getDefaultName() {
        Block block = this.getBlockState().getBlock();
        String path = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
        return Component.translatable(AurorianMod.MOD_ID + ".container." + path);
    }

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

    protected class Handler extends ItemStackHandler {

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