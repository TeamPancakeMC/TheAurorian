package cn.teampancake.theaurorian.common.level.feature.tree.decorators;

import cn.teampancake.theaurorian.common.blocks.TAClusterBlock;
import cn.teampancake.theaurorian.common.registry.TATreeDecoratorTypes;
import cn.teampancake.theaurorian.common.utils.TACommonUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.ArrayList;
import java.util.List;

public class CrystalBudDecorator extends TreeDecorator {

    public static final Codec<CrystalBudDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability")
            .xmap(CrystalBudDecorator::new, (decorator) -> decorator.probability).codec();
    private final float probability;

    public CrystalBudDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return TATreeDecoratorTypes.CRYSTAL_BUD.get();
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();
        if (random.nextFloat() < this.probability) {
            context.logs().forEach(pos -> {
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    if (random.nextFloat() <= 0.1F) {
                        Direction opposite = direction.getOpposite();
                        BlockPos blockPos = pos.offset(opposite.getStepX(), 0, opposite.getStepZ());
                        BlockState budState = this.clusterBudList().get(random.nextInt(this.clusterBudList().size()));
                        if (context.isAir(blockPos) && context.level().isStateAtPosition(blockPos.relative(direction), state -> state.is(BlockTags.LOGS))) {
                            context.setBlock(blockPos, budState.setValue(TAClusterBlock.WATERLOGGED, Boolean.FALSE).setValue(TAClusterBlock.FACING, opposite));
                        }
                    }
                }
            });
        }
    }

    private List<BlockState> clusterBudList() {
        List<BlockState> list = new ArrayList<>();
        for (Block block : TACommonUtils.getKnownBlocks()) {
            if (block instanceof TAClusterBlock clusterBlock) {
                BlockState state = clusterBlock.defaultBlockState();
                TAClusterBlock.LEVEL.getPossibleValues().forEach(level -> list.add(state.setValue(TAClusterBlock.LEVEL, level)));
            }
        }

        return list;
    }

}