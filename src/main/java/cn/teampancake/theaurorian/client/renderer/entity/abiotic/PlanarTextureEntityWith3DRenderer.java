package cn.teampancake.theaurorian.client.renderer.entity.abiotic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

/** @noinspection deprecation*/
@OnlyIn(Dist.CLIENT)
public abstract class PlanarTextureEntityWith3DRenderer<T extends Entity> extends EntityRenderer<T> {

    private final ModelManager modelManager;

    public PlanarTextureEntityWith3DRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.modelManager = context.getModelManager();
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        ModelResourceLocation modelLocation = ModelResourceLocation.inventory(this.getTextureLocation(entity));
        BakedModel bakedModel = this.modelManager.getModel(modelLocation);
        float f = bakedModel.getTransforms().getTransform(ItemDisplayContext.GROUND).scale.y();
        poseStack.translate(0.0F, 0.25F * f, 0.0F);
        poseStack.translate(-0.5F, -0.5F, -0.5F);
        VertexConsumer buffer = bufferSource.getBuffer(Sheets.translucentCullBlockSheet());
        this.renderModelLists(bakedModel, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer);
        poseStack.popPose();
    }

    public void renderModelLists(BakedModel model, int combinedLight, int combinedOverlay, PoseStack poseStack, VertexConsumer buffer) {
        RandomSource random = RandomSource.create();
        for (Direction direction : Direction.values()) {
            random.setSeed(42L);
            List<BakedQuad> quads = model.getQuads(null, direction, random);
            this.renderQuadList(poseStack, buffer, quads, combinedLight, combinedOverlay);
        }

        random.setSeed(42L);
        List<BakedQuad> quads = model.getQuads(null, null, random);
        this.renderQuadList(poseStack, buffer, quads, combinedLight, combinedOverlay);
    }

    public void renderQuadList(PoseStack poseStack, VertexConsumer buffer, List<BakedQuad> quads, int combinedLight, int combinedOverlay) {
        quads.forEach(bakedQuad -> buffer.putBulkData(poseStack.last(), bakedQuad, 1.0F, 1.0F, 1.0F, 1.0F, combinedLight, combinedOverlay, Boolean.TRUE));
    }

}