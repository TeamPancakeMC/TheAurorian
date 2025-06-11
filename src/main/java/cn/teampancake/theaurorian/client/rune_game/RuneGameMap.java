package cn.teampancake.theaurorian.client.rune_game;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.HashMap;
import java.util.Map;

public class RuneGameMap {

    protected RuneGameLayer[] runeGameLayers;
    // 添加缓存来存储已检查过的品牌交叉结果
    private final Map<RuneGameBrand, Boolean> intersectionCache = new HashMap<>();
    // 符文类型限制，默认为12（全部类型）
    private int runeTypesLimit = 12;
    // 是否为困难模式
    private boolean isHardMode = false;

    public RuneGameMap(int[][][] level) {
        List<RuneGameBrand> brands = RuneGameBrand.randomBrandList(level);
        this.runeGameLayers = new RuneGameLayer[level.length];
        for (int i = 0; i < level.length; i++) {
            this.runeGameLayers[i] = new RuneGameLayer(level[i], brands);
        }

        for (int i = 0; i < this.runeGameLayers.length; i++) {
            RuneGameLayer runeGameLayer = this.runeGameLayers[i];
            if (i != 0) {
                runeGameLayer.setParent(this.runeGameLayers[i - 1]);
            }
        }
    }

    /**
     * 使用自定义符文列表初始化地图
     * @param brands 自定义符文列表
     * @return 当前地图实例，用于链式调用
     */
    public RuneGameMap initWithCustomBrands(List<RuneGameBrand> brands) {
        if (runeGameLayers == null || runeGameLayers.length == 0) {
            return this;
        }
        
        // 获取地图数据结构
        int[][][] level = new int[runeGameLayers.length][][];
        for (int i = 0; i < runeGameLayers.length; i++) {
            RuneGameBrand[][] layerBrands = runeGameLayers[i].getBrands();
            int[][] layerData = new int[layerBrands.length][];
            for (int j = 0; j < layerBrands.length; j++) {
                layerData[j] = new int[layerBrands[j].length];
                for (int k = 0; k < layerBrands[j].length; k++) {
                    if (layerBrands[j][k] != null) {
                        layerData[j][k] = layerBrands[j][k].hasBrand();
                    }
                }
            }
            level[i] = layerData;
        }
        
        // 重新初始化层
        this.runeGameLayers = new RuneGameLayer[level.length];
        for (int i = 0; i < level.length; i++) {
            this.runeGameLayers[i] = new RuneGameLayer(level[i], brands);
        }
        
        // 设置父子关系
        for (int i = 0; i < this.runeGameLayers.length; i++) {
            if (i != 0) {
                this.runeGameLayers[i].setParent(this.runeGameLayers[i - 1]);
            }
        }
        
        // 清除缓存
        this.intersectionCache.clear();
        
        return this;
    }

    public RuneGameLayer[] getLayers() {
        return this.runeGameLayers;
    }

    /**
     * 设置符文类型限制
     * @param limit 限制数量，最大为12
     */
    public void setRuneTypesLimit(int limit) {
        this.runeTypesLimit = Math.min(limit, 12);
    }

    /**
     * 获取符文类型限制
     * @return 符文类型数量限制
     */
    public int getRuneTypesLimit() {
        return this.runeTypesLimit;
    }

    /**
     * 设置是否为困难模式
     * @param hardMode 是否为困难模式
     */
    public void setHardMode(boolean hardMode) {
        this.isHardMode = hardMode;
    }

    /**
     * 判断是否为困难模式
     * @return 是否为困难模式
     */
    public boolean isHardMode() {
        return this.isHardMode;
    }

    public boolean check(RuneGameBrand brand, RuneGameLayer runeGameLayer) {
        // 检查缓存中是否已有结果
        if (intersectionCache.containsKey(brand)) {
            return intersectionCache.get(brand);
        }
        
        boolean hasIntersect = Arrays.stream(runeGameLayer.getBrands()).flatMap(Arrays::stream)
                .filter(Objects::nonNull).filter(brand1 -> brand1.hasBrand() != 0)
                .anyMatch(brand1 -> brand.getRectangle().intersects(brand1.getRectangle()));

        if (hasIntersect) {
            intersectionCache.put(brand, true);
            return true;
        }

        if (runeGameLayer.getParent() != null) {
            boolean result = check(brand, runeGameLayer.getParent());
            intersectionCache.put(brand, result);
            return result;
        } else {
            intersectionCache.put(brand, false);
            return false;
        }
    }

    public void checkAll() {
        // 清除缓存，确保重新计算
        intersectionCache.clear();
        
        for (RuneGameLayer runeGameLayer : this.runeGameLayers) {
            Arrays.stream(runeGameLayer.getBrands()).flatMap(Arrays::stream).filter(Objects::nonNull)
                    .filter(brand -> brand.hasBrand() != 0 && runeGameLayer.getParent() != null)
                    .forEach(brand -> brand.setGray(check(brand, runeGameLayer.getParent())));
            
            // 每处理完一层后清除缓存，确保不同层之间的检测不受影响
            intersectionCache.clear();
        }
    }
    
    // 添加方法来清除特定层的缓存，当层中的卡片被移除时使用
    public void clearCacheForLayer(RuneGameLayer layer) {
        Arrays.stream(layer.getBrands()).flatMap(Arrays::stream)
            .filter(Objects::nonNull)
            .forEach(intersectionCache::remove);
    }
}