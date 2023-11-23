package cn.teampancake.theaurorian.client.inventory;

import cn.teampancake.theaurorian.common.blocks.entity.ScrapperBlockEntity;
import cn.teampancake.theaurorian.registry.TAItems;
import cn.teampancake.theaurorian.registry.TAMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ScrapperMenu extends AbstractSimpleMenu {

    private final ScrapperBlockEntity scrapper;
    private final ContainerData containerData;

    public ScrapperMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(1));
    }

    public ScrapperMenu(int containerId, Inventory inventory, BlockEntity blockEntity, ContainerData containerData) {
        super(TAMenuTypes.SCRAPPER_MENU.get(), containerId, inventory);
        checkContainerSize(inventory, 3);
        this.scrapper = (ScrapperBlockEntity) blockEntity;
        this.containerData = containerData;
        this.scrapper.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
            this.addSlot(new FirstSlot(itemHandler, 0, 80, 17));
            this.addSlot(new SecondSlot(itemHandler, 1, 40, 37));
            this.addSlot(new ResultSlot(itemHandler, 2, 80, 58));
        });
        this.addDataSlots(containerData);
    }

    public int getScrapTime() {
        return this.containerData.get(0);
    }

    @Override
    public ScrapperBlockEntity getBlockEntity() {
        return this.scrapper;
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