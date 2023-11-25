package cn.teampancake.theaurorian.common.level.legacy.layer;

import cn.teampancake.theaurorian.common.level.legacy.area.LazyArea;
import cn.teampancake.theaurorian.common.level.legacy.context.LazyAreaContext;

import java.util.function.LongFunction;

public interface BiomeLayerFactory {

    LazyArea build(LongFunction<LazyAreaContext> contextFactory);

    BiomeLayerType getType();

}