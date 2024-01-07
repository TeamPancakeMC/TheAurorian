package cn.teampancake.theaurorian.client.renderer.entity;

import cn.teampancake.theaurorian.AurorianMod;
import cn.teampancake.theaurorian.client.model.entity.MoonQueenModel;
import cn.teampancake.theaurorian.client.renderer.layers.TAModelLayers;
import cn.teampancake.theaurorian.common.entities.boss.MoonQueen;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class MoonQueenRenderer extends MobRenderer<MoonQueen, MoonQueenModel<MoonQueen>> {

    public MoonQueenRenderer(EntityRendererProvider.Context context) {
        super(context, new MoonQueenModel<>(context.bakeLayer(TAModelLayers.MOON_QUEEN)), 0.5F);
    }

    @Override
    public void render(MoonQueen entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        Minecraft minecraft = Minecraft.getInstance();
        this.model.attackTime = this.getAttackAnim(entity, partialTicks);
        boolean shouldSit = entity.isPassenger() && (entity.getVehicle() != null && entity.getVehicle().shouldRiderSit());
        boolean standAndAlive = !shouldSit && entity.isAlive();
        float f = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
        float f1 = Mth.rotLerp(partialTicks, entity.yHeadRotO, entity.yHeadRot);
        float f6 = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        float f7 = this.getBob(entity, partialTicks);
        this.setupRotations(entity, poseStack, f7, f, partialTicks);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        this.scale(entity, poseStack, partialTicks);
        poseStack.translate(0.0F, -1.501F, 0.0F);
        float f8 = standAndAlive ? Math.min(entity.walkAnimation.speed(partialTicks), 1.0F) : 0.0F;
        float f5 = standAndAlive ? entity.walkAnimation.position(partialTicks) : 0.0F;
        this.model.prepareMobModel(entity, f5, f8, partialTicks);
        this.model.setupAnim(entity, f5, f8, f7, f1 - f, f6);
        boolean flag1 = !this.isBodyVisible(entity) && !entity.isInvisibleTo(Objects.requireNonNull(minecraft.player));
        if (this.getRenderType(entity, this.isBodyVisible(entity), flag1, minecraft.shouldEntityAppearGlowing(entity)) != null) {
            VertexConsumer foilBufferDirect = ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(this.getTextureLocation(entity)), false, entity.isGlinting());
            int i = getOverlayCoords(entity, this.getWhiteOverlayProgress(entity, partialTicks));
            this.model.renderToBuffer(poseStack, foilBufferDirect, packedLight, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
        }

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(MoonQueen entity) {
        return AurorianMod.prefix("textures/entity/moon_queen.png");
    }

}