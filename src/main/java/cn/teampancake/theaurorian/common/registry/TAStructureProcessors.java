package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.processors.DecorationProcessor;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TAStructureProcessors {

    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSORS = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, AurorianMod.MOD_ID);
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<DecorationProcessor>> CRYSTAL_BUD_PROCESSOR = register("decoration_processor", DecorationProcessor.CODEC);

    private static <T extends StructureProcessor> DeferredHolder<StructureProcessorType<?>, StructureProcessorType<T>> register(String name, MapCodec<T> codec) {
        return STRUCTURE_PROCESSORS.register(name, () -> () -> codec);
    }

}