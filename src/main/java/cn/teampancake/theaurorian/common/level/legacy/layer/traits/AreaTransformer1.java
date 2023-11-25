package cn.teampancake.theaurorian.common.level.legacy.layer.traits;

import cn.teampancake.theaurorian.common.level.legacy.area.Area;
import cn.teampancake.theaurorian.common.level.legacy.context.BigContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public interface AreaTransformer1 extends DimensionTransformer {

	default <R extends Area> R run(BigContext<R> context, R area) {
		return context.createResult((x, z) -> {
			context.initRandom(x, z);
			return this.applyPixel(context, area, x, z);
		}, area);
	}

	ResourceKey<Biome> applyPixel(BigContext<?> context, Area layer, int x, int z);

}