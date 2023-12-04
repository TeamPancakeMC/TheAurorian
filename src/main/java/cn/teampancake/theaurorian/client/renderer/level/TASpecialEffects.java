package cn.teampancake.theaurorian.client.renderer.level;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class TASpecialEffects extends DimensionSpecialEffects {

    public TASpecialEffects() {
        super(OverworldEffects.CLOUD_LEVEL, Boolean.TRUE, DimensionSpecialEffects.SkyType.NONE, Boolean.FALSE, Boolean.FALSE);
    }

    @Override
    public float @Nullable [] getSunriseColor(float timeOfDay, float partialTicks) {
        return null;
    }

    @Override
    public @NotNull Vec3 getBrightnessDependentFogColor(Vec3 fogColor, float brightness) {
        return Vec3.ZERO;
    }

    @Override
    public boolean isFoggyAt(int x, int y) {
        return false;
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        return TASkyRenderer.renderSky(level, poseStack, projectionMatrix, partialTick, camera, setupFog);
    }

}