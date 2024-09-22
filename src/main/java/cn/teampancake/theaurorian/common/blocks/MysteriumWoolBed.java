package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.entity.MysteriumWoolBedBlockEntity;
import cn.teampancake.theaurorian.common.blocks.state.TABlockProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

@SuppressWarnings("deprecation")
public class MysteriumWoolBed extends BedBlock {

    public MysteriumWoolBed() {
        super(DyeColor.BLUE, TABlockProperties.ofFullCopy(Blocks.BLUE_BED).addBlockTag(BlockTags.BEDS));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return state.getValue(PART) == BedPart.HEAD ? RenderShape.MODEL : RenderShape.INVISIBLE;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MysteriumWoolBedBlockEntity(pos, state);
    }

}