package cn.teampancake.theaurorian.mixin;

import cn.teampancake.theaurorian.registry.TADimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.awt.*;

@Mixin(LightTexture.class)
public class MixinLightTexture {

    @Inject(method = "updateLightTexture", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/LightTexture;blockLightRedFlicker:F"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void updateLightTexture(float partialTicks, CallbackInfo ci, ClientLevel clientLevel, float $$2, float $$4, float $$5, float $$6, float $$7, float $$11, float $$8, Vector3f skyVector) {
        if (clientLevel.dimension() == TADimensions.AURORIAN_DIMENSION) {
            Vec3 position = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
            Vector3f targetColor = clientLevel.getSkyColor(position, 1.0F).toVector3f();
            skyVector.lerp(targetColor, clientLevel.getSkyDarken(1.0F) * 1.5F);
        }
    }

}