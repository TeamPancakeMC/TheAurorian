package cn.teampancake.theaurorian.common.level.legacy.area;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public interface Area {

	ResourceKey<Biome> getBiome(int x, int z);

}