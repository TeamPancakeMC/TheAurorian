package cn.teampancake.theaurorian.common.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class FenceGateBlockWithBase extends FenceGateBlock implements IHasBaseBlock, ISimpleBlockItem {

    private final Block base;

    public FenceGateBlockWithBase(Block base, Properties properties, WoodType type) {
        super(properties, type);
        this.base = base;
    }

    @Override
    public Block getBase() {
        return this.base;
    }

}