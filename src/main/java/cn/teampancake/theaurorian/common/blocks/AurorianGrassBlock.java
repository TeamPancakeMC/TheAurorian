package cn.teampancake.theaurorian.common.blocks;

import cn.teampancake.theaurorian.utils.AurorianUtil;
import cn.teampancake.theaurorian.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class AurorianGrassBlock extends GrassBlock {

    public AurorianGrassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.isClientSide && (level.isEmptyBlock(pos.above()) || level.getBlockState(pos.above()).getBlock() instanceof BushBlock)) {
            if (AurorianUtil.randomChanceOf(0.01) && AurorianUtil.randomChanceOf(0.5)) {
                double d0 = pos.getX() + 0.5D;
                double d1 = pos.getY() + random.nextDouble() * 6.0D / 16.0D;
                double d2 = pos.getZ() + 0.5D;
                double d3 = 4 * random.nextDouble();
                double mo = 0.1D * random.nextDouble();
                level.addParticle(ParticleTypes.FIREWORK, d0, d1 + 4 + d3, d2, mo, 0.0D, mo);
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random) {
        if (serverLevel.isAreaLoaded(pos, 1) && serverLevel.getMaxLocalRawBrightness(pos.above()) > 9) {
            for (int i = 0; i < 4; ++i) {
                int x = random.nextInt(3) - 1;
                int y = random.nextInt(5) - 3;
                int z = random.nextInt(3) - 1;
                BlockPos blockPos = new BlockPos(x, y, z);
                if (serverLevel.getBlockState(blockPos).is(ModBlocks.AURORIAN_DIRT.get())) {
                    serverLevel.setBlockAndUpdate(blockPos, this.defaultBlockState());
                }
            }
        }
    }

}