package cn.teampancake.theaurorian.common.level.feature.ruin.small;

import cn.teampancake.theaurorian.common.level.feature.ruin.SmallRuinFeature;
import cn.teampancake.theaurorian.common.level.feature.tree.decorators.CrystalBudDecorator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class SmallRuin09 extends SmallRuinFeature {

    @Override
    protected void placeSpecifically(WorldGenLevel level, RandomSource random, BlockPos originPos) {
        level.setBlock(originPos, Blocks.WATER.defaultBlockState(), Block.UPDATE_CLIENTS);
        List<BlockState> list = CrystalBudDecorator.clusterBudList();
        list.add(Blocks.AIR.defaultBlockState());
        BlockPos firstPos = originPos.west(2).north(2);
        BlockPos secondPos = originPos.east(2).south(2);
        for (Direction direction : Direction.BY_2D_DATA) {
            level.setBlock(originPos.relative(direction), this.aurorianCobblestoneStair.setValue(StairBlock.FACING, direction.getOpposite()), Block.UPDATE_CLIENTS);
            level.setBlock(originPos.relative(direction).relative(direction.getClockWise()).above(), list.get(random.nextInt(list.size())), Block.UPDATE_CLIENTS);
            level.setBlock(originPos.relative(direction).relative(direction.getClockWise()), this.aurorianCobblestone, Block.UPDATE_CLIENTS);
        }

        for (BlockPos pos : BlockPos.betweenClosed(firstPos, secondPos)) {
            if (level.getBlockState(pos).isAir()) {
                level.setBlock(pos, random.nextInt(10) >= 7 ? this.silentTreeLeaves : this.aurorianCobblestoneSlab, Block.UPDATE_CLIENTS);
                if (level.getBlockState(pos).is(this.silentTreeLeaves.getBlock()) && random.nextBoolean()) {
                    level.setBlock(pos.above(), this.silentTreeLeaves, Block.UPDATE_CLIENTS);
                }
            }
        }

        for (BlockPos pos : BlockPos.betweenClosed(firstPos.west().north(), secondPos.east().south())) {
            if (level.getBlockState(pos).isAir()) {
                level.setBlock(pos, random.nextBoolean() ? this.silentTreeLeaves : list.get(random.nextInt(list.size())), Block.UPDATE_CLIENTS);
                if (level.getBlockState(pos).is(this.silentTreeLeaves.getBlock()) && random.nextBoolean()) {
                    level.setBlock(pos.above(), this.silentTreeLeaves, Block.UPDATE_CLIENTS);
                }
            }
        }
    }

}