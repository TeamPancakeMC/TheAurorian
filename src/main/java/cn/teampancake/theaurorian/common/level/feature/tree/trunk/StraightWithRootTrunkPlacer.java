package cn.teampancake.theaurorian.common.level.feature.tree.trunk;

import cn.teampancake.theaurorian.common.registry.TATrunkPlacerTypes;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class StraightWithRootTrunkPlacer extends TrunkPlacer {

    public static final Codec<StraightWithRootTrunkPlacer> CODEC = RecordCodecBuilder.create(placer ->
            trunkPlacerParts(placer).apply(placer, StraightWithRootTrunkPlacer::new));

    public StraightWithRootTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return TATrunkPlacerTypes.STRAIGHT_WITH_ROOT.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int freeTreeHeight, BlockPos pos, TreeConfiguration config) {
        setDirtAt(level, blockSetter, random, pos.below(), config);
        for (int i = 0; i < freeTreeHeight; ++i) {
            this.placeLog(level, blockSetter, random, pos.above(i), config);
        }

        if (level instanceof LevelReader levelReader) {
            for (int i = 0; i < 3; i++) {
                for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
                    BlockPos newPos = pos.above(i).relative(direction).below();
                    if (levelReader.getBlockState(newPos).isCollisionShapeFullBlock(levelReader, newPos) && random.nextBoolean()) {
                        this.placeLog(level, blockSetter, random, newPos, config);
                    }
                }
            }
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(freeTreeHeight), 0, Boolean.FALSE));
    }

}