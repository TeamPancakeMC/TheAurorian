package cn.teampancake.theaurorian.common.blocks.entity;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.block.Block;

public class SilentWoodCraftingContainer extends CraftingMenu {

    private final Block block;
    private final ContainerLevelAccess access;

    public SilentWoodCraftingContainer(int id, Inventory inventory, ContainerLevelAccess access, Block block) {
        super(id, inventory, access);
        this.block = block;
        this.access = access;
    }

    protected static boolean isWithinUsableDistance(ContainerLevelAccess access, Player player, Block targetBlock) {
        return access.evaluate((world, pos) -> {
            double x = (double) pos.getX() + 0.5D;
            double y = (double) pos.getY() + 0.5D;
            double z = (double) pos.getZ() + 0.5D;
            return world.getBlockState(pos).getBlock() == targetBlock
                    && player.distanceToSqr(x, y, z) <= 64.0;
        }, Boolean.TRUE);
    }

    public boolean stillValid(Player player) {
        return isWithinUsableDistance(this.access, player, this.block);
    }

}