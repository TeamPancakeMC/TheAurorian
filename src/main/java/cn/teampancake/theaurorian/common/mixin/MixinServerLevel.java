package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.event.subscriber.LevelEventSubscriber;
import cn.teampancake.theaurorian.common.registry.TABiomes;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TADimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel extends Level {

    @Shadow public abstract ServerLevel getLevel();

    protected MixinServerLevel(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, Supplier<ProfilerFiller> profiler, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, profiler, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    @Inject(method = "tickPrecipitation", at = @At(
            target = "Lnet/minecraft/world/level/biome/Biome;shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z",
            value = "INVOKE", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void tickPrecipitation(BlockPos pBlockPos, CallbackInfo ci, BlockPos blockPos1, BlockPos blockPos2, Biome biome) {
        if (this.getBiome(blockPos2).is(TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD)) {
            if (biome.shouldFreeze(this, blockPos2)) {
                this.setBlockAndUpdate(blockPos2, TABlocks.FILTHY_ICE.get().defaultBlockState());
            }

            int i1 = this.getGameRules().getInt(GameRules.RULE_SNOW_ACCUMULATION_HEIGHT);
            if (i1 > 0 && biome.shouldSnow(this, blockPos1)) {
                BlockState state = this.getBlockState(blockPos1);
                if (state.is(Blocks.SNOW)) {
                    int k = state.getValue(SnowLayerBlock.LAYERS);
                    if (k < Math.min(i1, 8)) {
                        BlockState state1 = state.setValue(SnowLayerBlock.LAYERS, k + 1);
                        Block.pushEntitiesUp(state, state1, this, blockPos1);
                        this.setBlockAndUpdate(blockPos1, state1);
                    }
                } else {
                    this.setBlockAndUpdate(blockPos1, Blocks.SNOW.defaultBlockState());
                }
            }

            Biome.Precipitation precipitation = biome.getPrecipitationAt(blockPos2);
            if (precipitation != Biome.Precipitation.NONE) {
                BlockState state = this.getBlockState(blockPos2);
                state.getBlock().handlePrecipitation(state, this, blockPos2, precipitation);
            }

            ci.cancel();
        }
    }

    @Inject(method = "tickChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V",
            ordinal = 1, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    public void tickChunk(LevelChunk chunk, int randomTickSpeed, CallbackInfo ci, ChunkPos chunkPos, boolean flag, int i, int j, ProfilerFiller profiler) {
        if (this.dimension() == TADimensions.AURORIAN_DIMENSION && LevelEventSubscriber.phaseCode == 4) {
            LevelChunkSection[] chunkSections = chunk.getSections();
            for (int l = 0; l < chunkSections.length; ++l) {
                LevelChunkSection chunkSection = chunkSections[l];
                if (chunkSection.isRandomlyTicking()) {
                    int j1 = chunk.getSectionYFromSectionIndex(l);
                    int k1 = SectionPos.sectionToBlockCoord(j1);
                    for (int l1 = 0; l1 < Math.max(3, randomTickSpeed * 3); ++l1) {
                        BlockPos randomPos = this.getBlockRandomPos(i, k1, j, 15);
                        profiler.push("randomTick");
                        int x = randomPos.getX() - i;
                        int y = randomPos.getY() - k1;
                        int z = randomPos.getZ() - j;
                        BlockState state = chunkSection.getBlockState(x, y, z);
                        if (state.isRandomlyTicking() && state.is(BlockTags.CROPS)) {
                            state.randomTick(this.getLevel(), randomPos, this.random);
                        }

                        profiler.pop();
                    }
                }
            }
        }
    }

}