package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.entity.SilentWoodCraftingContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SilentWoodCraftingTable extends CraftingTableBlock {

    private static final Component GUI_TITLE = Component.translatable("container.crafting");

    public SilentWoodCraftingTable() {
        super(Properties.copy(Blocks.CRAFTING_TABLE));
    }

    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((id, inventory, entity) -> new SilentWoodCraftingContainer(id, inventory, ContainerLevelAccess.create(level, pos), this), GUI_TITLE);
    }

}