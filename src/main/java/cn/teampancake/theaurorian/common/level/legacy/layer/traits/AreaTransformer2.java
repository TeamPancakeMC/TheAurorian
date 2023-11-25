package cn.teampancake.theaurorian.common.level.legacy.layer.traits;

import cn.teampancake.theaurorian.common.level.legacy.area.Area;
import cn.teampancake.theaurorian.common.level.legacy.context.BigContext;
import cn.teampancake.theaurorian.common.level.legacy.context.Context;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public interface AreaTransformer2 extends DimensionTransformer {

	default <R extends Area> R run(BigContext<R> context, R area1, R area2) {
		return context.createResult((x, z) -> {
			context.initRandom(x, z);
			return this.applyPixel(context, area1, area2, x, z);
		}, area1, area2);
	}

	ResourceKey<Biome> applyPixel(Context context, Area layer1, Area layer2, int x, int z);

}