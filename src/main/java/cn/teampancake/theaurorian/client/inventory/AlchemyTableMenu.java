package cn.teampancake.theaurorian.client.inventory;

import cn.teampancake.theaurorian.common.blocks.entity.AlchemyTableBlockEntity;
import cn.teampancake.theaurorian.common.registry.TAMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class AlchemyTableMenu extends AbstractSimpleMenu {

    private final AlchemyTableBlockEntity alchemyTable;
    private final ContainerData containerData;

    public AlchemyTableMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(4));
    }

    public AlchemyTableMenu(int containerId, Inventory inventory, BlockEntity blockEntity, ContainerData containerData) {
        super(TAMenuTypes.ALCHEMY_TABLE_MENU.get(), containerId, inventory);
        checkContainerSize(inventory, 5);
        this.alchemyTable = (AlchemyTableBlockEntity) blockEntity;
        this.containerData = containerData;
        this.alchemyTable.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
            this.addSlot(new SlotItemHandler(itemHandler, 0, 20, 16));
            this.addSlot(new SlotItemHandler(itemHandler, 1, 44, 16));
            this.addSlot(new SlotItemHandler(itemHandler, 2, 69, 16));
            this.addSlot(new SlotItemHandler(itemHandler, 3, 89, 42));
            this.addSlot(new TAResultSlot(itemHandler, 4, 137, 16));
        });
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
        return ItemStack.EMPTY;
    }

}