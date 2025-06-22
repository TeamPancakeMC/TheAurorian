package cn.teampancake.theaurorian.common.level.chunk;

import cn.teampancake.theaurorian.common.level.biome.TABiomeSource;
import cn.teampancake.theaurorian.common.registry.TABiomes;
import net.minecraft.Util;
import net.minecraft.resources.ResourceKey;
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
    
    // 黯晶区域高度定义
    private static final int SNOWFIELD_HEIGHT = 70;   // 雪原高度
    private static final int HILLS_HEIGHT = 130;      // 丘陵高度
    private static final int MOUNTAIN_HEIGHT = 210;   // 雪山高度
    
    // 黯晶区域中心点坐标
    private static final int CENTER_X = 10000; // 固定中心点X坐标
    private static final int CENTER_Z = 10000; // 固定中心点Z坐标
    
    // 黯晶区域大小定义
    private static final int SNOWFIELD_RADIUS = 4000; // 雪原半径
    private static final int HILLS_RADIUS = 2500;     // 丘陵半径
    private static final int MOUNTAIN_RADIUS = 1200;  // 雪山半径
    
    // 过渡区域宽度
    private static final int TRANSITION_WIDTH = 200; // 区域间过渡宽度
    
    // 高度增强系数 - 控制每层的高度增量
    private static final double HILLS_HEIGHT_FACTOR = 0.0025; // 丘陵高度增强系数
    private static final double MOUNTAIN_HEIGHT_FACTOR = 0.0035; // 雪山高度增强系数
    
    // 垂直平滑系数 - 增加平滑度
    private static final float VERTICAL_SMOOTH_FACTOR = 0.92F;
    
    public static final float[] BIOME_WEIGHTS = Util.make(new float[25], (afloat) -> {
        for(int x = -2; x <= 2; ++x) {
            for(int z = -2; z <= 2; ++z) {
                float weight = 10.0F / Mth.sqrt((float)(x * x + z * z) + 0.2F);
                afloat[x + 2 + (z + 2) * 5] = weight;
            }
        }
    });

    public TANoiseSampler(int width, int height, int yCount, BiomeSource source, NoiseSlider topSlide, NoiseSlider bottomSlide, NoiseSettings settings, BlendedNoise blend, NoiseModifier modifier) {
        this.cellWidth = width;
        this.cellHeight = height;
        this.cellCountY = yCount;
        this.biomeSource = source;
        this.noiseSettings = settings;
        this.topSlide = topSlide;
        this.bottomSlide = bottomSlide;
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
            
            // 获取中心位置的生物群系键，用于检查是否为黯晶区域
            ResourceKey<?> centerBiome = source.getTerrainColumn(x, z).map(column -> column.getResourceKey()).orElse(null);
            boolean isMountainBiome = (centerBiome == TABiomes.FILTHY_ICE_MOUNTAIN);
            boolean isHillsBiome = (centerBiome == TABiomes.FILTHY_ICE_HILLS);
            boolean isSnowfieldBiome = (centerBiome == TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD);
            
            // 计算到黯晶区域中心的距离
            double distanceToCenter = Math.sqrt(Math.pow(x - CENTER_X, 2) + Math.pow(z - CENTER_Z, 2));
            
            // 临近方块采样半径
            final int SAMPLE_RADIUS = 2;
            
            for (int offX = -SAMPLE_RADIUS; offX <= SAMPLE_RADIUS; ++offX) {
                for (int offZ = -SAMPLE_RADIUS; offZ <= SAMPLE_RADIUS; ++offZ) {
                    Optional<TATerrainColumn> terrainColumn = source.getTerrainColumn(x + offX, z + offZ);
                    if (terrainColumn.isEmpty()) continue;
                    float neighborDepth = terrainColumn.get().depth();
                    float neighborScale = terrainColumn.get().scale();
                    
                    // 计算平滑权重
                    float distanceFactor = (float)Math.sqrt(offX * offX + offZ * offZ);
                    float topographicContribution = neighborDepth > centerDepth ? 0.95F : 1.0F;
                    
                    // 使用高斯权重提供更自然的平滑
                    float gaussWeight = (float)Math.exp(-distanceFactor * distanceFactor / 8.0F);
                    // 确保索引不会超出BIOME_WEIGHTS数组的范围(0-24)
                    int biomeWeightIndex = Math.min(4, Math.abs(offX)) + 2 + (Math.min(4, Math.abs(offZ)) + 2) * 5;
                    float piecewiseInfluence = topographicContribution * BIOME_WEIGHTS[biomeWeightIndex] * gaussWeight;
                    
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
                
                // 存储上一层的密度值，用于平滑过渡
                double prevDensity = 0;
                
                for (int index = 0; index <= max; ++index) {
                    int y = index + min;
                    double noise = blend.sampleAndClampNoise(x, y, z, scaleXZ, scaleY, factorXZ, factorY);
                    double totalDensity = this.computeInitialDensity(y, offset, factor, density) + noise;
                    
                    // 判断是否在黯晶区域内
                    if (distanceToCenter <= SNOWFIELD_RADIUS) {
                        // 根据生物群系类型应用高度处理
                        if (isMountainBiome) {
                            // 黯晶雪山 - 高度设置为210
                            totalDensity = applyMountainHeight(y, totalDensity);
                        } else if (isHillsBiome) {
                            // 黯晶丘陵 - 高度设置为130
                            totalDensity = applyHillsHeight(y, totalDensity);
                        } else if (isSnowfieldBiome) {
                            // 黯晶雪原 - 高度设置为70
                            totalDensity = applySnowfieldHeight(y, totalDensity);
                        }
                        
                        // 处理过渡区域
                        if (distanceToCenter > MOUNTAIN_RADIUS && distanceToCenter <= MOUNTAIN_RADIUS + TRANSITION_WIDTH) {
                            // 雪山到丘陵过渡区
                            double transitionFactor = (distanceToCenter - MOUNTAIN_RADIUS) / TRANSITION_WIDTH;
                            transitionFactor = smoothStep(transitionFactor);
                            
                            // 混合雪山和丘陵的高度
                            double mountainDensity = applyMountainHeight(y, totalDensity);
                            double hillsDensity = applyHillsHeight(y, totalDensity);
                            totalDensity = mountainDensity * (1 - transitionFactor) + hillsDensity * transitionFactor;
                        } else if (distanceToCenter > HILLS_RADIUS && distanceToCenter <= HILLS_RADIUS + TRANSITION_WIDTH) {
                            // 丘陵到雪原过渡区
                            double transitionFactor = (distanceToCenter - HILLS_RADIUS) / TRANSITION_WIDTH;
                            transitionFactor = smoothStep(transitionFactor);
                            
                            // 混合丘陵和雪原的高度
                            double hillsDensity = applyHillsHeight(y, totalDensity);
                            double snowfieldDensity = applySnowfieldHeight(y, totalDensity);
                            totalDensity = hillsDensity * (1 - transitionFactor) + snowfieldDensity * transitionFactor;
                        }
                    }
                    
                    // 应用垂直平滑，使地形更加自然
                    if (index > 0) {
                        // 垂直方向的平滑处理，减少陡峭的悬崖
                        totalDensity = prevDensity + (totalDensity - prevDensity) * VERTICAL_SMOOTH_FACTOR;
                    }
                    
                    // 改进的洞穴修改算法，使用平滑的过渡
                    totalDensity = this.caveNoiseModifier.modifyNoise(totalDensity, y * this.cellHeight, z * this.cellWidth, x * this.cellWidth);
                    
                    // 应用滑动平滑
                    totalDensity = this.applySlide(totalDensity, y);
                    
                    doubles[index] = totalDensity;
                    prevDensity = totalDensity;
                }
                
                // 额外垂直平滑处理
                if (isMountainBiome || isHillsBiome || isSnowfieldBiome) {
                    // 多次应用平滑以获得更好的效果
                    for (int pass = 0; pass < 3; pass++) {
                        // 自顶向下平滑
                        for (int i = max; i > 0; i--) {
                            if (i < max) {
                                doubles[i] = doubles[i] * 0.8 + doubles[i + 1] * 0.2;
                            }
                        }
                        
                        // 自底向上平滑
                        for (int i = 0; i < max; i++) {
                            if (i > 0) {
                                doubles[i] = doubles[i] * 0.8 + doubles[i - 1] * 0.2;
                            }
                        }
                    }
                }
            }
        }
    }
    
    // 应用雪山高度
    private double applyMountainHeight(int y, double density) {
        if (y < MOUNTAIN_HEIGHT) {
            // 在目标高度以下增强密度，使地形更高
            return density + (MOUNTAIN_HEIGHT - y) * 0.05;
        } else {
            // 在目标高度以上减少密度，创造山顶
            return density - (y - MOUNTAIN_HEIGHT) * 0.1;
        }
    }
    
    // 应用丘陵高度
    private double applyHillsHeight(int y, double density) {
        if (y < HILLS_HEIGHT) {
            // 在目标高度以下增强密度，使地形更高
            return density + (HILLS_HEIGHT - y) * 0.03;
        } else {
            // 在目标高度以上减少密度，创造丘陵顶
            return density - (y - HILLS_HEIGHT) * 0.1;
        }
    }
    
    // 应用雪原高度
    private double applySnowfieldHeight(int y, double density) {
        if (y < SNOWFIELD_HEIGHT) {
            // 在目标高度以下增强密度，使地形更高
            return density + (SNOWFIELD_HEIGHT - y) * 0.02;
        } else {
            // 在目标高度以上减少密度，创造平坦雪原
            return density - (y - SNOWFIELD_HEIGHT) * 0.15;
        }
    }
    
    // 平滑过渡函数
    private double smoothStep(double x) {
        // 使用改进的平滑步函数（3次多项式）
        return x * x * (3 - 2 * x);
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
    
    public int cellWidth() {
        return this.cellWidth;
    }

    public int cellHeight() {
        return this.cellHeight;
    }

    public int cellCountY() {
        return this.cellCountY;
    }
}