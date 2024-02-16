package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.BreadBeastModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.animal.BreadBeast;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BreadBeastRenderer extends MobRenderer<BreadBeast, BreadBeastModel<BreadBeast>> {

    public BreadBeastRenderer(EntityRendererProvider.Context context) {
        super(context, new BreadBeastModel<>(context.bakeLayer(TAModelLayers.BREAD_BEAST)), 0.8F);
    }

    @Override
    public ResourceLocation getTextureLocation(BreadBeast entity) {
        return AurorianMod.prefix("textures/entity/bread_beast.png");
    }

}