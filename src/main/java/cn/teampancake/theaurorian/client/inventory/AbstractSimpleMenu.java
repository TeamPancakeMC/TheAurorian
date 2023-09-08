package cn.teampancake.theaurorian.client.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractSimpleMenu extends AbstractContainerMenu {

    protected final Level level;

    protected AbstractSimpleMenu(@Nullable MenuType<?> menuType, int containerId, Inventory inventory) {
        super(menuType, containerId);
        this.level = inventory.player.level();
        this.addPlayerInventory(inventory);
        this.addPlayerHotBar(inventory);
    }

    abstract protected BaseContainerBlockEntity getBlockEntity();

    abstract protected boolean slot1ItemFlag(ItemStack stack);

    abstract protected boolean slot2ItemFlag(ItemStack stack);

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot sourceSlot = slots.get(index);
        int slotSize = this.slots.size();
        int containerSize = this.getBlockEntity().getContainerSize();
        if (sourceSlot.hasItem()) {
            ItemStack sourceStack = sourceSlot.getItem();
            itemStack = sourceStack.copy();
            if (index == 2) {
                if (!this.moveItemStackTo(sourceStack, containerSize, slotSize, Boolean.TRUE)) {
                    return ItemStack.EMPTY;
                }

                sourceSlot.onQuickCraft(sourceStack, itemStack);
            } else if (index != 0 && index != 1) {
                if (this.slot1ItemFlag(sourceStack)) {
                    if (!this.moveItemStackTo(sourceStack, (0), (1), Boolean.FALSE)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.slot2ItemFlag(sourceStack)) {
                    if (!this.moveItemStackTo(sourceStack, (1), (2), Boolean.FALSE)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= containerSize && index < 30) {
                    if (!this.moveItemStackTo(sourceStack, (30), slotSize, Boolean.FALSE)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < slotSize) {
                    if (!this.moveItemStackTo(sourceStack, containerSize, (30), Boolean.FALSE)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(sourceStack, containerSize, slotSize, Boolean.FALSE)) {
                return ItemStack.EMPTY;
            }

            if (sourceStack.isEmpty()) {
                sourceSlot.set(ItemStack.EMPTY);
            } else {
                sourceSlot.setChanged();
            }

            if (sourceStack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            sourceSlot.onTake(player, sourceStack);
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        Block block = this.getBlockEntity().getBlockState().getBlock();
        BlockPos pos = this.getBlockEntity().getBlockPos();
        return stillValid(ContainerLevelAccess.create(this.level, pos), player, block);
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                int index = l + i * 9 + 9;
                int x = 8 + l * 18;
                int y = 84 + i * 18;
                this.addSlot(new Slot(inventory, index, x, y));
            }
        }
    }

    private void addPlayerHotBar(Inventory inventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }

    protected static class FirstSlot extends SlotItemHandler {

        private final AbstractSimpleMenu menu;

        public FirstSlot(AbstractSimpleMenu menu, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
            this.menu = menu;
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return this.menu.slot1ItemFlag(stack);
        }

    }

    protected static class SecondSlot extends SlotItemHandler {

        private final AbstractSimpleMenu menu;

        public SecondSlot(AbstractSimpleMenu menu, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
            this.menu = menu;
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return this.menu.slot2ItemFlag(stack);
        }

    }

    protected static class ResultSlot extends SlotItemHandler {

        public ResultSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return false;
        }

    }

}