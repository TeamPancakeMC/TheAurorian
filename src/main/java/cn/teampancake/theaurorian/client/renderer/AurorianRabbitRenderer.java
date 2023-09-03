package cn.teampancake.theaurorian.client.renderer;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.renderer.layers.ModModelLayers;
import cn.teampancake.theaurorian.common.entities.animal.AurorianRabbit;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianRabbitRenderer extends MobRenderer<AurorianRabbit, RabbitModel<AurorianRabbit>> {

    public AurorianRabbitRenderer(EntityRendererProvider.Context context) {
        super(context, new RabbitModel<>(context.bakeLayer(ModModelLayers.AURORIAN_RABBIT)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(AurorianRabbit rabbit) {
        return AurorianMod.prefix("textures/entity/aurorian_rabbit.png");
    }

}