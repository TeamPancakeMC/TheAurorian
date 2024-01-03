package cn.teampancake.theaurorian.common.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.Supplier;

public class TALightPlantBlock extends TAPlantBlock implements IRiversidePlant {

    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL;

    public TALightPlantBlock(Properties properties, Supplier<Block> block) {
        super(properties.lightLevel(state -> state.getValue(LEVEL)), block);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

}