package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TAMobEffects;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Shadow @Final Minecraft minecraft;

    @Inject(method = "bobHurt", at = @At(value = "HEAD"), cancellable = true)
    private void bobHurt(PoseStack matrixStack, float partialTicks, CallbackInfo ci) {
        if (this.minecraft.getCameraEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(TAMobEffects.CORRUPTION.get())) {
                ci.cancel();
            }
        }
    }

}