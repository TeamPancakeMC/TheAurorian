package cn.teampancake.theaurorian.common.level.chunk;

import cn.teampancake.theaurorian.common.level.biome.TABiomeSource;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;

import java.util.Optional;

public class TANoiseSampler {

    private final int cellWidth;
    private final int cellHeight;
    private final int cellCountY;
    private final BiomeSource biomeSource;
    private final NoiseSettings noiseSettings;
    private final NoiseSlider topSlide;
    private final NoiseSlider bottomSlide;
    private final BlendedNoise blendedNoise;
    private final double dimensionDensityFactor;
    private final double dimensionDensityOffset;
    public final NoiseModifier caveNoiseModifier;
    public static final float[] BIOME_WEIGHTS = Util.make(new float[25], (afloat) -> {
        for(int x = -2; x <= 2; ++x) {
            for(int z = -2; z <= 2; ++z) {
                float weight = 10.0F / Mth.sqrt((float)(x * x + z * z) + 0.2F);
                afloat[x + 2 + (z + 2) * 5] = weight;
            }
        }
    });

    public TANoiseSampler(int width, int height, int yCount, BiomeSource source, NoiseSlider topslide, NoiseSlider bottomslide, NoiseSettings settings, BlendedNoise blend, NoiseModifier modifier) {
        this.cellWidth = width;
        this.cellHeight = height;
        this.cellCountY = yCount;
        this.biomeSource = source;
        this.noiseSettings = settings;
        this.topSlide = topslide;
        this.bottomSlide = bottomslide;
        this.blendedNoise = blend;
        this.dimensionDensityFactor = source instanceof TABiomeSource biomeSource ? biomeSource.getBaseFactor() : 1.0F;
        this.dimensionDensityOffset = source instanceof TABiomeSource biomeSource ? biomeSource.getBaseOffset() : 0.0F;
        this.caveNoiseModifier = modifier;
    }

    public void fillNoiseColumn(double[] doubles, int x, int z, int min, int max) {
        if (this.biomeSource instanceof TABiomeSource source) {
            float totalScale = 0.0F;
            float totalDepth = 0.0F;
            float totalContribution = 0.0F;
            float centerDepth = source.getBiomeDepth(x, z);
            for (int offX = -2; offX <= 2; ++offX) {
                for (int offZ = -2; offZ <= 2; ++offZ) {
                    Optional<TATerrainColumn> terrainColumn = source.getTerrainColumn(x + offX, z + offZ);
                    if (terrainColumn.isEmpty()) continue;
                    float neighborDepth = terrainColumn.get().depth();
                    float neighborScale = terrainColumn.get().scale();
                    float topographicContribution = neighborDepth > centerDepth ? 0.5F : 1.0F;
                    float piecewiseInfluence = topographicContribution * BIOME_WEIGHTS[offX + 2 + (offZ + 2) * 5] / (neighborDepth + 2.0F);
                    totalDepth += neighborDepth * piecewiseInfluence;
                    totalScale += neighborScale * piecewiseInfluence;
                    totalContribution += piecewiseInfluence;
                }
            }

            float depthNormalized = totalDepth / totalContribution;
            float scaleNormalized = totalScale / totalContribution;
            double modifiedDepth = depthNormalized * 0.5F - 0.125F;
            double modifiedScale = scaleNormalized * 0.9F + 0.1F;
            double offset = modifiedDepth * 0.265625D;
            double factor = 96.0D / modifiedScale;
            if (this.blendedNoise instanceof TABlendedNoise blend) {
                double scaleXZ = 684.412D * blend.xzScale;
                double scaleY = 684.412D * blend.yScale;
                double factorXZ = scaleXZ / blend.xzFactor;
                double factorY = scaleY / blend.yFactor;
                double density = -0.46875;
                for (int index = 0; index <= max; ++index) {
                    int y = index + min;
                    double noise = blend.sampleAndClampNoise(x, y, z, scaleXZ, scaleY, factorXZ, factorY);
                    double totalDensity = this.computeInitialDensity(y, offset, factor, density) + noise;
                    totalDensity = this.caveNoiseModifier.modifyNoise(totalDensity, y * this.cellHeight, z * this.cellWidth, x * this.cellWidth);
                    totalDensity = this.applySlide(totalDensity, y);
                    doubles[index] = totalDensity;
                }
            }
        }
    }

    protected double computeInitialDensity(int y, double offset, double factor, double density) {
        double base = 1.0D - (double)y * 2.0D / 32.0D + density;
        double factored = base * this.dimensionDensityFactor + this.dimensionDensityOffset;
        double total = (factored + offset) * factor;
        return total * (double)(total > 0.0D ? 4 : 1);
    }

    protected double applySlide(double density, int height) {
        int i = Math.floorDiv(this.noiseSettings.minY(), this.cellHeight);
        int j = height - i;
        density = this.topSlide.applySlide(density, this.cellCountY - j);
        density = this.bottomSlide.applySlide(density, j);
        return density;
    }

}