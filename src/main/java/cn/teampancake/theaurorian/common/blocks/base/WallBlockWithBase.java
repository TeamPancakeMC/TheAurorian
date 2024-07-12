package cn.teampancake.theaurorian.common.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;

public class WallBlockWithBase extends WallBlock implements IHasBaseBlock {

    private final Block base;

    public WallBlockWithBase(Block base, Properties properties) {
        super(properties);
        this.base = base;
    }

    @Override
    public Block getBase() {
        return this.base;
    }

}