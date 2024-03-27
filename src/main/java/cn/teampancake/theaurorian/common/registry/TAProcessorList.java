package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.List;


public class TAProcessorList {
    public static final ResourceKey<StructureProcessorList> STREET_CAVE = ResourceKey.create(Registries.PROCESSOR_LIST, new ResourceLocation(AurorianMod.MOD_ID, "street_cave"));

    public static void bootstrap(BootstapContext<StructureProcessorList> context) {
        register(context, STREET_CAVE, ImmutableList.of(new RuleProcessor(ImmutableList.of(new ProcessorRule(new BlockMatchTest(Blocks.CALCITE),
                new BlockMatchTest(Blocks.WATER), Blocks.OAK_PLANKS.defaultBlockState()), new ProcessorRule(new RandomBlockMatchTest(Blocks.CALCITE, 0.1F),
                AlwaysTrueTest.INSTANCE, Blocks.GRASS_BLOCK.defaultBlockState()), new ProcessorRule(new BlockMatchTest(Blocks.GRASS_BLOCK),
                new BlockMatchTest(Blocks.WATER), Blocks.WATER.defaultBlockState()), new ProcessorRule(new BlockMatchTest(Blocks.DIRT),
                new BlockMatchTest(Blocks.WATER), Blocks.WATER.defaultBlockState())))));
    }

    private static void register(BootstapContext<StructureProcessorList> bootstapContext,
                                 ResourceKey<StructureProcessorList> processorListResourceKey,
                                 List<StructureProcessor> structureProcessorList) {
        bootstapContext.register(processorListResourceKey, new StructureProcessorList(structureProcessorList));
    }
}
