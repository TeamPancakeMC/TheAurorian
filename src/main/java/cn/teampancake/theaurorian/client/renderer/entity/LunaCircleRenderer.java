package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.circle.LunaCircleModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.technical.LunaCircleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class LunaCircleRenderer<T extends LunaCircleEntity> extends EntityRenderer<T> {

    private final LunaCircleModel<T> model;

    public LunaCircleRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new LunaCircleModel<>(context.bakeLayer(TAModelLayers.LUNA_CIRCLE));
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        float f6 = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        float f7 = entity.tickCount + partialTicks;
        poseStack.scale(entity.scale, 1.0F, entity.scale);
        this.model.prepareMobModel(entity, 0.0F, 0.0F, partialTicks);
        this.model.setupAnim(entity, 0.0F, 0.0F, f7, 0.0F, f6);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySmoothCutout(this.getTextureLocation(entity)));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return AurorianMod.prefix("textures/entity/luna_circle.png");
    }

}