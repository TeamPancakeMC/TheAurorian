package cn.teampancake.theaurorian.common.level.carver;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABlockTags;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.function.Function;

@SuppressWarnings("deprecation")
public class AurorianCarver extends CaveWorldCarver {

    public AurorianCarver() {
        super(CaveCarverConfiguration.CODEC);
    }

    @Override
    protected boolean carveBlock(CarvingContext context, CaveCarverConfiguration config, ChunkAccess chunk, Function<BlockPos, Holder<Biome>> biomeGetter, CarvingMask carvingMask, BlockPos.MutableBlockPos pos, BlockPos.MutableBlockPos checkPos, Aquifer aquifer, MutableBoolean reachedSurface) {
        BlockState surfaceState = chunk.getBlockState(pos);
        if (surfaceState.is(TABlockTags.AURORIAN_GRASS_BLOCK)) {
            reachedSurface.setTrue();
        }

        for (Direction direction : Direction.BY_2D_DATA) {
            if (!chunk.getBlockState(checkPos.relative(direction)).getFluidState().isEmpty()) {
                return false;
            }
        }

        boolean isBedrockFloor = pos.getY() < chunk.getMinBuildHeight() + 6;
        boolean hasEmptyFluidAbove = chunk.getBlockState(pos.above()).getFluidState().isEmpty();
        if (!this.canReplaceBlock(config, surfaceState) && !config.debugSettings.isDebugMode() || isBedrockFloor || !hasEmptyFluidAbove) {
            return false;
        } else {
            chunk.setBlockState(pos, CAVE_AIR, Boolean.FALSE);
            if (reachedSurface.isTrue()) {
                checkPos.setWithOffset(pos, Direction.DOWN);
                if (chunk.getBlockState(checkPos).is(TABlocks.AURORIAN_DIRT.get())) {
                    context.topMaterial(biomeGetter, chunk, checkPos, !CAVE_AIR.getFluidState().isEmpty()).ifPresent((state) -> {
                        chunk.setBlockState(checkPos, state, Boolean.FALSE);
                        if (!state.getFluidState().isEmpty()) {
                            chunk.markPosForPostprocessing(checkPos);
                        }
                    });
                }
            }

            return true;
        }
    }

}