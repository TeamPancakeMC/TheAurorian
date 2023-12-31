package cn.teampancake.theaurorian.common.level.feature;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.level.feature.config.FallenLogConfig;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class FallenLogFeature extends Feature<FallenLogConfig> {

    public FallenLogFeature() {
        super(FallenLogConfig.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<FallenLogConfig> context) {
        FallenLogConfig config = context.config();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos originPos = context.origin();
        Direction direction = Direction.from2DDataValue(random.nextInt(4));
        Direction.Axis axis = direction.getAxis();
        int randomLength = random.nextInt(6, 9);
        boolean canPlace = true;
        for (int i = 0; i < randomLength; i++) {
            BlockPos relativePos = originPos.relative(direction, i);
            BlockState relativeState = level.getBlockState(relativePos);
            boolean canBeReplaced = relativeState.canBeReplaced() && !(relativeState.getBlock() instanceof LiquidBlock) && level.getBlockState(relativePos.above()).isAir();
            canPlace = canBeReplaced && level.getBlockState(relativePos.below()).is(TABlockTags.AURORIAN_GRASS_BLOCK);
        }

        if (canPlace && random.nextBoolean()) {
            for (int i = 0; i < randomLength; ++i) {
                BlockPos relativePos = originPos.relative(direction, i);
                BlockState logState = config.logState().getState(random, relativePos).setValue(RotatedPillarBlock.AXIS, axis);
                BlockState mushroomState = TABlocks.INDIGO_MUSHROOM.get().defaultBlockState();
                level.setBlock(relativePos, logState, Block.UPDATE_CLIENTS);
                if (random.nextFloat() < config.mushroomChance()) {
                    level.setBlock(relativePos.above(), mushroomState, Block.UPDATE_CLIENTS);
                }
            }

            return true;
        }

        return false;
    }

}