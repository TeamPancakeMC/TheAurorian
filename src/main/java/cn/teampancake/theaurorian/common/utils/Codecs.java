package cn.teampancake.theaurorian.common.utils;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.floats.Float2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectSortedMap;
import net.minecraft.Util;

import java.util.List;

@SuppressWarnings("deprecation")
public final class Codecs {

    public static <T> Codec<Float2ObjectSortedMap<T>> floatTreeCodec(Codec<T> elementCodec) {
        return Codec.compoundList(Codec.STRING.comapFlatMap(Codecs::parseString2Float, f -> Float.toString(f)), elementCodec)
                .xmap(floatEList -> floatEList.stream().collect(Float2ObjectAVLTreeMap::new,
                (map, pair) -> map.put(pair.getFirst(), pair.getSecond()), Float2ObjectAVLTreeMap::putAll),
                map -> map.entrySet().stream().map(entry -> new Pair<>(entry.getKey(), entry.getValue())).toList());
    }

    private static DataResult<Float> parseString2Float(String string) {
        try {
            return DataResult.success(Float.valueOf(string));
        } catch (Throwable e) {
            return DataResult.error(e::getMessage);
        }
    }

    public static <E> DataResult<Pair<E, E>> arrayToPair(List<E> list) {
        return Util.fixedSize(list, 2).map(l -> Pair.of(l.get(0), l.get(1)));
    }

}