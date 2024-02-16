package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.TongScorpionModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.monster.TongScorpion;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TongScorpionRenderer extends MobRenderer<TongScorpion, TongScorpionModel<TongScorpion>> {

    public TongScorpionRenderer(EntityRendererProvider.Context context) {
        super(context, new TongScorpionModel<>(context.bakeLayer(TAModelLayers.TONG_SCORPION)), 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(TongScorpion entity) {
        return AurorianMod.prefix("textures/entity/tong_scorpion.png");
    }

}