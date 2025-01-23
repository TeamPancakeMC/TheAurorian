package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.common.entities.monster.Spirit;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SpiritRenderer<T extends Spirit & GeoAnimatable> extends GeoEntityRenderer<T> {

    public SpiritRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> model) {
        super(renderManager, model);
    }

    @Override
    public @Nullable RenderType getRenderType(T animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        if (animatable.isAngry() && animatable.isInvisible()) {
            return RenderType.entityGlintDirect();
        }

        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

}