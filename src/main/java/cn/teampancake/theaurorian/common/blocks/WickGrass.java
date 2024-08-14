package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.registry.TAParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class WickGrass extends TAFlowerBlock {

    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL;

    public WickGrass() {
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 15));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.random.nextFloat() < state.getValue(LEVEL) / 30.0F) {
            double d3 = (double) pos.getX() + level.random.nextDouble();
            double d4 = (double) pos.getY() + level.random.nextDouble();
            double d5 = (double) pos.getZ() + level.random.nextDouble();
            double d6 = level.random.nextGaussian() * 0.02D;
            double d7 = level.random.nextGaussian() * 0.02D;
            double d8 = level.random.nextGaussian() * 0.02D;
            level.addParticle(TAParticleTypes.WICK.get(), d3, d4, d5, d6, d7, d8);
        }
    }

}