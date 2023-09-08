package cn.teampancake.theaurorian.client.inventory;

import cn.teampancake.theaurorian.common.blocks.entity.ScrapperBlockEntity;
import cn.teampancake.theaurorian.registry.ModItems;
import cn.teampancake.theaurorian.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;

public class ScrapperMenu extends AbstractSimpleMenu {

    public final ScrapperBlockEntity scrapper;

    public ScrapperMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public ScrapperMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(ModMenuTypes.SCRAPPER_MENU.get(), containerId, inventory);
        checkContainerSize(inventory, 3);
        this.scrapper = (ScrapperBlockEntity) blockEntity;
        this.scrapper.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
            this.addSlot(new FirstSlot(this, itemHandler, 0, 80, 17));
            this.addSlot(new SecondSlot(this, itemHandler, 1, 40, 37));
            this.addSlot(new ResultSlot(itemHandler, 2, 80, 58));
        });
    }

    @Override
    protected BaseContainerBlockEntity getBlockEntity() {
        return this.scrapper;
    }

    @Override
    protected boolean slot1ItemFlag(ItemStack stack) {
        return stack.isDamageableItem();
    }

    @Override
    protected boolean slot2ItemFlag(ItemStack stack) {
        return stack.is(ModItems.CRYSTAL.get());
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack prevItem = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotItem = slot.getItem();
            prevItem = slotItem.copy();
            if (index == 2 ) {
                if (!this.moveItemStackTo(slotItem, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotItem, prevItem);
            } else if(index != 0 && index != 1){
                if (slot1ItemFlag(slotItem)) {
                    if (!this.moveItemStackTo(slotItem, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slot2ItemFlag(slotItem)) {
                    if (!this.moveItemStackTo(slotItem, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                }else if (index >= 3 && index < 30) {
                    if (!this.moveItemStackTo(slotItem, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.moveItemStackTo(slotItem, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }else if (!this.moveItemStackTo(slotItem, 0, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotItem, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (slotItem.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotItem.getCount() == prevItem.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotItem);
        }
        return prevItem;
    }

}