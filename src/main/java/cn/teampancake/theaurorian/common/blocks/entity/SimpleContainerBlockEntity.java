package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

//TODO: 能力系统大改，原来的不能用了，要修改。
public abstract class SimpleContainerBlockEntity extends BaseContainerBlockEntity{

    protected Handler handler;

    protected SimpleContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.handler.deserializeNBT(registries, tag);
        ContainerHelper.loadAllItems(tag, this.handler.getStacks(), registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", this.handler.serializeNBT(registries));
        ContainerHelper.saveAllItems(tag, this.handler.getStacks(), registries);
    }

    @Override
    protected Component getDefaultName() {
        Block block = this.getBlockState().getBlock();
        String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
        return Component.translatable(AurorianMod.MOD_ID + ".container." + path);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.handler.getStacks();
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.handler.setStacks(items);
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
    public ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(this.handler.getStacks(), index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.handler.getStacks(), slot);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        this.handler.getStacks().set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
    }

    @Override
    public void clearContent() {
        this.handler.getStacks().clear();
    }

    protected class Handler extends ItemStackHandler {

        public Handler(int size) {
            super(size);
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        public NonNullList<ItemStack> getStacks() {
            return this.stacks;
        }

        public void setStacks(NonNullList<ItemStack> items) {
            this.stacks = items;
        }

    }

}