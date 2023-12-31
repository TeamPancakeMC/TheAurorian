package cn.teampancake.theaurorian.common.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class TrapDoorBlockWithBase extends TrapDoorBlock implements IHasBaseBlock {

    private final Block base;

    public TrapDoorBlockWithBase(Block base, Properties properties, BlockSetType type) {
        super(properties, type);
        this.base = base;
    }

    public Block getBase() {
        return this.base;
    }

}