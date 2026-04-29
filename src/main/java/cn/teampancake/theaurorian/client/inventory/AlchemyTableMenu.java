package cn.teampancake.theaurorian.client.inventory;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAMenus;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AlchemyTableMenu extends AbstractSimpleMenu {

    private final ContainerData containerData;
    private final BlockPos blockPos;
    public List<ItemStack> materials = Lists.newArrayList();

    public AlchemyTableMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buf) {
        this(containerId, inventory, ContainerLevelAccess.NULL, buf);
    }

    public AlchemyTableMenu(int containerId, Inventory inventory, ContainerLevelAccess access, RegistryFriendlyByteBuf buf) {
        this(containerId, inventory, access, new ItemStackHandler(14), new SimpleContainerData(4), buf.readBlockPos());
    }

    public AlchemyTableMenu(
            int containerId, Inventory inventory, ContainerLevelAccess access, ItemStackHandler itemHandler,
            ContainerData containerData, BlockPos blockPos) {
        super(TAMenus.ALCHEMY_TABLE_MENU.get(), containerId, inventory, access);
        this.addSlot(new SlotItemHandler(itemHandler, 0, 58, 21));
        this.addSlot(new SlotItemHandler(itemHandler, 1, 82, 21));
        this.addSlot(new SlotItemHandler(itemHandler, 2, 107, 21));
        this.addSlot(new SlotItemHandler(itemHandler, 3, 175, 21));
        this.addSlot(new TAResultSlot(itemHandler, 4, 127, 47));
        this.addInputMaterialDisplaySlot(itemHandler);
        this.addDataSlots(containerData);
        this.containerData = containerData;
        this.blockPos = blockPos;
    }

    public ContainerData getContainerData() {
        return this.containerData;
    }

    public int getLiquidLevel() {
        return this.containerData.get(2);
    }

    public int getLiquidData() {
        return this.containerData.get(3);
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public List<ItemStack> getMaterials() {
        return this.materials;
    }

    @Override
    protected void addPlayerInventory(Inventory inventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                int index = l + i * 9 + 9;
                int x = 46 + l * 18;
                int y = 89 + i * 18;
                this.addSlot(new Slot(inventory, index, x, y));
            }
        }
    }

    @Override
    protected void addPlayerHotBar(Inventory inventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 46 + i * 18, 147));
        }
    }

    private void addInputMaterialDisplaySlot(IItemHandler itemHandler) {
        for (int i = 5; i < 14; i++) {
            this.addSlot(new DisplayOnlySlot(itemHandler, i, 15, i * 18 - 83));
        }
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

    private static class DisplayOnlySlot extends SlotItemHandler {

        public DisplayOnlySlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }

        @Override
        public boolean mayPickup(Player playerIn) {
            return false;
        }

        @Override
        public boolean isFake() {
            return true;
        }

    }

}