package cn.teampancake.theaurorian.common.level.biome.layer;

import cn.teampancake.theaurorian.common.level.legacy.area.Area;
import cn.teampancake.theaurorian.common.level.legacy.area.LazyArea;
import cn.teampancake.theaurorian.common.level.legacy.context.BigContext;
import cn.teampancake.theaurorian.common.level.legacy.context.LazyAreaContext;
import cn.teampancake.theaurorian.common.level.legacy.layer.BiomeLayerFactory;
import cn.teampancake.theaurorian.common.level.legacy.layer.BiomeLayerType;
import cn.teampancake.theaurorian.common.level.legacy.layer.traits.AreaTransformer1;
import cn.teampancake.theaurorian.common.level.legacy.layer.traits.DimensionOffset0Transformer;
import cn.teampancake.theaurorian.common.registry.TABiomeLayers;
import cn.teampancake.theaurorian.common.registry.TABiomeLayerStack;
import cn.teampancake.theaurorian.common.registry.TABiomes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.LongFunction;

/**
 * 黯晶群系生成层
 * 负责将黯晶雪原、黯晶丘陵和黯晶雪山整合为一个统一的金字塔结构，
 * 并创建超大范围的平滑过渡区域，确保雪山生成在丘陵上，丘陵生成在雪原上
 */
public class FilthyIceBiomeLayer implements AreaTransformer1, DimensionOffset0Transformer {

    // 黯晶区域中心点与出生点的最小距离（方块）
    private static final int MIN_DISTANCE_FROM_SPAWN = 8000;
    
    // 黯晶区域大小定义
    private static final int SNOWFIELD_RADIUS = 4000; // 雪原半径
    private static final int HILLS_RADIUS = 2500;     // 丘陵半径
    private static final int MOUNTAIN_RADIUS = 1200;  // 雪山半径
    
    // 高度定义
    private static final int SNOWFIELD_HEIGHT = 70;   // 雪原高度
    private static final int HILLS_HEIGHT = 130;      // 丘陵高度
    private static final int MOUNTAIN_HEIGHT = 210;   // 雪山高度
    
    // 生物群系类型标识
    private static final int BIOME_MOUNTAIN = 1;
    private static final int BIOME_HILLS = 2;
    private static final int BIOME_SNOWFIELD = 3;
    
    // 黯晶区域中心点坐标
    private static final int CENTER_X = 10000; // 固定中心点X坐标
    private static final int CENTER_Z = 10000; // 固定中心点Z坐标

    @Override
    public ResourceKey<Biome> applyPixel(BigContext<?> context, Area parent, int x, int z) {
        ResourceKey<Biome> parentBiome = parent.getBiome(this.getParentX(x), this.getParentY(z));
        
        // 计算到黯晶区域中心点的距离
        double distanceToCenter = Math.sqrt(Math.pow(x - CENTER_X, 2) + Math.pow(z - CENTER_Z, 2));
        
        // 判断是否在黯晶区域内
        if (distanceToCenter <= SNOWFIELD_RADIUS) {
            // 在雪山半径内
            if (distanceToCenter <= MOUNTAIN_RADIUS) {
                return TABiomes.FILTHY_ICE_MOUNTAIN;
            }
            // 在丘陵半径内
            else if (distanceToCenter <= HILLS_RADIUS) {
                return TABiomes.FILTHY_ICE_HILLS;
            }
            // 在雪原半径内
            else {
                return TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD;
            }
        }
        
        return parentBiome; // 非黯晶区域保持不变
    }
    
    public static final class Factory implements BiomeLayerFactory {
    
        public static final MapCodec<Factory> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Codec.LONG.fieldOf("salt").forGetter(Factory::salt),
                TABiomeLayerStack.HOLDER_CODEC.optionalFieldOf("parent").forGetter(factory -> Optional.ofNullable(factory.parent))
        ).apply(inst, (salt, parent) -> new Factory(salt, parent)));
        
        private final long salt;
        private final Holder<BiomeLayerFactory> parent;
        private final FilthyIceBiomeLayer instance;
        
        public Factory(long salt, Optional<Holder<BiomeLayerFactory>> parent) {
            this.salt = salt;
            this.parent = parent.orElse(null);
            this.instance = new FilthyIceBiomeLayer();
        }
        
        @Override
        public LazyArea build(@NotNull LongFunction<LazyAreaContext> contextFactory) {
            if (this.parent != null) {
                return this.instance.run(contextFactory.apply(this.salt), this.parent.value().build(contextFactory));
            } else {
                // 创建一个简单的LazyArea，默认返回雪原生物群系
                LazyAreaContext context = contextFactory.apply(this.salt);
                return context.createResult((x, z) -> TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD);
            }
        }
        
        @Override
        public BiomeLayerType getType() {
            return TABiomeLayers.FILTHY_ICE.get();
        }
        
        public long salt() {
            return this.salt;
        }
    }
} 