package cn.teampancake.theaurorian.common.level;

import cn.teampancake.theaurorian.common.blocks.TATallWaterGrassBlock;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class WaterGrassFeature extends Feature<ProbabilityFeatureConfiguration> {

    public WaterGrassFeature() {
        super(ProbabilityFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> context) {
        boolean flag = false;
        RandomSource random = context.random();
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        ProbabilityFeatureConfiguration config = context.config();
        int i = random.nextInt(8) - random.nextInt(8);
        int j = random.nextInt(8) - random.nextInt(8);
        int k = level.getHeight(Heightmap.Types.OCEAN_FLOOR, origin.getX() + i, origin.getZ() + j);
        BlockPos blockPos = new BlockPos(origin.getX() + i, k, origin.getZ() + j);
        if (level.getBlockState(blockPos).is(TABlocks.MOON_WATER.get())) {
            boolean flag1 = random.nextDouble() < (double) config.probability;
            int lightLevel = random.nextInt(16);
            BlockState tallArurorianWaterGrass = TABlocks.TALL_AURORIAN_WATER_GRASS.get().defaultBlockState().setValue(TATallWaterGrassBlock.LEVEL, lightLevel);
            BlockState blockstate = flag1 ? tallArurorianWaterGrass : TABlocks.AURORIAN_WATER_GRASS.get().defaultBlockState();
            if (blockstate.canSurvive(level, blockPos)) {
                if (flag1) {
                    BlockState blockState = blockstate.setValue(TATallWaterGrassBlock.HALF, DoubleBlockHalf.UPPER);
                    BlockPos blockPos1 = blockPos.above();
                    if (level.getBlockState(blockPos1).is(TABlocks.MOON_WATER.get())) {
                        level.setBlock(blockPos, blockstate, 2);
                        level.setBlock(blockPos1, blockState, 2);
                    }
                } else {
                    level.setBlock(blockPos, blockstate, 2);
                }

                flag = true;
            }
        }

        return flag;
    }

}