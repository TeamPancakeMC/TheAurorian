package cn.teampancake.theaurorian.common.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class PressurePlateBlockWithBase extends PressurePlateBlock implements IHasBaseBlock {

    private final Block base;

    public PressurePlateBlockWithBase(Block base, boolean sensitive, Properties properties, BlockSetType type) {
        super(sensitive ? Sensitivity.EVERYTHING : Sensitivity.MOBS, properties, type);
        this.base = base;
    }

    public Block getBase() {
        return this.base;
    }

}