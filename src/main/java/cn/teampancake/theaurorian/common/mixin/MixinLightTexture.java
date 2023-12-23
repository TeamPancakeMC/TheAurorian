package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TADimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LightTexture.class)
public class MixinLightTexture {

    @Shadow @Final private Minecraft minecraft;

    @ModifyVariable(method = "updateLightTexture", at = @At("STORE"), ordinal = 1)
    private float updateLightTexture(float f) {
        ClientLevel level = this.minecraft.level;
        if (level != null && level.dimension() == TADimensions.AURORIAN_DIMENSION) {
            return Math.min(f, 0.35F);
        }

        return f;
    }

}