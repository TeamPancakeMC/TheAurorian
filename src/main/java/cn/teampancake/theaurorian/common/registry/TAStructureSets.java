package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

import java.util.Optional;

public class TAStructureSets {

    public static final ResourceKey<StructureSet> RUINS_ALTAR = createKey("ruins_altar");
    public static final ResourceKey<StructureSet> AURORIAN_VILLAGE = createKey("aurorian_village");
    public static final ResourceKey<StructureSet> RUNESTONE_DUNGEON = createKey("runestone_dungeon");

    private static ResourceKey<StructureSet> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, TheAurorian.prefix(name));
    }

    public static void bootstrap(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
        context.register(RUINS_ALTAR, new StructureSet(structures.getOrThrow(TAStructures.RUINS_ALTAR),
                new RandomSpreadStructurePlacement((80), (40), RandomSpreadType.LINEAR, 1664313299)));
        context.register(AURORIAN_VILLAGE, new StructureSet(structures.getOrThrow(TAStructures.AURORIAN_VILLAGE),
                new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.DEFAULT,
                        (0.5F), (1145141919), (Optional.empty()), (32), (16), RandomSpreadType.LINEAR)));
        context.register(RUNESTONE_DUNGEON, new StructureSet(structures.getOrThrow(TAStructures.RUNESTONE_DUNGEON),
                new RandomSpreadStructurePlacement(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.DEFAULT,
                        (0.4F), (1919810), (Optional.empty()), (64), (20), RandomSpreadType.LINEAR)));
    }

}