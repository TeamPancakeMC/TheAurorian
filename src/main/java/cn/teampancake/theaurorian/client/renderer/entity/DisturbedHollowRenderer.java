package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.model.entity.DisturbedHollowModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.monster.DisturbedHollow;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DisturbedHollowRenderer extends MobRenderer<DisturbedHollow, DisturbedHollowModel<DisturbedHollow>> {

    public DisturbedHollowRenderer(EntityRendererProvider.Context context) {
        super(context, new DisturbedHollowModel<>(context.bakeLayer(TAModelLayers.DISTURBED_HOLLOW)), 1.5F);
    }

    @Override
    public void render(DisturbedHollow entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        this.model.attackTime = this.getAttackAnim(entity, partialTicks);
        boolean shouldSit = entity.isPassenger() && (entity.getVehicle() != null && entity.getVehicle().shouldRiderSit());
        float f = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
        float f2 = Mth.rotLerp(partialTicks, entity.yHeadRotO, entity.yHeadRot) - f;
        float f6 = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        if (isEntityUpsideDown(entity)) {
            f6 *= -1.0F;
            f2 *= -1.0F;
        }

        f2 = Mth.wrapDegrees(f2);
        float f8 = entity.getScale();
        poseStack.scale(f8, f8, f8);
        float f9 = this.getBob(entity, partialTicks);
        this.setupRotations(entity, poseStack, f9, f, partialTicks, f8);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        this.scale(entity, poseStack, partialTicks);
        poseStack.translate(0.0F, -1.501F, 0.0F);
        float f4 = 0.0F;
        float f5 = 0.0F;
        if (!shouldSit && entity.isAlive()) {
            f4 = Math.min(entity.walkAnimation.speed(partialTicks), 1.0F);
            f5 = entity.walkAnimation.position(partialTicks);
        }

        this.model.setupAnim(entity, f5, f4, f9, f2, f6);
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            boolean flag = this.isBodyVisible(entity);
            boolean flag1 = !flag && !entity.isInvisibleTo(minecraft.player);
            boolean flag2 = minecraft.shouldEntityAppearGlowing(entity);
            RenderType renderType = this.getRenderType(entity, flag, flag1, flag2);
            if (renderType != null) {
                VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
                this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, getOverlayCoords(entity), flag1 ? 654311423 : -1);
            }
        }

        poseStack.popPose();
    }

    private static int getOverlayCoords(DisturbedHollow entity) {
        return OverlayTexture.pack(OverlayTexture.u(0.0F), OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0 || entity.isAngry()));
    }

    @Override
    public ResourceLocation getTextureLocation(DisturbedHollow entity) {
        return TheAurorian.prefix("textures/entity/disturbed_hollow.png");
    }

}