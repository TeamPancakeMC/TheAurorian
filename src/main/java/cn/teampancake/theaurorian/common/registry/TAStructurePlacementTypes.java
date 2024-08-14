package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TAStructurePlacementTypes {

    public static final DeferredRegister<StructurePlacementType<?>> STRUCTURE_PLACEMENT_TYPES = DeferredRegister.create(Registries.STRUCTURE_PLACEMENT, TheAurorian.MOD_ID);

    private static <S extends StructurePlacement> DeferredHolder<StructurePlacementType<?>, StructurePlacementType<S>> register(String id, MapCodec<S> placementType) {
        return STRUCTURE_PLACEMENT_TYPES.register(id, () -> () -> placementType);
    }

}