package cn.teampancake.theaurorian.common.level.legacy.layer.traits;

import cn.teampancake.theaurorian.common.level.legacy.area.Area;
import cn.teampancake.theaurorian.common.level.legacy.context.BigContext;
import cn.teampancake.theaurorian.common.level.legacy.context.Context;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public interface CastleTransformer extends AreaTransformer1, DimensionOffset1Transformer {

	ResourceKey<Biome> apply(Context context, ResourceKey<Biome> up, ResourceKey<Biome> right, ResourceKey<Biome> down, ResourceKey<Biome> left, ResourceKey<Biome> center);

	@Override
	default ResourceKey<Biome> applyPixel(BigContext<?> context, Area layer, int x, int z) {
		return this.apply(context, layer.getBiome(this.getParentX(x + 1), this.getParentY(z)),
				layer.getBiome(this.getParentX(x + 2), this.getParentY(z + 1)),
				layer.getBiome(this.getParentX(x + 1), this.getParentY(z + 2)),
				layer.getBiome(this.getParentX(x), this.getParentY(z + 1)),
				layer.getBiome(this.getParentX(x + 1), this.getParentY(z + 1)));
	}

}