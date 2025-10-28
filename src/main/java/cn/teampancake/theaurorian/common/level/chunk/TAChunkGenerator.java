package cn.teampancake.theaurorian.common.level.chunk;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.CrashReport;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FeatureSorter;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings({"ConstantConditions", "deprecation"})
public class TAChunkGenerator extends NoiseBasedChunkGenerator {

    public static final MapCodec<TAChunkGenerator> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            ChunkGenerator.CODEC.fieldOf("wrapped_generator").forGetter(o -> o.chunkGenerator),
            NoiseGeneratorSettings.CODEC.fieldOf("noise_generation_settings").forGetter(o -> o.noiseGeneratorSettings)
    ).apply(instance, TAChunkGenerator::new));

    private final ChunkGenerator chunkGenerator;
    private final Holder<NoiseGeneratorSettings> noiseGeneratorSettings;

    public TAChunkGenerator(ChunkGenerator chunkGenerator, Holder<NoiseGeneratorSettings> noiseGenSettings) {
        super(chunkGenerator.getBiomeSource(), noiseGenSettings);
        this.chunkGenerator = chunkGenerator;
        this.noiseGeneratorSettings = noiseGenSettings;
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

//    @Override
//    public CompletableFuture<ChunkAccess> createBiomes(RandomState random, Blender blender, StructureManager manager, ChunkAccess chunkAccess) {
//        return CompletableFuture.supplyAsync(Util.wrapThreadWithTaskName("init_biomes", () -> {
//            chunkAccess.fillBiomesFromNoise(this.getBiomeSource(), Climate.empty());
//            return chunkAccess;
//        }), Util.backgroundExecutor());
//    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion level) {
        if (!this.generatorSettings().value().disableMobGeneration()) {
            ChunkPos chunkPos = level.getCenter();
            Holder<Biome> holder = level.getBiome(chunkPos.getWorldPosition().atY(level.getMaxBuildHeight() - 1));
            WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(RandomSupport.generateUniqueSeed()));
            worldgenRandom.setDecorationSeed(level.getSeed(), chunkPos.getMinBlockX(), chunkPos.getMinBlockZ());
            spawnMobsForChunkGeneration(level, holder, chunkPos, worldgenRandom);
        }
    }

    public static void spawnMobsForChunkGeneration(ServerLevelAccessor levelAccessor, Holder<Biome> biome, ChunkPos chunkPos, RandomSource random) {
        MobSpawnSettings mobSpawnSettings = biome.value().getMobSettings();
        WeightedRandomList<MobSpawnSettings.SpawnerData> weightedRandomList = mobSpawnSettings.getMobs(MobCategory.CREATURE);
        boolean isBloodMoon = TAWorldEvents.BLOOD_MOON.get().isActive(levelAccessor.getLevel());
        if (!weightedRandomList.isEmpty()) {
            int i = chunkPos.getMinBlockX();
            int j = chunkPos.getMinBlockZ();
            int m = isBloodMoon ? 3 : 1;
            while (random.nextFloat() < mobSpawnSettings.getCreatureProbability() * m) {
                Optional<MobSpawnSettings.SpawnerData> optional = weightedRandomList.getRandom(random);
                if (optional.isPresent()) {
                    MobSpawnSettings.SpawnerData spawnerData = optional.get();
                    int k = spawnerData.minCount + random.nextInt(1 + spawnerData.maxCount - spawnerData.minCount);
                    SpawnGroupData spawnGroupData = null;
                    int l = i + random.nextInt(16);
                    int i1 = j + random.nextInt(16);
                    int j1 = l;
                    int k1 = i1;
                    for (int l1 = 0; l1 < k * m; l1++) {
                        boolean flag = false;
                        for (int i2 = 0; !flag && i2 < 4; i2++) {
                            BlockPos blockPos = getTopNonCollidingPos(levelAccessor, spawnerData.type, l, i1);
                            if (spawnerData.type.canSummon() && SpawnPlacements.isSpawnPositionOk(spawnerData.type, levelAccessor, blockPos)) {
                                double f = spawnerData.type.getWidth();
                                double d0 = Mth.clamp(l, (double)i + f, (double)i + 16.0 - f);
                                double d1 = Mth.clamp(i1, (double)j + f, (double)j + 16.0 - f);
                                if (!levelAccessor.noCollision(spawnerData.type.getSpawnAABB(d0, blockPos.getY(), d1))
                                        || !SpawnPlacements.checkSpawnRules(
                                        spawnerData.type,
                                        levelAccessor,
                                        MobSpawnType.CHUNK_GENERATION,
                                        BlockPos.containing(d0, blockPos.getY(), d1),
                                        levelAccessor.getRandom())) {
                                    continue;
                                }

                                Entity entity;
                                try {
                                    entity = spawnerData.type.create(levelAccessor.getLevel());
                                } catch (Exception exception) {
                                    TheAurorian.LOGGER.warn("Failed to create mob", exception);
                                    continue;
                                }

                                if (entity == null) continue;
                                entity.moveTo(d0, blockPos.getY(), d1, random.nextFloat() * 360.0F, 0.0F);
                                if (entity instanceof Mob mob && EventHooks.checkSpawnPosition(mob, levelAccessor, MobSpawnType.CHUNK_GENERATION)) {
                                    DifficultyInstance difficulty = levelAccessor.getCurrentDifficultyAt(mob.blockPosition());
                                    spawnGroupData = mob.finalizeSpawn(levelAccessor, difficulty, MobSpawnType.CHUNK_GENERATION, spawnGroupData);
                                    levelAccessor.addFreshEntityWithPassengers(mob);
                                    flag = true;
                                }
                            }

                            l += random.nextInt(5) - random.nextInt(5);
                            for (i1 += random.nextInt(5) - random.nextInt(5);
                                 l < i || l >= i + 16 || i1 < j || i1 >= j + 16;
                                 i1 = k1 + random.nextInt(5) - random.nextInt(5)) {
                                l = j1 + random.nextInt(5) - random.nextInt(5);
                            }
                        }
                    }
                }
            }
        }
    }

    private static BlockPos getTopNonCollidingPos(LevelReader level, EntityType<?> entityType, int x, int z) {
        int i = level.getHeight(SpawnPlacements.getHeightmapType(entityType), x, z);
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(x, i, z);
        if (level.dimensionType().hasCeiling()) {
            do {
                mutableBlockPos.move(Direction.DOWN);
            } while (!level.getBlockState(mutableBlockPos).isAir());

            do {
                mutableBlockPos.move(Direction.DOWN);
            } while (level.getBlockState(mutableBlockPos).isAir() && mutableBlockPos.getY() > level.getMinBuildHeight());
        }

        return SpawnPlacements.getPlacementType(entityType).adjustSpawnPosition(level, mutableBlockPos.immutable());
    }

}