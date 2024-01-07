package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.UndeadKnightModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.monster.UndeadKnight;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UndeadKnightRenderer extends MobRenderer<UndeadKnight, UndeadKnightModel<UndeadKnight>> {

    public UndeadKnightRenderer(EntityRendererProvider.Context context) {
        super(context, new UndeadKnightModel<>(context.bakeLayer(TAModelLayers.UNDEAD_KNIGHT)), 0.5F);
    }

    @Override
    protected void scale(UndeadKnight livingEntity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(1.3F, 1.3F, 1.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(UndeadKnight entity) {
        return AurorianMod.prefix("textures/entity/undead_knight.png");
    }

}