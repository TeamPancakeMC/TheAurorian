package cn.teampancake.theaurorian.common.level.feature.tree.foliage;

import cn.teampancake.theaurorian.common.registry.TAFoliagePlacerTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class TaperFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<TaperFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((placer) -> foliagePlacerParts(placer).apply(placer, TaperFoliagePlacer::new));

    public TaperFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return TAFoliagePlacerTypes.TAPER.get();
    }

    private void placeLeavesRowWithoutOffset(LevelSimulatedReader level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, BlockPos pos) {
        this.placeLeavesRow(level, foliageSetter, random, config, pos, 0, 0, false);
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter blockSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos startingPos = attachment.pos();
        for (int i = 0; i < random.nextInt(3) + 4; i++) {
            this.placeLeavesRowWithoutOffset(level, blockSetter, random, config, startingPos.above(i));
        }

        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            BlockPos relativePos = startingPos.relative(direction);
            this.placeLeavesRowWithoutOffset(level, blockSetter, random, config, relativePos);
            if (random.nextBoolean()) {
                this.placeLeavesRowWithoutOffset(level, blockSetter, random, config, relativePos.above());
            }

            int j = random.nextInt(3) + 4;
            for (int i = 0; i < j; i++) {
                this.placeLeavesRowWithoutOffset(level, blockSetter, random, config, relativePos.below(i));
            }

            int k = random.nextInt(4) + 3;
            for (int i = j; j < k; j++) {
                this.placeLeavesRowWithoutOffset(level, blockSetter, random, config, startingPos.relative(direction, 2).below(i));
            }

            if (random.nextBoolean()) {
                this.placeLeavesRowWithoutOffset(level, blockSetter, random, config, relativePos.relative(direction.getClockWise()).above(j - 2));
            }

            for (int i = j - 1; i < j + 2; i++) {
                this.placeLeavesRowWithoutOffset(level, blockSetter, random, config, relativePos.relative(direction.getClockWise()).below(i));
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return 6;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return false;
    }

}