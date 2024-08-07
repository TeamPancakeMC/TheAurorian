package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.structure.structures.LargeJigsawStructure;
import cn.teampancake.theaurorian.common.level.structure.structures.RuinsAltarStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class TAStructureTypes {

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, AurorianMod.MOD_ID);
    public static final RegistryObject<StructureType<?>> RUINS_ALTAR = register("ruins_altar", RuinsAltarStructure::new);
    public static final RegistryObject<StructureType<LargeJigsawStructure>> LARGE_JIGSAW = STRUCTURE_TYPES.register("large_jigsaw",()->()-> LargeJigsawStructure.CODEC);

    private static RegistryObject<StructureType<?>> register(String name, Function<Structure.StructureSettings, Structure> function) {
        return STRUCTURE_TYPES.register(name, () -> () -> Structure.simpleCodec(function));
    }

}