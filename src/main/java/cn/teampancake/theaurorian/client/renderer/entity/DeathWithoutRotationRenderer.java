package cn.teampancake.theaurorian.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class DeathWithoutRotationRenderer<T extends Mob, M extends EntityModel<T>> extends MobRenderer<T, M> {

    public DeathWithoutRotationRenderer(EntityRendererProvider.Context context, M model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Override
    protected void setupRotations(T livingEntity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        if (livingEntity.isAutoSpinAttack()) {
            poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F - livingEntity.getXRot()));
            poseStack.mulPose(Axis.YP.rotationDegrees(((float)livingEntity.tickCount + partialTicks) * -75.0F));
        } else if (isEntityUpsideDown(livingEntity)) {
            poseStack.translate(0.0F, livingEntity.getBbHeight() + 0.1F, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        }
    }

}