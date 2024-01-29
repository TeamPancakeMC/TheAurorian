package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.AurorianPigModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.animal.AurorianPig;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianPigRenderer extends MobRenderer<AurorianPig, AurorianPigModel<AurorianPig>> {

    public AurorianPigRenderer(EntityRendererProvider.Context context) {
        super(context, new AurorianPigModel<>(context.bakeLayer(TAModelLayers.AURORIAN_PIG)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(AurorianPig entity) {
        return AurorianMod.prefix("textures/entity/aurorian_pig.png");
    }

}