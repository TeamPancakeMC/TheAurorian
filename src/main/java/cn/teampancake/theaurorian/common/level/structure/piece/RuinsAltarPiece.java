package cn.teampancake.theaurorian.common.level.structure.piece;

import cn.teampancake.theaurorian.common.registry.TAStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class RuinsAltarPiece extends TemplateStructurePiece {

    public RuinsAltarPiece(StructureTemplateManager structureTemplateManager, ResourceLocation location, BlockPos templatePosition, Rotation rotation) {
        super(TAStructurePieceTypes.RUINS_ALTAR.get(), 0, structureTemplateManager, location, location.toString(), makeSettings(rotation), templatePosition);
    }

    public RuinsAltarPiece(StructureTemplateManager structureTemplateManager, CompoundTag tag) {
        super(TAStructurePieceTypes.RUINS_ALTAR.get(), tag, structureTemplateManager, (location) -> makeSettings(Rotation.valueOf(tag.getString("rot"))));
    }

    public RuinsAltarPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        this(context.structureTemplateManager(), tag);
    }

    @Override
    protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {

    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        super.addAdditionalSaveData(context, tag);
        tag.putString("rot", this.placeSettings.getRotation().name());
    }

    private static StructurePlaceSettings makeSettings(Rotation rotation) {
        return new StructurePlaceSettings().setRotation(rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
    }

}