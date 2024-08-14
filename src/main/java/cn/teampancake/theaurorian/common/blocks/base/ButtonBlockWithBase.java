package cn.teampancake.theaurorian.common.blocks.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class ButtonBlockWithBase extends ButtonBlock implements IHasBaseBlock {

    private final Block base;

    public ButtonBlockWithBase(Block base, Properties properties, BlockSetType type, int ticksToStayPressed) {
        super(type, ticksToStayPressed, properties);
        this.base = base;
    }

    @Override
    public Block getBase() {
        return this.base;
    }

}