package cn.teampancake.theaurorian.common.level;

import cn.teampancake.theaurorian.common.data.datagen.tags.TABiomeTags;
import cn.teampancake.theaurorian.common.level.data.WorldSkyManager;
import cn.teampancake.theaurorian.common.level.data.WorldSkyData;
import cn.teampancake.theaurorian.common.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;

public class TAServerLevel {

    public static void tickChunk(ServerLevel level, LevelChunk chunk, int randomTickSpeed) {
        WorldSkyData skyData = WorldSkyManager.getWorldSkyData(level);
        ChunkPos chunkpos = chunk.getPos();
        int i = chunkpos.getMinBlockX();
        int j = chunkpos.getMinBlockZ();
        int scale = skyData.currentDayColor.id() == 4 ? 3 : 1;
        ProfilerFiller profiler = level.getProfiler();
        profiler.popPush("iceAndSnow");
        for (int i1 = 0; i1 < randomTickSpeed; i1++) {
            if (level.random.nextInt(48) == 0) {
                tickPrecipitation(level, level.getBlockRandomPos(i, 0, j, 15));
            }
        }

        profiler.popPush("tickBlocks");
        if (randomTickSpeed > 0) {
            LevelChunkSection[] chunkSections = chunk.getSections();
            for (int j1 = 0; j1 < chunkSections.length; j1++) {
                LevelChunkSection chunkSection = chunkSections[j1];
                if (chunkSection.isRandomlyTicking()) {
                    int k1 = chunk.getSectionYFromSectionIndex(j1);
                    int k = SectionPos.sectionToBlockCoord(k1);
                    for (int l = 0; l < randomTickSpeed * scale; l++) {
                        BlockPos randomPos = level.getBlockRandomPos(i, k, j, 15);
                        profiler.push("randomTick");
                        int x = randomPos.getX() - i;
                        int y = randomPos.getY() - k;
                        int z = randomPos.getZ() - j;
                        BlockState state = chunkSection.getBlockState(x, y, z);
                        if (state.isRandomlyTicking() && state.is(BlockTags.CROPS)) {
                            state.randomTick(level, randomPos, level.random);
                        }

                        FluidState fluidState = state.getFluidState();
                        if (fluidState.isRandomlyTicking()) {
                            fluidState.randomTick(level, randomPos, level.random);
                        }

                        profiler.pop();
                    }
                }
            }
        }

        profiler.pop();
    }

    private static void tickPrecipitation(ServerLevel level, BlockPos blockPos) {
        BlockPos heightmapPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos);
        BlockPos belowPos = heightmapPos.below();
        Biome biome = level.getBiome(heightmapPos).value();
        boolean flag = level.getBiome(belowPos).is(TABiomeTags.IS_FILTHY_ICE);
        if (level.isAreaLoaded(belowPos, 1) && biome.shouldFreeze(level, belowPos)) {
            Block block = flag ? TABlocks.FILTHY_ICE.get() : Blocks.ICE;
            level.setBlockAndUpdate(belowPos, block.defaultBlockState());
        }

        if (level.isRaining() || flag) {
            int i = level.getGameRules().getInt(GameRules.RULE_SNOW_ACCUMULATION_HEIGHT);
            if (i > 0 && biome.shouldSnow(level, heightmapPos)) {
                BlockState blockState = level.getBlockState(heightmapPos);
                if (blockState.is(Blocks.SNOW)) {
                    int j = blockState.getValue(SnowLayerBlock.LAYERS);
                    if (j < Math.min(i, 8)) {
                        BlockState snowLayerState = blockState.setValue(SnowLayerBlock.LAYERS, j + 1);
                        Block.pushEntitiesUp(blockState, snowLayerState, level, heightmapPos);
                        level.setBlockAndUpdate(heightmapPos, snowLayerState);
                    }
                } else {
                    level.setBlockAndUpdate(heightmapPos, Blocks.SNOW.defaultBlockState());
                }
            }

            Biome.Precipitation precipitation = biome.getPrecipitationAt(belowPos);
            if (precipitation != Biome.Precipitation.NONE) {
                BlockState blockState = level.getBlockState(belowPos);
                blockState.getBlock().handlePrecipitation(blockState, level, belowPos, precipitation);
            }
        }
    }

}