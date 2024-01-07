package cn.teampancake.theaurorian.common.level.feature.ruin.small;

import cn.teampancake.theaurorian.common.blocks.TAClusterBlock;
import cn.teampancake.theaurorian.common.level.feature.tree.decorators.CrystalBudDecorator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class SmallRuin16 extends TASmallRuin {

    private Direction getRandomFacingOfClockwise(Direction direction) {
        return switch (direction) {
            case NORTH -> Direction.WEST;
            case EAST -> Direction.NORTH;
            case SOUTH -> Direction.EAST;
            case WEST -> Direction.SOUTH;
            default -> throw new IllegalStateException("Unable to get Y-rotated facing of " + direction);
        };
    }

    @Override
    protected void placeSpecifically(WorldGenLevel level, RandomSource random, BlockPos originPos) {
        List<BlockState> list = CrystalBudDecorator.clusterBudList();
        for (Direction direction : Direction.BY_2D_DATA) {
            BlockPos pos = originPos.relative(direction);
            BlockState[] states = new BlockState[] {this.aurorianCobblestone, this.aurorianCobblestoneSlab,
                    this.aurorianCobblestoneStair.setValue(StairBlock.FACING, direction.getOpposite())};
            Direction newDirection1 = random.nextBoolean() ? direction : this.getRandomFacingOfClockwise(direction);
            Direction newDirection2 = random.nextBoolean() ? direction.getOpposite() : this.getRandomFacingOfClockwise(direction.getOpposite());
            BlockState budState = list.get(random.nextInt(list.size())).setValue(TAClusterBlock.FACING, newDirection1);
            BlockState stairState = this.aurorianCobblestoneStair.setValue(StairBlock.FACING, newDirection2);
            level.setBlock(pos.above().relative(direction.getClockWise()), budState, Block.UPDATE_CLIENTS);
            level.setBlock(pos.relative(direction.getClockWise()), random.nextBoolean() ? states[1] : stairState, Block.UPDATE_CLIENTS);
            level.setBlock(pos.relative(direction), random.nextBoolean() ? states[1] : states[2], Block.UPDATE_CLIENTS);
            level.setBlock(pos.above(2), states[random.nextInt(states.length)], Block.UPDATE_CLIENTS);
            level.setBlock(pos.above(), this.aurorianCobblestone, Block.UPDATE_CLIENTS);
            level.setBlock(pos, this.aurorianCobblestone, Block.UPDATE_CLIENTS);
            if (level.getBlockState(pos.above(2)).is(this.aurorianCobblestone.getBlock()) && random.nextBoolean()) {
                level.setBlock(pos.above(3), states[2], Block.UPDATE_CLIENTS);
            }
        }

        for (int i = 0; i <= 6; i++) {
            if (i > 4) {
                level.setBlock(originPos.above(i), this.aurorianCobblestoneWall, Block.UPDATE_CLIENTS);
            } else {
                level.setBlock(originPos.above(i), this.aurorianCobblestone, Block.UPDATE_CLIENTS);
                for (Direction direction : Direction.BY_2D_DATA) {
                    BlockPos pos = originPos.relative(direction);
                    if (level.getBlockState(pos).isAir() && random.nextBoolean()) {
                        BlockState budState = list.get(random.nextInt(list.size()));
                        level.setBlock(pos, budState.setValue(TAClusterBlock.FACING, direction), Block.UPDATE_CLIENTS);
                    }
                }
            }
        }
    }

    @Override
    protected int verticalHeight() {
        return 6;
    }

}