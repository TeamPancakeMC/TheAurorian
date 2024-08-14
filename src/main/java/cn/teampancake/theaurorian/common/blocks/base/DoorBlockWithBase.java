package cn.teampancake.theaurorian.common.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class DoorBlockWithBase extends DoorBlock {

    private final Block base;

    public DoorBlockWithBase(Block base, Properties properties, BlockSetType type) {
        super(type, properties);
        this.base = base;
    }

    public Block getBase() {
        return this.base;
    }

}