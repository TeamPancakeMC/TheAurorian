package cn.teampancake.theaurorian.common.level.feature.ruin;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SmallRuinFeature extends Feature<NoneFeatureConfiguration> {

    protected final BlockState silentTreeLeaves = TABlocks.SILENT_TREE_LEAVES.get().defaultBlockState();
    protected final BlockState aurorianCobblestone = TABlocks.AURORIAN_COBBLESTONE.get().defaultBlockState();
    protected final BlockState aurorianCobblestoneStair = TABlocks.AURORIAN_COBBLESTONE_STAIRS.get().defaultBlockState();
    protected final BlockState aurorianCobblestoneSlab = TABlocks.AURORIAN_COBBLESTONE_SLAB.get().defaultBlockState();
    protected final BlockState aurorianCobblestoneWall = TABlocks.AURORIAN_COBBLESTONE_WALL.get().defaultBlockState();

    public SmallRuinFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    protected void placeSpecifically(WorldGenLevel level, RandomSource random, BlockPos originPos) {}

    protected int verticalHeight() {
        return 1;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        boolean hasEnoughReplaceableBlock = false;
        boolean hasEnoughSolidBlockUnder = false;
        WorldGenLevel level = context.level();
        BlockPos originPos = context.origin();
        BlockPos firstPos = originPos.west(3).north(3);
        BlockPos secondPos = originPos.east(3).south(3);
        for (BlockPos pos : BlockPos.betweenClosed(firstPos.above(this.verticalHeight()), secondPos)) {
            hasEnoughReplaceableBlock = level.getBlockState(pos).canBeReplaced();
            if (pos.getY() == originPos.getY()) {
                hasEnoughSolidBlockUnder = level.getBlockState(pos.below()).is(TABlocks.AURORIAN_GRASS_BLOCK.get());
            }
        }

        if (hasEnoughReplaceableBlock && hasEnoughSolidBlockUnder) {
            this.placeSpecifically(level, context.random(), originPos);
            return true;
        }

        return false;
    }

}