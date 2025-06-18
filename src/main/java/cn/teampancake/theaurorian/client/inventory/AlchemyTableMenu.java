package cn.teampancake.theaurorian.client.inventory;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAMenus;
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

public class AlchemyTableMenu extends AbstractSimpleMenu {

    private final ContainerData containerData;

    @SuppressWarnings("unused")
    public AlchemyTableMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buf) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public AlchemyTableMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        this(containerId, inventory, access, new ItemStackHandler(5), new SimpleContainerData(2));
    }

    public AlchemyTableMenu(int containerId, Inventory inventory, ContainerLevelAccess access, IItemHandler itemHandler, ContainerData containerData) {
        super(TAMenus.ALCHEMY_TABLE_MENU.get(), containerId, inventory, access);
        checkContainerSize(inventory, 5);
        this.containerData = containerData;
        this.addSlot(new SlotItemHandler(itemHandler, 0, 20, 16));
        this.addSlot(new SlotItemHandler(itemHandler, 1, 44, 16));
        this.addSlot(new SlotItemHandler(itemHandler, 2, 69, 16));
        this.addSlot(new SlotItemHandler(itemHandler, 3, 137, 16));
        this.addSlot(new TAResultSlot(itemHandler, 4, 89, 42));
        this.addDataSlots(containerData);
    }

    public int getAlchemyTime() {
        return this.containerData.get(0);
    }

    public int getMaxAlchemyTime() {
        return this.containerData.get(1);
    }

    public boolean stillValid(@NotNull Player player) {
        return stillValid(this.access, player, TABlocks.ALCHEMY_TABLE.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();
            if (index > 35 && index <= 40) {
                if (!this.moveItemStackTo(itemStack1, 0, 35, Boolean.TRUE)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack1, 36, 40, Boolean.FALSE)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemStack;
    }

}