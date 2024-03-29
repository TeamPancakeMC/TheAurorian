package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.HyphaWalkingMushroomModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.monster.HyphaWalkingMushroom;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class HyphaWalkingMushroomRenderer extends MobRenderer<HyphaWalkingMushroom, HyphaWalkingMushroomModel<HyphaWalkingMushroom>> {

    public HyphaWalkingMushroomRenderer(EntityRendererProvider.Context context) {
        super(context, new HyphaWalkingMushroomModel<>(context.bakeLayer(TAModelLayers.HYPHA_WALKING_MUSHROOM)), 0.8F);
    }

    @Override
    public ResourceLocation getTextureLocation(HyphaWalkingMushroom entity) {
        return AurorianMod.prefix("textures/entity/hypha_walking_mushroom.png");
    }

}