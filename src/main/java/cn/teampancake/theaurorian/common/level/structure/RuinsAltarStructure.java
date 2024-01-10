package cn.teampancake.theaurorian.common.level.structure;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.structure.piece.RuinsAltarPiece;
import cn.teampancake.theaurorian.common.registry.TAStructureTypes;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class RuinsAltarStructure extends Structure {

    public static final Codec<RuinsAltarStructure> CODEC = simpleCodec(RuinsAltarStructure::new);

    public RuinsAltarStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
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
            builder.addPiece(new RuinsAltarPiece(manager, location, pos, rotation));
        });
    }

    @Override
    public StructureType<?> type() {
        return TAStructureTypes.RUINS_ALTAR.get();
    }

}