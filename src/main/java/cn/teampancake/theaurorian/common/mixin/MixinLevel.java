package cn.teampancake.theaurorian.common.mixin;

import cn.teampancake.theaurorian.common.registry.TADimensions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Level.class)
public abstract class MixinLevel {

    @Shadow @Final private ResourceKey<Level> dimension;
    @Shadow public abstract float getRainLevel(float delta);
    @Shadow public abstract float getThunderLevel(float delta);
    @Shadow public abstract DimensionType dimensionType();
    @Shadow public abstract long getDayTime();
    @Shadow private int skyDarken;

    @Inject(method = "updateSkyBrightness", at = @At(value = "HEAD"), cancellable = true)
    public void updateSkyBrightness(CallbackInfo ci) {
        if (this.dimension == TADimensions.AURORIAN_DIMENSION) {
            float time = this.dimensionType().timeOfDay(this.getDayTime());
            double rainLevel = 1.0D - (double)(this.getRainLevel(1.0F) * 5.0F) / 16.0D;
            double thunderLevel = 1.0D - (double)(this.getThunderLevel(1.0F) * 5.0F) / 16.0D;
            double d2 = 0.5D + 2.0D * Mth.clamp(Mth.cos(time * ((float)Math.PI * 2F)), -0.25D, 0.25D);
            this.skyDarken = Math.max(5, (int)((1.0D - d2 * rainLevel * thunderLevel) * 11.0D));
            ci.cancel();
        }
    }

}