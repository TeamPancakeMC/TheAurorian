package cn.teampancake.theaurorian.common.level.feature.tree.trunk;

import cn.teampancake.theaurorian.registry.TATrunkPlacerTypes;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class StraightWithFallenLeavesTrunkPlacer extends TrunkPlacer {

    public static final Codec<StraightWithFallenLeavesTrunkPlacer> CODEC = RecordCodecBuilder.create(instance ->
            trunkPlacerParts(instance).apply(instance, StraightWithFallenLeavesTrunkPlacer::new));

    public StraightWithFallenLeavesTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return TATrunkPlacerTypes.STRAIGHT_WITH_FALLEN_LEAVES.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int freeTreeHeight, BlockPos pos, TreeConfiguration config) {
        setDirtAt(level, blockSetter, random, pos.below(), config);
        for (int i = 0; i < freeTreeHeight; ++i) {
            this.placeLog(level, blockSetter, random, pos.above(i), config);
        }

        for (int r = random.nextInt(2) + 3; r > 0; r--) {
            BlockPos leavesStartPos = pos.north(r).west(r);
            for (int i = 1; i <= r * 2; i++) {
                for (int j = 1; j <= r * 2; j++) {
                    if (i * i + j * j == r * r && random.nextBoolean()) {
                        BlockPos fillPos = leavesStartPos.south(i).east(j);
                        this.placeFallenLeaves(level, blockSetter, random, fillPos, config, Function.identity());
                    }
                }
            }
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(freeTreeHeight), 0, Boolean.FALSE));
    }

    private void placeFallenLeaves(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, TreeConfiguration config, Function<BlockState, BlockState> propertySetter) {
        if (this.validTreePos(level, pos)) {
            blockSetter.accept(pos, propertySetter.apply(config.foliageProvider.getState(random, pos)));
        }
    }

}
