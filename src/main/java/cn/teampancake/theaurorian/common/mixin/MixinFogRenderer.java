package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.level.SylvanisHandler;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class MixinFogRenderer {

    @Shadow private static float fogRed;
    @Shadow private static float fogGreen;
    @Shadow private static float fogBlue;

    @Inject(method = "levelFogColor", at = @At(value = "HEAD"), cancellable = true)
    private static void levelFogColor(CallbackInfo ci) {
        SylvanisHandler.levelFogColorAlpha(fogRed, fogGreen, fogBlue, ci);
    }

}