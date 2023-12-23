package cn.teampancake.theaurorian.common.level.feature.tree.foliage;

import cn.teampancake.theaurorian.registry.TAFoliagePlacerTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class HemisphereFoliagePlacer extends FoliagePlacer {

    public static final Codec<HemisphereFoliagePlacer> CODEC = RecordCodecBuilder.create((placer) -> foliagePlacerParts(placer).apply(placer, HemisphereFoliagePlacer::new));

    public HemisphereFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return TAFoliagePlacerTypes.HEMISPHERE.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter blockSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos startingPos = attachment.pos();
        this.placeLeavesRow(level, blockSetter, random, config, startingPos.above(), 0, 0, Boolean.FALSE);
        BlockStateProperties.HORIZONTAL_FACING.getPossibleValues().forEach(direction -> this.placeLeavesRow(level, blockSetter, random, config, startingPos.relative(direction), 0, 0, Boolean.FALSE));
        this.placeLeavesRow(level, blockSetter, random, config, startingPos, 0, 0, Boolean.FALSE);
        for (int h = 2; h <= 4; h++) {
            for (int r = h; r > 0; r--) {
                BlockPos leavesStartPos = startingPos.north(r).west(r).below(h - 2);
                for (int i = 1; i <= r * 2; i++) {
                    for (int j = 1; j <= r * 2; j++) {
                        if (i * i + j * j == r * r) {
                            BlockPos fillPos = leavesStartPos.south(i).east(j);
                            this.placeLeavesRow(level, blockSetter, random, config, fillPos, 0, 0, Boolean.FALSE);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return 4;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return localX == range && localZ == range && (random.nextInt(2) == 0 || localY == 0);
    }

}
