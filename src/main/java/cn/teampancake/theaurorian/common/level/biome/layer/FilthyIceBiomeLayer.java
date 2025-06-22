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
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.LongFunction;

public class FilthyIceBiomeLayer implements AreaTransformer1, DimensionOffset0Transformer {

    private static final int MIN_DISTANCE_FROM_SPAWN = 8000;

    private static final int SNOWFIELD_RADIUS = 4000;
    private static final int HILLS_RADIUS = 2500;
    private static final int MOUNTAIN_RADIUS = 1200;

    private static final int SNOWFIELD_HEIGHT = 70;
    private static final int HILLS_HEIGHT = 130;
    private static final int MOUNTAIN_HEIGHT = 210;

    private static final int BIOME_MOUNTAIN = 1;
    private static final int BIOME_HILLS = 2;
    private static final int BIOME_SNOWFIELD = 3;

    private static final int CENTER_X = 10000;
    private static final int CENTER_Z = 10000;

    @Override
    public ResourceKey<Biome> applyPixel(BigContext<?> context, Area parent, int x, int z) {
        ResourceKey<Biome> parentBiome = parent.getBiome(this.getParentX(x), this.getParentY(z));
        double distanceToCenter = Math.sqrt(Math.pow(x - CENTER_X, 2) + Math.pow(z - CENTER_Z, 2));
        if (distanceToCenter <= SNOWFIELD_RADIUS) {
            if (distanceToCenter <= MOUNTAIN_RADIUS) {
                return TABiomes.FILTHY_ICE_MOUNTAIN;
            } else if (distanceToCenter <= HILLS_RADIUS) {
                return TABiomes.FILTHY_ICE_HILLS;
            } else {
                return TABiomes.FILTHY_ICE_CRYSTAL_SNOWFIELD;
            }
        }
        
        return parentBiome;
    }
    
    public static final class Factory implements BiomeLayerFactory {
    
        public static final MapCodec<Factory> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Codec.LONG.fieldOf("salt").forGetter(Factory::salt),
                TABiomeLayerStack.HOLDER_CODEC.optionalFieldOf("parent").forGetter(factory -> Optional.ofNullable(factory.parent))
        ).apply(inst, Factory::new));
        
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