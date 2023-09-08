package cn.teampancake.theaurorian.client.inventory;

import cn.teampancake.theaurorian.common.blocks.entity.MoonlightForgeBlockEntity;
import cn.teampancake.theaurorian.registry.ModBlocks;
import cn.teampancake.theaurorian.registry.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class MoonlightForgeMenu extends AbstractContainerMenu {

    private final MoonlightForgeBlockEntity moonlightForge;
    private final Level level;

    public MoonlightForgeMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public MoonlightForgeMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(ModMenuTypes.MOONLIGHT_FORGE_MENU.get(), containerId);
        checkContainerSize(inventory, 3);
        this.moonlightForge = (MoonlightForgeBlockEntity) blockEntity;
        this.level = inventory.player.level();
        this.moonlightForge.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
            this.addSlot(new SlotItemHandler(itemHandler, 0, 22, 35));
            this.addSlot(new SlotItemHandler(itemHandler, 1, 84, 35));
            this.addSlot(new ModResultSlot(itemHandler, 2, 142, 35));
        });
        this.addPlayerInventory(inventory);
        this.addPlayerHotBar(inventory);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        Block block = ModBlocks.MOONLIGHT_FORGE.get();
        BlockPos pos = this.moonlightForge.getBlockPos();
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

}