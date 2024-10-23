package cn.teampancake.theaurorian.client.renderer.entity.abiotic;

import cn.teampancake.theaurorian.TheAurorian;
import cn.teampancake.theaurorian.client.model.entity.MoonQueenSwordModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.projectile.MoonQueenSword;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoonQueenSwordRenderer extends EntityRenderer<MoonQueenSword> {

    private static final float ZERO = 0.0F;
    private final MoonQueenSwordModel model;

    public MoonQueenSwordRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new MoonQueenSwordModel(context.bakeLayer(TAModelLayers.MOON_QUEEN_SWORD));
    }

    @Override
    public void render(MoonQueenSword entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0F, -0.8F, 0.0F);
        float extra = entity.getSwordType() == 1 && entity.getTimeUntilShoot() > 0 ? -90.0F : 0.0F;
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) + 180.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot()) + extra));
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(this.getTextureLocation(entity)));
        this.model.setupAnim(entity, ZERO, ZERO, entity.tickCount + partialTick, ZERO, ZERO);
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Override
    protected int getBlockLightLevel(MoonQueenSword entity, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(MoonQueenSword entity) {
        return TheAurorian.prefix("textures/entity/moon_queen_sword.png");
    }

}