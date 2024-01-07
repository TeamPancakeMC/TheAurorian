package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.SpiritModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.monster.Spirit;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpiritRenderer extends MobRenderer<Spirit, SpiritModel<Spirit>> {

    public SpiritRenderer(EntityRendererProvider.Context context) {
        super(context, new SpiritModel<>(context.bakeLayer(TAModelLayers.SPIRIT)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Spirit pEntity) {
        return AurorianMod.prefix("textures/entity/spirit.png");
    }

}