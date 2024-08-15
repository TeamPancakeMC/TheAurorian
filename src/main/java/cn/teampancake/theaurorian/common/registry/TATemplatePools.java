package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.ArrayList;
import java.util.List;

public class TATemplatePools {

    public static final ResourceKey<StructureTemplatePool> AURORIAN_VILLAGE_GATE = createKey("aurorian_village/gate");
    public static final ResourceKey<StructureTemplatePool> AURORIAN_VILLAGE_SET = createKey("aurorian_village/set");
    public static final ResourceKey<StructureTemplatePool> RUNESTONE_DUNGEON_START = createKey("runestone_dungeon/part_5");
    public static final List<ResourceKey<StructureTemplatePool>> RUNESTONE_DUNGEON_PARTS = new ArrayList<>();

    private static ResourceKey<StructureTemplatePool> createKey(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, TheAurorian.prefix(name));
    }

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureProcessorList> processorLists = context.lookup(Registries.PROCESSOR_LIST);
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
        Holder.Reference<StructureProcessorList> emptyPL = processorLists.getOrThrow(ProcessorLists.EMPTY);
        Holder<StructureTemplatePool> emptyPool = templatePools.getOrThrow(Pools.EMPTY);
        context.register(AURORIAN_VILLAGE_GATE, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single("theaurorian:village/set/gate", emptyPL), 1)
        ), StructureTemplatePool.Projection.RIGID));
        context.register(AURORIAN_VILLAGE_SET, new StructureTemplatePool(emptyPool, ImmutableList.of(
                Pair.of(StructurePoolElement.single("theaurorian:village/set/village_preset_1", emptyPL), 50),
                Pair.of(StructurePoolElement.single("theaurorian:village/set/village_preset_2", emptyPL), 50),
                Pair.of(StructurePoolElement.single("theaurorian:village/set/village_preset_3", emptyPL), 50),
                Pair.of(StructurePoolElement.single("theaurorian:village/set/village_preset_4", emptyPL), 50),
                Pair.of(StructurePoolElement.single("theaurorian:village/set/village_preset_5", emptyPL), 50),
                Pair.of(StructurePoolElement.single("theaurorian:village/set/village_preset_6", emptyPL), 50),
                Pair.of(StructurePoolElement.single("theaurorian:village/set/village_preset_7", emptyPL), 50),
                Pair.of(StructurePoolElement.single("theaurorian:village/set/village_preset_8", emptyPL), 50)
        ), StructureTemplatePool.Projection.RIGID));
        RUNESTONE_DUNGEON_PARTS.forEach(key -> context.register(key, new StructureTemplatePool(emptyPool, ImmutableList.of(Pair.of(
                StructurePoolElement.single(key.registry().toString(), emptyPL), 50)), StructureTemplatePool.Projection.RIGID)));
    }

    static {
        for (int i = 1; i < 10; i++) {
            RUNESTONE_DUNGEON_PARTS.add(createKey("runestone_dungeon/part_" + i));
        }
    }

}