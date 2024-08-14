package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.model.entity.AurorianPixieModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.animal.AurorianPixie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianPixieRenderer extends MobRenderer<AurorianPixie, AurorianPixieModel<AurorianPixie>> {

    public AurorianPixieRenderer(EntityRendererProvider.Context context) {
        super(context, new AurorianPixieModel<>(context.bakeLayer(TAModelLayers.AURORIAN_PIXIE)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(AurorianPixie entity) {
        return TheAurorian.prefix("textures/entity/aurorian_pixie_" + entity.getColorId() + ".png");
    }

}