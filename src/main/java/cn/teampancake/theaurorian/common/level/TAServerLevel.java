package cn.teampancake.theaurorian.common.level;

import cn.teampancake.theaurorian.client.renderer.level.TASkyRenderer;
import cn.teampancake.theaurorian.common.event.TAEventFactory;
import cn.teampancake.theaurorian.common.network.FutureNightS2CPacket;
import cn.teampancake.theaurorian.common.network.NightTypeS2CPacket;
import cn.teampancake.theaurorian.common.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

public class TAServerLevel extends ServerLevel {

    private int dayCount;
    private int phaseCode = 0;
    private final Queue<Integer> futurePhase = new LinkedList<>();

    public TAServerLevel(
            MinecraftServer server, Executor dispatcher, LevelStorageSource.LevelStorageAccess levelStorageAccess, ServerLevelData serverLevelData, ResourceKey<Level> dimension, LevelStem levelStem,
            ChunkProgressListener progressListener, boolean isDebug, long biomeZoomSeed, List<CustomSpawner> customSpawners, boolean tickTime, @Nullable RandomSequences randomSequences) {
        super(server, dispatcher, levelStorageAccess, serverLevelData, dimension, levelStem, progressListener, isDebug, biomeZoomSeed, customSpawners, tickTime, randomSequences);
    }

    @Override
    public void tick(BooleanSupplier hasTimeLeft) {
        super.tick(hasTimeLeft);
        List<ServerPlayer> playerList = this.players();
        long dayCounter = (this.dayTime() + 6000L) / 24000L;
        long dayTime = (this.dayTime() + 6000L) % 24000L;
        if (dayCounter != this.dayCount) {
            this.dayCount = Mth.floor(dayCounter);
            if (this.futurePhase.size() < 4) {
                this.futurePhase.add(this.random.nextInt(TASkyRenderer.DaySkyColors.size()));
            }

            this.phaseCode = this.futurePhase.remove();
            Integer[] list = this.futurePhase.toArray(Integer[]::new);
            int[] futureNight = Arrays.stream(list).mapToInt(Integer::valueOf).toArray();
            for (ServerPlayer serverPlayer : playerList) {
                PacketDistributor.sendToPlayer(serverPlayer, new NightTypeS2CPacket(this.phaseCode));
                PacketDistributor.sendToPlayer(serverPlayer, new FutureNightS2CPacket(futureNight));
            }
        }

        if (dayTime % 200 == 0) {
            for (ServerPlayer serverPlayer : playerList) {
                if (dayTime > 6000 && dayTime <= 18000) {
                    if (!serverPlayer.getData(TAAttachmentTypes.IMMUNE_TO_PRESSURE)) {
                        serverPlayer.addEffect(blessEffect(TAMobEffects.PRESSURE));
                    }
                } else {
                    if (this.getGameRules().getBoolean(TAGameRules.RULE_ENABLE_AURORIAN_BLESS)) {
                        if (this.phaseCode == 2) {
                            serverPlayer.addEffect(blessEffect(MobEffects.MOVEMENT_SPEED));
                        } else if (this.phaseCode == 0) {
                            serverPlayer.addEffect(blessEffect(MobEffects.DAMAGE_BOOST));
                            serverPlayer.addEffect(blessEffect(MobEffects.DAMAGE_RESISTANCE));
                        } else if (this.phaseCode == 3) {
                            serverPlayer.addEffect(blessEffect(MobEffects.DIG_SPEED));
                        } else if (this.phaseCode == 1) {
                            serverPlayer.addEffect(blessEffect(TAMobEffects.TOUGH));
                        } else {
                            TAEventFactory.onRegisterAurorianSkyBless(serverPlayer, this.getLevel(), this.phaseCode);
                        }
                    }
                }
            }
        }
    }

    public void tickChunk(LevelChunk chunk, int randomTickSpeed) {
        ChunkPos chunkpos = chunk.getPos();
        int i = chunkpos.getMinBlockX();
        int j = chunkpos.getMinBlockZ();
        int scale = this.phaseCode == 4 ? 3 : 1;
        ProfilerFiller profiler = this.getProfiler();
        profiler.popPush("iceAndSnow");
        for (int i1 = 0; i1 < randomTickSpeed; i1++) {
            if (this.random.nextInt(48) == 0) {
                this.tickPrecipitation(this.getBlockRandomPos(i, 0, j, 15));
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
                        BlockPos randomPos = this.getBlockRandomPos(i, k, j, 15);
                        profiler.push("randomTick");
                        int x = randomPos.getX() - i;
                        int y = randomPos.getY() - k;
                        int z = randomPos.getZ() - j;
                        BlockState state = chunkSection.getBlockState(x, y, z);
                        if (state.isRandomlyTicking() && state.is(BlockTags.CROPS)) {
                            state.randomTick(this, randomPos, this.random);
                        }

                        FluidState fluidState = state.getFluidState();
                        if (fluidState.isRandomlyTicking()) {
                            fluidState.randomTick(this, randomPos, this.random);
                        }

                        profiler.pop();
                    }
                }
            }
        }

        profiler.pop();
    }

    @Override
    public void tickPrecipitation(BlockPos blockPos) {
        BlockPos heightmapPos = this.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos);
        BlockPos belowPos = heightmapPos.below();
        Biome biome = this.getBiome(heightmapPos).value();
        boolean flag = this.getBiome(belowPos).is(TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD);
        if (this.isAreaLoaded(belowPos, 1) && biome.shouldFreeze(this, belowPos)) {
            Block block = flag ? TABlocks.FILTHY_ICE.get() : Blocks.ICE;
            this.setBlockAndUpdate(belowPos, block.defaultBlockState());
        }

        if (this.isRaining() || flag) {
            int i = this.getGameRules().getInt(GameRules.RULE_SNOW_ACCUMULATION_HEIGHT);
            if (i > 0 && biome.shouldSnow(this, heightmapPos)) {
                BlockState blockState = this.getBlockState(heightmapPos);
                if (blockState.is(Blocks.SNOW)) {
                    int j = blockState.getValue(SnowLayerBlock.LAYERS);
                    if (j < Math.min(i, 8)) {
                        BlockState snowLayerState = blockState.setValue(SnowLayerBlock.LAYERS, j + 1);
                        Block.pushEntitiesUp(blockState, snowLayerState, this, heightmapPos);
                        this.setBlockAndUpdate(heightmapPos, snowLayerState);
                    }
                } else {
                    this.setBlockAndUpdate(heightmapPos, Blocks.SNOW.defaultBlockState());
                }
            }

            Biome.Precipitation precipitation = biome.getPrecipitationAt(belowPos);
            if (precipitation != Biome.Precipitation.NONE) {
                BlockState blockState = this.getBlockState(belowPos);
                blockState.getBlock().handlePrecipitation(blockState, this, belowPos, precipitation);
            }
        }
    }

    @Override
    public void updateSkyBrightness() {
        float time = this.dimensionType().timeOfDay(this.getDayTime());
        double rainLevel = 1.0D - (double)(this.getRainLevel(1.0F) * 5.0F) / 16.0D;
        double thunderLevel = 1.0D - (double)(this.getThunderLevel(1.0F) * 5.0F) / 16.0D;
        double d2 = 0.5D + 2.0D * Mth.clamp(Mth.cos(time * Mth.TWO_PI), -0.25D, 0.25D);
        this.skyDarken = Math.max(5, (int)((1.0D - d2 * rainLevel * thunderLevel) * 11.0D));
    }

    private static MobEffectInstance blessEffect(Holder<MobEffect> effect) {
        return new MobEffectInstance(effect, 320, 0, false, false);
    }

}