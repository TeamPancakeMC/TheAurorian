package cn.teampancake.theaurorian.common.level.feature;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class FilthySnowAndFreezeFeature extends Feature<NoneFeatureConfiguration> {

    public FilthySnowAndFreezeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos mutableBlockPos1 = new BlockPos.MutableBlockPos();
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                int k = origin.getX() + i;
                int l = origin.getZ() + j;
                int i1 = level.getHeight(Heightmap.Types.MOTION_BLOCKING, k, l);
                mutableBlockPos.set(k, i1, l);
                mutableBlockPos1.set(mutableBlockPos).move(Direction.DOWN, 1);
                Biome biome = level.getBiome(mutableBlockPos).value();
                if (biome.shouldFreeze(level, mutableBlockPos1, Boolean.FALSE)) {
                    level.setBlock(mutableBlockPos1, TABlocks.FILTHY_ICE.get().defaultBlockState(), 2);
                }

                if (biome.shouldSnow(level, mutableBlockPos)) {
                    level.setBlock(mutableBlockPos, Blocks.SNOW.defaultBlockState(), 2);
                    BlockState blockstate = level.getBlockState(mutableBlockPos1);
                    if (blockstate.hasProperty(SnowyDirtBlock.SNOWY)) {
                        level.setBlock(mutableBlockPos1, blockstate.setValue(SnowyDirtBlock.SNOWY, Boolean.TRUE), 2);
                    }
                }
            }
        }

        return true;
    }

}