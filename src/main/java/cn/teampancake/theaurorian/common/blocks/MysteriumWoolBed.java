package cn.teampancake.theaurorian.common.blocks;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

@SuppressWarnings("deprecation")
public class MysteriumWoolBed extends BedBlock {

    public MysteriumWoolBed() {
        super(DyeColor.BLUE, Properties.ofFullCopy(Blocks.BLUE_BED));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return state.getValue(PART) == BedPart.HEAD ? RenderShape.MODEL : RenderShape.INVISIBLE;
    }

}