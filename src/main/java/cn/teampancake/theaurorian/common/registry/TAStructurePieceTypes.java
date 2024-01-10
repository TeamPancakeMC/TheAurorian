package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.structure.piece.RuinsAltarPiece;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;

public class TAStructurePieceTypes {

    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, AurorianMod.MOD_ID);
    public static final RegistryObject<StructurePieceType> RUINS_ALTAR = register("ruins_altar", RuinsAltarPiece::new);

    private static RegistryObject<StructurePieceType> register(String name, StructurePieceType pieceType) {
        return STRUCTURE_PIECE_TYPES.register(name, () -> pieceType);
    }

}