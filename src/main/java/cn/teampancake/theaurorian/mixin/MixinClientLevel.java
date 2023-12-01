package cn.teampancake.theaurorian.mixin;

import cn.teampancake.theaurorian.registry.TADimensions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;
import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public abstract class MixinClientLevel extends Level {

    protected MixinClientLevel(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, Supplier<ProfilerFiller> profiler, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, profiler, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    @Inject(method = "getSkyColor", at = @At(value = "HEAD"), cancellable = true)
    public void getSkyColor(Vec3 pos, float partialTick, CallbackInfoReturnable<Vec3> cir) {
        if (this.dimension() == TADimensions.AURORIAN_DIMENSION) {
            float timeOfDay = this.dimensionType().timeOfDay(1000L);
            int rgb = this.smoothColorTransition(this.dayTime()/24000F);
            Vec3 vec3 = pos.subtract(2.0D, 2.0D, 2.0D).scale(0.25D);
            Vec3 vec31 = CubicSampler.gaussianSampleVec3(vec3, (x, y, z) -> Vec3.fromRGB24(rgb));
            float f1 = Mth.cos(timeOfDay * ((float) Math.PI * 2F)) * 2.0F + 0.5F;
            float f2 = (float) vec31.x * f1;
            float f3 = (float) vec31.y * f1;
            float f4 = (float) vec31.z * f1;
            cir.setReturnValue(new Vec3(f2, f3, f4));
        }
    }

    private int smoothColorTransition(float t) {
        Color currentColor = new Color(0x010e34);
        Color targetColor = new Color(TADimensions.daySkyColors[TADimensions.getPhase_State(t)]);
        double d = Math.sin(2.0F * Math.PI * t + 0.25F * Math.PI);
        d = (d + 1.0D) / 2.0D;
        int r = currentColor.getRed() + (int) ((targetColor.getRed() - currentColor.getRed()) * d);
        int g = currentColor.getGreen() + (int) ((targetColor.getGreen() - currentColor.getGreen()) * d);
        int b = currentColor.getBlue() + (int) ((targetColor.getBlue() - currentColor.getBlue()) * d);
        return new Color(r, g, b).getRGB();
    }

}