package cn.teampancake.theaurorian.common.level.feature;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public class HugeIndigoMushroomFeature extends AbstractHugeMushroomFeature {

    public HugeIndigoMushroomFeature() {
        super(HugeMushroomFeatureConfiguration.CODEC);
    }

    @Override
    protected void makeCap(LevelAccessor level, RandomSource random, BlockPos pos, int treeHeight, BlockPos.MutableBlockPos mutablePos, HugeMushroomFeatureConfiguration config) {
        int capWidth = config.foliageRadius;
        int w2 = capWidth - 2;
        for (int x = 0; x < w2; x++) {
            for (int z = 0; z < w2; z++) {
                BlockPos p = pos.offset(x - w2 / 2, treeHeight, z - w2 / 2);
                if (level.getBlockState(p).isAir()) {
                    this.setBlock(level, p, config.capProvider.getState(random, pos));
                }
            }
        }

        for (int x = 0; x < capWidth; x++) {
            for (int z = 0; z < capWidth; z++) {
                BlockPos p = pos.offset(x - capWidth / 2, treeHeight - 1, z - capWidth / 2);
                if (level.getBlockState(p).isAir() && (x == 0 || z == 0 || x == capWidth - 1 || z == capWidth - 1)) {
                    if (!((x == 0 && z == 0) || (x == capWidth - 1 && z == capWidth - 1) || (x == 0 && z == capWidth - 1) || (x == capWidth - 1 && z == 0))) {
                        this.setBlock(level, p, config.capProvider.getState(random, pos));
                    }
                }
            }
        }

        for (int x = 0; x < w2; x++) {
            for (int z = 0; z < w2; z++) {
                BlockPos p = pos.offset(x - w2 / 2, treeHeight - 1, z - w2 / 2);
                if (level.getBlockState(p).isAir() && random.nextDouble() < 0.25D) {
                    this.setBlock(level, p, TABlocks.INDIGO_MUSHROOM_CRYSTAL.get().defaultBlockState());
                }
            }
        }
    }

    @Override
    protected int getTreeRadiusForHeight(int p_65094_, int p_65095_, int foliageRadius, int y) {
        return y <= 3 ? 0 : foliageRadius;
    }

}
