package cn.teampancake.theaurorian.client.renderer;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.resources.ResourceLocation;

public class SpiderMotherRenderer extends SpiderRenderer<SpiderMother> {

    public SpiderMotherRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 2.0F;
    }

    @Override
    public void render(SpiderMother entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(2.0F, 2.0F, 2.0F);
        if (entity.isHanging()) {
            poseStack.translate(0.0F, 0.0F, 0.5F);
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SpiderMother entity) {
        return AurorianMod.prefix("textures/entity/spider_mother.png");
    }

}