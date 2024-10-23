package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.state.TABlockProperties;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class TALightDoublePlantBlock extends DoublePlantBlock {

    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL;

    public TALightDoublePlantBlock(TABlockProperties properties) {
        super(properties.lightLevel(state -> state.getValue(LEVEL)).isRiversidePlant());
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, LEVEL);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter pLevel, BlockPos pPos) {
        return state.is(TABlockTags.AURORIAN_LIGHT_PLANT_MAY_PLACE_ON);
    }

}