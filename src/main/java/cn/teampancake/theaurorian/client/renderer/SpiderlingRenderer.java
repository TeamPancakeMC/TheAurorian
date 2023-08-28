package cn.teampancake.theaurorian.client.renderer;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.common.entities.monster.Spiderling;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.resources.ResourceLocation;

public class SpiderlingRenderer extends SpiderRenderer<Spiderling> {

    public SpiderlingRenderer(EntityRendererProvider.Context context) {
        super(context);
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