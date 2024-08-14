package cn.teampancake.theaurorian.common.blocks;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;

public class BaseFluidType extends FluidType {

    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;

    public BaseFluidType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
        super(properties);
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
    }

    private class CustomClientFluidTypeExtensions implements IClientFluidTypeExtensions {

        @Override
        public ResourceLocation getStillTexture() {
            return stillTexture;
        }

        @Override
        public ResourceLocation getFlowingTexture() {
            return flowingTexture;
        }

    }

}