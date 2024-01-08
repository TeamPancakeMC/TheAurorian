package cn.teampancake.theaurorian.common.level.feature.ruin.small;

import cn.teampancake.theaurorian.common.level.feature.ruin.SmallRuinFeature;
import cn.teampancake.theaurorian.common.level.feature.tree.decorators.CrystalBudDecorator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SmallRuin01 extends SmallRuinFeature {

    @Override
    protected void placeSpecifically(WorldGenLevel level, RandomSource random, BlockPos originPos) {
        level.setBlock(originPos, this.aurorianCobblestone, Block.UPDATE_CLIENTS);
        BlockPos tPos1 = originPos.west(2).north(2).above();
        BlockPos tPos2 = originPos.west(2).south(2).above();
        BlockPos tPos3 = originPos.east(2).north(2).above();
        BlockPos tPos4 = originPos.east(2).south(2).above();
        BlockPos[] tPos = new BlockPos[] {tPos1, tPos2, tPos3, tPos4};
        for (Direction direction : Direction.BY_2D_DATA) {
            BlockState budState = CrystalBudDecorator.clusterBudList().get(random.nextInt(CrystalBudDecorator.clusterBudList().size()));
            level.setBlock(originPos.relative(direction, 3).relative(direction.getClockWise()), this.aurorianCobblestoneStair, Block.UPDATE_CLIENTS);
            level.setBlock(originPos.relative(direction, 3).relative(direction.getCounterClockWise()), this.aurorianCobblestoneStair, Block.UPDATE_CLIENTS);
            level.setBlock(originPos.relative(direction), this.aurorianCobblestoneStair.setValue(StairBlock.FACING, direction.getOpposite()), Block.UPDATE_CLIENTS);
            level.setBlock(originPos.relative(direction).relative(direction.getClockWise()), this.aurorianCobblestoneSlab, Block.UPDATE_CLIENTS);
            level.setBlock(originPos.relative(direction, 2), this.aurorianCobblestoneSlab, Block.UPDATE_CLIENTS);
            level.setBlock(originPos.relative(direction, 3), budState, Block.UPDATE_CLIENTS);
            if (random.nextBoolean()) {
                level.setBlock(originPos.relative(direction, 3).relative(direction.getClockWise()), this.aurorianCobblestoneSlab, Block.UPDATE_CLIENTS);
            }
        }

        for (BlockPos pos : tPos) {
            BlockState budState = CrystalBudDecorator.clusterBudList().get(random.nextInt(CrystalBudDecorator.clusterBudList().size()));
            level.setBlock(pos, this.aurorianCobblestone, Block.UPDATE_CLIENTS);
            level.setBlock(pos.below(), this.aurorianCobblestone, Block.UPDATE_CLIENTS);
            level.setBlock(pos.above(), budState, Block.UPDATE_CLIENTS);
            for (Direction direction : Direction.BY_2D_DATA) {
                level.setBlock(pos.relative(direction), this.aurorianCobblestoneStair.setValue(StairBlock.FACING, direction.getOpposite()), Block.UPDATE_CLIENTS);
                level.setBlock(pos.relative(direction).below(), this.aurorianCobblestone, Block.UPDATE_CLIENTS);
            }
        }
    }

}