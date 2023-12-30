package cn.teampancake.theaurorian.client.renderer.level;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

@SuppressWarnings("SpellCheckingInspection")
public class TAFogRenderer {

    private static int targetBiomeFog = -1;
    private static int previousBiomeFog = -1;
    private static long biomeChangedTime = -1L;

    public static float[] setupColor(Camera activeRenderInfo, float partialTicks, ClientLevel level, int renderDistanceChunks, float bossColorModifier) {
        FogType fogType = activeRenderInfo.getFluidInCamera();
        Entity entity = activeRenderInfo.getEntity();
        float fogRed, fogGreen, fogBlue;
        if (fogType == FogType.WATER) {
            long i = Util.getMillis();
            int j = level.getBiome(BlockPos.containing(activeRenderInfo.getPosition())).value().getWaterFogColor();
            if (biomeChangedTime < 0L) {
                targetBiomeFog = j;
                previousBiomeFog = j;
                biomeChangedTime = i;
            }

            float f = Mth.clamp((float)(i - biomeChangedTime) / 5000.0F, 0.0F, 1.0F);
            float f1 = Mth.lerp(f, (float)(previousBiomeFog >> 16 & 255), (float)(targetBiomeFog >> 16 & 255));
            float f2 = Mth.lerp(f, (float)(previousBiomeFog >> 8 & 255), (float)(targetBiomeFog >> 8 & 255));
            float f3 = Mth.lerp(f, (float)(previousBiomeFog & 255), (float)(targetBiomeFog & 255));
            fogRed = f1 / 255.0F;
            fogGreen = f2 / 255.0F;
            fogBlue = f3 / 255.0F;
            if (targetBiomeFog != j) {
                targetBiomeFog = j;
                previousBiomeFog = Mth.floor(f1) << 16 | Mth.floor(f2) << 8 | Mth.floor(f3);
                biomeChangedTime = i;
            }
        } else if (fogType == FogType.LAVA) {
            fogRed = 0.6F;
            fogGreen = 0.1F;
            fogBlue = 0.0F;
            biomeChangedTime = -1L;
        } else if (fogType == FogType.POWDER_SNOW) {
            fogRed = 0.623F;
            fogGreen = 0.734F;
            fogBlue = 0.785F;
            biomeChangedTime = -1L;
            RenderSystem.clearColor(fogRed, fogGreen, fogBlue, 0.0F);
        } else {
            BiomeManager biomeManager = level.biomeManager;
            float f4 = 0.25F + 0.75F * (float)renderDistanceChunks / 32.0F;
            Vec3 vec3 = TASkyRenderer.getSkyColor(level, activeRenderInfo.getPosition());
            float f11 = Mth.clamp(Mth.cos(level.getTimeOfDay(partialTicks) * ((float)Math.PI * 2F)) * 2.0F + 0.5F, 0.0F, 1.0F);
            Vec3 vec31 = activeRenderInfo.getPosition().subtract(2.0D, 2.0D, 2.0D).scale(0.25D);
            Vec3 vec32 = CubicSampler.gaussianSampleVec3(vec31, (x, y, z) -> level.effects().getBrightnessDependentFogColor(
                    Vec3.fromRGB24(biomeManager.getNoiseBiomeAtQuart(x, y, z).value().getFogColor()), f11));
            f4 = 1.0F - (float)Math.pow(f4, 0.25D);
            fogRed = (float)vec32.x();
            fogGreen = (float)vec32.y();
            fogBlue = (float)vec32.z();
            fogRed += ((float)vec3.x - fogRed) * f4;
            fogGreen += ((float)vec3.y - fogGreen) * f4;
            fogBlue += ((float)vec3.z - fogBlue) * f4;
            biomeChangedTime = -1L;
        }

        float f5 = ((float)activeRenderInfo.getPosition().y - (float)level.getMinBuildHeight()) * level.getLevelData().getClearColorScale();
        FogRenderer.MobEffectFogFunction fogFunction = FogRenderer.getPriorityFogFunction(entity, partialTicks);
        if (fogFunction != null && entity instanceof LivingEntity livingEntity) {
            MobEffectInstance effect = livingEntity.getEffect(fogFunction.getMobEffect());
            f5 = fogFunction.getModifiedVoidDarkness(livingEntity, Objects.requireNonNull(effect), f5, partialTicks);
        }

        if (f5 < 1.0F && fogType != FogType.LAVA && fogType != FogType.POWDER_SNOW) {
            if (f5 < 0.0F) {
                f5 = 0.0F;
            }

            f5 *= f5;
            fogRed *= f5;
            fogGreen *= f5;
            fogBlue *= f5;
        }

        if (bossColorModifier > 0.0F) {
            fogRed = fogRed * (1.0F - bossColorModifier) + fogRed * 0.7F * bossColorModifier;
            fogGreen = fogGreen * (1.0F - bossColorModifier) + fogGreen * 0.6F * bossColorModifier;
            fogBlue = fogBlue * (1.0F - bossColorModifier) + fogBlue * 0.6F * bossColorModifier;
        }

        float f7;
        if (fogType == FogType.WATER) {
            if (entity instanceof LocalPlayer localPlayer) {
                f7 = localPlayer.getWaterVision();
            } else {
                f7 = 1.0F;
            }
        } else {
            label86: {
                if (entity instanceof LivingEntity livingEntity) {
                    if (livingEntity.hasEffect(MobEffects.NIGHT_VISION) && !livingEntity.hasEffect(MobEffects.DARKNESS)) {
                        f7 = GameRenderer.getNightVisionScale(livingEntity, partialTicks);
                        break label86;
                    }
                }

                f7 = 0.0F;
            }
        }

        if (fogRed != 0.0F && fogGreen != 0.0F && fogBlue != 0.0F) {
            float f9 = Math.min(1.0F / fogRed, Math.min(1.0F / fogGreen, 1.0F / fogBlue));
            fogRed = fogRed * (1.0F - f7) + fogRed * f9 * f7;
            fogGreen = fogGreen * (1.0F - f7) + fogGreen * f9 * f7;
            fogBlue = fogBlue * (1.0F - f7) + fogBlue * f9 * f7;
        }

        return new float[] {fogRed, fogGreen, fogBlue};
    }

}