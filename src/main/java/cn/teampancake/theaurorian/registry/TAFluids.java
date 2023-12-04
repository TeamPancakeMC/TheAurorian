package cn.teampancake.theaurorian.registry;

import cn.teampancake.theaurorian.AurorianMod;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = AurorianMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TAFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, AurorianMod.MOD_ID);
    public static final RegistryObject<FlowingFluid> MOLTEN_AURORIAN_STEEL_STILL = FLUIDS.register("molten_aurorian_steel_still",
            () -> new ForgeFlowingFluid.Source(TAFluids.MOLTEN_AURORIAN_STEEL_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MOLTEN_AURORIAN_STEEL_FLOWING = FLUIDS.register("molten_aurorian_steel_flowing",
            () -> new ForgeFlowingFluid.Flowing(TAFluids.MOLTEN_AURORIAN_STEEL_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MOLTEN_CERULEAN_STILL = FLUIDS.register("molten_cerulean_still",
            () -> new ForgeFlowingFluid.Source(TAFluids.MOLTEN_CERULEAN_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MOLTEN_CERULEAN_FLOWING = FLUIDS.register("molten_cerulean_flowing",
            () -> new ForgeFlowingFluid.Flowing(TAFluids.MOLTEN_CERULEAN_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MOLTEN_MOONSTONE_STILL = FLUIDS.register("molten_moonstone_still",
            () -> new ForgeFlowingFluid.Source(TAFluids.MOLTEN_MOONSTONE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MOLTEN_MOONSTONE_FLOWING = FLUIDS.register("molten_moonstone_flowing",
            () -> new ForgeFlowingFluid.Flowing(TAFluids.MOLTEN_MOONSTONE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MOON_WATER_STILL = FLUIDS.register("moon_water_still",
            () -> new ForgeFlowingFluid.Source(TAFluids.MOON_WATER_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MOON_WATER_FLOWING = FLUIDS.register("moon_water_flowing",
            () -> new ForgeFlowingFluid.Flowing(TAFluids.MOON_WATER_PROPERTIES));

    //Fluid Properties
    private static final ForgeFlowingFluid.Properties MOLTEN_AURORIAN_STEEL_PROPERTIES = new ForgeFlowingFluid.Properties(
            TAFluidTypes.MOLTEN_AURORIAN_STEEL, MOLTEN_AURORIAN_STEEL_STILL, MOLTEN_AURORIAN_STEEL_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(30).block(TABlocks.MOLTEN_AURORIAN_STEEL);
    private static final ForgeFlowingFluid.Properties MOLTEN_CERULEAN_PROPERTIES = new ForgeFlowingFluid.Properties(
            TAFluidTypes.MOLTEN_CERULEAN, MOLTEN_CERULEAN_STILL, MOLTEN_CERULEAN_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(30).block(TABlocks.MOLTEN_CERULEAN);
    private static final ForgeFlowingFluid.Properties MOLTEN_MOONSTONE_PROPERTIES = new ForgeFlowingFluid.Properties(
            TAFluidTypes.MOLTEN_MOONSTONE, MOLTEN_MOONSTONE_STILL, MOLTEN_MOONSTONE_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(30).block(TABlocks.MOLTEN_MOONSTONE);
    private static final ForgeFlowingFluid.Properties MOON_WATER_PROPERTIES = new ForgeFlowingFluid.Properties(
            TAFluidTypes.MOON_WATER, MOON_WATER_STILL, MOON_WATER_FLOWING).block(TABlocks.MOON_WATER);

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderLayer(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(MOLTEN_AURORIAN_STEEL_STILL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MOLTEN_AURORIAN_STEEL_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MOLTEN_CERULEAN_STILL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MOLTEN_CERULEAN_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MOLTEN_MOONSTONE_STILL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MOLTEN_MOONSTONE_FLOWING.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MOON_WATER_STILL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MOON_WATER_FLOWING.get(), RenderType.translucent());
    }

}