package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.circle.LunaCircleModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.technical.LunaCircleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class LunaCircleRenderer<T extends LunaCircleEntity> extends EntityRenderer<T> {

    private final LunaCircleModel<T> model;

    public LunaCircleRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new LunaCircleModel<>(context.bakeLayer(TAModelLayers.LUNA_CIRCLE));
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(entity.scale, 1.0F, entity.scale);
        this.model.setupAnim(entity, (0.0F), (0.0F), entity.tickCount + partialTicks, (0.0F), (0.0F));
        VertexConsumer vertexConsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(entity)));
        this.model.root().render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix4f, Matrix3f matrix3f, int lightmapUV, float x, float y, float u, float v) {
        consumer.vertex(matrix4f, x - 0.5F, y - 0.25F, 0.0F)
                .color(255, 255, 255, 255).uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lightmapUV)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return AurorianMod.prefix("textures/entity/luna_circle.png");
    }

}