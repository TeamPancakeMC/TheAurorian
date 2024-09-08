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

public class MoonlightForgeMenu extends AbstractSimpleMenu {

    private final ContainerData containerData;

    @SuppressWarnings("unused")
    public MoonlightForgeMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buf) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public MoonlightForgeMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        this(containerId, inventory, access, new ItemStackHandler(3), new SimpleContainerData(4));
    }

    public MoonlightForgeMenu(int containerId, Inventory inventory, ContainerLevelAccess access, IItemHandler itemHandler, ContainerData containerData) {
        super(TAMenus.MOONLIGHT_FORGE_MENU.get(), containerId, inventory, access);
        checkContainerSize(inventory, 3);
        this.containerData = containerData;
        this.addSlot(new SlotItemHandler(itemHandler, 0, 22, 35));
        this.addSlot(new SlotItemHandler(itemHandler, 1, 84, 35));
        this.addSlot(new TAResultSlot(itemHandler, 2, 142, 35));
        this.addDataSlots(containerData);
    }

    public int getCraftProgress() {
        return this.containerData.get(0);
    }
    
    public boolean hasMoonlight() {
        return this.containerData.get(1) == 1;
    }
    
    public boolean isCrafting() {
        return this.containerData.get(2) == 1;
    }
    
    public boolean isPowered() {
        return this.containerData.get(3) == 1;
    }

    public boolean stillValid(@NotNull Player player) {
        return stillValid(this.access, player, TABlocks.MOONLIGHT_FORGE.get());
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();
            if (index >= 36 && index < 39) {
                if (!this.moveItemStackTo(itemStack1, 0, 35, Boolean.TRUE)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack1, 36, 38, Boolean.FALSE)) {
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