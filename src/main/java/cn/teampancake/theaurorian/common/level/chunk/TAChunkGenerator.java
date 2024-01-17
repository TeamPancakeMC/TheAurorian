package cn.teampancake.theaurorian.common.level.chunk;

import cn.teampancake.theaurorian.common.level.biome.TABiomeSource;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "ConstantConditions"})
public class TAChunkGenerator extends NoiseBasedChunkGenerator {

    public static final Codec<TAChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
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
            this.defaultBlock = noiseGen.generatorSettings().get().defaultBlock();
            this.defaultFluid = noiseGen.generatorSettings().get().defaultFluid();
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
                this.warper = Optional.of(new TANoiseSampler(settings.getCellWidth(), settings.getCellHeight(), settings.height() / settings.getCellHeight(), source, new NoiseSlider(-10.0D, 3, 0), new NoiseSlider(15.0D, 3, 0), settings, blendedNoise, modifier));
            } else {
                this.warper = Optional.empty();
            }
        } else {
            this.warper = Optional.empty();
        }
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, RandomState random, StructureManager structureManager, ChunkAccess chunkAccess) {
        if (this.warper.isEmpty()) {
            return super.fillFromNoise(executor, blender, random, structureManager, chunkAccess);
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
                }, executor);
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
    public CompletableFuture<ChunkAccess> createBiomes(Executor executor, RandomState random, Blender blender, StructureManager manager, ChunkAccess chunkAccess) {
        return CompletableFuture.supplyAsync(Util.wrapThreadWithTaskName("init_biomes", () -> {
            chunkAccess.fillBiomesFromNoise(this.getBiomeSource(), Climate.empty());
            return chunkAccess;
        }), Util.backgroundExecutor());
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