package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.structure.structures.LargeJigsawStructure;
import cn.teampancake.theaurorian.common.level.structure.structures.RuinsAltarStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class TAStructureTypes {

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, AurorianMod.MOD_ID);
    public static final DeferredHolder<StructureType<?>, StructureType<RuinsAltarStructure>> RUINS_ALTAR = register("ruins_altar", RuinsAltarStructure::new);
    public static final DeferredHolder<StructureType<?>, StructureType<LargeJigsawStructure>> LARGE_JIGSAW = STRUCTURE_TYPES.register("large_jigsaw", () -> () -> LargeJigsawStructure.CODEC);

    private static<P extends Structure>  DeferredHolder<StructureType<?>, StructureType<P>> register(String name, Function<Structure.StructureSettings, P> function) {
        return STRUCTURE_TYPES.register(name, () -> () -> Structure.simpleCodec(function));
    }

}