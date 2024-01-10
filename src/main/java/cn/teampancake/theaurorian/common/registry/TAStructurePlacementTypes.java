package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TAStructurePlacementTypes {

    public static final DeferredRegister<StructurePlacementType<?>> STRUCTURE_PLACEMENT_TYPES = DeferredRegister.create(Registries.STRUCTURE_PLACEMENT, AurorianMod.MOD_ID);

    private static <S extends StructurePlacement> RegistryObject<StructurePlacementType<S>> register(String id, Codec<S> placementType) {
        return STRUCTURE_PLACEMENT_TYPES.register(id, () -> () -> placementType);
    }

}