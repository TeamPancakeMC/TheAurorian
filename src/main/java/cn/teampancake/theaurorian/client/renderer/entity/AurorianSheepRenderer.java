package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.renderer.layers.ModModelLayers;
import cn.teampancake.theaurorian.client.renderer.layers.AurorianSheepFurLayer;
import cn.teampancake.theaurorian.common.entities.animal.AurorianSheep;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianSheepRenderer extends MobRenderer<AurorianSheep, SheepModel<AurorianSheep>> {

    public AurorianSheepRenderer(EntityRendererProvider.Context context) {
        super(context, new SheepModel<>(context.bakeLayer(ModModelLayers.AURORIAN_SHEEP)), 0.7F);
        this.addLayer(new AurorianSheepFurLayer(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(AurorianSheep pEntity) {
        return AurorianMod.prefix("textures/entity/aurorian_sheep.png");
    }

}