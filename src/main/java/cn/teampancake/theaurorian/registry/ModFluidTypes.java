package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFluidTypes {

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.FLUID_TYPES.get(), AurorianMod.MOD_ID);

}
