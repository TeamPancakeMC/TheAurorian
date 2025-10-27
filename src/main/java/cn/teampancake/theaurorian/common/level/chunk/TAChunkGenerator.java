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

    public void applyBiomeDecoration(WorldGenLevel level, ChunkAccess chunk, StructureManager structureManager) {
        ChunkPos chunkPos = chunk.getPos();
        if (!SharedConstants.debugVoidTerrain(chunkPos)) {
            SectionPos sectionPos = SectionPos.of(chunkPos, level.getMinSection());
            BlockPos blockPos = sectionPos.origin();
            Registry<Structure> structureRegistry = level.registryAccess().registryOrThrow(Registries.STRUCTURE);
            Map<Integer, List<Structure>> map = structureRegistry.stream().collect(Collectors.groupingBy(structure -> structure.step().ordinal()));
            List<FeatureSorter.StepFeatureData> list = this.featuresPerStep.get();
            WorldgenRandom worldgenRandom = new WorldgenRandom(new XoroshiroRandomSource(RandomSupport.generateUniqueSeed()));
            long i = worldgenRandom.setDecorationSeed(level.getSeed(), blockPos.getX(), blockPos.getZ());
            Set<Holder<Biome>> set = new ObjectArraySet<>();
            ChunkPos.rangeClosed(sectionPos.chunk(), 1).forEach(pos -> {
                ChunkAccess chunkAccess = level.getChunk(pos.x, pos.z);
                for (LevelChunkSection section : chunkAccess.getSections()) {
                    section.getBiomes().getAll(set::add);
                }
            });
            set.retainAll(this.biomeSource.possibleBiomes());
            int j = list.size();
            try {
                Registry<PlacedFeature> placedFeatureRegistry = level.registryAccess().registryOrThrow(Registries.PLACED_FEATURE);
                int i1 = Math.max(GenerationStep.Decoration.values().length, j);
                for (int k = 0; k < i1; k++) {
                    int l = 0;
                    for (Structure structure : map.getOrDefault(k, Collections.emptyList())) {
                        worldgenRandom.setFeatureSeed(i, l, k);
                        Supplier<String> supplier = () -> structureRegistry.getResourceKey(structure).map(Object::toString).orElseGet(structure::toString);
                        try {
                            level.setCurrentlyGenerating(supplier);
                            structureManager.startsForStructure(sectionPos, structure).forEach(
                                    structureStart -> structureStart.placeInChunk(level, structureManager, this, worldgenRandom, getWritableArea(chunk), chunkPos));
                        } catch (Exception exception) {
                            CrashReport crashReport = CrashReport.forThrowable(exception, "Feature placement");
                            crashReport.addCategory("Feature").setDetail("Description", supplier::get);
                            throw new ReportedException(crashReport);
                        }

                        l++;
                    }

                    if (k < j) {
                        IntSet intSet = new IntArraySet();
                        for (Holder<Biome> holder : set) {
                            List<HolderSet<PlacedFeature>> features = this.generationSettingsGetter.apply(holder).features();
                            if (k < features.size()) {
                                HolderSet<PlacedFeature> holderSet = features.get(k);
                                FeatureSorter.StepFeatureData stepFeatureData = list.get(k);
                                holderSet.stream().map(Holder::value).forEach(feature -> intSet.add(stepFeatureData.indexMapping().applyAsInt(feature)));
                            }
                        }

                        int j1 = intSet.size();
                        int[] intArray = intSet.toIntArray();
                        Arrays.sort(intArray);
                        FeatureSorter.StepFeatureData stepFeatureData = list.get(k);
                        for (int k1 = 0; k1 < j1; k1++) {
                            int l1 = intArray[k1];
                            PlacedFeature placedFeature = stepFeatureData.features().get(l1);
                            Supplier<String> supplier = () -> placedFeatureRegistry.getResourceKey(placedFeature).map(Object::toString).orElseGet(placedFeature::toString);
                            worldgenRandom.setFeatureSeed(i, l1, k);
                            try {
                                level.setCurrentlyGenerating(supplier);
                                placedFeature.placeWithBiomeCheck(level, this, worldgenRandom, blockPos);
                            } catch (Exception exception) {
                                CrashReport crashReport = CrashReport.forThrowable(exception, "Feature placement");
                                crashReport.addCategory("Feature").setDetail("Description", supplier::get);
                                throw new ReportedException(crashReport);
                            }
                        }
                    }
                }

                level.setCurrentlyGenerating(null);
            } catch (Exception exception) {
                CrashReport crashReport = CrashReport.forThrowable(exception, "Biome decoration");
                crashReport.addCategory("Generation")
                        .setDetail("CenterX", chunkPos.x)
                        .setDetail("CenterZ", chunkPos.z)
                        .setDetail("Decoration Seed", i);
                throw new ReportedException(crashReport);
            }
        }
    }

    private static BoundingBox getWritableArea(ChunkAccess chunk) {
        ChunkPos chunkPos = chunk.getPos();
        int i = chunkPos.getMinBlockX();
        int j = chunkPos.getMinBlockZ();
        LevelHeightAccessor accessor = chunk.getHeightAccessorForGeneration();
        int k = accessor.getMinBuildHeight() + 1;
        int l = accessor.getMaxBuildHeight() - 1;
        return new BoundingBox(i, k, j, i + 15, l, j + 15);
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