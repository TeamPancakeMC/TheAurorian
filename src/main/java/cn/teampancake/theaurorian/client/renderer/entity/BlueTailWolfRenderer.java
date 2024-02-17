package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.BlueTailWolfModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.animal.BlueTailWolf;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlueTailWolfRenderer extends MobRenderer<BlueTailWolf, BlueTailWolfModel<BlueTailWolf>> {

    public BlueTailWolfRenderer(EntityRendererProvider.Context context) {
        super(context, new BlueTailWolfModel<>(context.bakeLayer(TAModelLayers.BLUE_TAIL_WOLF)), 0.6F);
    }

    @Override
    public ResourceLocation getTextureLocation(BlueTailWolf entity) {
        return AurorianMod.prefix("textures/entity/blue_tail_wolf.png");
    }

}