package cn.teampancake.theaurorian.common.blocks.entity;

import cn.teampancake.theaurorian.common.registry.TABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AurorianChestBlockEntity extends ChestBlockEntity {

    public AurorianChestBlockEntity(BlockPos pos, BlockState blockState) {
        super(TABlockEntityTypes.AURORIAN_CHEST.get(), pos, blockState);
        this.setItems(NonNullList.withSize(36, ItemStack.EMPTY));
    }

    @Override
    public int getContainerSize() {
        return 36;
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new ChestMenu(MenuType.GENERIC_9x4, id, player, this, 4);
    }

}