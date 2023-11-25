package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.SpiderlingModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.monster.Spiderling;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SpiderlingRenderer extends MobRenderer<Spiderling, SpiderlingModel<Spiderling>> {

    public SpiderlingRenderer(EntityRendererProvider.Context context) {
        super(context, new SpiderlingModel<>(context.bakeLayer(TAModelLayers.SPIDERLING)), 0.8F);
    }

    @Override
    protected void scale(Spiderling livingEntity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(0.5F, 0.5F, 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Spiderling entity) {
        return AurorianMod.prefix("textures/entity/spiderling.png");
    }

}