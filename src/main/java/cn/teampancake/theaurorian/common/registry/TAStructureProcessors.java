package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.level.processors.DecorationProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TAStructureProcessors {

    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSORS = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, AurorianMod.MOD_ID);
    public static final RegistryObject<StructureProcessorType<DecorationProcessor>> CRYSTAL_BUD_PROCESSOR = STRUCTURE_PROCESSORS.register("decoration_processor", () -> () -> DecorationProcessor.CODEC);

}