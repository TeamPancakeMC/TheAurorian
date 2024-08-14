package cn.teampancake.theaurorian.common.registry;

import cn.teampancake.theaurorian.TheAurorian;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = TheAurorian.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class TAFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, TheAurorian.MOD_ID);
    public static final DeferredHolder<Fluid, FlowingFluid> MOLTEN_AURORIAN_STEEL_STILL = FLUIDS.register("molten_aurorian_steel_still",
            () -> new BaseFlowingFluid.Source(TAFluids.MOLTEN_AURORIAN_STEEL_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> MOLTEN_AURORIAN_STEEL_FLOWING = FLUIDS.register("molten_aurorian_steel_flowing",
            () -> new BaseFlowingFluid.Flowing(TAFluids.MOLTEN_AURORIAN_STEEL_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> MOLTEN_CERULEAN_STILL = FLUIDS.register("molten_cerulean_still",
            () -> new BaseFlowingFluid.Source(TAFluids.MOLTEN_CERULEAN_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> MOLTEN_CERULEAN_FLOWING = FLUIDS.register("molten_cerulean_flowing",
            () -> new BaseFlowingFluid.Flowing(TAFluids.MOLTEN_CERULEAN_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> MOLTEN_MOONSTONE_STILL = FLUIDS.register("molten_moonstone_still",
            () -> new BaseFlowingFluid.Source(TAFluids.MOLTEN_MOONSTONE_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> MOLTEN_MOONSTONE_FLOWING = FLUIDS.register("molten_moonstone_flowing",
            () -> new BaseFlowingFluid.Flowing(TAFluids.MOLTEN_MOONSTONE_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> MOON_WATER_STILL = FLUIDS.register("moon_water_still",
            () -> new BaseFlowingFluid.Source(TAFluids.MOON_WATER_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> MOON_WATER_FLOWING = FLUIDS.register("moon_water_flowing",
            () -> new BaseFlowingFluid.Flowing(TAFluids.MOON_WATER_PROPERTIES));

    //Fluid Properties
    private static final BaseFlowingFluid.Properties MOLTEN_AURORIAN_STEEL_PROPERTIES = new BaseFlowingFluid.Properties(
            TAFluidTypes.MOLTEN_AURORIAN_STEEL, MOLTEN_AURORIAN_STEEL_STILL, MOLTEN_AURORIAN_STEEL_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(30).block(TABlocks.MOLTEN_AURORIAN_STEEL);
    private static final BaseFlowingFluid.Properties MOLTEN_CERULEAN_PROPERTIES = new BaseFlowingFluid.Properties(
            TAFluidTypes.MOLTEN_CERULEAN, MOLTEN_CERULEAN_STILL, MOLTEN_CERULEAN_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(30).block(TABlocks.MOLTEN_CERULEAN);
    private static final BaseFlowingFluid.Properties MOLTEN_MOONSTONE_PROPERTIES = new BaseFlowingFluid.Properties(
            TAFluidTypes.MOLTEN_MOONSTONE, MOLTEN_MOONSTONE_STILL, MOLTEN_MOONSTONE_FLOWING)
            .slopeFindDistance(2).levelDecreasePerBlock(30).block(TABlocks.MOLTEN_MOONSTONE);
    private static final BaseFlowingFluid.Properties MOON_WATER_PROPERTIES = new BaseFlowingFluid.Properties(
            TAFluidTypes.MOON_WATER, MOON_WATER_STILL, MOON_WATER_FLOWING).block(TABlocks.MOON_WATER).bucket(TAItems.MOON_WATER_BUCKET);

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