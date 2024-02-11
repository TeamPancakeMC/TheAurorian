package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class MixinLivingEntityRenderer {

    @Inject(method = "getOverlayCoords", at = @At(value = "HEAD"), cancellable = true)
    private static void getOverlayCoords(LivingEntity livingEntity, float u, CallbackInfoReturnable<Integer> cir) {
        if (livingEntity.hasEffect(TAMobEffects.CORRUPTION.get())) {
            cir.setReturnValue(OverlayTexture.NO_OVERLAY);
        }
    }

}