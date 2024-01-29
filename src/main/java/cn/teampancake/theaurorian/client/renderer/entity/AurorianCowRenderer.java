package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.AurorianCowModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.animal.AurorianCow;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AurorianCowRenderer extends MobRenderer<AurorianCow, AurorianCowModel<AurorianCow>> {

    public AurorianCowRenderer(EntityRendererProvider.Context context) {
        super(context, new AurorianCowModel<>(context.bakeLayer(TAModelLayers.AURORIAN_COW)), 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(AurorianCow entity) {
        return AurorianMod.prefix("textures/entity/aurorian_cow.png");
    }

}