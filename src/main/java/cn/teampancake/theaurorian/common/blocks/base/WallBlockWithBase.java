package cn.teampancake.theaurorian.common.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;

public class WallBlockWithBase extends WallBlock {

    private final Block base;

    public WallBlockWithBase(Block base, Properties properties) {
        super(properties);
        this.base = base;
    }

    public Block getBase() {
        return this.base;
    }

}