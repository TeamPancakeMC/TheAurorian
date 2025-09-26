package cn.teampancake.theaurorian.common.level.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/** @noinspection deprecation*/
public class LargeLake2Feature extends Feature<LargeLake2Feature.Configuration> {

    private static final NormalNoise.NoiseParameters SHAPE_NOISE = new NormalNoise.NoiseParameters(-3, 1.0, 1.0, 2.0, 2.0);
    private static final NormalNoise.NoiseParameters EDGE_NOISE = new NormalNoise.NoiseParameters(0, 1.5, 1.0, 1.0);
    private static final NormalNoise.NoiseParameters DEPTH_NOISE = new NormalNoise.NoiseParameters(1, 1.0, 0.5, 0.5);

    public LargeLake2Feature() {
        super(Configuration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<Configuration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        Configuration config = context.config();
        int radius = config.radius().sample(random);
        int depth = config.depth().sample(random);
        if (!this.isSuitableTerrain(level, origin, radius)) return false;
        BlockState innerWallBlock = config.innerWallBlock();
        BlockState fluidBlock = config.fluidBlock();
        NormalNoise shapeNoise = NormalNoise.create(random, SHAPE_NOISE);
        NormalNoise edgeNoise = NormalNoise.create(random, EDGE_NOISE);
        NormalNoise depthNoise = NormalNoise.create(random, DEPTH_NOISE);
        Set<BlockPos> processed = new HashSet<>();
        this.generateLakeBasin(level, origin, radius, depth,
                shapeNoise, edgeNoise, depthNoise,
                fluidBlock, innerWallBlock, processed);
        return true;
    }

    private boolean isSuitableTerrain(WorldGenLevel level, BlockPos center, int radius) {
        int samplePoints = 0;
        int steepPoints = 0;
        int maxHeightDifference = 4;
        Integer centerHeight = this.findTrueSurfaceHeight(level, center);
        if (centerHeight == null) return false;
        for (int x = -radius; x <= radius; x += 2) {
            for (int z = -radius; z <= radius; z += 2) {
                double distance = Math.sqrt(x * x + z * z);
                if (distance > radius) continue;
                BlockPos samplePos = center.offset(x, 0, z);
                Integer surfaceHeight = this.findTrueSurfaceHeight(level, samplePos);
                if (surfaceHeight == null) continue;
                samplePoints++;
                int heightDiff = Math.abs(surfaceHeight - centerHeight);
                if (heightDiff > maxHeightDifference) {
                    steepPoints++;
                }
            }
        }

        if (samplePoints == 0) return false;
        double steepRatio = (double) steepPoints / samplePoints;
        return steepRatio < 0.3;
    }

    private void generateLakeBasin(
            WorldGenLevel level, BlockPos surfaceOrigin, int baseRadius, int baseDepth,
            NormalNoise shapeNoise, NormalNoise edgeNoise, NormalNoise depthNoise,
            BlockState fluidBlock, BlockState innerWallBlock, Set<BlockPos> processed) {
        int searchRadius = Mth.floor(baseRadius * 1.8);
        int centerSurfaceHeight = surfaceOrigin.getY();
        for (int x = -searchRadius; x <= searchRadius; x++) {
            for (int z = -searchRadius; z <= searchRadius; z++) {
                BlockPos currentPos = surfaceOrigin.offset(x, 0, z);
                double normX = (double) x / baseRadius;
                double normZ = (double) z / baseRadius;
                double baseDistance = Math.sqrt(normX * normX + normZ * normZ);
                if (baseDistance > 1.8) continue;
                double shapeNoiseValue = shapeNoise.getValue(x * 0.15, z * 0.15, 0) * 0.3;
                double edgeNoiseValue = edgeNoise.getValue(x * 0.25, z * 0.25, 0) * 0.2;
                double combinedNoise = shapeNoiseValue + edgeNoiseValue;
                double effectiveRadius = 1.0 + combinedNoise;
                double boundaryDistance = baseDistance / effectiveRadius;
                double smoothBoundary = this.smoothStep(boundaryDistance);
                if (smoothBoundary < 1.0) {
                    Integer surfaceHeight = this.findTrueSurfaceHeight(level, currentPos);
                    if (surfaceHeight == null) continue;
                    int heightDifference = surfaceHeight - centerSurfaceHeight;
                    if (heightDifference > 3) {
                        this.generateVisibleShallowLake(level, currentPos, surfaceHeight,
                                heightDifference, fluidBlock, innerWallBlock, processed);
                    } else if (heightDifference < -3) {
                        this.generateNormalLakeFromSurface(level, currentPos, surfaceHeight, baseDepth,
                                smoothBoundary, fluidBlock, innerWallBlock, processed, depthNoise, x, z);
                    } else {
                        this.generateNormalLakeFromSurface(level, currentPos, surfaceHeight, baseDepth,
                                smoothBoundary, fluidBlock, innerWallBlock, processed, depthNoise, x, z);
                    }
                }
            }
        }
    }

    private void generateVisibleShallowLake(
            WorldGenLevel level, BlockPos pos, int surfaceHeight, int heightDifference,
            BlockState fluidBlock, BlockState innerWallBlock, Set<BlockPos> processed) {
        int minExcavationDepth = 1;
        int maxExcavationDepth = Math.min(3, heightDifference);
        int excavationDepth = Math.max(minExcavationDepth, maxExcavationDepth);
        int lakeBottomY = surfaceHeight - excavationDepth;
        for (int y = surfaceHeight; y >= lakeBottomY; y--) {
            BlockPos currentPos = new BlockPos(pos.getX(), y, pos.getZ()).above(4);
            if (processed.contains(currentPos)) continue;
            processed.add(currentPos);
            if (y == surfaceHeight) {
                this.setFluidBlock(level, currentPos, fluidBlock);
            } else if (y > surfaceHeight - 2) {
                this.setFluidBlock(level, currentPos, fluidBlock);
            } else {
                level.setBlock(currentPos, innerWallBlock, 3);
            }
        }
    }

    private void generateNormalLakeFromSurface(
            WorldGenLevel level, BlockPos pos, int surfaceHeight, int baseDepth,
            double boundaryFactor, BlockState fluidBlock, BlockState innerWallBlock,
            Set<BlockPos> processed, NormalNoise depthNoise, int x, int z) {
        double depthNoiseValue = depthNoise.getValue(x * 0.2, z * 0.2, 0) * 0.3 + 0.7;
        int localDepth = (int)(baseDepth * depthNoiseValue * (1.0 - boundaryFactor * 0.8));
        localDepth = Math.max(localDepth, 2);
        int lakeBottomY = surfaceHeight - localDepth;
        lakeBottomY = Math.max(lakeBottomY, level.getMinBuildHeight() + 3);
        for (int y = surfaceHeight; y >= lakeBottomY; y--) {
            BlockPos currentPos = new BlockPos(pos.getX(), y, pos.getZ()).above(4);
            if (processed.contains(currentPos)) continue;
            processed.add(currentPos);
            double layerDepth = (double) (surfaceHeight - y) / localDepth;
            double profileShape = this.calculateProfileShape(boundaryFactor);
            double layerFactor = this.calculateLayerFactor(layerDepth, boundaryFactor, profileShape);
            if (layerFactor > 0.3) {
                if (y > lakeBottomY) {
                    if (y >= surfaceHeight - 1 || shouldPlaceFluid(layerDepth, boundaryFactor)) {
                        this.setFluidBlock(level, currentPos, fluidBlock);
                    } else {
                        if (level.getBlockState(currentPos).isSolid()) {
                            level.setBlock(currentPos, fluidBlock, 3);
                        }
                    }
                } else {
                    level.setBlock(currentPos, innerWallBlock, 3);
                }
            }
        }
    }

    private void generateLimitedHeightLake(
            WorldGenLevel level, BlockPos pos, int surfaceHeight, int heightDifference,
            BlockState fluidBlock, BlockState innerWallBlock, Set<BlockPos> processed) {
        int excavationDepth = Math.min(3, heightDifference);
        int lakeBottomY = surfaceHeight - excavationDepth;
        for (int y = surfaceHeight; y >= lakeBottomY; y--) {
            BlockPos currentPos = new BlockPos(pos.getX(), y, pos.getZ());
            if (processed.contains(currentPos)) continue;
            processed.add(currentPos);
            if (y <= surfaceHeight - 2) {
                this.setFluidBlock(level, currentPos, fluidBlock);
            } else {
                if (y == surfaceHeight - excavationDepth) {
                    level.setBlock(currentPos, innerWallBlock, 3);
                } else {
                    level.setBlock(currentPos, fluidBlock, 3);
                }
            }
        }
    }

    private void generateNormalLakeProfile(
            WorldGenLevel level, BlockPos pos, int surfaceHeight, int baseDepth,
            double boundaryFactor, BlockState fluidBlock, BlockState innerWallBlock,
            Set<BlockPos> processed, NormalNoise depthNoise, int x, int z) {
        double depthNoiseValue = depthNoise.getValue(x * 0.2, z * 0.2, 0) * 0.3 + 0.7;
        int localDepth = Mth.floor(baseDepth * depthNoiseValue * (1.0 - boundaryFactor * 0.8));
        localDepth = Math.max(localDepth, 2);
        int lakeBottomY = surfaceHeight - localDepth;
        lakeBottomY = Math.max(lakeBottomY, level.getMinBuildHeight() + 3);
        for (int y = surfaceHeight; y >= lakeBottomY; y--) {
            BlockPos currentPos = new BlockPos(pos.getX(), y, pos.getZ());
            if (processed.contains(currentPos)) continue;
            processed.add(currentPos);
            double layerDepth = (double) (surfaceHeight - y) / localDepth;
            double profileShape = this.calculateProfileShape(boundaryFactor);
            double layerFactor = this.calculateLayerFactor(layerDepth, boundaryFactor, profileShape);
            if (layerFactor > 0.3) {
                if (y > lakeBottomY) {
                    if (this.shouldPlaceFluid(layerDepth, boundaryFactor)) {
                        this.setFluidBlock(level, currentPos, fluidBlock);
                    } else {
                        if (level.getBlockState(currentPos).isSolid()) {
                            level.setBlock(currentPos, fluidBlock, 3);
                        }
                    }
                } else {
                    level.setBlock(currentPos, innerWallBlock, 3);
                }
            }
        }
    }

    private double calculateProfileShape(double boundaryFactor) {
        if (boundaryFactor < 0.3) {
            return 1.0;
        } else if (boundaryFactor < 0.7) {
            return 0.7;
        } else {
            return 0.3;
        }
    }

    private double calculateLayerFactor(double layerDepth, double boundaryFactor, double profileShape) {
        double centerBias = 1.0 - boundaryFactor;
        double depthRequirement = layerDepth * (0.3 + boundaryFactor * 0.7);
        return centerBias * profileShape - depthRequirement;
    }

    private boolean shouldPlaceFluid(double layerDepth, double boundaryFactor) {
        return (1.0 - boundaryFactor) * (0.3 + layerDepth * 0.7) > 0.4;
    }

    private boolean isSolidGround(BlockState state) {
        return state.isSolid() && !state.getFluidState().isEmpty() && !state.isAir();
    }

    private boolean isAirOrReplaceable(BlockState state) {
        return state.isAir() || !state.isSolid() || state.canBeReplaced();
    }

    private double smoothStep(double x) {
        if (x <= 0) return 0;
        if (x >= 1) return 1;
        return x * x * (3 - 2 * x);
    }

    private double smootherStep(double x) {
        if (x <= 0) return 0;
        if (x >= 1) return 1;
        return x * x * x * (x * (x * 6 - 15) + 10);
    }

    @Nullable
    private Integer findTrueSurfaceHeight(WorldGenLevel level, BlockPos pos) {
        for (int y = level.getMaxBuildHeight() - 1; y > level.getMinBuildHeight() + 5; y--) {
            BlockPos testPos = new BlockPos(pos.getX(), y, pos.getZ());
            BlockState currentState = level.getBlockState(testPos);
            BlockState aboveState = level.getBlockState(testPos.above());
            if (isSolidGround(currentState) && isAirOrReplaceable(aboveState)) {
                boolean hasEnoughAir = true;
                for (int i = 1; i <= 3; i++) {
                    if (!isAirOrReplaceable(level.getBlockState(testPos.above(i)))) {
                        hasEnoughAir = false;
                        break;
                    }
                }

                if (hasEnoughAir) {
                    return y;
                }
            }
        }

        for (int y = level.getMaxBuildHeight() - 1; y > level.getMinBuildHeight() + 5; y--) {
            BlockPos testPos = new BlockPos(pos.getX(), y, pos.getZ());
            BlockState currentState = level.getBlockState(testPos);
            BlockState aboveState = level.getBlockState(testPos.above());
            if (isSolidGround(currentState) && isAirOrReplaceable(aboveState)) {
                return y;
            }
        }

        return null;
    }

    @Nullable
    private Integer findSurfaceHeight(WorldGenLevel level, BlockPos pos) {
        int solidCount = 0;
        int lastSolidY;
        for (int y = level.getMaxBuildHeight() - 1; y > level.getMinBuildHeight() + 10; y--) {
            BlockPos testPos = new BlockPos(pos.getX(), y, pos.getZ());
            BlockState state = level.getBlockState(testPos);
            if (state.isSolid()) {
                solidCount++;
                lastSolidY = y;
                if (solidCount >= 3) {
                    int airCount = 0;
                    for (int i = 1; i <= 5; i++) {
                        if (level.isEmptyBlock(testPos.above(i))) {
                            airCount++;
                        }
                    }

                    if (airCount >= 3) {
                        return lastSolidY - 2;
                    }
                }
            } else {
                solidCount = 0;
            }
        }

        return null;
    }

    private void setFluidBlock(WorldGenLevel level, BlockPos pos, BlockState fluidBlock) {
        level.setBlock(pos, fluidBlock, 3);
        if (level.isEmptyBlock(pos.below()) && pos.getY() > level.getMinBuildHeight() + 2) {
            level.setBlock(pos.below(), fluidBlock, 3);
        }
    }

    public record Configuration(IntProvider radius, IntProvider depth, BlockState innerWallBlock, BlockState topEdgeBlock, BlockState fluidBlock) implements FeatureConfiguration {

        public static final Codec<Configuration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                IntProvider.CODEC.fieldOf("radius").forGetter(Configuration::radius),
                IntProvider.CODEC.fieldOf("depth").forGetter(Configuration::depth),
                BlockState.CODEC.fieldOf("inner_wall_block").forGetter(Configuration::innerWallBlock),
                BlockState.CODEC.fieldOf("top_edge_block").forGetter(Configuration::topEdgeBlock),
                BlockState.CODEC.fieldOf("fluid_block").forGetter(Configuration::fluidBlock)
        ).apply(instance, Configuration::new));

    }

}