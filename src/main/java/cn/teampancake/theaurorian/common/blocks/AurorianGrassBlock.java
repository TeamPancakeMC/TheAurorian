package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.common.blocks.state.TABlockProperties;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

public class AurorianGrassBlock extends GrassBlock {

    public AurorianGrassBlock(TABlockProperties properties) {
        super(properties.addBlockTag(BlockTags.MINEABLE_WITH_SHOVEL));
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility toolAction, boolean simulate) {
        return toolAction == ItemAbilities.HOE_TILL ? TABlocks.AURORIAN_FARM_TILE.get().defaultBlockState() : null;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random) {
        if (!canBeGrass(state, serverLevel, pos)) {
            if (serverLevel.isAreaLoaded(pos, 1)) {
                serverLevel.setBlockAndUpdate(pos, TABlocks.AURORIAN_DIRT.get().defaultBlockState());
            }
        } else {
            if (serverLevel.isAreaLoaded(pos, 3) && serverLevel.getMaxLocalRawBrightness(pos.above()) > 9) {
                BlockState defaultState = this.defaultBlockState();
                for (int i = 0; i < 4; ++i) {
                    int x = random.nextInt(3) - 1;
                    int y = random.nextInt(5) - 3;
                    int z = random.nextInt(3) - 1;
                    BlockPos blockPos = new BlockPos(x, y, z);
                    if (serverLevel.getBlockState(blockPos).is(TABlocks.AURORIAN_DIRT.get()) && canPropagate(defaultState, serverLevel, blockPos)) {
                        serverLevel.setBlockAndUpdate(blockPos, defaultState.setValue(SNOWY, serverLevel.getBlockState(blockPos.above()).is(Blocks.SNOW)));
                    }
                }
            }
        }
    }

}