package cn.teampancake.theaurorian.client.renderer.level;

import cn.teampancake.theaurorian.common.registry.TABiomes;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;

import java.awt.*;

public class TAWeatherRenderer {

    public static boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
        LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
        lightTexture.turnOnLightLayer();
        int i = Mth.floor(camX);
        int j = Mth.floor(camY);
        int k = Mth.floor(camZ);
        int l = Minecraft.useFancyGraphics() ? 10 : 5;
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder builder = null;
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(Minecraft.useShaderTransparency());
        int i1 = -1;
        float f1 = (float) ticks + partialTick;
        RenderSystem.setShader(GameRenderer::getParticleShader);
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int j1 = k - l; j1 <= k + l; ++j1) {
            for (int k1 = i - l; k1 <= i + l; ++k1) {
                int l1 = (j1 - k + 16) * 32 + k1 - i + 16;
                double d0 = (double) levelRenderer.rainSizeX[l1] * 0.5D;
                double d1 = (double) levelRenderer.rainSizeZ[l1] * 0.5D;
                mutableBlockPos.set(k1, camY, j1);
                if (level.getBiome(mutableBlockPos).is(TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD)) {
                    int i2 = level.getHeight(Heightmap.Types.MOTION_BLOCKING, k1, j1);
                    int j2 = j - l;
                    int k2 = j + l;
                    if (j2 < i2) {
                        j2 = i2;
                    }

                    if (k2 < i2) {
                        k2 = i2;
                    }

                    int l2 = Math.max(i2, j);
                    if (j2 != k2) {
                        RandomSource random = RandomSource.create(k1 * k1 * 3121L + k1 * 45238971L ^ j1 * j1 * 418711L + j1 * 13761L);
                        mutableBlockPos.set(k1, j2, j1);
                        if (i1 != 1) {
                            if (i1 >= 0) {
                                BufferUploader.drawWithShader(builder.buildOrThrow());
                            }

                            i1 = 1;
                            RenderSystem.setShaderTexture(0, LevelRenderer.SNOW_LOCATION);
                            builder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
                        }

                        float f8 = -((float)(ticks & 511) + partialTick) / 512.0F;
                        float f9 = (float)(random.nextDouble() + (double)f1 * 0.01 * (double)((float)random.nextGaussian()));
                        float f10 = (float)(random.nextDouble() + (double)(f1 * (float)random.nextGaussian()) * 0.001);
                        mutableBlockPos.set(k1, l2, j1);
                        int j4 = LevelRenderer.getLightColor(level, mutableBlockPos);
                        int k4 = j4 >> 16 & 65535;
                        int l4 = j4 & 65535;
                        int l3 = (k4 * 3 + 240) / 4;
                        int i4 = (l4 * 3 + 240) / 4;
                        float v1 = (float) j2 * 0.25F + f8 + f10;
                        float v2 = (float) k2 * 0.25F + f8 + f10;
                        double x1 = (double) k1 - camX - d0 + 0.5D;
                        double x2 = (double) k1 - camX + d0 + 0.5D;
                        double z1 = (double) j1 - camZ - d1 + 0.5D;
                        double z2 = (double) j1 - camZ + d1 + 0.5D;
                        builder.addVertex((float) x1, (float)((double)k2 - camY), (float) z1)
                                .setUv(0.0F + f9, v1).setColor(Color.WHITE.getRGB()).setUv2(i4, l3);
                        builder.addVertex((float) x2, (float)((double)k2 - camY), (float) z2)
                                .setUv(1.0F + f9, v1).setColor(Color.WHITE.getRGB()).setUv2(i4, l3);
                        builder.addVertex((float) x2, (float)((double)j2 - camY), (float) z2)
                                .setUv(1.0F + f9, v2).setColor(Color.WHITE.getRGB()).setUv2(i4, l3);
                        builder.addVertex((float) x1, (float)((double)j2 - camY), (float) z1)
                                .setUv(0.0F + f9, v2).setColor(Color.WHITE.getRGB()).setUv2(i4, l3);
                    }
                }
            }
        }

        if (i1 >= 0) {
            BufferUploader.drawWithShader(builder.buildOrThrow());
        }

        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        lightTexture.turnOffLightLayer();
        return true;
    }

}