package cn.teampancake.theaurorian.common.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;

public class SlabBlockWithBase extends SlabBlock implements IHasBaseBlock, ISimpleBlockItem {

    private final Block base;

    public SlabBlockWithBase(Block base, Properties properties) {
        super(properties);
        this.base = base;
    }

    public Block getBase() {
        return this.base;
    }

}