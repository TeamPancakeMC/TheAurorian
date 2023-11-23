package cn.teampancake.theaurorian.mixin;

import cn.teampancake.theaurorian.data.tags.TAEntityTags;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class MixinLivingEntityRenderer {

    @Inject(method = "getOverlayCoords", at = @At(value = "HEAD"), cancellable = true)
    private static void getOverlayCoords(LivingEntity livingEntity, float u, CallbackInfoReturnable<Integer> cir) {
        if (livingEntity.getType().is(TAEntityTags.HAS_CUSTOM_DEATH_ANIMATION)) {
            cir.setReturnValue(OverlayTexture.pack(OverlayTexture.u(u), OverlayTexture.v(livingEntity.hurtTime > 0)));
        }
    }

    @Inject(method = "setupRotations", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionf;)V", ordinal = 1, shift = At.Shift.BEFORE), cancellable = true)
    private void setupRotations(LivingEntity livingEntity, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks, CallbackInfo ci) {
        if (livingEntity.getType().is(TAEntityTags.HAS_CUSTOM_DEATH_ANIMATION)) {
            ci.cancel();
        }
    }

}