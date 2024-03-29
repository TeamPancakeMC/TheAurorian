package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.IcefieldDeerModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.animal.IcefieldDeer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IcefieldDeerRenderer extends MobRenderer<IcefieldDeer, IcefieldDeerModel<IcefieldDeer>> {

    public IcefieldDeerRenderer(EntityRendererProvider.Context context) {
        super(context, new IcefieldDeerModel<>(context.bakeLayer(TAModelLayers.ICEFIELD_DEER)), 0.6F);
    }

    @Override
    public ResourceLocation getTextureLocation(IcefieldDeer entity) {
        return AurorianMod.prefix("textures/entity/icefield_deer.png");
    }

}