package cn.teampancake.theaurorian.common.level.legacy.layer;

import com.mojang.serialization.MapCodec;

@FunctionalInterface
public interface BiomeLayerType {

    MapCodec<? extends BiomeLayerFactory> getCodec();

}