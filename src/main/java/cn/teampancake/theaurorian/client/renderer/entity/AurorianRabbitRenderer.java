package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.AurorianRabbitModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.animal.AurorianRabbit;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AurorianRabbitRenderer extends MobRenderer<AurorianRabbit, AurorianRabbitModel<AurorianRabbit>> {

    public AurorianRabbitRenderer(EntityRendererProvider.Context context) {
        super(context, new AurorianRabbitModel<>(context.bakeLayer(TAModelLayers.AURORIAN_RABBIT)), 0.3F);

    }

    @Override
    public void render(AurorianRabbit entity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        this.shadowRadius = entity.isBaby() ? 0.1F : 0.3F;
        super.render(entity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(AurorianRabbit rabbit) {
        return AurorianMod.prefix("textures/entity/aurorian_rabbit.png");
    }

}