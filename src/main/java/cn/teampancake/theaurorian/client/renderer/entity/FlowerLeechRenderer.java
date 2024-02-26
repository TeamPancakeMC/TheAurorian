package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.FlowerLeechModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.monster.FlowerLeech;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FlowerLeechRenderer extends MobRenderer<FlowerLeech, FlowerLeechModel<FlowerLeech>> {

    public FlowerLeechRenderer(EntityRendererProvider.Context context) {
        super(context, new FlowerLeechModel<>(context.bakeLayer(TAModelLayers.FLOWER_LEECH)), 0.8F);
    }

    @Override
    public ResourceLocation getTextureLocation(FlowerLeech entity) {
        return AurorianMod.prefix("textures/entity/flower_leech.png");
    }

}