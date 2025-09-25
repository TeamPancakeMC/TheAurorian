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
            ResourceKey<?> centerBiome = source.getTerrainColumn(x, z).map(TATerrainColumn::getResourceKey).orElse(null);
            boolean isMountainBiome = (centerBiome == TABiomes.FILTHY_ICE_MOUNTAIN);
            boolean isHillsBiome = (centerBiome == TABiomes.FILTHY_ICE_HILLS);
            boolean isSnowfieldBiome = (centerBiome == TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD);
            
            // 计算到黯晶区域中心的距离
            double distanceToCenter = Math.sqrt(Math.pow(x - CENTER_X, 2) + Math.pow(z - CENTER_Z, 2));
            
            // 临近方块采样半径，增加采样范围以获得更平滑的结果
            final int SAMPLE_RADIUS = 2;
            
            for (int offX = -SAMPLE_RADIUS; offX <= SAMPLE_RADIUS; ++offX) {
                for (int offZ = -SAMPLE_RADIUS; offZ <= SAMPLE_RADIUS; ++offZ) {
                    Optional<TATerrainColumn> terrainColumn = source.getTerrainColumn(x + offX, z + offZ);
                    if (terrainColumn.isEmpty()) continue;
                    float neighborDepth = terrainColumn.get().depth();
                    float neighborScale = terrainColumn.get().scale();
                    
                    // 改进平滑权重计算
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
                    
                    // 根据生物群系和高度应用特殊处理
                    if (isInFilthyIceRegion(distanceToCenter)) {
                        // 黯晶区域的高度处理
                        
                        if (isMountainBiome) {
                            // 黯晶雪山 - 高度设置为210
                            if (y < MOUNTAIN_HEIGHT) {
                                // 在目标高度以下增强密度，使地形更高
                                totalDensity += (MOUNTAIN_HEIGHT - y) * MOUNTAIN_HEIGHT_FACTOR;
                            } else {
                                // 在目标高度以上减少密度，创造山顶
                                totalDensity -= (y - MOUNTAIN_HEIGHT) * MOUNTAIN_HEIGHT_FACTOR * 2;
                            }
                            
                            // 平滑山顶
                            if (y > MOUNTAIN_HEIGHT - 10 && y < MOUNTAIN_HEIGHT + 10) {
                                double flattenFactor = 1.0 - Math.abs(y - MOUNTAIN_HEIGHT) / 10.0;
                                totalDensity = totalDensity * (1 - flattenFactor * 0.5) + 0.2 * flattenFactor;
                            }
                            
                            // 处理山坡过渡到丘陵
                            double distFromMountainEdge = MOUNTAIN_RADIUS - distanceToCenter;
                            if (distFromMountainEdge < TRANSITION_WIDTH && distFromMountainEdge > 0) {
                                // 在山脚和丘陵之间平滑过渡
                                double transitionFactor = distFromMountainEdge / TRANSITION_WIDTH;
                                transitionFactor = smoothStep(transitionFactor);
                                
                                if (y > HILLS_HEIGHT) {
                                    // 高度超过丘陵高度时，根据过渡因子调整密度
                                    totalDensity = totalDensity * transitionFactor + 
                                                  (computeHillsDensity(y) * (1 - transitionFactor));
                                }
                            }
                        } else if (isHillsBiome) {
                            // 黯晶丘陵 - 高度设置为130
                            totalDensity = computeHillsDensity(y);
                            
                            // 处理丘陵过渡到雪原
                            double distFromHillsEdge = HILLS_RADIUS - distanceToCenter;
                            if (distFromHillsEdge < TRANSITION_WIDTH && distFromHillsEdge > 0) {
                                // 在丘陵边缘和雪原之间平滑过渡
                                double transitionFactor = distFromHillsEdge / TRANSITION_WIDTH;
                                transitionFactor = smoothStep(transitionFactor);
                                
                                if (y > SNOWFIELD_HEIGHT) {
                                    // 高度超过雪原高度时，根据过渡因子调整密度
                                    totalDensity = totalDensity * transitionFactor + 
                                                  (computeSnowfieldDensity(y) * (1 - transitionFactor));
                                }
                            }
                        } else if (isSnowfieldBiome) {
                            // 黯晶雪原 - 高度设置为70
                            totalDensity = computeSnowfieldDensity(y);
                        }
                    } else {
                        // 非黯晶区域：叠加类主世界地貌（高山/丘陵/山地）
                        // 宏观起伏（大陆性）
                        double macro = blend.sampleAndClampNoise(x >> 2, 0, z >> 2, scaleXZ * 0.25, scaleY * 0.25, factorXZ * 0.5, factorY * 0.5);
                        // 脊状噪声（山脊）
                        double ridged = Math.abs(blend.sampleAndClampNoise(x >> 1, 0, z >> 1, scaleXZ * 0.5, scaleY * 0.5, factorXZ * 0.9, factorY * 0.9));
                        ridged = Math.max(0.0, ridged - 0.25) * 1.8; // 去偏移，增强山峰
                        // 高度增强
                        double sea = 62.0;
                        double alt = Mth.clamp((y - (sea + 8.0)) / 96.0, 0.0, 1.0); // 海拔越高越强化
                        double overworldShape = macro * 0.7 + ridged * alt;
                        // 低处削弱，形成谷地/滩涂过渡
                        if (y < sea + 6.0) {
                            overworldShape -= (sea + 6.0 - y) * 0.01;
                        }
                        totalDensity += overworldShape;
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
                
                // 额外垂直平滑处理：自顶向下和自底向上双向平滑
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

    // 判断坐标是否在黯晶区域内
    private boolean isInFilthyIceRegion(double distanceToCenter) {
        return distanceToCenter <= SNOWFIELD_RADIUS;
    }
    
    // 计算黯晶丘陵的密度值
    private double computeHillsDensity(int y) {
        if (y < SNOWFIELD_HEIGHT) {
            // 在雪原高度以下保持正常密度
            return 0.2;
        } else if (y < HILLS_HEIGHT) {
            // 在雪原和丘陵高度之间线性增强密度
            double heightFactor = (double)(y - SNOWFIELD_HEIGHT) / (HILLS_HEIGHT - SNOWFIELD_HEIGHT);
            return 0.2 + heightFactor * HILLS_HEIGHT_FACTOR * 100;
        } else {
            // 在丘陵高度以上减少密度，创造平顶
            return 0.2 - (y - HILLS_HEIGHT) * HILLS_HEIGHT_FACTOR * 2;
        }
    }
    
    // 计算黯晶雪原的密度值
    private double computeSnowfieldDensity(int y) {
        if (y < SNOWFIELD_HEIGHT) {
            // 在雪原高度以下保持正常密度
            return 0.2;
        } else {
            // 在雪原高度以上减少密度，创造平顶
            return 0.2 - (y - SNOWFIELD_HEIGHT) * 0.01;
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

}