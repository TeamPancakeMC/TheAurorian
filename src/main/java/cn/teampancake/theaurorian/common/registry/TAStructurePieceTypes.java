package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.common.level.structure.pieces.RuinsAltarPiece;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TAStructurePieceTypes {

    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, TheAurorian.MOD_ID);
    public static final DeferredHolder<StructurePieceType, StructurePieceType> RUINS_ALTAR = register("ruins_altar", RuinsAltarPiece::new);

    private static DeferredHolder<StructurePieceType, StructurePieceType> register(String name, StructurePieceType pieceType) {
        return STRUCTURE_PIECE_TYPES.register(name, () -> pieceType);
    }

}