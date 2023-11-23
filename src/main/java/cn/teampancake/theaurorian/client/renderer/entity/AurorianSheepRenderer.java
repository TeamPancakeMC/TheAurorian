package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.AurorianSheepModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.animal.AurorianSheep;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianSheepRenderer extends MobRenderer<AurorianSheep, AurorianSheepModel<AurorianSheep>> {

    public AurorianSheepRenderer(EntityRendererProvider.Context context) {
        super(context, new AurorianSheepModel<>(context.bakeLayer(TAModelLayers.AURORIAN_SHEEP)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(AurorianSheep entity) {
        return AurorianMod.prefix("textures/entity/aurorian_sheep.png");
    }

}