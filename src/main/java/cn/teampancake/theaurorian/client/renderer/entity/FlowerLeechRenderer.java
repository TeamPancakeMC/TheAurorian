package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.model.entity.FlowerLeechModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.monster.FlowerLeech;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlowerLeechRenderer extends MobRenderer<FlowerLeech, FlowerLeechModel<FlowerLeech>> {

    public FlowerLeechRenderer(EntityRendererProvider.Context context) {
        super(context, new FlowerLeechModel<>(context.bakeLayer(TAModelLayers.FLOWER_LEECH)), 0.8F);
    }

    @Override
    public ResourceLocation getTextureLocation(FlowerLeech entity) {
        return TheAurorian.prefix("textures/entity/flower_leech.png");
    }

}