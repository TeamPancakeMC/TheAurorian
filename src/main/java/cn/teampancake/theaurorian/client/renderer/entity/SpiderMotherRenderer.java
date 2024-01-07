package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.SpiderMotherModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.boss.SpiderMother;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpiderMotherRenderer extends MobRenderer<SpiderMother, SpiderMotherModel<SpiderMother>> {

    public SpiderMotherRenderer(EntityRendererProvider.Context context) {
        super(context, new SpiderMotherModel<>(context.bakeLayer(TAModelLayers.SPIDER_MOTHER)), 2.0F);
    }

    @Override
    public void render(SpiderMother entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
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