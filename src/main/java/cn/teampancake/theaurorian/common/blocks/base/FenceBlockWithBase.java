package cn.teampancake.theaurorian.common.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;

public class FenceBlockWithBase extends FenceBlock implements IHasBaseBlock {

    private final Block base;

    public FenceBlockWithBase(Block base, Properties properties) {
        super(properties);
        this.base = base;
    }

    @Override
    public Block getBase() {
        return this.base;
    }

}