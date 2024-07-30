package cn.teampancake.theaurorian.common.level.structure.structures;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.structure.pieces.RuinsAltarPiece;
import cn.teampancake.theaurorian.common.registry.TABlocks;
import cn.teampancake.theaurorian.common.registry.TAStructureTypes;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class RuinsAltarStructure extends Structure {

    public static final Codec<RuinsAltarStructure> CODEC = simpleCodec(RuinsAltarStructure::new);

    public RuinsAltarStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(@NotNull GenerationContext context) {
        return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, builder -> {
            StructureTemplateManager manager = context.structureTemplateManager();
            ChunkPos chunkPos = context.chunkPos();
            WorldgenRandom random = context.random();
            int x = chunkPos.getMinBlockX();
            int z = chunkPos.getMinBlockZ();
            int y = context.chunkGenerator().getFirstOccupiedHeight(
                    x, z, Heightmap.Types.WORLD_SURFACE_WG,
                    context.heightAccessor(), context.randomState());
            Rotation rotation = Rotation.getRandom(random);
            BlockPos pos = new BlockPos(x, y, z);
            ResourceLocation location = AurorianMod.prefix("ruins/ruins_altar/ruins_altar");
            builder.addPiece(new RuinsAltarPiece(manager, location, pos.above(), rotation));
        });
    }

    @Override
    public void afterPlace(
            WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator,
            RandomSource random, BoundingBox boundingBox, ChunkPos chunkPos, PiecesContainer pieces) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        BoundingBox box = pieces.calculateBoundingBox();
        for (int k = boundingBox.minX(); k <= boundingBox.maxX(); ++k) {
            for (int l = boundingBox.minZ(); l <= boundingBox.maxZ(); ++l) {
                mutableBlockPos.set(k, box.minY(), l);
                if (!level.isEmptyBlock(mutableBlockPos) && box.isInside(mutableBlockPos) && pieces.isInsidePiece(mutableBlockPos)) {
                    for (int i1 = box.minY() - 1; i1 > level.getMinBuildHeight(); --i1) {
                        mutableBlockPos.setY(i1);
                        if (!level.isEmptyBlock(mutableBlockPos)) {
                            break;
                        }

                        level.setBlock(mutableBlockPos, TABlocks.AURORIAN_STONE.get().defaultBlockState(), 2);
                    }
                }
            }
        }
    }

    @Override
    public StructureType<?> type() {
        return TAStructureTypes.RUINS_ALTAR.get();
    }

}