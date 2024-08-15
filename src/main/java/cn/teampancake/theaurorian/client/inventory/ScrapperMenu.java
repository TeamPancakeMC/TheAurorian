package cn.teampancake.theaurorian.client.inventory;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAItems;
import cn.teampancake.theaurorian.common.registry.TAMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ScrapperMenu extends AbstractSimpleMenu {

    private final ContainerData containerData;

    @SuppressWarnings("unused")
    public ScrapperMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buf) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public ScrapperMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        this(containerId, inventory, access, new ItemStackHandler(3), new SimpleContainerData(1));
    }

    public ScrapperMenu(int containerId, Inventory inventory, ContainerLevelAccess access, IItemHandler itemHandler, ContainerData containerData) {
        super(TAMenus.SCRAPPER_MENU.get(), containerId, inventory, access);
        checkContainerSize(inventory, 3);
        this.containerData = containerData;
        this.addSlot(new FirstSlot(itemHandler, 0, 80, 17));
        this.addSlot(new SecondSlot(itemHandler, 1, 40, 37));
        this.addSlot(new TAResultSlot(itemHandler, 2, 80, 58));
        this.addDataSlots(containerData);
    }

    public int getScrapTime() {
        return this.containerData.get(0);
    }

    public boolean stillValid(@NotNull Player player) {
        return stillValid(this.access, player, TABlocks.SCRAPPER.get());
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack copyOfSourceStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack sourceStack = slot.getItem();
            copyOfSourceStack = sourceStack.copy();
            if (index == 38) {
                if (!this.moveItemStackTo(sourceStack, 0, 35, Boolean.TRUE)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(sourceStack, copyOfSourceStack);
            } else if (index != 37 && index != 36) {
                if (sourceStack.isDamageableItem()) {
                    if (!this.moveItemStackTo(sourceStack, 36, 37, Boolean.FALSE)) {
                        return ItemStack.EMPTY;
                    }
                } else if (sourceStack.is(TAItems.CRYSTAL.get())) {
                    if (!this.moveItemStackTo(sourceStack, 37, 38, Boolean.FALSE)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 0 && index < 27) {
                    if (!this.moveItemStackTo(sourceStack, 26, 35, Boolean.FALSE)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 27 && index < 36 && !this.moveItemStackTo(sourceStack, 0, 27, Boolean.FALSE)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(sourceStack, 0, 35, Boolean.FALSE)) {
                return ItemStack.EMPTY;
            }

            if (sourceStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (sourceStack.getCount() == copyOfSourceStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, sourceStack);
        }

        return copyOfSourceStack;
    }

    private static class FirstSlot extends SlotItemHandler {

        public FirstSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return stack.isDamageableItem();
        }

    }

    private static class SecondSlot extends SlotItemHandler {

        public SecondSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return stack.is(TAItems.CRYSTAL.get());
        }

    }

}