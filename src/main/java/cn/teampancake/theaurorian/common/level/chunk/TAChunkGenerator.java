package cn.teampancake.theaurorian.common.level.chunk;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.biome.TABiomeSource;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAWorldEvents;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.*;
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
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.FeatureSorter;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "ConstantConditions", "deprecation"})
public class TAChunkGenerator extends NoiseBasedChunkGenerator {

    public static final MapCodec<TAChunkGenerator> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            ChunkGenerator.CODEC.fieldOf("wrapped_generator").forGetter(o -> o.chunkGenerator),
            NoiseGeneratorSettings.CODEC.fieldOf("noise_generation_settings").forGetter(o -> o.noiseGeneratorSettings)
    ).apply(instance, TAChunkGenerator::new));

    private final ChunkGenerator chunkGenerator;
    private final BlockState defaultBlock;
    private final BlockState defaultFluid;
    private final Optional<TANoiseSampler> warper;
    private final Holder<NoiseGeneratorSettings> noiseGeneratorSettings;
    private static final BlockState[] EMPTY_COLUMN = new BlockState[0];

    public TAChunkGenerator(ChunkGenerator chunkGenerator, Holder<NoiseGeneratorSettings> noiseGenSettings) {
        super(chunkGenerator.getBiomeSource(), noiseGenSettings);
        this.chunkGenerator = chunkGenerator;
        this.noiseGeneratorSettings = noiseGenSettings;
        if (chunkGenerator instanceof NoiseBasedChunkGenerator noiseGen && noiseGen.generatorSettings().isBound()) {
            this.defaultBlock = noiseGen.generatorSettings().value().defaultBlock();
            this.defaultFluid = noiseGen.generatorSettings().value().defaultFluid();
        } else {
            this.defaultBlock = TABlocks.AURORIAN_STONE.get().defaultBlockState();
            this.defaultFluid = Blocks.WATER.defaultBlockState();
        }

        if (noiseGenSettings.isBound()) {
            NoiseSettings settings = noiseGenSettings.value().noiseSettings();
            if (chunkGenerator.getBiomeSource() instanceof TABiomeSource source) {
                WorldgenRandom random = new WorldgenRandom(new LegacyRandomSource(0L));
                TABlendedNoise blendedNoise = new TABlendedNoise(random);
                NoiseModifier modifier = NoiseModifier.PASS_THROUGH;
                int yCount = settings.height() / settings.getCellHeight();
                NoiseSlider topSlide = new NoiseSlider(-10.0D, 3, 0);
                NoiseSlider bottomSlide = new NoiseSlider(15.0D, 3, 0);
                this.warper = Optional.of(new TANoiseSampler(settings.getCellWidth(), settings.getCellHeight(),
                        yCount, source, topSlide, bottomSlide, settings, blendedNoise, modifier));
            } else {
                this.warper = Optional.empty();
            }
        } else {
            this.warper = Optional.empty();
        }
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

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState random, StructureManager structureManager, ChunkAccess chunkAccess) {
        if (this.warper.isEmpty()) {
            return super.fillFromNoise(blender, random, structureManager, chunkAccess);
        } else {
            NoiseSettings settings = this.noiseGeneratorSettings.value().noiseSettings();
            int cellHeight = settings.getCellHeight();
            int minY = Math.max(settings.minY(), chunkAccess.getMinBuildHeight());
            int maxY = Math.min(settings.minY() + settings.height(), chunkAccess.getMaxBuildHeight());
            int minCell = Math.floorDiv(minY, cellHeight);
            int maxCell = Math.floorDiv(maxY - minY, cellHeight);
            if (maxCell <= 0) {
                return CompletableFuture.completedFuture(chunkAccess);
            } else {
                int maxIndex = chunkAccess.getSectionIndex(maxCell * cellHeight - 1 + minY);
                int minIndex = chunkAccess.getSectionIndex(minY);
                Set<LevelChunkSection> sections = Sets.newHashSet();
                for (int index = maxIndex; index >= minIndex; index--) {
                    LevelChunkSection section = chunkAccess.getSection(index);
                    section.acquire();
                    sections.add(section);
                }

                return CompletableFuture.supplyAsync(() -> this.doFill(random, chunkAccess, minCell, maxCell), Util.backgroundExecutor()).whenCompleteAsync((chunk, throwable) -> {
                    for (LevelChunkSection section : sections) {
                        section.release();
                    }
                }, Util.backgroundExecutor());
            }
        }
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types heightMap, LevelHeightAccessor level, RandomState random) {
        if (this.warper.isEmpty()) {
            return super.getBaseHeight(x, z, heightMap, level, random);
        } else {
            NoiseSettings settings = this.noiseGeneratorSettings.value().noiseSettings();
            int minY = Math.max(settings.minY(), level.getMinBuildHeight());
            int maxY = Math.min(settings.minY() + settings.height(), level.getMaxBuildHeight());
            int minCell = Math.floorDiv(minY, settings.getCellHeight());
            int maxCell = Math.floorDiv(maxY - minY, settings.getCellHeight());
            return maxCell <= 0 ? level.getMinBuildHeight() : this.iterateNoiseColumn(x, z, null, heightMap.isOpaque(), minCell, maxCell).orElse(level.getMinBuildHeight());
        }
    }

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor level, RandomState random) {
        if (this.warper.isEmpty()) {
            return super.getBaseColumn(x, z, level, random);
        } else {
            NoiseSettings settings = this.noiseGeneratorSettings.value().noiseSettings();
            int minY = Math.max(settings.minY(), level.getMinBuildHeight());
            int maxY = Math.min(settings.minY() + settings.height(), level.getMaxBuildHeight());
            int minCell = Math.floorDiv(minY, settings.getCellHeight());
            int maxCell = Math.floorDiv(maxY - minY, settings.getCellHeight());
            if (maxCell <= 0) {
                return new NoiseColumn(minY, EMPTY_COLUMN);
            } else {
                BlockState[] states = new BlockState[maxCell * settings.getCellHeight()];
                this.iterateNoiseColumn(x, z, states, null, minCell, maxCell);
                return new NoiseColumn(minY, states);
            }
        }
    }

    protected OptionalInt iterateNoiseColumn(int x, int z, BlockState[] states, @Nullable Predicate<BlockState> predicate, int min, int max) {
        NoiseSettings settings = this.noiseGeneratorSettings.value().noiseSettings();
        int cellWidth = settings.getCellWidth();
        int cellHeight = settings.getCellHeight();
        int xDiv = Math.floorDiv(x, cellWidth);
        int zDiv = Math.floorDiv(z, cellWidth);
        int xMod = Math.floorMod(x, cellWidth);
        int zMod = Math.floorMod(z, cellWidth);
        int xMin = xMod / cellWidth;
        int zMin = zMod / cellWidth;
        double[][] columns = new double[][] {
                this.makeAndFillNoiseColumn(xDiv, zDiv, min, max),
                this.makeAndFillNoiseColumn(xDiv, zDiv + 1, min, max),
                this.makeAndFillNoiseColumn(xDiv + 1, zDiv, min, max),
                this.makeAndFillNoiseColumn(xDiv + 1, zDiv + 1, min, max)};

        for (int cell = max - 1; cell >= 0; cell--) {
            double d00 = columns[0][cell];
            double d10 = columns[1][cell];
            double d20 = columns[2][cell];
            double d30 = columns[3][cell];
            double d01 = columns[0][cell + 1];
            double d11 = columns[1][cell + 1];
            double d21 = columns[2][cell + 1];
            double d31 = columns[3][cell + 1];
            for (int height = cellHeight - 1; height >= 0; height--) {
                double dCell = height / (double)cellHeight;
                double lCell = Mth.lerp3(dCell, xMin, zMin, d00, d01, d20, d21, d10, d11, d30, d31);
                int layer = cell * cellHeight + height;
                int maxLayer = layer + min * cellHeight;
                BlockState state = this.generateBaseState(lCell, layer);
                if (states != null) {
                    states[layer] = state;
                }

                if (predicate != null && predicate.test(state)) {
                    return OptionalInt.of(maxLayer + 1);
                }
            }
        }

        return OptionalInt.empty();
    }

    @Override
    public CompletableFuture<ChunkAccess> createBiomes(RandomState random, Blender blender, StructureManager manager, ChunkAccess chunkAccess) {
        return CompletableFuture.supplyAsync(Util.wrapThreadWithTaskName("init_biomes", () -> {
            chunkAccess.fillBiomesFromNoise(this.getBiomeSource(), Climate.empty());
            return chunkAccess;
        }), Util.backgroundExecutor());
    }

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

    private ChunkAccess doFill(RandomState random, ChunkAccess access, int min, int max) {
        NoiseSettings settings = noiseGeneratorSettings.value().noiseSettings();
        int cellWidth = settings.getCellWidth();
        int cellHeight = settings.getCellHeight();
        int cellCountX = 16 / cellWidth;
        int cellCountZ = 16 / cellWidth;
        Heightmap oceanFloor = access.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
        Heightmap surface = access.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);
        ChunkPos chunkpos = access.getPos();
        int minX = chunkpos.getMinBlockX();
        int minZ = chunkpos.getMinBlockZ();
        TANoiseInterpolator interpolator = new TANoiseInterpolator(cellCountX, max, cellCountZ, chunkpos, min, (columns, x, z, min1, max1, max12) -> fillNoiseColumn(x, z, min1, max1, max12));
        List<TANoiseInterpolator> list = Lists.newArrayList(interpolator);
        list.forEach(noiseInt -> noiseInt.initialiseFirstX(random));
        for (int cellX = 0; cellX < cellCountX; cellX++) {
            int advX = cellX;
            list.forEach((noiseInt) -> noiseInt.advanceX(random, advX));
            for (int cellZ = 0; cellZ < cellCountZ; cellZ++) {
                int sections = access.getSectionsCount() - 1;
                LevelChunkSection section = access.getSection(sections);
                for (int cellY = max - 1; cellY >= 0; cellY--) {
                    int advY = cellY;
                    int advZ = cellZ;
                    list.forEach((noiseInt) -> noiseInt.selectYZ(advY, advZ));
                    for (int height = cellHeight - 1; height >= 0; height--) {
                        int minHeight = (min + cellY) * cellHeight + height;
                        int minCellY = minHeight & 15;
                        int minIndexY = access.getSectionIndex(minHeight);
                        if (sections != minIndexY) {
                            sections = minIndexY;
                            section = access.getSection(minIndexY);
                        }

                        double heightDiv = (double)height / (double)cellHeight;
                        list.forEach((noiseInt) -> noiseInt.updateY(heightDiv));
                        for (int widthX = 0; widthX < cellWidth; widthX++) {
                            int minWidthX = minX + cellX * cellWidth + widthX;
                            int minCellX = minWidthX & 15;
                            double widthDivX = (double)widthX / (double)cellWidth;
                            list.forEach((noiseInt) -> noiseInt.updateX(widthDivX));
                            for (int widthZ = 0; widthZ < cellWidth; widthZ++) {
                                int minWidthZ = minZ + cellZ * cellWidth + widthZ;
                                int minCellZ = minWidthZ & 15;
                                double widthDivZ = (double)widthZ / (double)cellWidth;
                                double noiseVal = interpolator.updateZ(widthDivZ);
                                BlockState state = this.generateBaseState(noiseVal, minHeight);
                                if (state != Blocks.AIR.defaultBlockState()) {
                                    section.setBlockState(minCellX, minCellY, minCellZ, state, false);
                                    oceanFloor.update(minCellX, minHeight, minCellZ, state);
                                    surface.update(minCellX, minHeight, minCellZ, state);
                                }
                            }
                        }
                    }
                }
            }

            list.forEach(TANoiseInterpolator::swapSlices);
        }

        return access;
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

    private double[] makeAndFillNoiseColumn(int x, int z, int min, int max) {
        double[] columns = new double[max + 1];
        this.fillNoiseColumn(columns, x, z, min, max);
        return columns;
    }

    private void fillNoiseColumn(double[] columns, int x, int z, int min, int max) {
        this.warper.ifPresent(taNoiseSampler -> taNoiseSampler.fillNoiseColumn(columns, x, z, min, max));
    }

    private BlockState generateBaseState(double noiseVal, double level) {
        BlockState state;
        if (noiseVal > 0.0D) {
            state = this.defaultBlock;
        } else if (level < this.getSeaLevel()) {
            state = this.defaultFluid;
        } else {
            state = Blocks.AIR.defaultBlockState();
        }

        return state;
    }

}