package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.data.datagen.tags.TABiomeTags;
import cn.teampancake.theaurorian.common.level.structure.RuinsAltarStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;

import java.util.Map;

public class TAStructures {

    public static final ResourceKey<Structure> RUINS_ALTAR = createKey("ruins_altar");

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, AurorianMod.prefix(name));
    }

    public static void bootstrap(BootstapContext<Structure> context) {
        Structure.StructureSettings ruinsAltarSettings = new Structure.StructureSettings(
                context.lookup(Registries.BIOME).getOrThrow(TABiomeTags.HAS_RUINS_ALTAR), Map.of(),
                GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE);
        context.register(RUINS_ALTAR, new RuinsAltarStructure(ruinsAltarSettings));
    }

}