package cn.teampancake.theaurorian.common.level.feature;

import cn.teampancake.theaurorian.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class UrnFeature extends Feature<NoneFeatureConfiguration> {

    public UrnFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos originPos = context.origin();
        if (level.isEmptyBlock(originPos) && level.getBlockState(originPos.below()).is(ModBlocks.URN.get())) {
            int countedSides = 0;
            for (Direction direction : Direction.values()) {
                if (level.getBlockState(originPos.relative(direction)).is(ModBlocks.AURORIAN_STONE.get())) {
                    countedSides++;
                }
            }
            return countedSides >= 2;
        }
        return false;
    }

}