package cn.teampancake.theaurorian.client.renderer.level;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class TASpecialEffects extends DimensionSpecialEffects {

    public TASpecialEffects() {
        super(OverworldEffects.CLOUD_LEVEL, Boolean.TRUE, SkyType.NONE, Boolean.FALSE, Boolean.FALSE);
    }

    @Override
    public float[] getSunriseColor(float timeOfDay, float partialTicks) {
        return null;
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 fogColor, float brightness) {
        return fogColor.multiply(brightness * 0.94F + 0.06F, brightness * 0.94F + 0.06F, brightness * 0.91F + 0.09F);
    }

    @Override
    public boolean isFoggyAt(int x, int y) {
        return false;
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        return TASkyRenderer.renderSky(level, poseStack, projectionMatrix, partialTick, camera, setupFog);
    }

    @Override
    public boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
        return TAWeatherRenderer.renderSnowAndRain(level, ticks, partialTick, lightTexture, camX, camY, camZ);
    }

    @Override
    public void adjustLightmapColors(ClientLevel level, float partialTicks, float skyDarken, float blockLightRedFlicker, float skyLight, int pixelX, int pixelY, Vector3f colors) {
        GameRenderer gameRenderer = Minecraft.getInstance().gameRenderer;
        float f = Math.min(level.getSkyDarken(partialTicks), 0.35F);
        float f1 = level.getSkyFlashTime() > 0 ? 1.0F : f * 0.95F + 0.05F;
        float f8 = LightTexture.getBrightness(level.dimensionType(), pixelY) * f1;
        float f9 = LightTexture.getBrightness(level.dimensionType(), pixelX) * blockLightRedFlicker;
        float f10 = f9 * ((f9 * 0.6F + 0.4F) * 0.6F + 0.4F);
        float f11 = f9 * (f9 * f9 * 0.6F + 0.4F);
        colors.set(f9, f10, f11);
        Vector3f defaultScale = new Vector3f(1.0F, 1.0F, 1.0F);
        Vector3f vector3f = (new Vector3f(f, f, 1.0F)).lerp(defaultScale, 0.35F);
        colors.add((new Vector3f(vector3f)).mul(f8));
        colors.lerp(new Vector3f(0.75F, 0.75F, 0.75F), 0.04F);
        if (gameRenderer.getDarkenWorldAmount(partialTicks) > 0.0F) {
            float f12 = gameRenderer.getDarkenWorldAmount(partialTicks);
            Vector3f vector3f3 = (new Vector3f(colors)).mul(0.7F, 0.6F, 0.6F);
            colors.lerp(vector3f3, f12);
        }
    }

}