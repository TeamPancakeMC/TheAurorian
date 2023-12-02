package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class AurorianGrassBlock extends GrassBlock {

    public AurorianGrassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random) {
        if (serverLevel.isAreaLoaded(pos, 1) && serverLevel.getMaxLocalRawBrightness(pos.above()) > 9) {
            for (int i = 0; i < 4; ++i) {
                int x = random.nextInt(3) - 1;
                int y = random.nextInt(5) - 3;
                int z = random.nextInt(3) - 1;
                BlockPos blockPos = new BlockPos(x, y, z);
                if (serverLevel.getBlockState(blockPos).is(TABlocks.AURORIAN_DIRT.get())) {
                    serverLevel.setBlockAndUpdate(blockPos, this.defaultBlockState());
                }
            }
        }
    }

}