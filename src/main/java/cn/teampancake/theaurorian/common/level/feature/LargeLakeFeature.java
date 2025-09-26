package cn.teampancake.theaurorian.common.level.feature;

import com.mojang.math.Constants;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraft.world.phys.Vec2;

import java.util.ArrayList;
import java.util.List;

public class LargeLakeFeature extends Feature<LargeLakeFeature.Configuration> {

    public LargeLakeFeature() {
        super(Configuration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<Configuration> context) {
        WorldGenLevel world = context.level();
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        Configuration config = context.config();
        int radius = config.minRadius + random.nextInt(config.maxRadius - config.minRadius + 1);
        List<Vec2> lakeShape = this.generateLakeShape(random, radius, config.irregularity, config.pointCount);
        int surfaceHeight = this.findSurfaceHeight(world, origin);
        int bottomHeight = surfaceHeight - (int) (radius * config.depth);
        return this.generateLake(world, origin, surfaceHeight, bottomHeight, lakeShape, config);
    }

    private boolean generateLake(
            WorldGenLevel world, BlockPos center, int surfaceHeight,
            int bottomHeight, List<Vec2> lakeShape, Configuration config) {
        boolean placedAny = false;
        for (int y = surfaceHeight; y >= bottomHeight; y--) {
            float depthFactor = (float) (surfaceHeight - y) / (surfaceHeight - bottomHeight);
            float currentRadius = lakeShape.getFirst().length() * (1 - depthFactor * 0.3f);
            placedAny |= this.generateLakeLayer(world, center, y, currentRadius, lakeShape, config, depthFactor);
            placedAny |= this.fillLakeInterior(world, center, y, currentRadius, config);
        }

        return placedAny;
    }

    private List<Vec2> generateLakeShape(RandomSource random, int pointCount, float radius, float irregularity) {
        List<Vec2> points = new ArrayList<>();
        for (int i = 0; i < pointCount; i++) {
            float angle = i * 2.0F * Constants.PI / pointCount;
            points.add(new Vec2(Mth.cos(angle), Mth.sin(angle)));
        }

        this.applyIrregularity(points, random, radius, irregularity);
        this.smoothShape(points, 2);
        return points;
    }

    private void applyIrregularity(List<Vec2> points, RandomSource random, float radius, float irregularity) {
        for (Vec2 point : points) {
            ImprovedNoise noise = new ImprovedNoise(random);
            float noiseX = (float) noise.noise(point.x * 10, point.y * 10, random.nextFloat() * 100) * 0.5f;
            float noiseY = (float) noise.noise(point.y * 10, point.x * 10, random.nextFloat() * 100) * 0.5f;
            float offsetScale = radius * irregularity;
            point.add(new Vec2(noiseX * offsetScale, noiseY * offsetScale));
            float minDist = radius * 0.7f;
            float dist = point.length();
            if (dist < minDist) {
                point.scale(minDist / dist);
            }
        }
    }

    private void smoothShape(List<Vec2> points, int iterations) {
        for (int i = 0; i < iterations; i++) {
            List<Vec2> newPoints = new ArrayList<>();
            for (int j = 0; j < points.size(); j++) {
                Vec2 prev = points.get((j - 1 + points.size()) % points.size());
                Vec2 curr = points.get(j);
                Vec2 next = points.get((j + 1) % points.size());
                Vec2 smoothed = new Vec2(
                        (prev.x + curr.x + next.x) / 3,
                        (prev.y + curr.y + next.y) / 3);
                newPoints.add(smoothed);
            }

            points.clear();
            points.addAll(newPoints);
        }
    }

    private int findSurfaceHeight(WorldGenLevel world, BlockPos pos) {
        for (int y = world.getMaxBuildHeight(); y >= world.getMinBuildHeight(); y--) {
            BlockPos checkPos = new BlockPos(pos.getX(), y, pos.getZ());
            if (!world.getBlockState(checkPos).isAir()) return y;
        }

        return pos.getY();
    }

    private boolean generateLakeLayer(
            WorldGenLevel world, BlockPos center, int y,
            float radius, List<Vec2> lakeShape,
            Configuration config, float depthFactor) {
        boolean placedAny = false;
        for (Vec2 point : lakeShape) {
            Vec2 scaledPoint = new Vec2(point.x, point.y).normalized().scale(radius);
            int x = center.getX() + (int) scaledPoint.x;
            int z = center.getZ() + (int) scaledPoint.y;
            BlockPos pos = new BlockPos(x, y, z);
            if (world.getBlockState(pos).canBeReplaced()) {
                BlockState blockToPlace = this.getPreciseLayerBlockState(config, depthFactor, world, pos);
                world.setBlock(pos, blockToPlace, 2);
                placedAny = true;
            }
        }

        return placedAny;
    }

    private BlockState getPreciseLayerBlockState(Configuration config, float depthFactor, WorldGenLevel world, BlockPos pos) {
        if (this.isTopEdgePosition(config, world, pos)) {
            return config.topEdgeBlock;
        }

        if (depthFactor > 0.8f) {
            return config.edgeBlock;
        } else if (depthFactor < 0.2f) {
            return config.bottomBlock;
        } else {
            return config.innerWallBlock;
        }
    }

    private boolean fillLakeInterior(WorldGenLevel world, BlockPos center, int y, float radius, Configuration config) {
        boolean placedAny = false;
        int radiusInt = (int) radius;
        for (int x = -radiusInt; x <= radiusInt; x++) {
            for (int z = -radiusInt; z <= radiusInt; z++) {
                float distance = Mth.sqrt(x * x + z * z);
                if (distance <= radius * 0.9f) {
                    BlockPos pos = new BlockPos(center.getX() + x, y, center.getZ() + z);
                    if (world.getBlockState(pos).canBeReplaced()) {
                        BlockState blockToPlace = (y <= center.getY() - 2) ? config.bottomBlock  : config.fluidBlock;
                        world.setBlock(pos, blockToPlace, 2);
                        placedAny = true;
                    }
                }
            }
        }

        return placedAny;
    }

    private boolean isTopEdgePosition(Configuration config, WorldGenLevel world, BlockPos pos) {
        return world.isEmptyBlock(pos.above()) && !world.isEmptyBlock(pos.below()) &&
                world.getBlockState(pos.below()).is(config.innerWallBlock.getBlock());
    }

    public record Configuration(
            int minRadius, int maxRadius, int pointCount, float depth, float irregularity,
            BlockState innerWallBlock, BlockState edgeBlock, BlockState bottomBlock,
            BlockState topEdgeBlock, BlockState fluidBlock) implements FeatureConfiguration {

        public static final Codec<Configuration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ExtraCodecs.POSITIVE_INT.fieldOf("min_radius").forGetter(Configuration::minRadius),
                ExtraCodecs.POSITIVE_INT.fieldOf("max_radius").forGetter(Configuration::maxRadius),
                ExtraCodecs.POSITIVE_INT.fieldOf("point_count").forGetter(Configuration::pointCount),
                Codec.floatRange(0.0F, 1.0F).fieldOf("depth").forGetter(Configuration::depth),
                Codec.floatRange(0.0F, 1.0F).fieldOf("irregularity").forGetter(Configuration::irregularity),
                BlockState.CODEC.fieldOf("inner_wall").forGetter(Configuration::innerWallBlock),
                BlockState.CODEC.fieldOf("edge_block").forGetter(Configuration::edgeBlock),
                BlockState.CODEC.fieldOf("bottom_block").forGetter(Configuration::bottomBlock),
                BlockState.CODEC.fieldOf("top_edge_block").forGetter(Configuration::topEdgeBlock),
                BlockState.CODEC.fieldOf("fluid_block").forGetter(Configuration::fluidBlock)
        ).apply(instance, Configuration::new));

    }

}