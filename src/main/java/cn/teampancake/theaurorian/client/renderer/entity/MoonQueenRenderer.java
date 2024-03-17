package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@OnlyIn(Dist.CLIENT)
public class MoonQueenRenderer<T extends MoonQueen> extends DeathWithoutRotationRenderer<T> {

    public MoonQueenRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(TAEntityTypes.MOON_QUEEN.getId()));
        this.shadowRadius = 0.4F;
    }

    @Override
    public void renderFinal(PoseStack poseStack, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderFinal(poseStack, animatable, model, bufferSource, buffer, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

}