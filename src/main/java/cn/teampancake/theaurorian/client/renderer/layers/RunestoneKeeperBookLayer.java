package cn.teampancake.theaurorian.client.renderer.layers;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.RunestoneBookModel;
import cn.teampancake.theaurorian.common.entities.boss.RunestoneKeeper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RunestoneKeeperBookLayer<T extends RunestoneKeeper, M extends EntityModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation LAYER_LOCATION = AurorianMod.prefix("textures/entity/runestone_books.png");
    private final RunestoneBookModel<T> runestoneBookModel;

    public RunestoneKeeperBookLayer(RenderLayerParent<T, M> renderer, RunestoneBookModel<T> runestoneBookModel) {
        super(renderer);
        this.runestoneBookModel = runestoneBookModel;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing,
            float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        poseStack.pushPose();
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.armorCutoutNoCull(LAYER_LOCATION));
        this.runestoneBookModel.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.runestoneBookModel.book.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

}