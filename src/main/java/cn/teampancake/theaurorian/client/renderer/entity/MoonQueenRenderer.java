package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import cn.teampancake.theaurorian.common.registry.TAEntityTypes;
import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("UnstableApiUsage")
public class MoonQueenRenderer<T extends MoonQueen> extends DeathWithoutRotationRenderer<T> {

    public MoonQueenRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(TAEntityTypes.MOON_QUEEN.getId()));
        this.shadowRadius = 0.4F;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        boolean flag = entity.hasEffect(TAMobEffects.MOON_BEFALL);
        RenderType renderType = this.getRenderType(entity, this.getTextureLocation(entity), bufferSource, partialTick);
        if (renderType != null) {
            VertexConsumer foilBufferDirect = ItemRenderer.getFoilBufferDirect(bufferSource, renderType, Boolean.FALSE, flag);
            this.defaultRender(poseStack, entity, bufferSource, renderType, foilBufferDirect, entityYaw, partialTick, packedLight);
        }

        this.animatable = entity;
    }

}