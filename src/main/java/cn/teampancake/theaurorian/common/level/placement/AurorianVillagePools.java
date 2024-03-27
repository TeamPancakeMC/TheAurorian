package cn.teampancake.theaurorian.common.level.placement;

import cn.teampancake.theaurorian.common.registry.TAProcessorList;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class AurorianVillagePools {
    public static final ResourceKey<StructureTemplatePool> START = Pools.createKey("theaurorian:village_fountain");
    public static final ResourceKey<StructureTemplatePool> TERMINATORS_KEY = Pools.createKey("theaurorian:windmill");


    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        HolderGetter<PlacedFeature> featureGetter = context.lookup(Registries.PLACED_FEATURE);

        HolderGetter<StructureProcessorList> processorListHolderGetter = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> mossify10Holder = processorListHolderGetter.getOrThrow(ProcessorLists.MOSSIFY_10_PERCENT);
        Holder<StructureProcessorList> mossify20Holder = processorListHolderGetter.getOrThrow(ProcessorLists.MOSSIFY_20_PERCENT);
        Holder<StructureProcessorList> mossify70Holder = processorListHolderGetter.getOrThrow(ProcessorLists.MOSSIFY_70_PERCENT);

        Holder<StructureProcessorList> streetCaveHolder = processorListHolderGetter.getOrThrow(TAProcessorList.STREET_CAVE);
        HolderGetter<StructureTemplatePool> templateGetter = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> emptyPoolHolder = templateGetter.getOrThrow(Pools.EMPTY);
        Holder<StructureTemplatePool> terminatorPoolHolder = templateGetter.getOrThrow(TERMINATORS_KEY);

        context.register(START, new StructureTemplatePool(emptyPoolHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.legacy("theaurorian:village_fountain/village_fountain", mossify20Holder), 50)),
                StructureTemplatePool.Projection.RIGID));

        Pools.register(context, "theaurorian:village/village_street_light", new StructureTemplatePool(terminatorPoolHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.legacy("theaurorian:village/village_street_light/village_street_light", streetCaveHolder), 2)),
                StructureTemplatePool.Projection.RIGID));

        Pools.register(context, "theaurorian:village/village_street_sign", new StructureTemplatePool(terminatorPoolHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.legacy("theaurorian:village/village_street_sign/village_street_sign", streetCaveHolder), 2)),
                StructureTemplatePool.Projection.RIGID));

        Pools.register(context, "theaurorian:village/village_stall", new StructureTemplatePool(terminatorPoolHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.legacy("theaurorian:village/village_stall/village_stall_1", streetCaveHolder), 1),
                        Pair.of(StructurePoolElement.legacy("theaurorian:village/village_stall/village_stall_2", streetCaveHolder), 1),
                        Pair.of(StructurePoolElement.legacy("theaurorian:village/village_stall/village_stall_3", streetCaveHolder), 1)),
                StructureTemplatePool.Projection.RIGID));

        Pools.register(context, "theaurorian:village/village_house", new StructureTemplatePool(terminatorPoolHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.legacy("theaurorian:village/village_house/village_house_1", mossify10Holder), 1),
                        Pair.of(StructurePoolElement.legacy("theaurorian:village/village_house/village_house_2", mossify10Holder), 1),
                        Pair.of(StructurePoolElement.legacy("theaurorian:village/village_house/village_house_3", mossify10Holder), 1),
                        Pair.of(StructurePoolElement.legacy("theaurorian:village/village_house/village_house_4", mossify10Holder), 1),
                        Pair.of(StructurePoolElement.legacy("theaurorian:village/village_house/village_house_5", mossify10Holder), 1),
                        Pair.of(StructurePoolElement.legacy("theaurorian:village/village_house/village_house_6", mossify10Holder), 1)),
                StructureTemplatePool.Projection.RIGID));

        context.register(TERMINATORS_KEY, new StructureTemplatePool(emptyPoolHolder,
                ImmutableList.of(
                        Pair.of(StructurePoolElement.legacy("theaurorian:village/windmill/windmill", streetCaveHolder), 1)),
                StructureTemplatePool.Projection.RIGID));
    }
}


