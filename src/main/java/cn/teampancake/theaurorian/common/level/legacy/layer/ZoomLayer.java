package cn.teampancake.theaurorian.common.level.legacy.layer;

import cn.teampancake.theaurorian.common.level.legacy.area.Area;
import cn.teampancake.theaurorian.common.level.legacy.area.LazyArea;
import cn.teampancake.theaurorian.common.level.legacy.context.BigContext;
import cn.teampancake.theaurorian.common.level.legacy.context.LazyAreaContext;
import cn.teampancake.theaurorian.common.level.legacy.layer.traits.AreaTransformer1;
import cn.teampancake.theaurorian.registry.TABiomeLayerStack;
import cn.teampancake.theaurorian.registry.TABiomeLayers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.function.LongFunction;

public enum ZoomLayer implements AreaTransformer1 {

	NORMAL,
	FUZZY {
		@Override
		protected ResourceKey<Biome> modeOrRandom(BigContext<?> context, ResourceKey<Biome> p_76980_, ResourceKey<Biome> p_76981_, ResourceKey<Biome> p_76982_, ResourceKey<Biome> p_76983_) {
			return context.random(p_76980_, p_76981_, p_76982_, p_76983_);
		}
	};

	@Override
    public int getParentX(int x) {
		return x >> 1;
	}

	@Override
	public int getParentY(int z) {
		return z >> 1;
	}

	@Override
	public ResourceKey<Biome> applyPixel(BigContext<?> context, Area layer, int x, int z) {
		ResourceKey<Biome> i = layer.getBiome(this.getParentX(x), this.getParentY(z));
		context.initRandom(x >> 1 << 1, z >> 1 << 1);
		int j = x & 1;
		int k = z & 1;
		if (j == 0 && k == 0) {
			return i;
		} else {
			ResourceKey<Biome> l = layer.getBiome(this.getParentX(x), this.getParentY(z + 1));
			ResourceKey<Biome> i1 = context.random(i, l);
			if (j == 0) {
				return i1;
			} else {
				ResourceKey<Biome> j1 = layer.getBiome(this.getParentX(x + 1), this.getParentY(z));
				ResourceKey<Biome> k1 = context.random(i, j1);
				if (k == 0) {
					return k1;
				} else {
					ResourceKey<Biome> l1 = layer.getBiome(this.getParentX(x + 1), this.getParentY(z + 1));
					return this.modeOrRandom(context, i, j1, l, l1);
				}
			}
		}
	}

	protected ResourceKey<Biome> modeOrRandom(BigContext<?> p_76960_, ResourceKey<Biome> p_76961_, ResourceKey<Biome> p_76962_, ResourceKey<Biome> p_76963_, ResourceKey<Biome> p_76964_) {
		if (p_76962_ == p_76963_ && p_76963_ == p_76964_) {
			return p_76962_;
		} else if (p_76961_ == p_76962_ && p_76961_ == p_76963_) {
			return p_76961_;
		} else if (p_76961_ == p_76962_ && p_76961_ == p_76964_) {
			return p_76961_;
		} else if (p_76961_ == p_76963_ && p_76961_ == p_76964_) {
			return p_76961_;
		} else if (p_76961_ == p_76962_ && p_76963_ != p_76964_) {
			return p_76961_;
		} else if (p_76961_ == p_76963_ && p_76962_ != p_76964_) {
			return p_76961_;
		} else if (p_76961_ == p_76964_ && p_76962_ != p_76963_) {
			return p_76961_;
		} else if (p_76962_ == p_76963_ && p_76961_ != p_76964_) {
			return p_76962_;
		} else if (p_76962_ == p_76964_ && p_76961_ != p_76963_) {
			return p_76962_;
		} else {
			return p_76963_ == p_76964_ && p_76961_ != p_76962_ ? p_76963_ : p_76960_.random(p_76961_, p_76962_, p_76963_, p_76964_);
		}
	}

	public record Factory(long salt, boolean fuzzy, Holder<BiomeLayerFactory> parent) implements BiomeLayerFactory {
		public static final Codec<Factory> CODEC = RecordCodecBuilder.create(inst -> inst.group(
				Codec.LONG.fieldOf("salt").forGetter(Factory::salt),
				Codec.BOOL.fieldOf("fuzzy").forGetter(Factory::fuzzy),
				TABiomeLayerStack.HOLDER_CODEC.fieldOf("parent").forGetter(Factory::parent)
		).apply(inst, Factory::new));

		@Override
		public LazyArea build(LongFunction<LazyAreaContext> contextFactory) {
			LazyAreaContext seededContext = contextFactory.apply(this.salt);
			LazyArea parentLayer = this.parent.get().build(contextFactory);
			if (this.fuzzy) {
				return FUZZY.run(seededContext, parentLayer);
			} else {
				return NORMAL.run(seededContext, parentLayer);
			}
		}

		@Override
		public @NotNull BiomeLayerType getType() {
			return TABiomeLayers.ZOOM.get();
		}

	}

}