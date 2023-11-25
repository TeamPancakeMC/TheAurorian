package cn.teampancake.theaurorian.common.level.legacy.layer;

import com.mojang.serialization.Codec;

@FunctionalInterface
public interface BiomeLayerType {

    Codec<? extends BiomeLayerFactory> getCodec();

}