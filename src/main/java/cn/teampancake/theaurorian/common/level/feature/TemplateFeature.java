package cn.teampancake.theaurorian.common.level.feature;

import cn.teampancake.theaurorian.common.registry.TABlocks;
import com.google.common.math.StatsAccumulator;
import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"UnstableApiUsage", "deprecation"})
public abstract class TemplateFeature<T extends FeatureConfiguration> extends Feature<T> {

    public TemplateFeature(Codec<T> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<T> context) {
        WorldGenLevel level = context.level();
        BlockPos originPos = context.origin();
        RandomSource random = context.random();
        T config = context.config();
        StructureTemplateManager templateManager = level.getLevel().getServer().getStructureManager();
        StructureTemplate template = this.getTemplate(templateManager, random);
        if (template != null) {
            Rotation rotation = Rotation.getRandom(random);
            Mirror mirror = Util.getRandom(Mirror.values(), random);
            Vec3i templateSize = template.getSize(rotation);
            ChunkPos chunkPos = new ChunkPos(originPos);
            BoundingBox boundingBox = new BoundingBox(chunkPos.getMinBlockX(), level.getMinBuildHeight(), chunkPos.getMinBlockZ(),
                    chunkPos.getMaxBlockX(), level.getMaxBuildHeight(), chunkPos.getMaxBlockZ());
            BlockPos posSnap = chunkPos.getWorldPosition().offset(0, originPos.getY(), 0);
            posSnap = posSnap.offset(random.nextInt((16 - templateSize.getX())), 0, random.nextInt((16 - templateSize.getZ())));
            BlockPos.MutableBlockPos startPos = new BlockPos.MutableBlockPos(posSnap.getX(), posSnap.getY(), posSnap.getZ());
            if (this.offsetToAverageGroundLevel(level, startPos, templateSize)) {
                BlockPos placePos = template.getZeroPositionWithTransform(startPos.move(0, 1, 0), mirror, rotation);
                StructurePlaceSettings placeSettings = (new StructurePlaceSettings()).setMirror(mirror)
                        .setRotation(rotation).setBoundingBox(boundingBox).setRandom(random);
                this.modifySettings(placeSettings.clearProcessors(), random, config);
                template.placeInWorld(level, placePos, placePos, placeSettings, random, 20);
                return true;
            }
        }

        return false;
    }

    @Nullable
    protected abstract StructureTemplate getTemplate(StructureTemplateManager templateManager, RandomSource random);

    protected void modifySettings(StructurePlaceSettings settings, RandomSource random, T config) {}

    private boolean offsetToAverageGroundLevel(WorldGenLevel level, BlockPos.MutableBlockPos startPos, Vec3i size) {
        StatsAccumulator heights = new StatsAccumulator();
        for (int dx = 0; dx < size.getX(); dx++) {
            for (int dz = 0; dz < size.getZ(); dz++) {
                int x = startPos.getX() + dx;
                int z = startPos.getZ() + dz;
                int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
                while (y >= 0) {
                    BlockState state = level.getBlockState(new BlockPos(x, y, z));
                    if (state.liquid() || state.is(Blocks.BEDROCK)) {
                        return false;
                    }
                    if (state.isSolid()) {
                        break;
                    }
                    y--;
                }

                if (y < 0) {
                    return false;
                }

                heights.add(y);
            }
        }

        if (heights.populationStandardDeviation() > 2.0F) {
            return false;
        }

        int baseY = (int) (heights.mean() + 0.5F);
        int maxY = (int) heights.max();
        startPos.setY(baseY);
        for (BlockPos pos : BlockPos.betweenClosed(startPos, startPos.offset(size.getX(), 0, size.getZ()))) {
            BlockState state = level.getBlockState(pos);
            if (!state.isSolid() || state.is(TABlocks.AURORIAN_DIRT.get())) {
                return false;
            }
        }

        return hasSufficientArea(level, startPos.above((maxY - baseY + 1)), startPos.offset(size));
    }

    private static boolean hasSufficientArea(LevelAccessor level, BlockPos min, BlockPos max) {
        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            BlockState state = level.getBlockState(pos);
            if (!(state.getBlock() instanceof BushBlock) && !state.canBeReplaced()) {
                return false;
            }
        }

        return true;
    }

}