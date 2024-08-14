package cn.teampancake.theaurorian.common.level.legacy.layer.traits;

import cn.teampancake.theaurorian.common.level.legacy.area.Area;
import cn.teampancake.theaurorian.common.level.legacy.context.BigContext;
import cn.teampancake.theaurorian.common.level.legacy.context.Context;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public interface AreaTransformer0 {

	default <R extends Area> R run(BigContext<R> context) {
		return context.createResult((x, z) -> {
			context.initRandom(x, z);
			return this.applyPixel(context, x, z);
		});
	}

	ResourceKey<Biome> applyPixel(Context context, int x, int z);

}