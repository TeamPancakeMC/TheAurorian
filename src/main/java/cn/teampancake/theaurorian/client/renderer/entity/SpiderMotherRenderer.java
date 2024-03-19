package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@OnlyIn(Dist.CLIENT)
public class SpiderMotherRenderer<T extends SpiderMother> extends DeathWithoutRotationRenderer<T> {

    public SpiderMotherRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(TAEntityTypes.SPIDER_MOTHER.getId()));
    }

    @Override
    public void renderFinal(PoseStack poseStack, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (animatable.isHanging()) {
            poseStack.translate(0.0F, 0.0F, 0.5F);
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        }
        super.renderFinal(poseStack, animatable, model, bufferSource, buffer, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

}