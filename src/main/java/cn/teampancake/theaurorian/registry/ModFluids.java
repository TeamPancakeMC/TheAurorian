package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, AurorianMod.MOD_ID);
    public static final RegistryObject<FlowingFluid> MOLTEN_AURORIAN_STEEL_STILL = FLUIDS.register("molten_aurorian_steel_still",
            () -> new ForgeFlowingFluid.Source(ModFluids.MOLTEN_AURORIAN_STEEL_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MOLTEN_AURORIAN_STEEL_FLOWING = FLUIDS.register("molten_aurorian_steel_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.MOLTEN_AURORIAN_STEEL_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MOLTEN_CERULEAN_STILL = FLUIDS.register("molten_cerulean_still",
            () -> new ForgeFlowingFluid.Source(ModFluids.MOLTEN_CERULEAN_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MOLTEN_CERULEAN_FLOWING = FLUIDS.register("molten_cerulean_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.MOLTEN_CERULEAN_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MOLTEN_MOONSTONE_STILL = FLUIDS.register("molten_moonstone_still",
            () -> new ForgeFlowingFluid.Source(ModFluids.MOLTEN_MOONSTONE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MOLTEN_MOONSTONE_FLOWING = FLUIDS.register("molten_moonstone_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.MOLTEN_MOONSTONE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MOON_WATER_STILL = FLUIDS.register("moon_water_still",
            () -> new ForgeFlowingFluid.Source(ModFluids.MOON_WATER_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MOON_WATER_FLOWING = FLUIDS.register("moon_water_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.MOON_WATER_PROPERTIES));

    //Fluid Properties
    private static final ForgeFlowingFluid.Properties MOLTEN_AURORIAN_STEEL_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_AURORIAN_STEEL, MOLTEN_AURORIAN_STEEL_STILL, MOLTEN_AURORIAN_STEEL_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(30).block(ModBlocks.MOLTEN_AURORIAN_STEEL_BLOCK);
    private static final ForgeFlowingFluid.Properties MOLTEN_CERULEAN_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_CERULEAN, MOLTEN_CERULEAN_STILL, MOLTEN_CERULEAN_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(30).block(ModBlocks.MOLTEN_CERULEAN_BLOCK);
    private static final ForgeFlowingFluid.Properties MOLTEN_MOONSTONE_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_MOONSTONE, MOLTEN_MOONSTONE_STILL, MOLTEN_MOONSTONE_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(30).block(ModBlocks.MOLTEN_MOONSTONE_BLOCK);
    private static final ForgeFlowingFluid.Properties MOON_WATER_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.MOON_WATER, MOON_WATER_STILL, MOON_WATER_FLOWING)
            .slopeFindDistance(4).levelDecreasePerBlock(5).block(ModBlocks.MOON_WATER_BLOCK);

}