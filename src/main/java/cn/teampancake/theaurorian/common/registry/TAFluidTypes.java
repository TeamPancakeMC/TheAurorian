package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.blocks.BaseFluidType;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class TAFluidTypes {

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, AurorianMod.MOD_ID);
    public static final DeferredHolder<FluidType, FluidType> MOLTEN_AURORIAN_STEEL = FLUID_TYPES.register("molten_aurorian_steel",
            () -> new BaseFluidType(defaultProperties(), AurorianMod.prefix("block/molten_aurorian_steel"),
                    AurorianMod.prefix("block/molten_aurorian_steel_flowing")));
    public static final DeferredHolder<FluidType, FluidType> MOLTEN_CERULEAN = FLUID_TYPES.register("molten_cerulean",
            () -> new BaseFluidType(defaultProperties(), AurorianMod.prefix("block/molten_cerulean"),
                    AurorianMod.prefix("block/molten_cerulean_flowing")));
    public static final DeferredHolder<FluidType, FluidType> MOLTEN_MOONSTONE = FLUID_TYPES.register("molten_moonstone",
            () -> new BaseFluidType(defaultProperties(), AurorianMod.prefix("block/molten_moonstone"),
                    AurorianMod.prefix("block/molten_moonstone_flowing")));
    public static final DeferredHolder<FluidType, FluidType> MOON_WATER = FLUID_TYPES.register("moon_water",
            () -> new BaseFluidType(FluidType.Properties.create().supportsBoating(true), AurorianMod.prefix("block/moon_water"),
                    AurorianMod.prefix("block/moon_water")));

    private static FluidType.Properties defaultProperties() {
        return FluidType.Properties.create().density(2000).viscosity(10000).temperature(800).lightLevel(10);
    }

}