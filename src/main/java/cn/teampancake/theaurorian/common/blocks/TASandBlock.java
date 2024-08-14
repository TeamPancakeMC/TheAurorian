package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.TriState;

public class TASandBlock extends ColoredFallingBlock {

    public TASandBlock(int dustColor, BlockBehaviour.Properties properties) {
        super(new ColorRGBA(dustColor), properties);
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        return state.is(TABlockTags.AURORIAN_SAND_BLOCK) ? TriState.TRUE : TriState.FALSE;
    }

}