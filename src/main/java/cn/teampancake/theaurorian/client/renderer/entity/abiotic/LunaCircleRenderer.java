package cn.teampancake.theaurorian.client.renderer.entity.abiotic;

import cn.teampancake.theaurorian.common.entities.technical.LunaCircleEntity;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class LunaCircleRenderer<T extends LunaCircleEntity> extends GeoEntityRenderer<T> {

    public LunaCircleRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(TAEntityTypes.LUNA_CIRCLE.getId()));
    }

    @Override
    public void renderFinal(PoseStack poseStack, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.model.getBone("all").ifPresent(geoBone -> geoBone.updateScale(animatable.getScale(), 1.0F, animatable.getScale()));
        super.renderFinal(poseStack, animatable, model, bufferSource, buffer, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

}