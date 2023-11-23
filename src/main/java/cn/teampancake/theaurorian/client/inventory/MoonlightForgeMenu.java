package cn.teampancake.theaurorian.client.inventory;

import cn.teampancake.theaurorian.common.blocks.entity.MoonlightForgeBlockEntity;
import cn.teampancake.theaurorian.registry.TAMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class MoonlightForgeMenu extends AbstractSimpleMenu {

    private final MoonlightForgeBlockEntity moonlightForge;

    public MoonlightForgeMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public MoonlightForgeMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(TAMenuTypes.MOONLIGHT_FORGE_MENU.get(), containerId, inventory);
        checkContainerSize(inventory, 3);
        this.moonlightForge = (MoonlightForgeBlockEntity) blockEntity;
        this.moonlightForge.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
            this.addSlot(new SlotItemHandler(itemHandler, 0, 22, 35));
            this.addSlot(new SlotItemHandler(itemHandler, 1, 84, 35));
            this.addSlot(new TAResultSlot(itemHandler, 2, 142, 35));
        });
    }

    @Override
    public MoonlightForgeBlockEntity getBlockEntity() {
        return this.moonlightForge;
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