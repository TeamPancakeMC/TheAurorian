package cn.teampancake.theaurorian.client.inventory;

import cn.teampancake.theaurorian.common.blocks.entity.AlchemyTableBlockEntity;
import cn.teampancake.theaurorian.common.registry.TAMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class AlchemyTableMenu extends AbstractSimpleMenu {

    private final AlchemyTableBlockEntity alchemyTable;
    private final ContainerData containerData;

    public AlchemyTableMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new ItemStackHandler(5), new SimpleContainerData(4));
    }

    public AlchemyTableMenu(int containerId, Inventory inventory, BlockEntity blockEntity, IItemHandler itemHandler, ContainerData containerData) {
        super(TAMenus.ALCHEMY_TABLE_MENU.get(), containerId, inventory);
        checkContainerSize(inventory, 5);
        this.alchemyTable = (AlchemyTableBlockEntity) blockEntity;
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

    @Override
    public AlchemyTableBlockEntity getBlockEntity() {
        return this.alchemyTable;
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