package cn.teampancake.theaurorian.common.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class PressurePlateBlockWithBase extends PressurePlateBlock implements IHasBaseBlock {

    private final Block base;

    public PressurePlateBlockWithBase(Block base, Properties properties, BlockSetType type) {
        super(type, properties);
        this.base = base;
    }

    @Override
    public Block getBase() {
        return this.base;
    }

}